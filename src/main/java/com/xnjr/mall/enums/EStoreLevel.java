package com.xnjr.mall.enums;

public enum EStoreLevel {
    NOMAL("1", "普通商家"), MINGSU("2", "酒店名宿"), SUPPLIER("3", "供应商");

    EStoreLevel(String code, String value) {
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
