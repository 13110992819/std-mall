package com.xnjr.mall.dto.req;

/**
 * 支付订单
 * @author: xieyj 
 * @since: 2016年5月23日 上午8:46:53 
 * @history:
 */
public class XN808052Req {

    // 编号（必填）
    private String code;

    // 支付渠道（必填）
    private String payType;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
