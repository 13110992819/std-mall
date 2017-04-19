package com.xnjr.mall.enums;

public enum EO2OPayType {
    ZH_YE("1", "正汇余额"), WEIXIN_APP("2", "微信APP"), WEIXIN_H5("5", "微信H5"), ALIPAY(
            "3", "支付宝"), CG_O2O_CGB("31", "菜狗020菜狗币支付"), CG_02O_RMBJF("32",
            "菜狗020人民币积分支付"), GD_YE("40", "管道余额支付");

    EO2OPayType(String code, String value) {
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
