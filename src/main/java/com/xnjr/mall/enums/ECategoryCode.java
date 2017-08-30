package com.xnjr.mall.enums;

public enum ECategoryCode {
    PRODUCT_JF("J01", "商品分类"), RENT_PRODUCT_JF("J04", "租赁商品分类");

    ECategoryCode(String code, String value) {
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
