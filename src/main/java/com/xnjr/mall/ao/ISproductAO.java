package com.xnjr.mall.ao;

import java.util.List;

import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.Sproduct;

public interface ISproductAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public String saveSproductAO(String name, String category, String type,
            String storeCode, String slogan, String advPic, String pic,
            String description, Long amount, Integer totalNum,
            String companyCode, String systemCode);

    public void dropSproduct(String code);

    public void editSproduct(String code, String name, String category,
            String type, String slogan, String advPic, String pic,
            String description, Long amount, Integer totalNum);

    public void putOn(String code, String location, Integer orderNo);

    public void putOff(String code);

    public Paginable<Sproduct> querySproductPage(int start, int limit,
            Sproduct condition);

    public Sproduct getSproduct(String code);

    public List<Sproduct> querySproductList(Sproduct condition);

}
