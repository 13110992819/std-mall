package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IVproductAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808616Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 详情查询产品
 * @author: myb858 
 * @since: 2017年4月20日 下午3:41:05 
 * @history:
 */
public class XN808616 extends AProcessor {
    private IVproductAO vproductAO = SpringContextHolder
        .getBean(IVproductAO.class);

    private XN808616Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return vproductAO.getVproduct(req.getCode());
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808616Req.class);
        StringValidater.validateBlank(req.getCode());

    }

}
