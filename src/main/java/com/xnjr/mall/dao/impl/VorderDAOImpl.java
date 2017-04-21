package com.xnjr.mall.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xnjr.mall.dao.IVorderDAO;
import com.xnjr.mall.dao.base.support.AMybatisTemplate;
import com.xnjr.mall.domain.Vorder;

/**
 * 
 * @author: asus 
 * @since: 2017年4月20日 下午7:54:03 
 * @history:
 */
@Repository("vorderDAOImpl")
public class VorderDAOImpl extends AMybatisTemplate implements IVorderDAO {

    @Override
    public int insert(Vorder data) {
        return super.insert(NAMESPACE.concat("insert_vorder"), data);
    }

    @Override
    public int delete(Vorder data) {
        return 0;
    }

    @Override
    public Vorder select(Vorder condition) {
        return super.select(NAMESPACE.concat("select_vorder"), condition,
            Vorder.class);
    }

    @Override
    public Long selectTotalCount(Vorder condition) {
        return super.selectTotalCount(NAMESPACE.concat("select_vorder_count"),
            condition);
    }

    @Override
    public List<Vorder> selectList(Vorder condition) {
        return super.selectList(NAMESPACE.concat("select_vorder"), condition,
            Vorder.class);
    }

    @Override
    public List<Vorder> selectList(Vorder condition, int start, int count) {
        return super.selectList(NAMESPACE.concat("select_vorder"), start,
            count, condition, Vorder.class);
    }

    @Override
    public int payOrderByCGB(Vorder order) {
        return super.update(NAMESPACE.concat("update_payOrderByCGB"), order);
    }

    @Override
    public int cancelOrder(Vorder order) {
        return super.update(NAMESPACE.concat("update_cancelOrder"), order);
    }

    @Override
    public int deliverOrder(Vorder order) {
        return super.update(NAMESPACE.concat("update_deliverOrder"), order);
    }

}
