package com.xnjr.mall.dao;

import com.xnjr.mall.dao.base.IBaseDAO;
import com.xnjr.mall.domain.Sproduct;

public interface ISproductDAO extends IBaseDAO<Sproduct> {
    String NAMESPACE = ISproductDAO.class.getName().concat(".");

    public int putOff(Sproduct product);

    public int putOn(Sproduct product);

    public int update(Sproduct product);
}
