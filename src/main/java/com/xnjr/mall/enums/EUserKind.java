/**
 * @Title UserKind.java 
 * @Package com.ibis.pz.enums 
 * @Description 
 * @author miyb  
 * @date 2015-3-7 上午8:51:05 
 * @version V1.0   
 */
package com.xnjr.mall.enums;

/** 
 * @author: miyb 
 * @since: 2015-3-7 上午8:51:05 
 * @history:
 */
public enum EUserKind {
    F1("f1", "C端会员"), F2("f2", "B端商家"), Operator("01", "平台"), Partner("11",
            "合伙人"),

    // 健康e购
    JKEG_OPERATOR("operator", "市/区运营商"), JKEG_O2O("o2o", "o2o商家"), JKEG_SUPPLIER(
            "supplier", "供应商"), JKEG_MINGSU("mingsu", "名宿主");

    EUserKind(String code, String value) {
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
