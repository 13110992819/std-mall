package com.xnjr.mall.enums;

public enum EVorderStatus {
    Topay("1", "待支付"), Payed("2", "已支付"), Delivered("3", "已发货"), Canceled("4",
            "已取消");

    EVorderStatus(String code, String value) {
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
