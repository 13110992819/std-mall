package com.xnjr.mall.ao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xnjr.mall.ao.IStorePurchaseAO;
import com.xnjr.mall.bo.IAccountBO;
import com.xnjr.mall.bo.IDistributeBO;
import com.xnjr.mall.bo.ISYSConfigBO;
import com.xnjr.mall.bo.IStoreBO;
import com.xnjr.mall.bo.IStorePurchaseBO;
import com.xnjr.mall.bo.IStoreTicketBO;
import com.xnjr.mall.bo.IUserBO;
import com.xnjr.mall.bo.IUserTicketBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.common.AmountUtil;
import com.xnjr.mall.common.SysConstants;
import com.xnjr.mall.core.OrderNoGenerater;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Account;
import com.xnjr.mall.domain.SYSConfig;
import com.xnjr.mall.domain.Store;
import com.xnjr.mall.domain.StorePurchase;
import com.xnjr.mall.domain.StoreTicket;
import com.xnjr.mall.domain.User;
import com.xnjr.mall.domain.UserTicket;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.enums.EBizType;
import com.xnjr.mall.enums.ECurrency;
import com.xnjr.mall.enums.EGeneratePrefix;
import com.xnjr.mall.enums.EO2OPayType;
import com.xnjr.mall.enums.EPayType;
import com.xnjr.mall.enums.EStoreLevel;
import com.xnjr.mall.enums.EStorePurchaseStatus;
import com.xnjr.mall.enums.EStoreStatus;
import com.xnjr.mall.enums.EStoreTicketType;
import com.xnjr.mall.enums.ESysUser;
import com.xnjr.mall.enums.EUserTicketStatus;
import com.xnjr.mall.exception.BizException;

@Service
public class StorePurchaseAOImpl implements IStorePurchaseAO {
    static Logger logger = Logger.getLogger(StorePurchaseAOImpl.class);

    @Autowired
    private IStorePurchaseBO storePurchaseBO;

    @Autowired
    private IStoreBO storeBO;

    @Autowired
    private IDistributeBO distributeBO;

    @Autowired
    private IStoreTicketBO storeTicketBO;

    @Autowired
    private IUserTicketBO userTicketBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IAccountBO accountBO;

    @Override
    @Transactional
    public Object storePurchaseYC(String userId, String storeCode, Long amount,
            String payType) {
        User user = userBO.getRemoteUser(userId);
        Store store = storeBO.getStore(storeCode);
        if (!EStoreStatus.ON_OPEN.getCode().equals(store.getStatus())) {
            throw new BizException("xn0000", "店铺不处于可消费状态");
        }
        // 1-人民币余额支付 5-微信H5支付 50-橙券余额支付
        if (EO2OPayType.YC_CB.getCode().equals(payType)) {
            return storePurchaseYCCB(user, store, amount);
        } else if (EO2OPayType.ZH_YE.getCode().equals(payType)) {
            return storePurchaseYCRMBYE(user, store, amount);
        } else if (EO2OPayType.WEIXIN_H5.getCode().equals(payType)) {
            return storePurchaseYCWXH5(user, store, amount);
        } else {
            throw new BizException("xn0000", "暂不支持此支付方式");
        }
    }

    private Object storePurchaseYCWXH5(User user, Store store, Long amount) {
        // 落地本地系统消费记录
        Long fxCbAmount = AmountUtil.mul(amount, store.getRate2());
        String payGroup = OrderNoGenerater.generateM(EGeneratePrefix.PAY_GROUP
            .getCode());
        String code = storePurchaseBO.storePurchaseYCRMBWXH5(user, store,
            amount, fxCbAmount, payGroup);
        String systemUser = ESysUser.SYS_USER_YAOCHENG.getCode();
        return accountBO.doWeiXinH5PayRemote(user.getUserId(),
            user.getOpenId(), systemUser, payGroup, code, EBizType.YC_O2O_RMB,
            "O2O消费微信支付", amount);
    }

