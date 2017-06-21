package com.xnjr.mall.ao;

import java.util.List;

import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.Sproduct;
import com.xnjr.mall.dto.req.XN808400Req;
import com.xnjr.mall.dto.req.XN808402Req;

public interface ISproductAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public String saveSproduct(XN808400Req req);

    public void dropSproduct(String code);

    public void editSproduct(XN808402Req req);

    public void putOn(String code, String location, Integer orderNo, Long price);

    public void putOff(String code);

    public Paginable<Sproduct> querySproductPage(int start, int limit,
            Sproduct condition);

    public Sproduct getSproduct(String code);

    public List<Sproduct> querySproductList(Sproduct condition);

    // 重置服务可提供的数量
    public void resetAvaliableNumbers(String code);

    public void doResetAvaliableNumbersDaily();

}
