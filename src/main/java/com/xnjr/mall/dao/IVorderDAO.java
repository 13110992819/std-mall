package com.xnjr.mall.dao;

import com.xnjr.mall.dao.base.IBaseDAO;
import com.xnjr.mall.domain.Vorder;

public interface IVorderDAO extends IBaseDAO<Vorder> {
    String NAMESPACE = IVorderDAO.class.getName().concat(".");

    public int payOrderByCGB(Vorder order);

    public int cancelOrder(Vorder order);

    public int deliverOrder(Vorder order);
}