    private Object storePurchaseYCRMBYE(User user, Store store,
            Long rmbTotalAmount) {
        // 返给C端多少钱 = 支付总金额 * 商家返橙券比例
        Long fxCbAmount = AmountUtil.mul(rmbTotalAmount, store.getRate2());
        // 付给商家多少钱 = 支付总金额 - 返点给C端用户的橙券
        Long payStoreRmbAmount = rmbTotalAmount - fxCbAmount;
        // 落地本地系统消费记录
        String code = storePurchaseBO.storePurchaseYCRMBYE(user, store,
            rmbTotalAmount, rmbTotalAmount, fxCbAmount);
        // 资金划转开始--------------
        String systemUser = ESysUser.SYS_USER_YAOCHENG.getCode();
        // 1、用户人民币给平台人民币
        accountBO.doTransferAmountRemote(user.getUserId(), systemUser,
            ECurrency.CNY, rmbTotalAmount, EBizType.YC_O2O_RMB, "O2O消费人民币支付",
            "O2O消费人民币支付", code);
        // 2、平台给商家一定比例人民币
        accountBO.doTransferAmountRemote(systemUser, store.getOwner(),
            ECurrency.CNY, payStoreRmbAmount, EBizType.YC_O2O_RMB,
            "O2O消费人民币支付", "O2O消费人民币支付", code);
        // 3、平台返现等比例橙券(回收人民币)
        accountBO.doTransferAmountRemote(systemUser, user.getUserId(),
            ECurrency.YC_CB, fxCbAmount, EBizType.YC_O2O_RMBFD, "O2O消费人民币支付返点",
            "O2O消费人民币支付返点", code);
        // 资金划转结束--------------
        return code;
    }

    public Object storePurchaseYCCB(User user, Store store, Long amount) {
        Account cbAccount = accountBO.getRemoteAccount(user.getUserId(),
            ECurrency.YC_CB);
        if (cbAccount.getAmount() < amount) {
            throw new BizException("xn0000", "橙券账户余额不足");
        }
        // 计算返点人民币
        Long fdAmount = AmountUtil.mul(amount, store.getRate1());
        // 落地本地系统消费记录
        String code = storePurchaseBO
            .storePurchaseYCCB(user, store, amount, 0L);
        // 资金划转开始--------------
        // 橙券从消费者回收至平台
        String systemUser = ESysUser.SYS_USER_YAOCHENG.getCode();
        accountBO.doTransferAmountRemote(user.getUserId(), systemUser,
            ECurrency.YC_CB, amount, EBizType.YC_O2O_CB,
            "O2O消费—" + store.getName(), "O2O消费回收橙券", code);
        // 商家从平台处拿到返点
        accountBO.doTransferAmountRemote(systemUser, store.getOwner(),
            ECurrency.CNY, fdAmount, EBizType.YC_O2O_CBFD,
            EBizType.YC_O2O_CBFD.getValue(), EBizType.YC_O2O_CBFD.getValue(),
            code);
        // 加盟商从平台拿到返点
        User storeUser = userBO.getRemoteUser(store.getOwner());
        User jmsUser = userBO.getRemoteUser(storeUser.getUserReferee());
        Long jmsFdAmount = AmountUtil.mul(amount, jmsUser.getDivRate());
        accountBO.doTransferAmountRemote(systemUser, jmsUser.getUserId(),
            ECurrency.CNY, jmsFdAmount, EBizType.YC_O2O_CBFD,
            "O2O消费支付加盟商返点人民币", "O2O消费收到返点人民币", code);
        // 资金划转结束--------------
        return code;
    }

