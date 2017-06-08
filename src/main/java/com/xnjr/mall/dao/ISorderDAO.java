package com.xnjr.mall.dao;

import com.xnjr.mall.dao.base.IBaseDAO;
import com.xnjr.mall.domain.Sorder;

public interface ISorderDAO extends IBaseDAO<Sorder> {
    String NAMESPACE = ISorderDAO.class.getName().concat(".");

    public int cancelOrder(Sorder order);

    public int deliverOrder(Sorder order);
}
