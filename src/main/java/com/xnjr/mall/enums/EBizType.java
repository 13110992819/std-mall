package com.xnjr.mall.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: xieyj 
 * @since: 2016年11月11日 上午10:09:32 
 * @history:
 */
public enum EBizType {
    AJ_GW("-30", "购物"), AJ_GWTK("30", "购物退款"), AJ_QRSH("42", "确认收货，商户收钱"), AJ_DPXF(
            "-31", "店铺消费"), AJ_GMZKQ("-32", "购买折扣券"), AJ_HB2FR("50", "红包兑分润"), AJ_HBYJ2FR(
            "52", "红包业绩兑分润"), AJ_HBYJ2GXJL("54", "红包业绩兑贡献奖励"), AJ_FR2RMB("56",
            "分润兑人民币"), AJ_GXJL2RMB("58", "贡献奖励兑人民币");

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
