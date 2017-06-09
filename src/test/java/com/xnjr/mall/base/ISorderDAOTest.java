package com.xnjr.mall.base;

import java.util.Date;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import com.xnjr.mall.dao.ISorderDAO;
import com.xnjr.mall.domain.Sorder;
import com.xnjr.mall.enums.ESproductStatus;

public class ISorderDAOTest extends ADAOTest {
    @SpringBeanByType
    private ISorderDAO sorderDAO;

    @Test
    public void insert() {
        Sorder data = new Sorder();
        data.setCode("code");
        data.setProductCode("productCode");
        data.setCategory("category");
        data.setType("type");
        data.setStartDate(new Date());
        data.setEndDate(new Date());

        data.setReName("reName");
        data.setReMobile("reMobile");
        data.setApplyUser("applyUser");
        data.setApplyNote("applyNote");
        data.setApplyDatetime(new Date());

        data.setAmount1(100L);
        data.setAmount2(200L);
        data.setAmount3(300L);
        data.setPayAmount1(100L);
        data.setPayAmount2(200L);
        data.setPayAmount3(300L);

        data.setStatus(ESproductStatus.PUBLISH_YES.getCode());
        data.setCompanyCode("companyCode");
        data.setSystemCode("systemCode");
        sorderDAO.insert(data);
        logger.info("insert : {}");
    }

    @Test
    public void select() {
        Sorder condition = new Sorder();
        condition.setCode("code");
        Sorder data = sorderDAO.select(condition);
        logger.info("select : {}", data);
    }
}
