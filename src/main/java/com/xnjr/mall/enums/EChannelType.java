package com.xnjr.mall.enums;

import java.util.HashMap;
import java.util.Map;

import com.xnjr.mall.exception.BizException;

public enum EChannelType {
    ALIPAY("30", "支付宝APP支付"), WECHAT_H5("35", "微信公众号支付"), WECHAT_APP("36",
            "微信APP支付"), WECHAT_NATIVE("37", "微信扫码支付");

    public static Map<String, EChannelType> getChannelTypeResultMap() {
        Map<String, EChannelType> map = new HashMap<String, EChannelType>();
        for (EChannelType type : EChannelType.values()) {
            map.put(type.getCode(), type);
        }
        return map;
    }

    public static EChannelType getEChannelType(String code) {
        Map<String, EChannelType> map = getChannelTypeResultMap();
        EChannelType channelType = map.get(code);
        if (null == channelType) {
            throw new BizException("xn0000", code + "对应支付渠道类型不存在");
        }
        return channelType;
    }

    EChannelType(String code, String value) {
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
