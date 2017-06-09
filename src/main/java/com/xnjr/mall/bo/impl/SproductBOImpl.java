package com.xnjr.mall.bo.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xnjr.mall.bo.ISproductBO;
import com.xnjr.mall.bo.base.PaginableBOImpl;
import com.xnjr.mall.dao.ISproductDAO;
import com.xnjr.mall.domain.Sproduct;
import com.xnjr.mall.enums.ESproductStatus;
import com.xnjr.mall.exception.BizException;

@Component
public class SproductBOImpl extends PaginableBOImpl<Sproduct> implements
        ISproductBO {
    @Autowired
    private ISproductDAO sproductDAO;

    @Override
    public void addSproduct(Sproduct data) {
        sproductDAO.insert(data);
    }

    @Override
    public Sproduct getSproduct(String code) {
        Sproduct data = null;
        if (StringUtils.isNotBlank(code)) {
            Sproduct condition = new Sproduct();
            condition.setCode(code);
            data = sproductDAO.select(condition);
        }
        if (null == data) {
            throw new BizException("xn0000", "服务编号不存在");
        }
        return data;
    }

    @Override
    public void removeSproduct(Sproduct data) {
        sproductDAO.delete(data);
    }

    @Override
    public void refreshSproduct(Sproduct data) {
        sproductDAO.update(data);
    }

    @Override
    public List<Sproduct> quarySproductList(String location, Integer orderNo) {
        Sproduct condition = new Sproduct();
        condition.setStatus(ESproductStatus.PUBLISH_YES.getCode());
        condition.setLocation(location);
        condition.setOrderNo(orderNo);
        return sproductDAO.selectList(condition);
    }

    @Override
    public void putOn(Sproduct data, String location, Integer orderNo,
            Long price) {
        data.setStatus(ESproductStatus.PUBLISH_YES.getCode());
        data.setLocation(location);
        data.setOrderNo(orderNo);
        data.setPrice(price);
        sproductDAO.putOn(data);
    }

    @Override
    public void putOff(Sproduct data) {
        data.setStatus(ESproductStatus.PUBLISH_NO.getCode());
        sproductDAO.putOff(data);
    }

    @Override
    public List<Sproduct> querySproductList(Sproduct condition) {
        return sproductDAO.selectList(condition);
    }

    @Override
    public void refreshSproduct(Sproduct sproduct, Integer remainNum) {
        sproduct.setRemainNum(remainNum);
        sproductDAO.updateRemainNum(sproduct);
    }

}
