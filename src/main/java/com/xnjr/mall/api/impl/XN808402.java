package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.ISproductAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808402Req;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 修改服务产品
 * @author: myb858 
 * @since: 2017年6月7日 下午6:05:09 
 * @history:
 */
public class XN808402 extends AProcessor {
    private ISproductAO sproductAO = SpringContextHolder
        .getBean(ISproductAO.class);

    private XN808402Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        sproductAO.editSproduct(req);
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808402Req.class);
        StringValidater.validateBlank(req.getCode(), req.getName(),
            req.getSlogan(), req.getAdvPic(), req.getPic(),
            req.getDescription());
        StringValidater.validateAmount(req.getPrice());
        StringValidater.validateNumber(req.getTotalNum());
    }
}
