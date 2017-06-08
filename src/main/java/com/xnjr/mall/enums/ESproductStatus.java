package com.xnjr.mall.enums;

public enum ESproductStatus {
    MAN_NO("0", "未满"), MAN_YES("1", "已满");

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