    @Override
    @Transactional
    public String storePurchaseCGB(String userId, String storeCode,
            Long cgbTotalAmount, String payType) {
        User user = userBO.getRemoteUser(userId);
        Store store = storeBO.getStore(storeCode);
        if (!EStoreStatus.ON_OPEN.getCode().equals(store.getStatus())) {
            throw new BizException("xn0000", "店铺不处于可消费状态");
        }
        Account cgbAccount = accountBO.getRemoteAccount(user.getUserId(),
            ECurrency.CG_CGB);
        if (cgbAccount.getAmount() < cgbTotalAmount) {
            throw new BizException("xn0000", "菜狗币账户余额不足");
        }
        // 菜狗币消费：
        // 1、落地消费记录，支付消费者菜狗币
        // 2、返现规则：
        // 1)消费者返现人民币=cgbTotalAmount * xffxRate(全局)；
        // 2)商家返现人民币=cgbTotalAmount * store.rate3(商家特有)
        // 3)商家对应加盟商分成返现人民币=cgbTotalAmount * user.getDivRate(加盟商分成比例)
        // 计算返点人民币
        Long fdAmount = AmountUtil.mul(cgbTotalAmount, store.getRate3());
        // 落地本地系统消费记录
        String code = storePurchaseBO.storePurchaseCGcgb(user, store,
            cgbTotalAmount, fdAmount);
        // 资金划转开始--------------
        // 菜狗币从消费者回收至平台
        String systemUser = ESysUser.SYS_USER_CAIGO.getCode();
        accountBO.doTransferAmountRemote(user.getUserId(), systemUser,
            ECurrency.CG_CGB, cgbTotalAmount, EBizType.CG_O2O_CGB,
            "O2O消费使用菜狗币", "O2O消费回收菜狗币", code);

        // 消费者从平台拿到返点
        SYSConfig config = sysConfigBO.getSYSConfig(SysConstants.STORE_XFFX,
            store.getCompanyCode(), store.getSystemCode());
        Long xffxAmount = AmountUtil.mul(cgbTotalAmount,
            Double.valueOf(config.getCvalue()));
        accountBO.doTransferAmountRemote(systemUser, user.getUserId(),
            ECurrency.CNY, xffxAmount, EBizType.CG_O2O_CGBFD,
            "O2O消费支付消费者返现人民币", "O2O消费收到返现人民币", code);

        // 商家从平台处拿到返点
        accountBO.doTransferAmountRemote(systemUser, store.getOwner(),
            ECurrency.CNY, fdAmount, EBizType.CG_O2O_CGBFD, "O2O消费支付返点人民币",
            "O2O消费收到返点人民币", code);

        // 加盟商从平台拿到返点
        User storeUser = userBO.getRemoteUser(store.getOwner());
        User jmsUser = userBO.getRemoteUser(storeUser.getUserReferee());
        Long jmsFdAmount = AmountUtil.mul(cgbTotalAmount, jmsUser.getDivRate());
        accountBO.doTransferAmountRemote(systemUser, jmsUser.getUserId(),
            ECurrency.CNY, jmsFdAmount, EBizType.CG_O2O_CGBFD,
            "O2O消费支付加盟商返点人民币", "O2O消费收到返点人民币", code);
        // 资金划转结束--------------
        return code;
    }

    /**
     * 支付金额：存人民币支付，返菜狗币
     */
    @Override
    @Transactional
    public Object storePurchaseRMB(String userId, String storeCode,
            Long rmbTotalAmount, String payType) {
        User user = userBO.getRemoteUser(userId);
        Store store = storeBO.getStore(storeCode);
        if (!EStoreStatus.ON_OPEN.getCode().equals(store.getStatus())) {
            throw new BizException("xn0000", "店铺不处于可消费状态");
        }
        if (EPayType.CG_YE.getCode().equals(payType)) {
            return storePurchaseCGRMBYE(user, store, rmbTotalAmount);
        }
        if (EPayType.WEIXIN_H5.getCode().equals(payType)) {
            return storePurchaseCGRMBWX(user, store, rmbTotalAmount);
        } else {
            throw new BizException("xn0000", "支付方式不存在");
        }
    }

    /**
     * 支付金额：等比例支付人民币和积分，例如100人民币，使用积分比例25%，人民币需支付75元，积分支付25
     */
    @Override
    @Transactional
    public Object storePurchaseRMBJF(String userId, String storeCode,
            Long rmbTotalAmount, String payType) {
        User user = userBO.getRemoteUser(userId);
        Store store = storeBO.getStore(storeCode);
        if (!EStoreStatus.ON_OPEN.getCode().equals(store.getStatus())) {
            throw new BizException("xn0000", "店铺不处于可消费状态");
        }
        if (EPayType.CG_YE.getCode().equals(payType)) {
            return storePurchaseCGRMBJFYE(user, store, rmbTotalAmount);
        }
        if (EPayType.WEIXIN_H5.getCode().equals(payType)) {
            return storePurchaseCGRMBJFWX(user, store, rmbTotalAmount);
        } else {
            throw new BizException("xn0000", "支付方式不存在");
        }
    }

    @Override
    public Object storePurchaseGD(String userId, String storeCode, Long amount,
            String payType) {
        User user = userBO.getRemoteUser(userId);
        Store store = storeBO.getStore(storeCode);
        if (amount <= 0) {
            throw new BizException("xn0000", "消费金额必须大于0");
        }
        if (!EStoreStatus.ON_OPEN.getCode().equals(store.getStatus())) {
            throw new BizException("xn0000", "店铺不处于可消费状态");
        }
        if (EPayType.GD_YE.getCode().equals(payType)) {
            return storePurchaseGDYE(user, store, amount);
        } else {
            throw new BizException("xn0000", "支付方式不存在");
        }
    }

