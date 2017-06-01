package com.xnjr.mall.dto.res;

public class XN808276Res {
    // 产品数量
    private Integer productCount;

    // 产品上架数量
    private Integer putOnProductCount;

    // 产品下架数量
    private Integer putOffProductCount;

    // 订单总数
    private Integer orderCount;

    // 待发货订单数
    private Integer toSendOrderCount;

    // 待收货订单数
    private Integer toReceiveOrderCount;

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public Integer getPutOnProductCount() {
        return putOnProductCount;
    }

    public void setPutOnProductCount(Integer putOnProductCount) {
        this.putOnProductCount = putOnProductCount;
    }

    public Integer getPutOffProductCount() {
        return putOffProductCount;
    }

    public void setPutOffProductCount(Integer putOffProductCount) {
        this.putOffProductCount = putOffProductCount;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getToSendOrderCount() {
        return toSendOrderCount;
    }

    public void setToSendOrderCount(Integer toSendOrderCount) {
        this.toSendOrderCount = toSendOrderCount;
    }

    public Integer getToReceiveOrderCount() {
        return toReceiveOrderCount;
    }

    public void setToReceiveOrderCount(Integer toReceiveOrderCount) {
        this.toReceiveOrderCount = toReceiveOrderCount;
    }
}
