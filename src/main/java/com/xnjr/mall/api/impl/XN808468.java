package com.xnjr.mall.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.xnjr.mall.ao.ISorderAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Sorder;
import com.xnjr.mall.dto.req.XN808468Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 我的订单分页查询（前端）
 * @author: myb858 
 * @since: 2017年6月9日 下午12:55:06 
 * @history:
 */
public class XN808468 extends AProcessor {
    private ISorderAO sorderAO = SpringContextHolder.getBean(ISorderAO.class);

    private XN808468Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Sorder condition = new Sorder();
        condition.setProductCode(req.getProductCode());
        condition.setCategory(req.getCategory());
        condition.setApplyUser(req.getApplyUser());
        condition.setStatus(req.getStatus());
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
        req = JsonUtil.json2Bean(inputparams, XN808468Req.class);
        StringValidater.validateBlank(req.getStart(), req.getLimit());
        StringValidater.validateBlank(req.getSystemCode(),
            req.getCompanyCode(), req.getApplyUser());

    }

}
