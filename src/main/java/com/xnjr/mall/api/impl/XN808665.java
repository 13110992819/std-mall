package com.xnjr.mall.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.xnjr.mall.ao.IVorderAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Vorder;
import com.xnjr.mall.dto.req.XN808665Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 分页查询虚拟产品订单
 * @author: myb858 
 * @since: 2017年4月20日 下午3:55:04 
 * @history:
 */
public class XN808665 extends AProcessor {
    private IVorderAO vorderAO = SpringContextHolder.getBean(IVorderAO.class);

    private XN808665Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Vorder condition = new Vorder();
        condition.setType(req.getType());
        condition.setReCardno(req.getReCardno());
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
            orderColumn = IVorderAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(orderColumn, req.getOrderDir());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return vorderAO.queryVorderPage(start, limit, condition);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808665Req.class);
        StringValidater.validateBlank(req.getStart(), req.getLimit());
        StringValidater
            .validateBlank(req.getSystemCode(), req.getCompanyCode());

    }

}
