package com.xnjr.mall.dto.req;

import java.util.List;

public class XN808651Req {
    // 编号（必填）
    private List<String> codeList;

    // 支付渠道（必填）
    private String payType;

    public List<String> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<String> codeList) {
        this.codeList = codeList;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

}
