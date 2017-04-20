package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IVproductAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808600Req;
import com.xnjr.mall.dto.res.PKCodeRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 新增虚拟产品
 * @author: myb858 
 * @since: 2017年4月20日 下午3:34:36 
 * @history:
 */
public class XN808600 extends AProcessor {
    private IVproductAO vproductAO = SpringContextHolder
        .getBean(IVproductAO.class);

    private XN808600Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return new PKCodeRes(vproductAO.addVproduct(req));
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808600Req.class);
        StringValidater.validateBlank(req.getName(), req.getSlogan(),
            req.getAdvPic(), req.getPic(), req.getDescription(),
            req.getPrice(), req.getUpdater(), req.getCompanyCode(),
            req.getSystemCode());

    }

}
