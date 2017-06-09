package com.xnjr.mall.base;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import com.xnjr.mall.dao.ISproductDAO;
import com.xnjr.mall.domain.Sproduct;
import com.xnjr.mall.enums.ESproductStatus;

public class ISproductDAOTest extends ADAOTest {
    @SpringBeanByType
    private ISproductDAO sproductDAO;

    @Test
    public void insert() {
        Sproduct data = new Sproduct();
        data.setCode("code");
        data.setName("name");
        data.setCategory("category");
        data.setType("type");
        data.setStoreCode("storeCode");

        data.setSlogan("slogan");
        data.setAdvPic("advPic");
        data.setPic("pic");
        data.setDescription("description");
        data.setPrice(100000L);

        data.setStatus(ESproductStatus.PUBLISH_YES.getCode());
        data.setTotalNum(100);
        data.setRemainNum(0);
        // data.setLocation("location");
        // data.setOrderNo(1);

        data.setCompanyCode("companyCode");
        data.setSystemCode("systemCode");
        sproductDAO.insert(data);
        logger.info("insert : {}");
    }

    @Test
    public void select() {
        Sproduct condition = new Sproduct();
        condition.setCode("code");
        Sproduct data = sproductDAO.select(condition);
        logger.info("select : {}", data);
    }

}
