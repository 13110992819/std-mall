package com.xnjr.mall.enums;

public enum EO2OPayType {
    ZH_YE("1", "正汇余额"), WEIXIN_APP("2", "微信APP"), ALIPAY("3", "支付宝"), WEIXIN_H5(
            "5", "微信H5"),

    CG_RMBJF_WEIXIN_H5("6", "菜狗020人民币积分微信H5支付"), CG_O2O_CGB("31", "菜狗020菜狗币支付"), CG_02O_RMBJF(
            "32", "菜狗020人民币积分支付"), CG_02O_RMB("33", "菜狗020人民币支付"),

    GD_YE("40", "管道余额支付"),

    YC_CB("50", "姚橙020橙币支付");

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
