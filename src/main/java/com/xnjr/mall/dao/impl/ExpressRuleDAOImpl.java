package com.xnjr.mall.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xnjr.mall.dao.IExpressRuleDAO;
import com.xnjr.mall.dao.base.support.AMybatisTemplate;
import com.xnjr.mall.domain.ExpressRule;

@Repository("expressRuleDAOImpl")
public class ExpressRuleDAOImpl extends AMybatisTemplate implements
        IExpressRuleDAO {

    @Override
    public int insert(ExpressRule data) {
        return super.insert(NAMESPACE.concat("insert_expressRule"), data);
    }

    @Override
    public int delete(ExpressRule data) {
        return super.delete(NAMESPACE.concat("delete_expressRule"), data);
    }

    @Override
    public ExpressRule select(ExpressRule condition) {
        return super.select(NAMESPACE.concat("select_expressRule"), condition,
            ExpressRule.class);
    }

    @Override
    public Long selectTotalCount(ExpressRule condition) {
        return super.selectTotalCount(
            NAMESPACE.concat("select_expressRule_count"), condition);
    }

    @Override
    public List<ExpressRule> selectList(ExpressRule condition) {
        return super.selectList(NAMESPACE.concat("select_expressRule"),
            condition, ExpressRule.class);
    }

    @Override
    public List<ExpressRule> selectList(ExpressRule condition, int start,
            int count) {
        return super.selectList(NAMESPACE.concat("select_expressRule"), start,
            count, condition, ExpressRule.class);
    }

    @Override
    public int update(ExpressRule data) {
        return super.update(NAMESPACE.concat("update_expressRule"), data);
    }

    @Override
    public int updatePrice(ExpressRule data) {
        return super.update(NAMESPACE.concat("update_price"), data);
    }

}
