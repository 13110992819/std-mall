package com.xnjr.mall.common;

import java.util.Map;

import com.xnjr.mall.enums.ESystemCode;
import com.xnjr.mall.exception.BizException;

public class SystemUtil {

    public static String getSysUser(String systemCode) {
        String sysUser = "SYS_USER_";
        Map<String, ESystemCode> map = ESystemCode.getMap();
        ESystemCode eSystemCode = map.get(systemCode);
        if (eSystemCode != null) {
            sysUser = sysUser + eSystemCode.name();
        } else {
            throw new BizException("xn000000", "系统编号不存在，请联系管理员");
        }
        return sysUser;
    }
}
