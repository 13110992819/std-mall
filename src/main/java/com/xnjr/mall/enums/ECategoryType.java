package com.xnjr.mall.enums;

public enum ECategoryType {
    Product("1", "商品分类"), Store("2", "店铺分类"), Serve("3", "服务分类"), PRODUCT_RENT(
            "4", "租赁商品");
    ECategoryType(String code, String value) {
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
