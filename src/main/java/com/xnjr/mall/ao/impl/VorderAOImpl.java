package com.xnjr.mall.ao.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xnjr.mall.ao.IVorderAO;
import com.xnjr.mall.bo.IAccountBO;
import com.xnjr.mall.bo.ISmsOutBO;
import com.xnjr.mall.bo.IVorderBO;
import com.xnjr.mall.bo.IVproductBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.common.AmountUtil;
import com.xnjr.mall.core.OrderNoGenerater;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Vorder;
import com.xnjr.mall.domain.Vproduct;
import com.xnjr.mall.dto.req.XN808650Req;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.enums.EBizType;
import com.xnjr.mall.enums.ECurrency;
import com.xnjr.mall.enums.EGeneratePrefix;
import com.xnjr.mall.enums.ESysUser;
import com.xnjr.mall.enums.ESystemCode;
import com.xnjr.mall.enums.EVorderStatus;
import com.xnjr.mall.exception.BizException;

@Service
public class VorderAOImpl implements IVorderAO {
    protected static final Logger logger = LoggerFactory
        .getLogger(VorderAOImpl.class);

    @Autowired
    private IVproductBO vproductBO;

    @Autowired
    private IVorderBO vorderBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private ISmsOutBO smsOutBO;

    @Override
    public String commitOrder(XN808650Req req) {
        Vproduct product = vproductBO.getVproduct(req.getVproductCode());
        Date now = new Date();
        Long amount = StringValidater.toLong(req.getAmount());
        Long payAmount = AmountUtil.mul(amount, product.getRate());

        Vorder data = new Vorder();
        String code = OrderNoGenerater.generateM(EGeneratePrefix.PRODUCT
            .getCode());
        data.setCode(code);
        data.setType(product.getName());
        data.setReCardno(req.getReCardno());
        data.setReName(req.getReName());
        data.setReMobile(req.getReMobile());

        data.setApplyUser(req.getApplyUser());
        data.setApplyDatetime(now);
        data.setAmount(amount);
        data.setPayAmount(payAmount);
        data.setStatus(EVorderStatus.Topay.getCode());

        data.setSystemCode(product.getSystemCode());
        data.setCompanyCode(product.getCompanyCode());
        vorderBO.saveOrder(data);
        return code;
    }

    @Override
    public Object toPayOrder(List<String> codeList, String payType) {
        // 暂时只实现单笔订单支付
        String code = codeList.get(0);
        Vorder order = vorderBO.getVorder(code);
        if (!EVorderStatus.Topay.getCode().equals(order.getStatus())) {
            throw new BizException("xn000000", "订单不处于待支付状态");
        }
        Long payAmount = order.getPayAmount();
        if (ESystemCode.Caigo.getCode().equals(order.getSystemCode())) {
            // 菜狗币支付
            accountBO.doTransferAmountRemote(order.getApplyUser(),
                ESysUser.SYS_USER_CAIGO.getCode(), ECurrency.CG_CGB, payAmount,
                EBizType.CG_XNCZ, EBizType.CG_XNCZ.getValue(),
                EBizType.CG_XNCZ.getValue());
            vorderBO.payOrder(order);
        } else {
            throw new BizException("xn000000", "系统编号不能识别");
        }

        return new BooleanRes(true);
    }

    @Override
    public void cancelOrder(List<String> codeList, String updater, String remark) {
        for (String code : codeList) {
            cancelOrderSingle(code, updater, remark);
        }

    }

    private void cancelOrderSingle(String code, String updater, String remark) {
        Vorder order = vorderBO.getVorder(code);
        String applyUser = order.getApplyUser();
        if (EVorderStatus.Topay.getCode().equals(order.getStatus())) {
            // 发短信
            smsOutBO.sentContent(applyUser, applyUser,
                "尊敬的用户，您的订单[" + order.getCode() + "]已取消");
        } else if (EVorderStatus.Payed.getCode().equals(order.getStatus())) {
            // 退款
            // 发短信
            smsOutBO.sentContent(applyUser, applyUser,
                "尊敬的用户，您的订单[" + order.getCode() + "]已取消,退款原因:[" + remark
                        + "],请及时查看退款。");
        } else {
            throw new BizException("xn0000", "该订单，无法操作");
        }
        vorderBO.cancelOrder(order, updater, remark);
    }

    @Override
    public void deliverOrder(List<String> codeList, String updater,
            String remark) {
        for (String code : codeList) {
            deliverOrderSingle(code, updater, remark);
        }
    }

    private void deliverOrderSingle(String code, String updater, String remark) {
        Vorder order = vorderBO.getVorder(code);
        if (EVorderStatus.Payed.getCode().equals(order.getStatus())) {
            vorderBO.deliverOrder(order, updater, remark);
        } else {
            throw new BizException("xn0000", "该订单不是已支付状态，无法发货");
        }

    }

    @Override
    public Paginable<Vorder> queryVorderPage(int start, int limit,
            Vorder condition) {
        return vorderBO.getPaginable(start, limit, condition);
    }

    @Override
    public Vorder getVorder(String code) {
        return vorderBO.getVorder(code);
    }

    @Override
    public List<Vorder> queryVorderList(Vorder condition) {
        return vorderBO.queryVorderList(condition);
    }
}
