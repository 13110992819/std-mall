package com.xnjr.mall.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xnjr.mall.bo.IVorderBO;
import com.xnjr.mall.bo.base.PaginableBOImpl;
import com.xnjr.mall.core.OrderNoGenerater;
import com.xnjr.mall.dao.IVorderDAO;
import com.xnjr.mall.domain.Vorder;
import com.xnjr.mall.enums.EGeneratePrefix;
import com.xnjr.mall.enums.EPayType;
import com.xnjr.mall.enums.EVorderStatus;
import com.xnjr.mall.exception.BizException;

@Component
public class VorderBOImpl extends PaginableBOImpl<Vorder> implements IVorderBO {
    @Autowired
    private IVorderDAO vorderDAO;

    @Override
    public void saveOrder(Vorder data) {
        vorderDAO.insert(data);
    }

    @Override
    public void payOrderByCGB(Vorder order) {
        order.setStatus(EVorderStatus.PAYED.getCode());
        order.setPayType(EPayType.INTEGRAL.getCode());
        order.setPayGroup(OrderNoGenerater.generateM(EGeneratePrefix.PRODUCT
            .getCode()));
        order.setPayCode(null);
        order.setPayDatetime(new Date());
        vorderDAO.payOrderByCGB(order);
    }

    @Override
    public void cancelOrder(Vorder order, String updater, String remark) {
        order.setStatus(EVorderStatus.CANCEL.getCode());
        order.setHandleUser(updater);
        order.setHandleDatetime(new Date());
        order.setRemark(remark);
        vorderDAO.cancelOrder(order);
    }

    @Override
    public void deliverOrder(Vorder order, String updater, String remark) {
        order.setStatus(EVorderStatus.DELIVER.getCode());
        order.setHandleUser(updater);
        order.setHandleDatetime(new Date());
        order.setRemark(remark);
        vorderDAO.deliverOrder(order);
    }

    @Override
    public List<Vorder> queryVorderList(Vorder condition) {
        return vorderDAO.selectList(condition);
    }

    @Override
    public Vorder getVorder(String code) {
        Vorder data = null;
        if (StringUtils.isNotBlank(code)) {
            Vorder condition = new Vorder();
            condition.setCode(code);
            data = vorderDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "订单不存在");
            }
        }
        return data;
    }

}