    @Transactional
    public Object storePurchaseGDYE(User user, Store store, Long amount) {
        SYSConfig config = sysConfigBO.getSYSConfig(SysConstants.STORE_RMB2JF,
            store.getCompanyCode(), store.getSystemCode());
        Long payJF = 0L;// 需要支付的积分金额
        try {
            payJF = Double.valueOf(
                amount * StringValidater.toDouble(config.getCvalue()))
                .longValue();
        } catch (Exception e) {
            logger.error("人民币换算积分");
        }
        Account jfAccount = accountBO.getRemoteAccount(user.getUserId(),
            ECurrency.JF);// 积分账户
        if (jfAccount.getAmount() < payJF) {
            throw new BizException("xn0000", "积分账户余额不足");
        }
        // 落地本地系统消费记录
        String code = storePurchaseBO.storePurchaseGDYE(user, store, amount,
            payJF);
        // 资金划转开始--------------
        // 积分从消费者回收至平台，
        String systemUser = ESysUser.SYS_USER_PIPE.getCode();
        accountBO.doTransferAmountRemote(user.getUserId(), systemUser,
            ECurrency.JF, payJF, EBizType.GD_O2O, "O2O消费积分回收", "O2O消费积分回收",
            code);
        // 资金划转结束--------------
        return new BooleanRes(true);
    }

    private Object storePurchaseCGRMBYE(User user, Store store,
            Long rmbTotalAmount) {
        // 落地本地系统消费记录
        Long payStoreRmbAmount = AmountUtil.mul(rmbTotalAmount,
            1 - store.getRate1());
        Long fxCgbAmount = rmbTotalAmount - payStoreRmbAmount;
        String code = storePurchaseBO.storePurchaseCGRMB(user, store,
            rmbTotalAmount, rmbTotalAmount, fxCgbAmount);
        // 资金划转开始--------------
        String systemUser = ESysUser.SYS_USER_CAIGO.getCode();
        // 1、用户人民币给平台人民币
        accountBO.doTransferAmountRemote(user.getUserId(), systemUser,
            ECurrency.CNY, rmbTotalAmount, EBizType.CG_O2O_RMB, "O2O消费人民币支付",
            "O2O消费人民币支付", code);
        // 2、平台给商家一定比例人民币
        accountBO.doTransferAmountRemote(systemUser, store.getOwner(),
            ECurrency.CNY, payStoreRmbAmount, EBizType.CG_O2O_RMB,
            "O2O消费人民币支付", "O2O消费人民币支付", code);
        // 3、平台返现等比例菜狗币(回收人民币)
        accountBO.doTransferAmountRemote(systemUser, user.getUserId(),
            ECurrency.CG_CGB, fxCgbAmount, EBizType.CG_O2O_RMBFD,
            "O2O消费人民币支付返点", "O2O消费人民币支付返点", code);
        // 资金划转结束--------------
        return code;
    }

    private Object storePurchaseCGRMBJFYE(User user, Store store,
            Long rmbTotalAmount) {
        Long payRmbAmount = AmountUtil.mulRmbJinFen(rmbTotalAmount,
            1 - store.getRate2());// 需要支付的RMB金额
        Long payJfAmount = rmbTotalAmount - payRmbAmount;// 需要支付的积分金额

        // 落地本地系统消费记录
        String code = storePurchaseBO.storePurchaseCGRMBJF(user, store,
            rmbTotalAmount, payRmbAmount, payJfAmount);
        // 资金划转开始--------------
        // 积分从消费者回收至平台，
        // 验证余额是否足够
        accountBO.checkRmbJf(user.getUserId(), payRmbAmount, payJfAmount);
        // 人民币给商家（人民币）
        accountBO.doTransferAmountRemote(user.getUserId(), store.getOwner(),
            ECurrency.CNY, payRmbAmount, EBizType.CG_O2O_RMB, "O2O消费人民币支付",
            "O2O消费人民币支付", code);
        String systemUser = ESysUser.SYS_USER_CAIGO.getCode();
        accountBO.doTransferAmountRemote(user.getUserId(), systemUser,
            ECurrency.CGJF, payJfAmount, EBizType.CG_O2O_CGJF, "O2O消费积分支付",
            "O2O消费积分支付", code);
        // 资金划转结束--------------
        return code;
    }

