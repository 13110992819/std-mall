package com.xnjr.mall.dao;

import com.xnjr.mall.dao.base.IBaseDAO;
import com.xnjr.mall.domain.Sbook;

//dao层 
public interface ISbookDAO extends IBaseDAO<Sbook> {
    String NAMESPACE = ISbookDAO.class.getName().concat(".");

    public int update(Sbook data);
}
