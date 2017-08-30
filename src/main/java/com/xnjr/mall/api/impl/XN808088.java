package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IExpressRuleAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808088Req;
import com.xnjr.mall.dto.res.XN808088Res;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 查询快递费
 * @author: xieyj 
 * @since: 2017年7月18日 下午2:16:53 
 * @history:
 */
public class XN808088 extends AProcessor {

    private IExpressRuleAO expressRuleAO = SpringContextHolder
        .getBean(IExpressRuleAO.class);

    private XN808088Req req = null;

    /** 
     * @see com.xnjr.mall.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        return new XN808088Res(expressRuleAO.getPrice(req.getStartPoint(),
            req.getEndPoint(), req.getCompanyCode(), req.getSystemCode()));
    }

    /** 
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808088Req.class);
        StringValidater.validateBlank(req.getStartPoint(), req.getEndPoint(),
            req.getCompanyCode(), req.getSystemCode());
    }
}