    private Object storePurchaseCGRMBWX(User user, Store store,
            Long rmbTotalAmount) {
        // 落地本地系统消费记录
        Long payStoreRmbAmount = AmountUtil.mul(rmbTotalAmount,
            1 - store.getRate1());
        Long fxCgbAmount = rmbTotalAmount - payStoreRmbAmount;
        String payGroup = OrderNoGenerater.generateM(EGeneratePrefix.PAY_GROUP
            .getCode());
        String code = storePurchaseBO.storePurchaseCGRMBWX(user, store,
            rmbTotalAmount, fxCgbAmount, payGroup);
        // 资金划转开始--------------
        String systemUser = ESysUser.SYS_USER_CAIGO.getCode();
        // RMB调用微信渠道至平台
        return accountBO.doWeiXinH5PayRemote(user.getUserId(),
            user.getOpenId(), systemUser, payGroup, code, EBizType.CG_O2O_RMB,
            "O2O消费微信支付", rmbTotalAmount);
        // 资金划转结束--------------
    }

    private Object storePurchaseCGRMBJFWX(User user, Store store,
            Long rmbTotalAmount) {
        Long payRmbAmount = AmountUtil.mulRmbJinFen(rmbTotalAmount,
            1 - store.getRate2());// 需要支付的RMB金额
        Long payJfAmount = rmbTotalAmount - payRmbAmount;// 需要支付的积分金额
        String payGroup = OrderNoGenerater.generateM(EGeneratePrefix.PAY_GROUP
            .getCode());
        String code = storePurchaseBO.storePurchaseCGRMBJFWX(user, store,
            rmbTotalAmount, payJfAmount, payGroup);
        // 资金划转开始--------------
        // 验证积分是否足够
        Account jfAccount = accountBO.getRemoteAccount(user.getUserId(),
            ECurrency.CGJF);
        if (payJfAmount > jfAccount.getAmount()) {
            throw new BizException("xn0000", "积分不足");
        }
        // RMB调用微信渠道至商家
        return accountBO.doWeiXinH5PayRemote(user.getUserId(),
            user.getOpenId(), store.getOwner(), payGroup, code,
            EBizType.CG_O2O_RMB, "O2O消费微信支付", payRmbAmount);
        // 资金划转结束--------------
    }

    private Object storePurchaseZHWX(User user, Store store, Long amount,
            String ticketCode) {
        // 验证支付渠道是否开通
        sysConfigBO.doCheckPayOpen(EPayType.WEIXIN_APP);
        // 落地本地系统消费记录
        String payGroup = OrderNoGenerater.generateM(EGeneratePrefix.PAY_GROUP
            .getCode());
        String code = storePurchaseBO.storePurchaseZHWX(user, store, amount,
            ticketCode, payGroup);

        // 资金划转开始--------------
        // RMB调用微信渠道至商家
        return accountBO.doWeiXinPayRemote(user.getUserId(), store.getOwner(),
            payGroup, code, EBizType.ZH_O2O, "O2O消费微信支付", amount);
        // 资金划转结束--------------
    }

    private Object storePurchaseZHZFB(User user, Store store, Long amount,
            String ticketCode) {
        // 验证支付渠道是否开通
        sysConfigBO.doCheckPayOpen(EPayType.ALIPAY);
        // 落地本地系统消费记录
        String payGroup = OrderNoGenerater.generateM(EGeneratePrefix.PAY_GROUP
            .getCode());
        String code = storePurchaseBO.storePurchaseZHZFB(user, store, amount,
            ticketCode, payGroup);

        // 资金划转开始--------------
        // RMB调用支付宝渠道至商家
        return accountBO.doAlipayRemote(user.getUserId(), store.getOwner(),
            payGroup, code, EBizType.ZH_O2O, "O2O消费支付宝支付", amount);
        // 资金划转结束--------------
    }

    @Override
    @Transactional
    public Object storePurchaseZH(String userId, String storeCode, Long amount,
            String payType, String ticketCode, String tradePwd) {
        Store store = storeBO.getStore(storeCode);
        if (!EStoreStatus.ON_OPEN.getCode().equals(store.getStatus())) {
            throw new BizException("xn0000", "店铺不处于可消费状态");
        }
        User user = userBO.getRemoteUser(userId);
        // 折扣券可扣减优惠金额
        Long ticketAmount = getTicketAmount(ticketCode, amount);
        if (ticketAmount > 0) {
            amount = amount - ticketAmount;
        }
        if (EO2OPayType.ZH_YE.getCode().equals(payType)) {
            return storePurchaseZHYE(user, store, amount, ticketCode, tradePwd);
        } else if (EO2OPayType.ALIPAY.getCode().equals(payType)) {
            return storePurchaseZHZFB(user, store, amount, ticketCode);
        } else if (EO2OPayType.WEIXIN_APP.getCode().equals(payType)) {
            return storePurchaseZHWX(user, store, amount, ticketCode);
        } else {
            throw new BizException("xn0000", payType + "支付方式暂不支持");
        }
    }

