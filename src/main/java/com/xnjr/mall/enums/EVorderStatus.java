package com.xnjr.mall.enums;

public enum EVorderStatus {
    TOPAY("0", "待支付"), PAYED("1", "已支付"), DELIVER("2", "已兑换"), CANCEL("3",
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
