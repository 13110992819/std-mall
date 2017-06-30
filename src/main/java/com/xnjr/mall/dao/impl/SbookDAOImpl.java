package com.xnjr.mall.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xnjr.mall.dao.ISbookDAO;
import com.xnjr.mall.dao.base.support.AMybatisTemplate;
import com.xnjr.mall.domain.Sbook;

@Repository("sbookDAOImpl")
public class SbookDAOImpl extends AMybatisTemplate implements ISbookDAO {

    @Override
    public int insert(Sbook data) {
        return super.insert(NAMESPACE.concat("insert_sbook"), data);
    }

    @Override
    public int delete(Sbook data) {
        return super.delete(NAMESPACE.concat("delete_sbook"), data);
    }

    @Override
    public Sbook select(Sbook condition) {
        return super.select(NAMESPACE.concat("select_sbook"), condition,
            Sbook.class);
    }

    @Override
    public Long selectTotalCount(Sbook condition) {
        return super.selectTotalCount(NAMESPACE.concat("select_sbook_count"),
            condition);
    }

    @Override
    public List<Sbook> selectList(Sbook condition) {
        return super.selectList(NAMESPACE.concat("select_sbook"), condition,
            Sbook.class);
    }

    @Override
    public List<Sbook> selectList(Sbook condition, int start, int count) {
        return super.selectList(NAMESPACE.concat("select_sbook"), start, count,
            condition, Sbook.class);
    }

    @Override
    public int update(Sbook data) {
        return super.update(NAMESPACE.concat("update_sbook"), data);
    }

}
