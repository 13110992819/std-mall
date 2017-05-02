package com.xnjr.mall.dto.req;

public class XN808037Req {

    // 产品名称(必填)
    private String productCode;

    // key(选填)
    private String dkey;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDkey() {
        return dkey;
    }

    public void setDkey(String dkey) {
        this.dkey = dkey;
    }

}
