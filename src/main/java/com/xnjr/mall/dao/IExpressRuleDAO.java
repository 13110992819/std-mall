package com.xnjr.mall.dao;

import com.xnjr.mall.dao.base.IBaseDAO;
import com.xnjr.mall.domain.ExpressRule;

public interface IExpressRuleDAO extends IBaseDAO<ExpressRule> {
    String NAMESPACE = IExpressRuleDAO.class.getName().concat(".");

    int update(ExpressRule data);

    int updatePrice(ExpressRule data);

}
