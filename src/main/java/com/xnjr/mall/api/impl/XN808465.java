package com.xnjr.mall.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.xnjr.mall.ao.ISorderAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Sorder;
import com.xnjr.mall.dto.req.XN808465Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

public class XN808465 extends AProcessor {
    private ISorderAO sorderAO = SpringContextHolder.getBean(ISorderAO.class);

    private XN808465Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Sorder condition = new Sorder();
        condition.setCode(req.getCode());
        condition.setProductCode(req.getProductCode());
        condition.setCategory(req.getCategory());
        condition.setType(req.getType());
        condition.setReName(req.getReName());
        condition.setReMobile(req.getReMobile());
        condition.setApplyUser(req.getApplyUser());
        condition.setStatus(req.getStatus());
        condition.setPayType(req.getPayType());
        condition.setPayGroup(req.getPayGroup());
        condition.setPayCode(req.getPayCode());
        condition.setHandleUser(req.getHandleUser());
        condition.setCompanyCode(req.getCompanyCode());
        condition.setSystemCode(req.getSystemCode());
        String orderColumn = req.getOrderColumn();
        if (StringUtils.isBlank(orderColumn)) {
            orderColumn = ISorderAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(orderColumn, req.getOrderDir());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return sorderAO.querySorderPage(start, limit, condition);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808465Req.class);
        StringValidater.validateNumber(req.getStart(), req.getLimit());
    }

}
