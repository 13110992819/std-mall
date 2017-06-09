package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.ISproductAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808416Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 详情查询服务产品
 * @author: myb858 
 * @since: 2017年6月7日 下午6:06:56 
 * @history:
 */
public class XN808416 extends AProcessor {
    private ISproductAO sproductAO = SpringContextHolder
        .getBean(ISproductAO.class);

    private XN808416Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return sproductAO.getSproduct(req.getCode());
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808416Req.class);
        StringValidater.validateBlank(req.getCode());
    }

}
