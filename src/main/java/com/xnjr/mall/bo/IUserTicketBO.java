package com.xnjr.mall.bo;

import java.util.List;

import com.xnjr.mall.bo.base.IPaginableBO;
import com.xnjr.mall.domain.UserTicket;

public interface IUserTicketBO extends IPaginableBO<UserTicket> {

    public boolean isUserTicketExist(String code);

    public String saveUserTicket(UserTicket data);

    public int removeUserTicket(String code);

    public int refreshUserTicketStatus(String code, String status);

    public List<UserTicket> queryUserTicketList(UserTicket condition);

    public boolean isExistBuyTicket(String userId, String storeTicket);

    public UserTicket getUserTicket(String code);

}
