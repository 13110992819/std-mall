package com.xnjr.mall.ao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xnjr.mall.ao.IOrderAO;
import com.xnjr.mall.ao.ISorderAO;
import com.xnjr.mall.ao.ISproductAO;
import com.xnjr.mall.bo.IAccountBO;
import com.xnjr.mall.bo.ISmsOutBO;
import com.xnjr.mall.bo.ISorderBO;
import com.xnjr.mall.bo.ISproductBO;
import com.xnjr.mall.bo.IStoreBO;
import com.xnjr.mall.bo.IUserBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.common.DateUtil;
import com.xnjr.mall.core.OrderNoGenerater;
import com.xnjr.mall.domain.Account;
import com.xnjr.mall.domain.Sorder;
import com.xnjr.mall.domain.Sproduct;
import com.xnjr.mall.domain.User;
import com.xnjr.mall.dto.req.XN808450Req;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.enums.EBizType;
import com.xnjr.mall.enums.ECurrency;
import com.xnjr.mall.enums.EGeneratePrefix;
import com.xnjr.mall.enums.EPayType;
import com.xnjr.mall.enums.ESproductStatus;
import com.xnjr.mall.enums.ESysUser;
import com.xnjr.mall.enums.ESystemCode;
import com.xnjr.mall.enums.EVorderStatus;
import com.xnjr.mall.exception.BizException;

@Service
public class SorderAOImpl implements ISorderAO {
    static Logger logger = Logger.getLogger(StorePurchaseAOImpl.class);

    @Autowired
    private ISorderBO sorderBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private ISproductBO sproductBO;

    @Autowired
    private IStoreBO storeBO;

    @Autowired
    private ISmsOutBO smsOutBO;

    @Autowired
    private ISproductAO sproductAO;

    @Autowired
    private IOrderAO orderAO;

    @Override
    public String commitOrder(XN808450Req req) {
        Sproduct product = sproductBO.getSproduct(req.getProductCode());
        if (!ESproductStatus.PUBLISH_YES.getCode().equals(product.getStatus())) {
            throw new BizException("xn000000", "产品未上架，无法下单");
        }
        if (product.getRemainNum() <= 0) {
            throw new BizException("xn0000", "已满数据，不能再购买");
        }
        String today = DateUtil.getToday(DateUtil.FRONT_DATE_FORMAT_STRING);
        if (DateUtil.daysBetween(req.getStartDate(), today,
            DateUtil.FRONT_DATE_FORMAT_STRING) < 0) {
            throw new BizException("xn0000", "预订时间起必须是今天或今天之后");
        }
        if (DateUtil.daysBetween(req.getStartDate(), req.getEndDate(),
            DateUtil.FRONT_DATE_FORMAT_STRING) <= 0) {
            throw new BizException("xn0000", "预订时间起必须小于预订时间止");
        }
        // 计算总价格
        int count = DateUtil.daysBetween(req.getStartDate(), req.getEndDate(),
            DateUtil.FRONT_DATE_FORMAT_STRING);
        Long totalAmount = product.getPrice() * count;

        String code = OrderNoGenerater.generateM(EGeneratePrefix.SORDER
            .getCode());
        Sorder data = new Sorder();
        data.setCode(code);
        data.setProductCode(product.getCode());
        data.setCategory(product.getCategory());
        data.setType(product.getType());
        data.setStoreCode(product.getStoreCode());
        data.setStoreUser(product.getStoreUser());

        data.setStartDate(DateUtil.strToDate(req.getStartDate(),
            DateUtil.FRONT_DATE_FORMAT_STRING));
        data.setEndDate(DateUtil.strToDate(req.getEndDate(),
            DateUtil.FRONT_DATE_FORMAT_STRING));
        data.setReName(req.getReName());
        data.setReMobile(req.getReMobile());

        data.setApplyUser(req.getApplyUser());
        data.setApplyNote(req.getApplyNote());
        data.setApplyDatetime(new Date());
        data.setAmount1(totalAmount);
        data.setAmount2(0L);
        data.setAmount3(0L);
        data.setStatus(EVorderStatus.TOPAY.getCode());

        data.setCompanyCode(product.getCompanyCode());
        data.setSystemCode(product.getSystemCode());
        sorderBO.saveSorder(data);
        return code;
    }

    @Override
    @Transactional
    public Object toPayOrder(String code, String payType) {
        // 暂时只实现单笔订单支付
        Sorder order = sorderBO.getSorder(code);
        if (!EVorderStatus.TOPAY.getCode().equals(order.getStatus())) {
            throw new BizException("xn000000", "订单不处于待支付状态");
        }
        Sproduct sproduct = sproductBO.getSproduct(order.getProductCode());
        if (!ESproductStatus.PUBLISH_YES.getCode().equals(sproduct.getStatus())) {
            throw new BizException("xn000000", "产品未上架，无法支付");
        }
        if (sproduct.getRemainNum() <= 0) {
            throw new BizException("xn0000", "已满数据，不能再购买");
        }

        if (EPayType.YE.getCode().equals(payType)) {
            return toPayOrderYE(order, sproduct);
        } else if (EPayType.WEIXIN_APP.getCode().equals(payType)) {
            return toPayOrderWXAPP(order, sproduct);
        } else if (EPayType.ALIPAY.getCode().equals(payType)) {
            return toPayOrderALIPAY(order, sproduct);
        } else {
            throw new BizException("xn0000", "支付类型不支持");
        }
    }

    private Object toPayOrderALIPAY(Sorder order, Sproduct sproduct) {
        Long rmbAmount = order.getAmount1();
        User user = userBO.getRemoteUser(order.getApplyUser());
        String payGroup = sorderBO.addPayGroup(order, EPayType.ALIPAY);
        return accountBO
            .doAlipayRemote(user.getUserId(), ESysUser.SYS_USER_JKEG.getCode(),
                payGroup, order.getCode(), EBizType.JKEG_FW,
                EBizType.JKEG_FW.getValue() + "-支付宝支付", rmbAmount);
    }

