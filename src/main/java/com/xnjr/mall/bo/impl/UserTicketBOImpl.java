package com.xnjr.mall.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xnjr.mall.bo.IUserTicketBO;
import com.xnjr.mall.bo.base.PaginableBOImpl;
import com.xnjr.mall.core.OrderNoGenerater;
import com.xnjr.mall.dao.IUserTicketDAO;
import com.xnjr.mall.domain.UserTicket;
import com.xnjr.mall.enums.EGeneratePrefix;
import com.xnjr.mall.enums.EUserTicketStatus;
import com.xnjr.mall.exception.BizException;

@Component
public class UserTicketBOImpl extends PaginableBOImpl<UserTicket> implements
        IUserTicketBO {

    @Autowired
    private IUserTicketDAO userTicketDAO;

    @Override
    public boolean isUserTicketExist(String code) {
        UserTicket condition = new UserTicket();
        condition.setCode(code);
        if (userTicketDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isExistBuyTicket(String userId, String storeTicketCode) {
        boolean result = false;
        if (StringUtils.isNotBlank(userId)) {
            UserTicket condition = new UserTicket();
            condition.setUserId(userId);
            condition.setTicketCode(storeTicketCode);
            condition.setStatus(EUserTicketStatus.UNUSED.getCode());
            long total = userTicketDAO.selectTotalCount(condition);
            if (total > 0) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public String saveUserTicket(UserTicket data) {
        String code = null;
        if (data != null) {
            code = OrderNoGenerater.generateM(EGeneratePrefix.USER_TICKET
                .getCode());
            data.setCode(code);
            data.setStatus(EUserTicketStatus.UNUSED.getCode());
            data.setCreateDatetime(new Date());
            userTicketDAO.insert(data);
        }
        return code;
    }

    @Override
    public int removeUserTicket(String code) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            UserTicket data = new UserTicket();
            data.setCode(code);
            count = userTicketDAO.delete(data);
        }
        return count;
    }

    @Override
    public List<UserTicket> queryUserTicketList(UserTicket condition) {
        return userTicketDAO.selectList(condition);
    }

    @Override
    public UserTicket getUserTicket(String code) {
        UserTicket data = null;
        if (StringUtils.isNotBlank(code)) {
            UserTicket condition = new UserTicket();
            condition.setCode(code);
            data = userTicketDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "折扣券不存在");
            }
        }
        return data;
    }

    @Override
    public int refreshUserTicketStatus(String code, String status) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            UserTicket data = new UserTicket();
            data.setCode(code);
            data.setStatus(status);
            count = userTicketDAO.updateUserTicketStatus(data);
        }
        return count;
    }
}
