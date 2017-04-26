package com.xnjr.mall.enums;

import java.util.HashMap;
import java.util.Map;

import com.xnjr.mall.exception.BizException;

public enum EVproductType {
    ZSH_CZ("1", "中石化充值"), ZSY_CZ("2", "中石油充值"), MOBILE_CZ("3", "手机充值");

    EVproductType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static Map<String, EVproductType> getResultMap() {
        Map<String, EVproductType> map = new HashMap<String, EVproductType>();
        for (EVproductType vproductType : EVproductType.values()) {
            map.put(vproductType.getCode(), vproductType);
        }
        return map;
    }

    public static EVproductType getEVproductType(String code) {
        Map<String, EVproductType> map = getResultMap();
        EVproductType vproductType = map.get(code);
        if (null == vproductType) {
            throw new BizException("xn0000", code + "对应币种不存在");
        }
        return vproductType;
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
