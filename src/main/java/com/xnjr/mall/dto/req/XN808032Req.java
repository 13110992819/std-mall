package com.xnjr.mall.dto.req;

public class XN808032Req {
    // 编号(必填)
    private String code;

    // 名称(必填)
    private String name;

    // 市场参考价，原价
    private String originalPrice;

    // 价格1(人民币)(必填)
    private String price1;

    // 价格2(购物币)(必填)
    private String price2;

    // 价格3(钱包币)(必填)
    private String price3;

    // 库存(必填)
    private String quantity;

    // 相对位置编号(必填)
    private String orderNo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getPrice1() {
        return price1;
    }

    public void setPrice1(String price1) {
        this.price1 = price1;
    }

    public String getPrice2() {
        return price2;
    }

    public void setPrice2(String price2) {
        this.price2 = price2;
    }

    public String getPrice3() {
        return price3;
    }

    public void setPrice3(String price3) {
        this.price3 = price3;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

}
