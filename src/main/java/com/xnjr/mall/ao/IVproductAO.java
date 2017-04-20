package com.xnjr.mall.ao;

import java.util.List;

import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.Vproduct;
import com.xnjr.mall.dto.req.XN808600Req;
import com.xnjr.mall.dto.req.XN808602Req;
import com.xnjr.mall.dto.req.XN808603Req;

public interface IVproductAO {

    static final String DEFAULT_ORDER_COLUMN = "code";

    String addVproduct(XN808600Req req);

    void dropVproduct(String code);

    void editVproduct(XN808602Req req);

    void putOn(XN808603Req req);

    void putOff(String code, String updater, String remark);

    Paginable<Vproduct> queryVproductPage(int start, int limit,
            Vproduct condition);

    Vproduct getVproduct(String code);

    List<Vproduct> queryVproductList(Vproduct condition);

}
