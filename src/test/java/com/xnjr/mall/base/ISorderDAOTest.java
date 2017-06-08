package com.xnjr.mall.base;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import com.xnjr.mall.dao.ISorderDAO;
import com.xnjr.mall.domain.Sorder;

public class ISorderDAOTest extends ADAOTest {
    @SpringBeanByType
    private ISorderDAO sorderDAO;

    @Test
    public void insert() {
        Sorder data = new Sorder();
        data.setCode("stock001");

        data.setCompanyCode("companyCode");
        data.setSystemCode("systemCode");
        sorderDAO.insert(data);
        logger.info("insert : {}");
    }
}
