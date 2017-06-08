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
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int delete(Sorder data) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Sorder select(Sorder condition) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long selectTotalCount(Sorder condition) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Sorder> selectList(Sorder condition) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Sorder> selectList(Sorder condition, int start, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int cancelOrder(Sorder order) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int deliverOrder(Sorder order) {
        // TODO Auto-generated method stub
        return 0;
    }

}
