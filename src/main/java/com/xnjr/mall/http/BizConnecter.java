/**
 * @Title BizConnecter.java 
 * @Package com.ibis.pz.http 
 * @Description 
 * @author miyb  
 * @date 2015-5-12 下午9:44:59 
 * @version V1.0   
 */
package com.xnjr.mall.http;

import java.util.Properties;

import com.xnjr.mall.common.PropertiesUtil;
import com.xnjr.mall.core.RegexUtils;
import com.xnjr.mall.exception.BizException;

/** 
 * @author: miyb 
 * @since: 2015-5-12 下午9:44:59 
 * @history:
 */
public class BizConnecter {
    public static final String YES = "0";

    public static final String USER_URL = PropertiesUtil.Config.USER_URL;

    public static final String ACCOUNT_URL = PropertiesUtil.Config.ACCOUNT_URL;

    public static final String CORE_URL = PropertiesUtil.Config.CORE_URL;

    public static final String POST_URL = "...";

    public static <T> T getBizData(String code, String json, Class<T> clazz) {
        String data = getBizData(code, json);
        return JsonUtils.json2Bean(data, clazz);
    }

    public static String getBizData(String code, String json) {
        String data = null;
        String resJson = null;
        try {
            Properties formProperties = new Properties();
            formProperties.put("code", code);
            formProperties.put("json", json);
            resJson = PostSimulater.requestPostForm(getPostUrl(code),
                formProperties);
        } catch (Exception e) {
            throw new BizException("Biz000", "链接请求超时，请联系管理员");
        }
        // 开始解析响应json
        String errorCode = RegexUtils.find(resJson, "errorCode\":\"(.+?)\"", 1);
        String errorInfo = RegexUtils.find(resJson, "errorInfo\":\"(.+?)\"", 1);
        if (YES.equalsIgnoreCase(errorCode)) {
            data = RegexUtils.find(resJson, "data\":(.*)\\}", 1);
        } else {
            throw new BizException("Biz000", errorInfo);
        }
        return data;
    }

    private static String getPostUrl(String code) {
        String postUrl = POST_URL;
        if (code.startsWith("805") || code.startsWith("806")
                || code.startsWith("807") || code.startsWith("001")) {
            postUrl = USER_URL;
        } else if (code.startsWith("802") || code.startsWith("002")) {
            postUrl = ACCOUNT_URL;
        } else if (code.startsWith("801") || code.startsWith("003")) {
            postUrl = CORE_URL;
        } else {
            throw new BizException("Biz000", "请求接口号无对应请求URL");
        }
        return postUrl;
    }
}
