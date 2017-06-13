package com.xnjr.mall.dto.req;


/**
 * 订单支付
 * @author: asus 
 * @since: 2017年6月8日 下午4:09:19 
 * @history:
 */
public class XN808451Req {

    // 编号（必填）
    private String code;

    // 支付方式(1 余额,2 微信APP,3 支付宝,5 微信H5)
    private String payType;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

}
