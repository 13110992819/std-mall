package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IInteractAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808950Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 分页查询产品收藏
 * @author: xieyj 
 * @since: 2017年9月5日 上午10:37:00 
 * @history:
 */
public class XN808950 extends AProcessor {
    private IInteractAO interactAO = SpringContextHolder
        .getBean(IInteractAO.class);

    private XN808950Req req = null;

    /** 
     * @see com.xnjr.mall.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        return interactAO.queryProductSCPage(req.getStart(),
            req.getLimit(), req.getUserId(), req.getCompanyCode(),
            req.getSystemCode());
    }

    /** 
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808950Req.class);
        StringValidater.validateBlank(req.getStart(), req.getLimit(),
            req.getUserId(), req.getCompanyCode(), req.getSystemCode());
    }
}
