package com.xnjr.mall.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xnjr.mall.bo.ISorderBO;
import com.xnjr.mall.bo.base.PaginableBOImpl;
import com.xnjr.mall.core.OrderNoGenerater;
import com.xnjr.mall.dao.ISorderDAO;
import com.xnjr.mall.domain.Sorder;
import com.xnjr.mall.domain.Sproduct;
import com.xnjr.mall.enums.EGeneratePrefix;
import com.xnjr.mall.enums.EPayType;
import com.xnjr.mall.enums.EVorderStatus;
import com.xnjr.mall.exception.BizException;

@Component
public class SorderBOImpl extends PaginableBOImpl<Sorder> implements ISorderBO {
    @Autowired
    private ISorderDAO sorderDAO;

    @Override
    public String saveSorder(Sproduct sproduct, Date startDate, Date endDate,
            String reName, String reMobile, String applyUser, String applyNote) {
        Sorder data = new Sorder();
        String code = OrderNoGenerater.generateM(EGeneratePrefix.SORDER
            .getCode());
        data.setCode(code);
        data.setProductCode(sproduct.getCode());
        data.setCategory(sproduct.getCategory());
        data.setType(sproduct.getType());
        data.setStartDate(startDate);

        data.setEndDate(endDate);
        data.setReName(reName);
        data.setReMobile(reMobile);
        data.setApplyUser(applyUser);
        data.setApplyNote(applyNote);

        data.setApplyDatetime(new Date());
        data.setAmount1(sproduct.getPrice());
        data.setAmount2(0L);
        data.setAmount3(0L);
        data.setStatus(EVorderStatus.TOPAY.getCode());

        data.setCompanyCode(sproduct.getCompanyCode());
        data.setSystemCode(sproduct.getSystemCode());
        sorderDAO.insert(data);
        return code;
    }

    @Override
    public Sorder getSorder(String code) {
        Sorder data = null;
        if (StringUtils.isNotBlank(code)) {
            Sorder condition = new Sorder();
            condition.setCode(code);
            data = sorderDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "订单编号不存在");
            }
        }
        return data;
    }

    @Override
    public String addPayGroup(Sorder order, EPayType payType) {
        String payGroup = OrderNoGenerater.generateM(EGeneratePrefix.PAY_GROUP
            .getCode());
        order.setPayGroup(payGroup);
        order.setPayType(payType.getCode());
        sorderDAO.updatePayGroup(order);
        return payGroup;
    }

    @Override
    public int refreshPaySuccess(Sorder order, Long payAmount1,
            Long payAmount2, Long payAmount3, String payCode) {
        int count = 0;
        if (order != null && StringUtils.isNotBlank(order.getCode())) {
            Date now = new Date();
            order.setStatus(EVorderStatus.PAYED.getCode());
            order.setPayAmount1(payAmount1);
            order.setPayAmount2(payAmount2);
            order.setPayAmount3(payAmount3);
            order.setPayCode(payCode);
            order.setPayDatetime(now);
            order.setRemark("订单已成功支付");
            count = sorderDAO.updatePaySuccess(order);
        }
        return count;
    }

    @Override
    public void deliver(Sorder order, String handleUser, String remark) {
        order.setStatus(EVorderStatus.DELIVER.getCode());
        order.setHandleUser(handleUser);
        order.setHandleDatetime(new Date());
        order.setRemark(remark);
        sorderDAO.deliverOrder(order);
    }

    @Override
    public void cancelSorder(Sorder order, String handleUser, String remark) {
        order.setStatus(EVorderStatus.CANCEL.getCode());
        order.setHandleUser(handleUser);
        order.setHandleDatetime(new Date());
        order.setRemark(remark);
        sorderDAO.deliverOrder(order);
    }

    @Override
    public List<Sorder> querySorderList(Sorder condition) {
        return sorderDAO.selectList(condition);
    }

    @Override
    public List<Sorder> queryOrderListByPayGroup(String payGroup) {
        Sorder condition = new Sorder();
        condition.setPayGroup(payGroup);
        return sorderDAO.selectList(condition);
    }
}
