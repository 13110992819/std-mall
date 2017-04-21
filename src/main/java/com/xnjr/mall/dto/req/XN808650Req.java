package com.xnjr.mall.dto.req;

public class XN808650Req {
    // 商品编号（必填）
    private String vproductCode;

    // 收件人卡号（必填）
    private String reCardno;

    // 收件人姓名（必填）
    private String reName;

    // 收件人电话（必填）
    private String reMobile;

    // 订单金额（必填）
    private String amount;

    // 下单人（必填）
    private String applyUser;

    public String getVproductCode() {
        return vproductCode;
    }

    public void setVproductCode(String vproductCode) {
        this.vproductCode = vproductCode;
    }

    public String getReCardno() {
        return reCardno;
    }

    public void setReCardno(String reCardno) {
        this.reCardno = reCardno;
    }

    public String getReName() {
        return reName;
    }

    public void setReName(String reName) {
        this.reName = reName;
    }

    public String getReMobile() {
        return reMobile;
    }

    public void setReMobile(String reMobile) {
        this.reMobile = reMobile;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

}
