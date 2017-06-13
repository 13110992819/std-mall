package com.xnjr.mall.ao;

import java.util.List;

import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.Sorder;
import com.xnjr.mall.dto.req.XN808450Req;

public interface ISorderAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public String commitOrder(XN808450Req req);

    public Object toPayOrder(String code, String payType);

    public void paySuccess(String payGroup, String payCode, Long amount);

    public void deliverOrder(String code, String handleUser, String remark);

    public void cancelOrder(String code, String handleUser, String remark);

    public Paginable<Sorder> querySorderPage(int start, int limit,
            Sorder condition);

    public Sorder getSorder(String code);

    public List<Sorder> querySorderList(Sorder condition);

}
