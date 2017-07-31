package com.xnjr.mall.ao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xnjr.mall.ao.IOrderAO;
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
import com.xnjr.mall.enums.EPayType;
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

    @Autowired
    private IOrderAO orderAO;

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
        if (EPayType.YC_CB.getCode().equals(payType)) {
            return storePurchaseYCCB(user, store, amount);
        } else if (EPayType.YE.getCode().equals(payType)) {
            return storePurchaseYCRMBYE(user, store, amount);
        } else if (EPayType.WEIXIN_H5.getCode().equals(payType)) {
            return storePurchaseYCWXH5(user, store, amount);
        } else {
            throw new BizException("xn0000", "暂不支持此支付方式");
        }
    }

    @Override
    @Transactional
    public Object storePurchaseJKEG(String userId, String storeCode,
            Long amount, String payType) {
        User user = userBO.getRemoteUser(userId);
        Store store = storeBO.getStore(storeCode);
        if (!EStoreStatus.ON_OPEN.getCode().equals(store.getStatus())) {
            throw new BizException("xn0000", "店铺不处于可消费状态");
        }
        // 1-人民币余额支付 5-微信H5支付 50-橙券余额支付
        if (EPayType.YE.getCode().equals(payType)) {
            return storePurchaseJKEGRMBYE(user, store, amount);
        } else if (EPayType.WEIXIN_APP.getCode().equals(payType)) {
            return storePurchaseJKEGWXAPP(user, store, amount);
        } else if (EPayType.ALIPAY.getCode().equals(payType)) {
            return storePurchaseJKEGZFBAPP(user, store, amount);
        } else {
            throw new BizException("xn0000", "暂不支持此支付方式");
        }
    }

    private Object storePurchaseJKEGZFBAPP(User user, Store store, Long amount) {
        // 付给平台多少钱
        Long payAmount = AmountUtil.eraseLiUp(AmountUtil.mul(amount,
            store.getRate1()));
        Long frAmount = AmountUtil.eraseLiDown(AmountUtil.mul(payAmount,
            store.getRate2()));
        if (payAmount < 10) {
            throw new BizException("xn000000", "折扣后支付金额，必须大于0.01元");
        }
        String payGroup = OrderNoGenerater.generateM(EGeneratePrefix.PAY_GROUP
            .getCode());
        String code = storePurchaseBO.storePurchaseJKEGZFBAPP(user, store,
            amount, frAmount, payGroup);
        String systemUser = ESysUser.SYS_USER_JKEG.getCode();
        return accountBO.doAlipayRemote(user.getUserId(), systemUser, payGroup,
            code, EBizType.JKEG_O2O_RMB, EBizType.JKEG_O2O_RMB + "-支付宝支付",
            payAmount);
    }

    private Object storePurchaseJKEGWXAPP(User user, Store store, Long amount) {
        // 付给平台多少钱
        Long payAmount = AmountUtil.eraseLiUp(AmountUtil.mul(amount,
            store.getRate1()));
        Long frAmount = AmountUtil.eraseLiDown(AmountUtil.mul(payAmount,
            store.getRate2()));
        if (payAmount < 10) {
            throw new BizException("xn000000", "折扣后支付金额，必须大于0.01元");
        }
        String payGroup = OrderNoGenerater.generateM(EGeneratePrefix.PAY_GROUP
            .getCode());
        String code = storePurchaseBO.storePurchaseJKEGWXAPP(user, store,
            amount, frAmount, payGroup);
        String systemUser = ESysUser.SYS_USER_JKEG.getCode();
        return accountBO.doWeiXinPayRemote(user.getUserId(), systemUser,
            payGroup, code, EBizType.JKEG_O2O_RMB, EBizType.JKEG_O2O_RMB
                    + "-微信支付", payAmount);
    }

    private Object storePurchaseJKEGRMBYE(User user, Store store,
            Long rmbTotalAmount) {
        // 折扣之后的金额（用户需支付的金额）
        Long amount = AmountUtil.eraseLiUp(AmountUtil.mul(rmbTotalAmount,
            store.getRate1()));
        // 分润金额
        Long frAmount = AmountUtil.eraseLiDown(AmountUtil.mul(amount,
            store.getRate2()));
        // 商家实际应该受到的钱
        Long payStoreRmbAmount = amount - frAmount;
        // 检查用户余额是否充足
        Account rmbAccount = accountBO.getRemoteAccount(user.getUserId(),
            ECurrency.CNY);
        if (amount > rmbAccount.getAmount()) {
            throw new BizException("xn0000", "健康币不足");
        }
        // 落地本地系统消费记录
        String code = storePurchaseBO.storePurchaseJKEGRMBYE(user, store,
            rmbTotalAmount, amount, frAmount);
        // 用户付钱给平台
        accountBO.doTransferAmountRemote(user.getUserId(),
            ESysUser.SYS_USER_JKEG.getCode(), ECurrency.CNY, amount,
            EBizType.JKEG_O2O_RMB, "店铺消费健康币支付", "店铺消费健康币支付", code);
        // 平台划转给商家
        orderAO.checkUpgrade(user.getUserId());
        accountBO.doTransferAmountRemote(ESysUser.SYS_USER_JKEG.getCode(),
            store.getOwner(), ECurrency.CNY, payStoreRmbAmount,
            EBizType.JKEG_O2O_RMB, "店铺消费健康币支付", "店铺消费健康币支付", code);
        // 开始分赃
        distributeBO.distributeO2O(user, store, frAmount, code);
        return code;
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
            ECurrency.JF, payJF, EBizType.GD_O2O, "[" + store.getName()
                    + "]消费支付",
            "用户[" + user.getMobile() + "]在[" + store.getName() + "]消费支付", code);
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

    @Override
    @Transactional
    public void paySuccessCG(String payGroup, String payCode, Long payAmount) {
        StorePurchase storePurchase = storePurchaseBO
            .getStorePurchaseByPayGroup(payGroup);
        if (EStorePurchaseStatus.TO_PAY.getCode().equals(
            storePurchase.getStatus())) {
            // 更新支付记录
            storePurchaseBO.paySuccess(storePurchase, payCode, payAmount);
            if (EPayType.CG_RMBJF_WEIXIN_H5.getCode().equals(
                storePurchase.getPayType())) {
                // 支付成功后，将积分从消费者回收至平台
                String systemUser = ESysUser.SYS_USER_CAIGO.getCode();
                accountBO.doTransferAmountRemote(storePurchase.getUserId(),
                    systemUser, ECurrency.CGJF, storePurchase.getPayAmount3(),
                    EBizType.CG_O2O_CGJF, "O2O消费使用积分", "O2O消费回收积分",
                    storePurchase.getCode());
            } else if (EPayType.WEIXIN_H5.getCode().equals( // 全人民币支付
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
    @Transactional
    public void paySuccessJKEG(String payGroup, String payCode, Long payAmount) {
        StorePurchase storePurchase = storePurchaseBO
            .getStorePurchaseByPayGroup(payGroup);
        if (EStorePurchaseStatus.TO_PAY.getCode().equals(
            storePurchase.getStatus())) {
            // 更新支付记录
            storePurchaseBO.paySuccess(storePurchase, payCode, payAmount);
            // 资金划转逻辑--------------
            Store store = storeBO.getStore(storePurchase.getStoreCode());
            // 商家该收到的钱
            Long payStoreRmbAmount = payAmount - storePurchase.getBackAmount();
            String systemUser = ESysUser.SYS_USER_JKEG.getCode();
            accountBO.doTransferAmountRemote(systemUser, store.getOwner(),
                ECurrency.CNY, payStoreRmbAmount, EBizType.JKEG_O2O_RMB,
                "周边消费人民币支付", "周边消费人民币支付", storePurchase.getCode());
            orderAO.checkUpgrade(storePurchase.getUserId());
            // 开始分赃
            User consumer = userBO.getRemoteUser(storePurchase.getUserId());
            distributeBO.distributeO2O(consumer, store,
                storePurchase.getBackAmount(), storePurchase.getCode());
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
