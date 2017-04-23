package com.xnjr.mall.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.xnjr.mall.ao.IVorderAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Vorder;
import com.xnjr.mall.dto.req.XN808668Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 我的订单分页查询（前端）
 * @author: myb858 
 * @since: 2017年4月20日 下午3:59:38 
 * @history:
 */
public class XN808668 extends AProcessor {
    private IVorderAO vorderAO = SpringContextHolder.getBean(IVorderAO.class);

    private XN808668Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Vorder condition = new Vorder();
        condition.setProductCode(req.getProductCode());
        condition.setApplyUser(req.getApplyUser());
        condition.setStatus(req.getStatus());
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
        req = JsonUtil.json2Bean(inputparams, XN808668Req.class);
        StringValidater.validateBlank(req.getStart(), req.getLimit());
        StringValidater.validateBlank(req.getSystemCode(),
            req.getCompanyCode(), req.getApplyUser());
    }

}
