package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.ISorderAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808453Req;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

public class XN808453 extends AProcessor {
    private ISorderAO sorderAO = SpringContextHolder.getBean(ISorderAO.class);

    private XN808453Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        sorderAO.cancelSorder(req.getCode(), req.getHandleUser(),
            req.getRemark());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808453Req.class);
        StringValidater.validateBlank(req.getCode(), req.getHandleUser());
    }

}
