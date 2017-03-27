package com.xnjr.mall.ao;

import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.StorePurchase;

public interface IStorePurchaseAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public Object storePurchase(String userId, String storeCode, Long amount,
            String payType, String ticketCode);

    public Paginable<StorePurchase> queryStorePurchasePage(int start,
            int limit, StorePurchase condition);

    public int paySuccess(String jourCode);

}
