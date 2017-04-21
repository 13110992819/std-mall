package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IVproductAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808602Req;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 修改虚拟产品
 * @author: myb858 
 * @since: 2017年4月20日 下午3:37:33 
 * @history:
 */
public class XN808602 extends AProcessor {
    private IVproductAO vproductAO = SpringContextHolder
        .getBean(IVproductAO.class);

    private XN808602Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        vproductAO.editVproduct(req);
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808602Req.class);
        StringValidater.validateBlank(req.getCode(), req.getName(),
            req.getSlogan(), req.getAdvPic(), req.getPic(),
            req.getDescription(), req.getPrice(), req.getUpdater());

    }

}
