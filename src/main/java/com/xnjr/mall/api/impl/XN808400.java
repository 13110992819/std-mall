package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.ISproductAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808400Req;
import com.xnjr.mall.dto.res.PKCodeRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 新增服务产品
 * @author: myb858 
 * @since: 2017年6月7日 下午6:04:16 
 * @history:
 */
public class XN808400 extends AProcessor {
    private ISproductAO sproductAO = SpringContextHolder
        .getBean(ISproductAO.class);

    private XN808400Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return new PKCodeRes(sproductAO.saveSproduct(req));
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808400Req.class);
        StringValidater.validateBlank(req.getName(), req.getUserId(),
            req.getSlogan(), req.getAdvPic(), req.getPic(),
            req.getDescription());
        StringValidater.validateAmount(req.getPrice());
        StringValidater.validateNumber(req.getTotalNum());
    }
}
