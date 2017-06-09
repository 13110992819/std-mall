package com.xnjr.mall.ao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xnjr.mall.ao.ISorderAO;
import com.xnjr.mall.bo.IAccountBO;
import com.xnjr.mall.bo.ISorderBO;
import com.xnjr.mall.bo.ISproductBO;
import com.xnjr.mall.bo.IStoreBO;
import com.xnjr.mall.bo.IUserBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.common.DateUtil;
import com.xnjr.mall.domain.Account;
import com.xnjr.mall.domain.Sorder;
import com.xnjr.mall.domain.Sproduct;
import com.xnjr.mall.domain.Store;
import com.xnjr.mall.domain.User;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.enums.EBizType;
import com.xnjr.mall.enums.ECurrency;
import com.xnjr.mall.enums.EOrderStatus;
import com.xnjr.mall.enums.EPayType;
import com.xnjr.mall.enums.ESproductStatus;
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

    @Override
    public String commitSorder(String productCode, String startDate,
            String endDate, String reName, String reMobile, String applyUser,
            String applyNote) {
        Sproduct sproduct = sproductBO.getSproduct(productCode);
        this.check(sproduct);
        return sorderBO.saveSorder(sproduct,
            DateUtil.strToDate(startDate, DateUtil.FRONT_DATE_FORMAT_STRING),
            DateUtil.strToDate(endDate, DateUtil.FRONT_DATE_FORMAT_STRING),
            reName, reMobile, applyUser, applyNote);
    }

    private boolean check(Sproduct sproduct) {
        boolean flag = true;
        if (!ESproductStatus.PUBLISH_YES.getCode().equals(sproduct.getStatus())) {
            flag = false;
        }
        if (sproduct.getRemainNum() == 0) {
            flag = false;
        }
        if (flag = false) {
            throw new BizException("xn0000", "服务不能购买");
        }
        return flag;
    }

    @Override
    public Object payOrder(List<String> codeList, String payType) {
        String code = codeList.get(0);
        Sorder order = sorderBO.getSorder(code);
        if (!EOrderStatus.TO_PAY.getCode().equals(order.getStatus())) {
            throw new BizException("xn000000", "订单不处于待支付状态");
        }
        Sproduct sproduct = sproductBO.getSproduct(order.getProductCode());
        this.check(sproduct);
        if (EPayType.YE.getCode().equals(payType)) {
            return toPayOrderYE(order);
        } else if (EPayType.WEIXIN_APP.getCode().equals(payType)) {
            return toPayOrderWXAPP(order);
        } else if (EPayType.ALIPAY.getCode().equals(payType)) {
            return toPayOrderALIPAY(order);
        } else if (EPayType.WEIXIN_H5.getCode().equals(payType)) {
            return toPayOrderWXH5(order);
        } else {
            throw new BizException("xn0000", "支付类型不支持");
        }
    }

    private Object toPayOrderWXH5(Sorder order) {
        Long rmbAmount = order.getAmount1();
        User user = userBO.getRemoteUser(order.getApplyUser());
        Sproduct sproduct = sproductBO.getSproduct(order.getProductCode());
        Store store = storeBO.getStore(sproduct.getStoreCode());
        String payGroup = sorderBO.addPayGroup(order.getCode());
        return accountBO.doWeiXinH5PayRemote(user.getUserId(),
            user.getOpenId(), store.getOwner(), payGroup, order.getCode(),
            EBizType.FW, "购物微信支付", rmbAmount);
    }

    private Object toPayOrderALIPAY(Sorder order) {
        Long rmbAmount = order.getAmount1();
        User user = userBO.getRemoteUser(order.getApplyUser());
        Sproduct sproduct = sproductBO.getSproduct(order.getProductCode());
        Store store = storeBO.getStore(sproduct.getStoreCode());
        String payGroup = sorderBO.addPayGroup(order.getCode());
        return accountBO.doAlipayRemote(user.getUserId(), store.getOwner(),
            payGroup, order.getCode(), EBizType.YC_MALL, "支付宝支付", rmbAmount);
    }

    private Object toPayOrderWXAPP(Sorder order) {
        Long rmbAmount = order.getAmount1();
        User user = userBO.getRemoteUser(order.getApplyUser());
        Sproduct sproduct = sproductBO.getSproduct(order.getProductCode());
        Store store = storeBO.getStore(sproduct.getStoreCode());
        String payGroup = sorderBO.addPayGroup(order.getCode());
        return accountBO.doWeiXinPayRemote(user.getUserId(), store.getOwner(),
            payGroup, order.getCode(), EBizType.FW, "微信APP支付", rmbAmount);
    }

    private Object toPayOrderYE(Sorder order) {
        Long rmbAmount = order.getAmount1();
        String fromUserId = order.getApplyUser();
        Account rmbAccount = accountBO.getRemoteAccount(fromUserId,
            ECurrency.CNY);
        if (rmbAmount > rmbAccount.getAmount()) {
            throw new BizException("xn0000", "人民币账户余额不足");
        }
        Sproduct sproduct = sproductBO.getSproduct(order.getProductCode());
        Store store = storeBO.getStore(sproduct.getStoreCode());
        // 更新订单支付金额
        sorderBO.refreshPaySuccess(order, rmbAmount, 0L, 0L, null);
        sproductBO.refreshSproduct(sproduct, sproduct.getRemainNum() + 1);
        accountBO.doTransferAmountRemote(fromUserId, store.getOwner(),
            ECurrency.CNY, rmbAmount, EBizType.FW, EBizType.FW.getValue(),
            EBizType.FW.getValue(), order.getCode());
        return new BooleanRes(true);
    }

    @Override
    public void deliver(String code, String handleUser, String remark) {
        Sorder order = sorderBO.getSorder(code);
        if (!EOrderStatus.PAY_YES.getCode().equals(order.getStatus())) {
            throw new BizException("xn0000", "订单不处于已支付状态,不能出理");
        }
        sorderBO.deliver(order, handleUser, remark);
    }

    @Override
    public void cancelSorder(String code, String handleUser, String remark) {
        Sorder order = sorderBO.getSorder(code);
        EOrderStatus status = EOrderStatus.SHYC;
        Long rmbAmount = order.getPayAmount1(); // 人民币
        Sproduct sproduct = sproductBO.getSproduct(order.getProductCode());
        Store store = storeBO.getStore(sproduct.getStoreCode());
        if (!EOrderStatus.PAY_YES.getCode().equals(order.getStatus())
                || EOrderStatus.TO_PAY.getCode().equals(order.getStatus())) {
            throw new BizException("xn0000", "订单不处于可取消的范围内");
        }
        if (EOrderStatus.PAY_YES.getCode().equals(order.getStatus())) {
            accountBO.doTransferAmountRemote(store.getOwner(),
                order.getApplyUser(), ECurrency.CNY, rmbAmount, EBizType.FWTK,
                EBizType.FWTK.getValue(), EBizType.FWTK.getValue(),
                order.getCode());
        }
        if (order.getApplyUser().equals(handleUser)) {
            status = EOrderStatus.YHYC;
        }
        sorderBO.cancelSorder(order, status, handleUser, remark);
        sproductBO.refreshSproduct(sproduct, sproduct.getRemainNum() - 1);
    }

    @Override
    public Paginable<Sorder> querySorderPage(int start, int limit,
            Sorder condition) {
        return sorderBO.getPaginable(start, limit, condition);
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
        if (EOrderStatus.TO_PAY.getCode().equals(order.getStatus())) {
            // 更新订单支付金额
            sorderBO.refreshPaySuccess(order, amount, 0L, 0L, null);
            sproductBO.refreshSproduct(sproduct, sproduct.getRemainNum() + 1);
        } else {
            logger.info("订单号：" + order.getCode() + "已支付，重复回调");
        }
    }
}
