package com.xnjr.mall.bo;

import java.util.List;

import com.xnjr.mall.bo.base.IPaginableBO;
import com.xnjr.mall.domain.Store;
import com.xnjr.mall.domain.StorePurchase;
import com.xnjr.mall.domain.User;

public interface IStorePurchaseBO extends IPaginableBO<StorePurchase> {

    public boolean isStorePurchaseExist(String code);

    public String storePurchaseCGcgb(User user, Store store, Long amount,
            Long fdAmount);

    public String saveStorePurchase(StorePurchase data);

    public int removeStorePurchase(String code);

    public List<StorePurchase> queryStorePurchaseList(StorePurchase condition);

    public StorePurchase getStorePurchase(String code);

    public int refreshStatus(String code, String status);

}
