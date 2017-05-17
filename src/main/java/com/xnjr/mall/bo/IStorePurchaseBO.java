package com.xnjr.mall.bo;

import java.util.List;

import com.xnjr.mall.bo.base.IPaginableBO;
import com.xnjr.mall.domain.Store;
import com.xnjr.mall.domain.StorePurchase;
import com.xnjr.mall.domain.User;

public interface IStorePurchaseBO extends IPaginableBO<StorePurchase> {

    public String storePurchaseCGcgb(User user, Store store, Long amount,
            Long fdAmount);

    public String storePurchaseCGRMB(User user, Store store, Long amount,
            Long payRMB, Long fdAmount);

    public String storePurchaseCGRMBJF(User user, Store store, Long amount,
            Long payRMB, Long payJF);

    public String storePurchaseCGRMBWX(User user, Store store, Long amount,
            Long fdAmount, String payGroup);

    public String storePurchaseCGRMBJFWX(User user, Store store, Long amount,
            Long jfAmount, String payGroup);

    public String storePurchaseZHWX(User user, Store store, Long amount,
            String ticketCode, String payGroup);

    public String storePurchaseZHZFB(User user, Store store, Long amount,
            String ticketCode, String payGroup);

    public String storePurchaseZHYE(User user, Store store, String ticketCode,
            Long amount, Long frResultAmount, Long gxjlResultAmount);

    public String storePurchaseGDYE(User user, Store store, Long amount,
            Long jfAmount);

    public List<StorePurchase> queryStorePurchaseList(StorePurchase condition);

    /** 
     * 获取店铺人民币总收入
     * @param storeCode 
     * @create: 2017年3月28日 下午5:11:25 xieyj
     * @history: 
     */
    public Long getTotalIncome(String storeCode);

    public StorePurchase getStorePurchaseByPayGroup(String payGroup);

    public void paySuccess(StorePurchase storePurchase, String payCode,
            Long payAmount);

}
