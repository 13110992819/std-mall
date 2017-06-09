package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.ISorderAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808466Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

public class XN808466 extends AProcessor {
    private ISorderAO sorderAO = SpringContextHolder.getBean(ISorderAO.class);

    private XN808466Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return sorderAO.getSorder(req.getCode());
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808466Req.class);
        StringValidater.validateBlank(req.getCode());
    }

}
