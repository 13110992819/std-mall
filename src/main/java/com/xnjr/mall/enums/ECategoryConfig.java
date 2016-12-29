/**
 * @Title ECheckResult.java 
 * @Package com.ibis.account.enums 
 * @Description 
 * @author miyb  
 * @date 2015-2-26 下午2:58:54 
 * @version V1.0   
 */
package com.xnjr.mall.enums;

/** 
 * @author: miyb 
 * @since: 2015-2-26 下午2:58:54 
 * @history:
 */
public enum ECategoryConfig {
    // 分类(A 商品运费，B 分销规则，C摇一摇规则，D虚拟币规则，E定位规则)

    SPYF("A", "商品运费"), FXGZ("B", "分销规则"), YYYGZ("C", "摇一摇规则"), XNBGZ("D",
            "虚拟币规则"), DWGZ("E", "定位规则");

    ECategoryConfig(String code, String value) {
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
