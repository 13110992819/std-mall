package com.xnjr.mall.dto.req;

/**
 * 上架服务
 * @author: asus 
 * @since: 2017年6月8日 下午3:54:08 
 * @history:
 */
public class XN808403Req {
    // 编号
    private String code;

    // UI位置
    private String location;

    // 相对位置编号
    private String orderNo;

    // 价格(必填)
    private String price;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
