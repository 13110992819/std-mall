package com.xnjr.mall.dao;

import com.xnjr.mall.dao.base.IBaseDAO;
import com.xnjr.mall.domain.Vproduct;

public interface IVproductDAO extends IBaseDAO<Vproduct> {
    String NAMESPACE = IVproductDAO.class.getName().concat(".");

    public int putOff(Vproduct product);

    public int putOn(Vproduct product);

    public int update(Vproduct product);
}
