package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.ISproductAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808403Req;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 服务产品上架
 * @author: myb858 
 * @since: 2017年6月7日 下午6:05:22 
 * @history:
 */
public class XN808403 extends AProcessor {
    private ISproductAO sproductAO = SpringContextHolder
        .getBean(ISproductAO.class);

    private XN808403Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        sproductAO.putOn(req.getCode(), req.getLocation(),
            StringValidater.toInteger(req.getOrderNo()));
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808403Req.class);
        StringValidater.validateBlank(req.getCode(), req.getLocation(),
            req.getOrderNo());
    }

}
