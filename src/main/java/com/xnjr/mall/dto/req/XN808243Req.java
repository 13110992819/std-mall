package com.xnjr.mall.dto.req;

public class XN808243Req {
    // 用户编号（必填）
    private String userId;

    // 商家编号（必填）
    private String storeCode;

    // 消费金额（必填）
    private String amount;

    // 支付类型（必填）:余额支付/微信支付
    private String payType;

    // 是否只有人民币支付(必填)
    private String isOnlyRmb;

    public String getIsOnlyRmb() {
        return isOnlyRmb;
    }

    public void setIsOnlyRmb(String isOnlyRmb) {
        this.isOnlyRmb = isOnlyRmb;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

}
