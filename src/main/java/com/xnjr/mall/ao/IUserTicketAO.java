package com.xnjr.mall.ao;

import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.UserTicket;
import com.xnjr.mall.dto.res.XN808268Res;

public interface IUserTicketAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public String buyTicket(String code, String userId);

    public Paginable<UserTicket> queryUserTicketPage(int start, int limit,
            UserTicket condition);

    public XN808268Res getMyStoreTicketCount(String storeCode, String systemCode);
}