    private Long getTicketAmount(String ticketCode, Long amount) {
        Long ticketAmount = 0L; // 扣除折扣券优惠
        if (StringUtils.isNotBlank(ticketCode)) {// 使用折扣券
            UserTicket userTicket = userTicketBO.getUserTicket(ticketCode);
            if (!EUserTicketStatus.UNUSED.getCode().equals(
                userTicket.getStatus())) {
                throw new BizException("xn0000", "该折扣券不可用");
            }
            StoreTicket storeTicket = storeTicketBO.getStoreTicket(userTicket
                .getTicketCode());
            if (storeTicket.getValidateStart().after(new Date())) {
                throw new BizException("xn0000", "该折扣券还未生效，请选择其他折扣券");
            }
            if (EStoreTicketType.MANJIAN.getCode()
                .equals(storeTicket.getType())) {
                if (amount < storeTicket.getKey1()) {
                    // throw new BizException("xn0000", "消费金额还未达到折扣券满减金额，无法使用");
                } else {
                    // 扣减消费金额
                    ticketAmount = storeTicket.getKey2();
                }
            }
        }
        return ticketAmount;
    }

    private String storePurchaseZHYE(User user, Store store, Long amount,
            String ticketCode, String tradePwd) {
        // 验证交易密码
        userBO.checkTradePwd(user.getUserId(), tradePwd);

        // 优惠券状态修改
        userTicketBO.ticketUsed(ticketCode);
        Long frResultAmount = 0L;// 需要支付的分润金额
        Long gxjlResultAmount = 0L;// 需要支付的贡献值金额计算
        String buyUserId = user.getUserId();
        String storeUserId = store.getOwner();
        // 1、贡献奖励+分润<yhAmount 余额不足
        Account gxjlAccount = accountBO.getRemoteAccount(buyUserId,
            ECurrency.ZH_GXZ);
        Long gxjlAmount = gxjlAccount.getAmount();
        Account frAccount = accountBO.getRemoteAccount(buyUserId,
            ECurrency.ZH_FRB);
        Long frAmount = frAccount.getAmount();
        Double gxjl2cnyRate = accountBO.getExchangeRateRemote(ECurrency.ZH_GXZ);
        Double fr2cnyRate = accountBO.getExchangeRateRemote(ECurrency.ZH_FRB);
        if (gxjlAmount / gxjl2cnyRate + frAmount / fr2cnyRate < amount) {
            throw new BizException("xn0000", "余额不足");
        }
        // 2、计算frResultAmount和gxjlResultAmount
        if (gxjlAmount <= 0L) {
            frResultAmount = Double.valueOf(amount * fr2cnyRate).longValue();
            gxjlResultAmount = 0L;
        } else if (gxjlAmount > 0L && gxjlAmount / gxjl2cnyRate < amount) {// 0<贡献奖励<yhAmount
            frResultAmount = Double
                .valueOf(
                    (amount - Double.valueOf(gxjlAmount / gxjl2cnyRate)
                        .longValue()) * fr2cnyRate).longValue();
            gxjlResultAmount = gxjlAmount;
        } else if (gxjlAccount.getAmount() / gxjl2cnyRate >= amount) { // 4、贡献奖励>=yhAmount
            frResultAmount = 0L;
            gxjlResultAmount = Double.valueOf(amount * gxjl2cnyRate)
                .longValue();
        }
        // 落地本地系统消费记录
        String code = storePurchaseBO.storePurchaseZHYE(user, store,
            ticketCode, amount, frResultAmount, gxjlResultAmount);
        // ---资金划拨开始-----
        // 会员扣分润和贡献值，商家收分润，先全额收掉。
        String systemUser = ESysUser.SYS_USER_ZHPAY.getCode();
        if (gxjlResultAmount > 0L) {// 贡献值是给平台的，贡献值等值的(1:1)分润有平台给商家
            accountBO.doTransferAmountRemote(buyUserId, systemUser,
                ECurrency.ZH_GXZ, gxjlResultAmount, EBizType.ZH_O2O,
                "正汇O2O贡献值消费", "正汇O2O消费者的贡献值回收", code);
            accountBO.doTransferAmountRemote(systemUser, storeUserId,
                ECurrency.ZH_FRB, gxjlResultAmount, EBizType.ZH_O2O,
                "正汇O2O平台返现分润", "正汇O2O平台返现分润", code);
        }
        if (frResultAmount > 0L) {
            accountBO.doTransferAmountRemote(buyUserId, storeUserId,
                ECurrency.ZH_FRB, frResultAmount, EBizType.ZH_O2O,
                "正汇O2O消费者的分润支付", "正汇O2O消费者的分润支付", code);
        }
        // 用商家的钱开始分销
        if (StringUtils.isNotBlank(ticketCode)) {
            distributeBO.distribute1Amount(amount, store, user, code);
        } else {
            if (EStoreLevel.NOMAL.getCode().equals(store.getLevel())) {
                distributeBO.distribute10Amount(amount, store, user, code);
            }
            if (EStoreLevel.FINANCIAL.getCode().equals(store.getLevel())) {
                distributeBO.distribute25Amount(amount, frResultAmount, store,
                    user, code);
            }
        }
        return code;
    }

