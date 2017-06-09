package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.ISorderAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808450Req;
import com.xnjr.mall.dto.res.PKCodeRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 服务下单
 * @author: asus 
 * @since: 2017年6月8日 下午4:05:11 
 * @history:
 */
public class XN808450 extends AProcessor {
    private ISorderAO sorderAO = SpringContextHolder.getBean(ISorderAO.class);

    private XN808450Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return new PKCodeRes(sorderAO.commitOrder(req.getProductCode(),
            req.getStartDate(), req.getEndDate(), req.getReName(),
            req.getReMobile(), req.getApplyUser(), req.getApplyNote()));
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808450Req.class);
        StringValidater.validateBlank(req.getProductCode(), req.getReName(),
            req.getReMobile(), req.getApplyUser());
    }

}
