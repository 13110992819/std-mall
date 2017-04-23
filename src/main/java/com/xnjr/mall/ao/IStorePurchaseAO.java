package com.xnjr.mall.ao;

import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.StorePurchase;

public interface IStorePurchaseAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public Object storePurchaseZH(String userId, String storeCode, Long amount,
            String payType, String ticketCode);

    public Object storePurchaseCGB(String userId, String storeCode,
            Long cgbTotalAmount, String payType);

    public Object storePurchaseRMBJF(String userId, String storeCode,
            Long rmbTotalAmount, String payType);

    public Object storePurchaseGD(String userId, String storeCode, Long amount,
            String payType);

    public void paySuccessZH(String payGroup, String payCode, Long payAmount);

    public void paySuccessCG(String payGroup, String payCode, Long payAmount);

    public Paginable<StorePurchase> queryStorePurchasePage(int start,
            int limit, StorePurchase condition);

    public StorePurchase getLasterStorePurchase(String storeCode);

}
