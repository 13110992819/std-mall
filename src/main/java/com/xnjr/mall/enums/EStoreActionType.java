package com.xnjr.mall.enums;

public enum EStoreActionType {
    DZ("1", "点赞"), SC("1", "点赞");

    EStoreActionType(String code, String value) {
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
