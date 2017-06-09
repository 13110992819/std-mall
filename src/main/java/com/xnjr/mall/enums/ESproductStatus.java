package com.xnjr.mall.enums;

public enum ESproductStatus {
    TO_APPROVE("1", "待审核"), PUBLISH_YES("2", "已上架"), PUBLISH_NO("3", "已下架");

    ESproductStatus(String code, String value) {
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
