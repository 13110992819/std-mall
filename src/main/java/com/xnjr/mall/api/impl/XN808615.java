package com.xnjr.mall.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.xnjr.mall.ao.IVproductAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Vproduct;
import com.xnjr.mall.dto.req.XN808615Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 分页查询虚拟产品
 * @author: myb858 
 * @since: 2017年4月20日 下午3:40:23 
 * @history:
 */
public class XN808615 extends AProcessor {
    private IVproductAO vproductAO = SpringContextHolder
        .getBean(IVproductAO.class);

    private XN808615Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Vproduct condition = new Vproduct();
        condition.setName(req.getName());
        condition.setLocation(req.getLocation());
        condition.setStatus(req.getStatus());
        condition.setUpdater(req.getUpdater());

        condition.setCompanyCode(req.getCompanyCode());
        condition.setSystemCode(req.getSystemCode());
        String orderColumn = req.getOrderColumn();
        if (StringUtils.isBlank(orderColumn)) {
            orderColumn = IVproductAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(orderColumn, req.getOrderDir());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return vproductAO.queryVproductPage(start, limit, condition);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808615Req.class);
        StringValidater.validateBlank(req.getStart(), req.getLimit());
        StringValidater
            .validateBlank(req.getSystemCode(), req.getCompanyCode());

    }

}
