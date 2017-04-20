package com.xnjr.mall.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xnjr.mall.bo.IVproductBO;
import com.xnjr.mall.bo.base.PaginableBOImpl;
import com.xnjr.mall.dao.IVproductDAO;
import com.xnjr.mall.domain.Vproduct;
import com.xnjr.mall.enums.EVproductStatus;
import com.xnjr.mall.exception.BizException;

@Component
public class VproductBOImpl extends PaginableBOImpl<Vproduct> implements
        IVproductBO {
    @Autowired
    private IVproductDAO vproductDAO;

    @Override
    public void saveVproduct(Vproduct product) {
        vproductDAO.insert(product);
    }

    @Override
    public int removeVproduct(String code) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Vproduct data = new Vproduct();
            data.setCode(code);
            count = vproductDAO.delete(data);
        }
        return count;
    }

    @Override
    public int refreshVproduct(Vproduct product) {
        int count = 0;
        if (StringUtils.isNotBlank(product.getCode())) {
            count = vproductDAO.update(product);
        }
        return count;
    }

    @Override
    public int putOff(String code, String updater, String remark) {
        Vproduct product = new Vproduct();
        product.setCode(code);
        product.setStatus(EVproductStatus.PUBLISH_NO.getCode());
        product.setUpdater(updater);
        product.setUpdateDatetime(new Date());
        product.setRemark(remark);
        return vproductDAO.putOff(product);
    }

    @Override
    public int putOn(Vproduct product) {
        return vproductDAO.putOn(product);
    }

    @Override
    public List<Vproduct> queryVproductList(Vproduct condition) {
        return vproductDAO.selectList(condition);
    }

    @Override
    public Vproduct getVproduct(String code) {
        Vproduct data = null;
        if (StringUtils.isNotBlank(code)) {
            Vproduct condition = new Vproduct();
            condition.setCode(code);
            data = vproductDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "产品不存在");
            }
        }
        return data;
    }

}
