package com.xnjr.mall.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xnjr.mall.dao.IOrderDAO;
import com.xnjr.mall.dao.base.support.AMybatisTemplate;
import com.xnjr.mall.domain.Order;

/**
 * @author: haiqingzheng 
 * @since: 2016年11月16日 下午3:11:49 
 * @history:
 */
@Repository("orderDAOImpl")
public class OrderDAOImpl extends AMybatisTemplate implements IOrderDAO {

    /**
     * @see com.xnjr.mall.dao.base.IBaseDAO#insert(java.lang.Object)
     */
    @Override
    public int insert(Order data) {
        return super.insert(NAMESPACE.concat("insert_order"), data);
    }

    /**
     * @see com.xnjr.mall.dao.base.IBaseDAO#delete(java.lang.Object)
     */
    @Override
    public int delete(Order data) {
        return super.delete(NAMESPACE.concat("delete_order"), data);
    }

    /**
     * @see com.xnjr.mall.dao.base.IBaseDAO#select(java.lang.Object)
     */
    @Override
    public Order select(Order condition) {
        return super.select(NAMESPACE.concat("select_order"), condition,
            Order.class);
    }

    /**
     * @see com.xnjr.mall.dao.base.IBaseDAO#selectTotalCount(java.lang.Object)
     */
    @Override
    public Long selectTotalCount(Order condition) {
        return super.selectTotalCount(NAMESPACE.concat("select_order_count"),
            condition);
    }

    /**
     * @see com.xnjr.mall.dao.base.IBaseDAO#selectList(java.lang.Object)
     */
    @Override
    public List<Order> selectList(Order condition) {
        return super.selectList(NAMESPACE.concat("select_order"), condition,
            Order.class);
    }

    /**
     * @see com.xnjr.mall.dao.base.IBaseDAO#selectList(java.lang.Object, int, int)
     */
    @Override
    public List<Order> selectList(Order condition, int start, int count) {
        return super.selectList(NAMESPACE.concat("select_order"), start, count,
            condition, Order.class);
    }

    @Override
    public int updateUserCancel(Order data) {
        return super.update(NAMESPACE.concat("update_userCancel"), data);
    }

    @Override
    public int updatePlatCancel(Order data) {
        return super.update(NAMESPACE.concat("update_platCancel"), data);
    }

    /** 
     * @see com.xnjr.mall.dao.IOrderDAO#updatePaySuccess(com.xnjr.mall.domain.Order)
     */
    @Override
    public int updatePaySuccess(Order data) {
        return super.update(NAMESPACE.concat("update_paySuccess"), data);
    }

    @Override
    public int updateDeliverLogistics(Order data) {
        return super.update(NAMESPACE.concat("update_deliverLogistics"), data);
    }

    @Override
    public int updateDeliverXianchang(Order data) {
        return super.update(NAMESPACE.concat("update_deliverXianchang"), data);
    }

    @Override
    public int updateConfirm(Order data) {
        return super.update(NAMESPACE.concat("update_confirm"), data);
    }

    @Override
    public int updatePayGroup(Order data) {
        return super.update(NAMESPACE.concat("update_payGroup"), data);
    }

    @Override
    public Long selectTotalAmount(Order condition) {
        return super.selectTotalCount(NAMESPACE.concat("select_totalAmount"),
            condition);
    }

    @Override
    public Long selectXFAmount(String userId) {
        return super.select(NAMESPACE.concat("select_XF_Amount"), userId,
            Long.class);
    }
}
