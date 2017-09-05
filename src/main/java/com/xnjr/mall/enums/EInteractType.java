/**
 * @Title EDirection.java 
 * @Package com.ibis.account.enums 
 * @Description 
 * @author miyb  
 * @date 2015-2-26 下午3:37:06 
 * @version V1.0   
 */
package com.xnjr.mall.enums;

import java.util.HashMap;
import java.util.Map;

/** 
 * @author: miyb 
 * @since: 2015-2-26 下午3:37:06 
 * @history:
 */
public enum EInteractType {
    PRODUCT("P", "产品"), NEWS("N", "资讯"), RENT_PRODUCT("RP", "租赁");

    public static Map<String, EInteractType> getMap() {
        Map<String, EInteractType> map = new HashMap<String, EInteractType>();
        for (EInteractType direction : EInteractType.values()) {
            map.put(direction.getCode(), direction);
        }
        return map;
    }

    EInteractType(String code, String value) {
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
