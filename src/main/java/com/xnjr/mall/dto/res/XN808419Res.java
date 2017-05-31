package com.xnjr.mall.dto.res;

public class XN808419Res {

    // 当前类用户分红权总数
    private Integer totalStockCount;

    // 该用户拥有分红权个数
    private Integer stockCount;

    // 今日收益总金额
    private Long todayProfitAmount;

    public Integer getTotalStockCount() {
        return totalStockCount;
    }

    public void setTotalStockCount(Integer totalStockCount) {
        this.totalStockCount = totalStockCount;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Long getTodayProfitAmount() {
        return todayProfitAmount;
    }

    public void setTodayProfitAmount(Long todayProfitAmount) {
        this.todayProfitAmount = todayProfitAmount;
    }

}
