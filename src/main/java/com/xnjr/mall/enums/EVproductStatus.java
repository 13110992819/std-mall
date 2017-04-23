package com.xnjr.mall.enums;

public enum EVproductStatus {
    TO_PUBLISH("0", "待上架"), PUBLISH_YES("1", "已上架"), PUBLISH_NO("2", "已下架");

    EVproductStatus(String code, String value) {
        this.code = code;
        this.value = value;
    }

    private String code;

    private String value;

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
