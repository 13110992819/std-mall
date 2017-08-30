package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IExpressRuleAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808080Req;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 快递规则新增
 * @author: xieyj 
 * @since: 2017年7月18日 下午1:53:54 
 * @history:
 */
public class XN808080 extends AProcessor {

    private IExpressRuleAO expressRuleAO = SpringContextHolder
        .getBean(IExpressRuleAO.class);

    private XN808080Req req = null;

    /** 
     * @see com.xnjr.mall.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        Long price = StringValidater.toLong(req.getPrice());
        expressRuleAO.addExpressRule(req.getStartPoint(), req.getEndPoint(),
            price, req.getUpdater(), req.getRemark(), req.getCompanyCode(),
            req.getSystemCode());
        return new BooleanRes(true);
    }

    /** 
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808080Req.class);
        StringValidater.validateBlank(req.getStartPoint(), req.getEndPoint(),
            req.getPrice(), req.getUpdater(), req.getCompanyCode(),
            req.getSystemCode());
    }
}
