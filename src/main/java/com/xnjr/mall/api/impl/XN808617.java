package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IVproductAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Vproduct;
import com.xnjr.mall.dto.req.XN808617Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 列表查询虚拟产品
 * @author: myb858 
 * @since: 2017年4月20日 下午3:43:00 
 * @history:
 */
public class XN808617 extends AProcessor {
    private IVproductAO vproductAO = SpringContextHolder
        .getBean(IVproductAO.class);

    private XN808617Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Vproduct condition = new Vproduct();
        condition.setType(req.getType());
        condition.setName(req.getName());
        condition.setLocation(req.getLocation());
        condition.setStatus(req.getStatus());
        condition.setUpdater(req.getUpdater());

        condition.setCompanyCode(req.getCompanyCode());
        condition.setSystemCode(req.getSystemCode());
        return vproductAO.queryVproductList(condition);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808617Req.class);
        StringValidater
            .validateBlank(req.getSystemCode(), req.getCompanyCode());

    }

}
