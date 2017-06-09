package com.xnjr.mall.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: xieyj 
 * @since: 2016年11月11日 上午10:09:32 
 * @history:
 */
public enum EBizType {
    AJ_GW("-30", "购物"), AJ_GWTK("30", "购物退款"), AJ_QRSH("32", "确认收货，商户收钱"), AJ_GMZKQ(
            "-40", "购买折扣券")

    , CG_HB2CGB("211", "嗨币兑换菜狗币"), CG_O2O_CGB("90", "菜狗O2O菜狗币支付"), CG_O2O_CGBFD(
            "91", "菜狗O2O菜狗币返点人民币"), CG_O2O_RMB("92", "菜狗O2O人民币支付"), CG_O2O_CGJF(
            "93", "菜狗O2O积分支付"), CG_O2O_RMBFD("95", "菜狗O2O人民币支付返点菜狗币"), CG_XNCZ_P(
            "94", "菜狗充值专区用款"), CG_XNCZ_M("-94", "菜狗充值专区退款")

    , GD_MALL("GD_MALL", "积分商城消费"), GD_O2O("GD_O2O", "O2O店铺积分消费"), EXCHANGE_CURRENCY(
            "200", "币种兑换")

    , YC_O2O_RMB("YC_O2O_RMB", "姚橙O2O人民币支付"), YC_O2O_RMBFD("YC_O2O_RMBFD",
            "姚橙O2O人民币支付返橙券"), YC_O2O_CB("YC_O2O_CB", "姚橙O2O橙券支付"), YC_O2O_CBFD(
            "YC_O2O_CBFD", "姚橙O2O橙券支付返人民币"), YC_MALL("YC_MALL", "姚橙商城购物支付"), YC_MALL_BACK(
            "YC_MALL_BACK", "姚橙商城购物退款"), YC_XNCZ_P("YC_XNCZ_P", "姚橙充值专区支付"), YC_XNCZ_M(
            "YC_XNCZ_M", "姚橙充值专区退款"),

    FW("FW", "服务购买"), FWTK("FWTK", "服务购买退款");
    public static Map<String, EBizType> getBizTypeMap() {
        Map<String, EBizType> map = new HashMap<String, EBizType>();
        for (EBizType bizType : EBizType.values()) {
            map.put(bizType.getCode(), bizType);
        }
        return map;
    }

    EBizType(String code, String value) {
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