    @Override
    @Transactional
    public void paySuccessZH(String payGroup, String payCode, Long payAmount) {
        StorePurchase storePurchase = storePurchaseBO
            .getStorePurchaseByPayGroup(payGroup);
        if (EStorePurchaseStatus.TO_PAY.getCode().equals(
            storePurchase.getStatus())) {
            // 更新支付记录
            storePurchaseBO.paySuccess(storePurchase, payCode, payAmount);

            // 优惠券状态修改
            String ticketCode = storePurchase.getTicketCode();
            UserTicket userTicket = userTicketBO.getUserTicket(ticketCode);
            if (null != userTicket
                    && !EUserTicketStatus.UNUSED.getCode().equals(
                        userTicket.getStatus())) {
                throw new BizException("xn000000", "该折扣券["
                        + userTicket.getCode() + "]不是未使用状态，无法支付");
            } else {
                userTicketBO.ticketUsed(ticketCode);
            }

            // 将商家人民币金额划转成分润币种
            Store store = storeBO.getStore(storePurchase.getStoreCode());
            accountBO.doTransferAmountRemote(store.getOwner(), ECurrency.CNY,
                store.getOwner(), ECurrency.ZH_FRB, payAmount,
                EBizType.EXCHANGE_CURRENCY, "O2O消费人民币转分润", "O2O消费加分润",
                storePurchase.getCode());
            // 用商家的钱开始分销
            Long storeFrAmount = storePurchase.getPrice();// 商家收到的分润
            Long userFrAmount = storeFrAmount;// 用户支付的分润
            User user = userBO.getRemoteUser(storePurchase.getUserId());
            if (StringUtils.isNotBlank(ticketCode)) {
                distributeBO.distribute1Amount(storeFrAmount, store, user,
                    storePurchase.getCode());
            } else {
                if (EStoreLevel.NOMAL.getCode().equals(store.getLevel())) {
                    distributeBO.distribute10Amount(storeFrAmount, store, user,
                        storePurchase.getCode());
                }
                if (EStoreLevel.FINANCIAL.getCode().equals(store.getLevel())) {
                    distributeBO.distribute25Amount(storeFrAmount,
                        userFrAmount, store, user, storePurchase.getCode());
                }
            }
        } else {
            logger.error("订单号：" + storePurchase.getCode() + "已支付，重复回调");
        }
    }

