package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IVorderAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808650Req;
import com.xnjr.mall.dto.res.PKCodeRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 虚拟产品立即下单
 * @author: myb858 
 * @since: 2017年4月20日 下午3:44:09 
 * @history:
 */
public class XN808650 extends AProcessor {
    private IVorderAO vorderAO = SpringContextHolder.getBean(IVorderAO.class);

    private XN808650Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        String code = vorderAO.commitOrder(req);
        return new PKCodeRes(code);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808650Req.class);
        StringValidater.validateBlank(req.getVproductCode(), req.getReCardno(),
            req.getReName(), req.getReMobile(), req.getApplyUser());
        StringValidater.validateAmount(req.getAmount());
    }
}
