package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IProductSpecsAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808030Req;
import com.xnjr.mall.dto.res.PKCodeRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 新增产品型号
 * @author: myb858 
 * @since: 2017年3月25日 下午3:41:52 
 * @history:
 */
public class XN808030 extends AProcessor {

    private IProductSpecsAO productSpecsAO = SpringContextHolder
        .getBean(IProductSpecsAO.class);

    private XN808030Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return new PKCodeRes(productSpecsAO.addProductSpecs(req));
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808030Req.class);
        StringValidater.validateBlank(req.getName(), req.getProductCode(),
            req.getOriginalPrice(), req.getPrice1(), req.getPrice2(),
            req.getPrice3(), req.getQuantity(), req.getOrderNo());
    }

}
