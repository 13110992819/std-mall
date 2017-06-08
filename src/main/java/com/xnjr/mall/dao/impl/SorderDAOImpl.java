package com.xnjr.mall.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xnjr.mall.dao.ISorderDAO;
import com.xnjr.mall.dao.base.support.AMybatisTemplate;
import com.xnjr.mall.domain.Sorder;

@Repository("sorderDAOImpl")
public class SorderDAOImpl extends AMybatisTemplate implements ISorderDAO {

    @Override
    public int insert(Sorder data) {
        return super.insert(NAMESPACE.concat("insert_sorder"), data);
    }

    @Override
    public int delete(Sorder data) {
        return 0;
    }

    @Override
    public Sorder select(Sorder condition) {
        return super.select(NAMESPACE.concat("select_sorder"), condition,
            Sorder.class);
    }

    @Override
    public Long selectTotalCount(Sorder condition) {
        return super.selectTotalCount(NAMESPACE.concat("select_sorder_count"),
            condition);
    }

    @Override
    public List<Sorder> selectList(Sorder condition) {
        return super.selectList(NAMESPACE.concat("select_sorder"), condition,
            Sorder.class);
    }

    @Override
    public List<Sorder> selectList(Sorder condition, int start, int count) {
        return super.selectList(NAMESPACE.concat("select_sorder"), start,
            count, condition, Sorder.class);
    }

    @Override
    public int cancelOrder(Sorder order) {
        return super.update(NAMESPACE.concat("update_cancelOrder"), order);
    }

    @Override
    public int deliverOrder(Sorder order) {
        return super.update(NAMESPACE.concat("update_deliverOrder"), order);
    }

}
