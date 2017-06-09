package com.xnjr.mall.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xnjr.mall.dao.ISproductDAO;
import com.xnjr.mall.dao.base.support.AMybatisTemplate;
import com.xnjr.mall.domain.Sproduct;

@Repository("sproductDAOImpl")
public class SproductDAOImpl extends AMybatisTemplate implements ISproductDAO {

    @Override
    public int insert(Sproduct data) {
        return super.insert(NAMESPACE.concat("insert_sproduct"), data);
    }

    @Override
    public int delete(Sproduct data) {
        return super.delete(NAMESPACE.concat("delete_sproduct"), data);
    }

    @Override
    public Sproduct select(Sproduct condition) {
        return super.select(NAMESPACE.concat("select_sproduct"), condition,
            Sproduct.class);
    }

    @Override
    public Long selectTotalCount(Sproduct condition) {
        return super.selectTotalCount(
            NAMESPACE.concat("select_sproduct_count"), condition);
    }

    @Override
    public List<Sproduct> selectList(Sproduct condition) {
        return super.selectList(NAMESPACE.concat("select_sproduct"), condition,
            Sproduct.class);
    }

    @Override
    public List<Sproduct> selectList(Sproduct condition, int start, int count) {
        return super.selectList(NAMESPACE.concat("select_sproduct"), start,
            count, condition, Sproduct.class);
    }

    @Override
    public int putOff(Sproduct product) {
        return super.update(NAMESPACE.concat("update_putOff"), product);
    }

    @Override
    public int putOn(Sproduct product) {
        return super.update(NAMESPACE.concat("update_putOn"), product);
    }

    @Override
    public int update(Sproduct product) {
        return super.update(NAMESPACE.concat("update_sproduct"), product);
    }

    @Override
    public int updateRemainNum(Sproduct product) {
        return super.update(NAMESPACE.concat("update_remainNum"), product);
    }

}
