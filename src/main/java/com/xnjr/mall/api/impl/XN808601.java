package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IVproductAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808601Req;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 删除虚拟产品
 * @author: myb858 
 * @since: 2017年4月20日 下午3:37:12 
 * @history:
 */
public class XN808601 extends AProcessor {
    private IVproductAO vproductAO = SpringContextHolder
        .getBean(IVproductAO.class);

    private XN808601Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        vproductAO.dropVproduct(req.getCode());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808601Req.class);
        StringValidater.validateBlank(req.getCode());

    }

}
