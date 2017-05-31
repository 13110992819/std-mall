package com.xnjr.mall.dto.req;

/**
 * @author: xieyj 
 * @since: 2017年5月27日 下午5:35:35 
 * @history:
 */
public class XN808268Req {

    // 店铺编号（必填）
    private String storeCode;

    // 系统编号（必填）
    private String systemCode;

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }
}
