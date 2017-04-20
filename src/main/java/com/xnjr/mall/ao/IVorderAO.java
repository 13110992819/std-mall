package com.xnjr.mall.ao;

import java.util.List;

import com.xnjr.mall.dto.req.XN808650Req;

public interface IVorderAO {

    String commitOrder(XN808650Req req);

    Object toPayOrder(List<String> codeList, String payType);

    void cancelOrder(List<String> codeList, String updater, String remark);

    void deliverOrder(List<String> codeList, String updater, String remark);

}
