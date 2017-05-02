package com.xnjr.mall.dao;

import com.xnjr.mall.dao.base.IBaseDAO;
import com.xnjr.mall.domain.Stock;
import com.xnjr.mall.enums.EStockStatus;

public interface IStockDAO extends IBaseDAO<Stock> {
    String NAMESPACE = IStockDAO.class.getName().concat(".");

    int doDailyStock(Stock ele);

    int updateCostAmount(Stock dbStock);

    int updateTOeffectStatus(Stock dbStock);

    int awakenStock(Stock data);

    int deleteStock(String userId, EStockStatus toEffect);

}
