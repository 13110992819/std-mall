package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IExpressRuleAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808082Req;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 快递规则修改
 * @author: xieyj 
 * @since: 2017年7月18日 下午2:08:51 
 * @history:
 */
public class XN808082 extends AProcessor {

    private IExpressRuleAO expressRuleAO = SpringContextHolder
        .getBean(IExpressRuleAO.class);

    private XN808082Req req = null;

    /** 
     * @see com.xnjr.mall.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        Long id = StringValidater.toLong(req.getId());
        Long price = StringValidater.toLong(req.getPrice());
        expressRuleAO.editExpressRule(id, req.getStartPoint(),
            req.getEndPoint(), price, req.getUpdater(), req.getRemark());
        return new BooleanRes(true);
    }

    /** 
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808082Req.class);
        StringValidater.validateBlank(req.getId(), req.getStartPoint(),
            req.getEndPoint(), req.getPrice(), req.getUpdater());
    }
}