    private Object toPayOrderWXAPP(Sorder order, Sproduct sproduct) {
        Long rmbAmount = order.getAmount1();
        User user = userBO.getRemoteUser(order.getApplyUser());
        String payGroup = sorderBO.addPayGroup(order, EPayType.WEIXIN_APP);
        return accountBO.doWeiXinPayRemote(user.getUserId(),
            ESysUser.SYS_USER_JKEG.getCode(), payGroup, order.getCode(),
            EBizType.JKEG_FW, EBizType.JKEG_FW.getValue() + "-微信支付", rmbAmount);
    }

    private Object toPayOrderYE(Sorder order, Sproduct sproduct) {
        Long rmbAmount = order.getAmount1();
        String fromUserId = order.getApplyUser();
        Account rmbAccount = accountBO.getRemoteAccount(fromUserId,
            ECurrency.CNY);
        if (rmbAmount > rmbAccount.getAmount()) {
            throw new BizException("xn0000", "健康币不足");
        }
        sorderBO.addPayGroup(order, EPayType.YE);
        // 更新订单支付金额
        sorderBO.refreshPaySuccess(order, rmbAmount, 0L, 0L, null);
        sproductBO.refreshSproduct(sproduct, sproduct.getRemainNum() + 1);
        accountBO.doTransferAmountRemote(fromUserId,
            ESysUser.SYS_USER_JKEG.getCode(), ECurrency.CNY, rmbAmount,
            EBizType.JKEG_FW, EBizType.JKEG_FW.getValue(),
            EBizType.JKEG_FW.getValue(), order.getCode());
        orderAO.checkUpgrade(order.getApplyUser());
        return new BooleanRes(true);
    }

    @Override
    @Transactional
    public void deliverOrder(String code, String handleUser, String remark) {
        Sorder order = sorderBO.getSorder(code);
        if (EVorderStatus.PAYED.getCode().equals(order.getStatus())) {
            sorderBO.deliver(order, handleUser, remark);
            // 平台付款给商家
            accountBO.doTransferAmountRemote(ESysUser.SYS_USER_JKEG.getCode(),
                order.getStoreUser(), ECurrency.CNY, order.getPayAmount1(),
                EBizType.JKEG_FW, EBizType.JKEG_FW.getValue(),
                EBizType.JKEG_FW.getValue(), order.getCode());
            // 发送短信
            smsOutBO.sentContent(order.getApplyUser(),
                "尊敬的用户，您的订单《" + order.getCode() + "》已办理入住，详情请登录网站查询。");
        } else {
            throw new BizException("xn0000", "该订单不是已支付状态，无法办理入住");
        }
    }

    @Override
    @Transactional
    public void cancelOrder(String code, String handleUser, String remark) {
        Sorder order = sorderBO.getSorder(code);
        if (EVorderStatus.TOPAY.getCode().equals(order.getStatus())) {
            // 发短信
            smsOutBO.sentContent(order.getApplyUser(),
                "尊敬的用户，您的订单[" + order.getCode() + "]已取消");
        } else if (EVorderStatus.PAYED.getCode().equals(order.getStatus())) {
            if (ESystemCode.JKEG.getCode().equals(order.getSystemCode())) {
                accountBO.doTransferAmountRemote(
                    ESysUser.SYS_USER_JKEG.getCode(), order.getApplyUser(),
                    ECurrency.CNY, order.getPayAmount1(), EBizType.JKEG_FWTK,
                    EBizType.JKEG_FWTK.getValue(),
                    EBizType.JKEG_FWTK.getValue(), order.getCode());
            }
            // 发短信
            smsOutBO.sentContent(order.getApplyUser(),
                "尊敬的用户，您的订单[" + order.getCode() + "]已取消,请及时查看退款。");
        } else {
            throw new BizException("xn0000", "该订单，无法操作");
        }
        sorderBO.cancelSorder(order, handleUser, remark);
        Sproduct sproduct = sproductBO.getSproduct(order.getProductCode());
        sproductBO.refreshSproduct(sproduct, sproduct.getRemainNum() - 1);

    }

    @Override
    public Paginable<Sorder> querySorderPage(int start, int limit,
            Sorder condition) {
        Paginable<Sorder> orders = sorderBO.getPaginable(start, limit,
            condition);
        for (Sorder order : orders.getList()) {
            order.setProduct(sproductBO.getSproduct(order.getProductCode()));
        }
        return orders;
    }

    @Override
    public Sorder getSorder(String code) {
        return sorderBO.getSorder(code);
    }

    @Override
    public List<Sorder> querySorderList(Sorder condition) {
        return sorderBO.querySorderList(condition);
    }

    @Override
    @Transactional
    public void paySuccess(String payGroup, String payCode, Long amount) {
        List<Sorder> orderList = sorderBO.queryOrderListByPayGroup(payGroup);
        if (CollectionUtils.isEmpty(orderList)) {
            throw new BizException("XN000000", "找不到对应的订单记录");
        }
        Sorder order = orderList.get(0);
        Sproduct sproduct = sproductBO.getSproduct(order.getProductCode());
        if (EVorderStatus.TOPAY.getCode().equals(order.getStatus())) {
            // 更新订单支付金额
            sorderBO.refreshPaySuccess(order, amount, 0L, 0L, null);
            sproductAO.resetAvaliableNumbers(order.getProductCode());
            orderAO.checkUpgrade(order.getApplyUser());
        } else {
            logger.info("订单号：" + order.getCode() + "已支付，重复回调");
        }
    }
}
