package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IExpressRuleAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808086Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 快递规则详情
 * @since: 2016年5月23日 上午9:04:12 
 * @history:
 */
public class XN808086 extends AProcessor {

    private IExpressRuleAO expressRuleAO = SpringContextHolder
        .getBean(IExpressRuleAO.class);

    private XN808086Req req = null;

    /** 
     * @see com.xnjr.mall.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        Long id = StringValidater.toLong(req.getId());
        return expressRuleAO.getExpressRule(id);
    }

    /** 
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808086Req.class);
        StringValidater.validateBlank(req.getId());
    }
}
