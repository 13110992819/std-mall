package com.xnjr.mall.enums;

/**
 * @author: xieyj 
 * @since: 2016年11月11日 上午10:09:32 
 * @history:
 */
public enum EPayType {
    YE("1", "余额"), WEIXIN_APP("2", "微信APP"), ALIPAY("3", "支付宝"), WEIXIN_H5("5",
            "微信H5"), INTEGRAL("90", "单一虚拟币支付")

    , CG_YE("21", "菜狗余额支付")

    , GD_YE("40", "管道余额支付")

    , YC_CB("50", "姚橙橙券支付"), CG_RMBJF_WEIXIN_H5("6", "菜狗020人民币积分微信H5支付"), CG_O2O_CGB(
            "31", "菜狗020菜狗币支付"), CG_02O_RMBJF("32", "菜狗020人民币积分支付"), CG_02O_RMB(
            "33", "菜狗020人民币支付");

    EPayType(String code, String value) {
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
