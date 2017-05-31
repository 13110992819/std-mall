package com.xnjr.mall.dto.res;

public class XN808275Res {

    // 累计营业额(包括o2o和商品订单)
    private Long totalProfit;

    // 累计分红权收益
    private Long totalStockProfit;

    public XN808275Res() {

    }

    public XN808275Res(Long totalProfit, Long totalStockProfit) {
        this.totalProfit = totalProfit;
        this.totalStockProfit = totalStockProfit;
    }

    public Long getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Long totalProfit) {
        this.totalProfit = totalProfit;
    }

    public Long getTotalStockProfit() {
        return totalStockProfit;
    }

    public void setTotalStockProfit(Long totalStockProfit) {
        this.totalStockProfit = totalStockProfit;
    }
}
