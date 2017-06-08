/**
 * @Title EProductKind.java 
 * @Package com.xnjr.mall.enums 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年6月8日 上午7:01:30 
 * @version V1.0   
 */
package com.xnjr.mall.enums;

/** 
 * @author: haiqingzheng 
 * @since: 2017年6月8日 上午7:01:30 
 * @history:
 */
public enum EProductKind {

    NORMAL("1", "标准商城"), INTEGRAL("2", "积分商城");

    EProductKind(String code, String value) {
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
