package com.xnjr.mall.ao;

import java.util.List;

import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.Vorder;
import com.xnjr.mall.dto.req.XN808650Req;

public interface IVorderAO {

    String DEFAULT_ORDER_COLUMN = "code";

    String commitOrder(XN808650Req req);

    Object toPayOrder(List<String> codeList, String payType);

    void cancelOrder(String code, String updater, String remark);

    void deliverOrder(List<String> codeList, String updater, String remark);

    Paginable<Vorder> queryVorderPage(int start, int limit, Vorder condition);

    Vorder getVorder(String code);

    List<Vorder> queryVorderList(Vorder condition);

}