    @Override
    @Transactional
    public void paySuccessCG(String payGroup, String payCode, Long payAmount) {
        StorePurchase storePurchase = storePurchaseBO
            .getStorePurchaseByPayGroup(payGroup);
        if (EStorePurchaseStatus.TO_PAY.getCode().equals(
            storePurchase.getStatus())) {
            // 更新支付记录
            storePurchaseBO.paySuccess(storePurchase, payCode, payAmount);
            if (EO2OPayType.CG_RMBJF_WEIXIN_H5.getCode().equals(
                storePurchase.getPayType())) {
                // 支付成功后，将积分从消费者回收至平台
                String systemUser = ESysUser.SYS_USER_CAIGO.getCode();
                accountBO.doTransferAmountRemote(storePurchase.getUserId(),
                    systemUser, ECurrency.CGJF, storePurchase.getPayAmount3(),
                    EBizType.CG_O2O_CGJF, "O2O消费使用积分", "O2O消费回收积分",
                    storePurchase.getCode());
            } else if (EO2OPayType.WEIXIN_H5.getCode().equals( // 全人民币支付
                storePurchase.getPayType())) {
                // 资金划转逻辑--------------
                // 1、平台给商家一定比例人民币
                // 2、平台返现等比例菜狗币(回收人民币)
                Store store = storeBO.getStore(storePurchase.getStoreCode());
                Long payStoreRmbAmount = AmountUtil.mul(payAmount,
                    1 - store.getRate1());
                String systemUser = ESysUser.SYS_USER_CAIGO.getCode();
                accountBO.doTransferAmountRemote(systemUser, store.getOwner(),
                    ECurrency.CNY, payStoreRmbAmount, EBizType.CG_O2O_RMB,
                    "O2O消费人民币支付", "O2O消费人民币支付", storePurchase.getCode());
                Long fxCgbAmount = payAmount - payStoreRmbAmount;
                accountBO.doTransferAmountRemote(systemUser,
                    storePurchase.getUserId(), ECurrency.CG_CGB, fxCgbAmount,
                    EBizType.CG_O2O_RMBFD, "O2O消费人民币支付返点", "O2O消费人民币支付返点",
                    storePurchase.getCode());
            }
        } else {
            logger.info("订单号：" + storePurchase.getCode() + "已支付，重复回调");
        }
    }

    @Override
    @Transactional
    public void paySuccessYC(String payGroup, String payCode, Long payAmount) {
        StorePurchase storePurchase = storePurchaseBO
            .getStorePurchaseByPayGroup(payGroup);
        if (EStorePurchaseStatus.TO_PAY.getCode().equals(
            storePurchase.getStatus())) {
            // 更新支付记录
            storePurchaseBO.paySuccess(storePurchase, payCode, payAmount);
            // 资金划转逻辑--------------
            Store store = storeBO.getStore(storePurchase.getStoreCode());
            Long fxCbAmount = storePurchase.getBackAmount();
            // 付给商家多少钱 = 支付总金额 - 返点给C端用户的橙券
            Long payStoreRmbAmount = payAmount - fxCbAmount;
            String systemUser = ESysUser.SYS_USER_YAOCHENG.getCode();
            accountBO.doTransferAmountRemote(systemUser, store.getOwner(),
                ECurrency.CNY, payStoreRmbAmount, EBizType.YC_O2O_RMB,
                "O2O消费人民币支付", "O2O消费人民币支付", storePurchase.getCode());
            accountBO.doTransferAmountRemote(systemUser,
                storePurchase.getUserId(), ECurrency.YC_CB, fxCbAmount,
                EBizType.YC_O2O_RMBFD, "O2O消费人民币支付返点", "O2O消费人民币支付返点",
                storePurchase.getCode());
        } else {
            logger.info("订单号：" + storePurchase.getCode() + "已支付，重复回调");
        }
    }

    @Override
    public Paginable<StorePurchase> queryStorePurchasePage(int start,
            int limit, StorePurchase condition) {
        Paginable<StorePurchase> page = storePurchaseBO.getPaginable(start,
            limit, condition);
        List<StorePurchase> list = page.getList();
        for (StorePurchase storePurchase : list) {
            Store store = storeBO.getStore(storePurchase.getStoreCode());
            storePurchase.setStore(store);
            if (StringUtils.isNotBlank(storePurchase.getTicketCode())) {
                UserTicket userTicket = userTicketBO
                    .getUserTicket(storePurchase.getTicketCode());
                StoreTicket storeTicket = storeTicketBO
                    .getStoreTicket(userTicket.getTicketCode());
                storePurchase.setStoreTicket(storeTicket);
            }
            User user = userBO.getRemoteUser(storePurchase.getUserId());
            storePurchase.setUser(user);
            User storeUser = userBO.getRemoteUser(store.getOwner());
            storePurchase.setStoreUser(storeUser);
        }
        return page;
    }

    /** 
     * @see com.xnjr.mall.ao.IStorePurchaseAO#getLasterStorePurchase(java.lang.String)
     */
    @Override
    public StorePurchase getLasterStorePurchase(String storeCode) {
        StorePurchase result = null;
        StorePurchase condition = new StorePurchase();
        condition.setStoreCode(storeCode);
        condition.setStatus(EStorePurchaseStatus.PAYED.getCode());
        condition.setOrder("code", "desc");
        Paginable<StorePurchase> page = storePurchaseBO.getPaginable(1, 1,
            condition);
        List<StorePurchase> list = page.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            result = list.get(0);
            User user = userBO.getRemoteUser(result.getUserId());
            result.setUser(user);
        }
        return result;
    }

}
