package com.xnjr.mall.dto.res;

public class XN808275Res {

    // 累计营业额(包括o2o和商品订单)
    private Long totalProfit;

    // 累计分红权收益
    private Long totalStockProfit;

    // 该用户拥有分红权个数
    private Integer stockCount;

    // 总的分红权个数
    private Integer totalStockCount;

    public Integer getTotalStockCount() {
        return totalStockCount;
    }

    public void setTotalStockCount(Integer totalStockCount) {
        this.totalStockCount = totalStockCount;
    }

    public XN808275Res() {

    }

    public XN808275Res(Long totalProfit, Long totalStockProfit,
            Integer stockCount, Integer totalStockCount) {
        this.totalProfit = totalProfit;
        this.totalStockProfit = totalStockProfit;
        this.stockCount = stockCount;
        this.totalStockCount = totalStockCount;
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

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }
}
