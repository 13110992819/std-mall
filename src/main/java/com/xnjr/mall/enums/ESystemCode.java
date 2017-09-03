package com.xnjr.mall.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: xieyj 
 * @since: 2017年2月9日 下午8:10:43 
 * @history:
 */
public enum ESystemCode {
    CAIGO("CD-CCG000007", "菜狗"), PIPE("CD-CGD000006", "全能水电工助手"), YAOCHENG(
            "CD-CYC000009", "姚橙"), JKEG("CD-JKEG000011", "健康E购"), HW(
            "CD-CHW000015", "户外电商");

    public static Map<String, ESystemCode> getMap() {
        Map<String, ESystemCode> map = new HashMap<String, ESystemCode>();
        for (ESystemCode systemCode : ESystemCode.values()) {
            map.put(systemCode.getCode(), systemCode);
        }
        return map;
    }

    ESystemCode(String code, String value) {
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

    public static void main(String[] args) {

    }
}
