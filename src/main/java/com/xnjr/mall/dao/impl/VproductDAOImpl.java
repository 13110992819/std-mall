package com.xnjr.mall.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xnjr.mall.dao.IVproductDAO;
import com.xnjr.mall.dao.base.support.AMybatisTemplate;
import com.xnjr.mall.domain.Vproduct;

/**
 * 
 * @author: asus 
 * @since: 2017年4月20日 下午7:54:03 
 * @history:
 */
@Repository("vproductDAOImpl")
public class VproductDAOImpl extends AMybatisTemplate implements IVproductDAO {

    @Override
    public int insert(Vproduct data) {
        return super.insert(NAMESPACE.concat("insert_vproduct"), data);
    }

    @Override
    public int delete(Vproduct data) {
        return super.delete(NAMESPACE.concat("delete_vproduct"), data);
    }

    @Override
    public Vproduct select(Vproduct condition) {
        return super.select(NAMESPACE.concat("select_vproduct"), condition,
            Vproduct.class);
    }

    @Override
    public Long selectTotalCount(Vproduct condition) {
        return super.selectTotalCount(
            NAMESPACE.concat("select_vproduct_count"), condition);
    }

    @Override
    public List<Vproduct> selectList(Vproduct condition) {
        return super.selectList(NAMESPACE.concat("select_vproduct"), condition,
            Vproduct.class);
    }

    @Override
    public List<Vproduct> selectList(Vproduct condition, int start, int count) {
        return super.selectList(NAMESPACE.concat("select_vproduct"), start,
            count, condition, Vproduct.class);
    }

    @Override
    public int putOff(Vproduct product) {
        return super.update(NAMESPACE.concat("update_putOff"), product);
    }

    @Override
    public int putOn(Vproduct product) {
        return super.update(NAMESPACE.concat("update_putOn"), product);
    }

    @Override
    public int update(Vproduct product) {
        return super.update(NAMESPACE.concat("update_vproduct"), product);
    }

}
