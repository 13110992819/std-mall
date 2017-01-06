package com.xnjr.mall.ao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xnjr.mall.ao.IStoreTicketAO;
import com.xnjr.mall.bo.IStoreTicketBO;
import com.xnjr.mall.bo.IUserTicketBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.StoreTicket;
import com.xnjr.mall.domain.UserTicket;
import com.xnjr.mall.enums.EBoolean;
import com.xnjr.mall.enums.EStoreTicketStatus;
import com.xnjr.mall.enums.EUserTicketStatus;
import com.xnjr.mall.exception.BizException;

@Service
public class StoreTicketAOImpl implements IStoreTicketAO {

    @Autowired
    private IStoreTicketBO storeTicketBO;

    @Autowired
    private IUserTicketBO userTicketBO;

    @Override
    public String addStoreTicket(StoreTicket data) {
        if (data.getValidateEnd().before(data.getValidateStart())) {
            throw new BizException("xn0000", "有效期结束时间不能早于有效期起始时间");
        }
        return storeTicketBO.saveStoreTicket(data);
    }

    @Override
    public int editStoreTicket(StoreTicket data) {
        StoreTicket storeTicket = storeTicketBO.getStoreTicket(data.getCode());
        if (!EStoreTicketStatus.NEW.getCode().equals(storeTicket.getStatus())) {
            throw new BizException("xn0000", "折扣券状态不允许修改，待上架状态可修改");
        }
        if (data.getValidateEnd().before(data.getValidateStart())) {
            throw new BizException("xn0000", "有效期结束时间不能早于有效期起始时间");
        }
        return storeTicketBO.refreshStoreTicket(data);
    }

    @Override
    public int dropStoreTicket(String code) {
        StoreTicket storeTicket = storeTicketBO.getStoreTicket(code);
        if (!EStoreTicketStatus.NEW.getCode().equals(storeTicket.getStatus())) {
            throw new BizException("xn0000", "折扣券状态不允许删除，待上架状态可删除");
        }
        return storeTicketBO.removeStoreTicket(code);
    }

    @Override
    public Paginable<StoreTicket> queryStoreTicketPage(int start, int limit,
            StoreTicket condition) {
        Paginable<StoreTicket> page = storeTicketBO.getPaginable(start, limit,
            condition);
        List<StoreTicket> list = page.getList();
        for (StoreTicket storeTicket : list) {
            String userId = condition.getUserId();
            storeTicket.setIsExist(EBoolean.NO.getCode());
            if (StringUtils.isNotBlank(userId)) {
                UserTicket utCondition = new UserTicket();
                utCondition.setUserId(userId);
                utCondition.setTicketCode(storeTicket.getCode());
                utCondition.setStatus(EUserTicketStatus.UNUSED.getCode());
                long total = userTicketBO.getTotalCount(utCondition);
                if (total > 0) {
                    storeTicket.setIsExist(EBoolean.YES.getCode());
                }
            }
        }
        return page;
    }

    @Override
    public List<StoreTicket> queryStoreTicketList(StoreTicket condition) {
        List<StoreTicket> list = storeTicketBO.queryStoreTicketList(condition);
        for (StoreTicket storeTicket : list) {
            String userId = condition.getUserId();
            storeTicket.setIsExist(EBoolean.NO.getCode());
            if (StringUtils.isNotBlank(userId)) {
                UserTicket utCondition = new UserTicket();
                utCondition.setUserId(userId);
                utCondition.setTicketCode(storeTicket.getCode());
                utCondition.setStatus(EUserTicketStatus.UNUSED.getCode());
                long total = userTicketBO.getTotalCount(utCondition);
                if (total > 0) {
                    storeTicket.setIsExist(EBoolean.YES.getCode());
                }
            }
        }
        return list;
    }

    @Override
    public StoreTicket getStoreTicket(String code) {
        return storeTicketBO.getStoreTicket(code);
    }

    @Override
    public int putOnOff(String code) {
        StoreTicket storeTicket = storeTicketBO.getStoreTicket(code);
        String status = null;
        if (EStoreTicketStatus.OFFLINE.getCode()
            .equals(storeTicket.getStatus())
                || EStoreTicketStatus.NEW.getCode().equals(
                    storeTicket.getStatus())) {
            status = EStoreTicketStatus.ONLINE.getCode();
        } else if (EStoreTicketStatus.ONLINE.getCode().equals(
            storeTicket.getStatus())) {
            status = EStoreTicketStatus.OFFLINE.getCode();
        } else {
            throw new BizException("xn0000", "折扣券状态不允许上下架操作");
        }
        return storeTicketBO.refreshStatus(code, status);
    }
}
