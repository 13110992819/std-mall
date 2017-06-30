package com.xnjr.mall.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xnjr.mall.bo.ISbookBO;
import com.xnjr.mall.bo.base.PaginableBOImpl;
import com.xnjr.mall.core.OrderNoGenerater;
import com.xnjr.mall.dao.ISbookDAO;
import com.xnjr.mall.domain.Sbook;
import com.xnjr.mall.enums.EGeneratePrefix;
import com.xnjr.mall.exception.BizException;

@Component
public class SbookBOImpl extends PaginableBOImpl<Sbook> implements ISbookBO {

    @Autowired
    private ISbookDAO sbookDAO;

    @Override
    public boolean isSbookExist(String code) {
        Sbook condition = new Sbook();
        condition.setCode(code);
        if (sbookDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String saveSbook(String sproductCode, Date date, Integer remain) {
        String code = null;
        Sbook data = new Sbook();
        code = OrderNoGenerater.generateM(EGeneratePrefix.SBOOK.getCode());
        data.setCode(code);
        data.setSproductCode(sproductCode);
        data.setBookDate(date);
        data.setRemain(remain);
        sbookDAO.insert(data);
        return code;
    }

    @Override
    public int removeSbook(String code) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Sbook data = new Sbook();
            data.setCode(code);
            count = sbookDAO.delete(data);
        }
        return count;
    }

    @Override
    public int refreshSbook(String code, Integer remain) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Sbook data = new Sbook();
            data.setCode(code);
            data.setRemain(remain);
            count = sbookDAO.update(data);
        }
        return count;
    }

    @Override
    public List<Sbook> querySbookList(Sbook condition) {
        return sbookDAO.selectList(condition);
    }

    @Override
    public Sbook getSbook(String code) {
        Sbook data = null;
        if (StringUtils.isNotBlank(code)) {
            Sbook condition = new Sbook();
            condition.setCode(code);
            data = sbookDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "没有该天的订房信息");
            }
        }
        return data;
    }

    @Override
    public List<Sbook> querySbookList(String sproductCode, Date date) {
        Sbook condition = new Sbook();
        condition.setSproductCode(sproductCode);
        condition.setBookDate(date);
        return sbookDAO.selectList(condition);
    }
}
