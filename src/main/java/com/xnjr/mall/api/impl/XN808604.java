package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IVproductAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808604Req;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 虚拟产品下架
 * @author: myb858 
 * @since: 2017年4月20日 下午3:38:34 
 * @history:
 */
public class XN808604 extends AProcessor {
    private IVproductAO vproductAO = SpringContextHolder
        .getBean(IVproductAO.class);

    private XN808604Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        vproductAO.putOff(req.getCode(), req.getUpdater(), req.getRemark());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808604Req.class);
        StringValidater.validateBlank(req.getCode(), req.getUpdater());

    }
}
