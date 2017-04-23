package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IVorderAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Vorder;
import com.xnjr.mall.dto.req.XN808667Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 列表查询订单
 * @author: myb858 
 * @since: 2017年4月20日 下午3:56:48 
 * @history:
 */
public class XN808667 extends AProcessor {
    private IVorderAO vorderAO = SpringContextHolder.getBean(IVorderAO.class);

    private XN808667Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Vorder condition = new Vorder();
        condition.setProductCode(req.getProductCode());
        condition.setReCardno(req.getReCardno());
        condition.setReName(req.getReName());
        condition.setReMobile(req.getReMobile());
        condition.setApplyUser(req.getApplyUser());

        condition.setStatus(req.getStatus());
        condition.setPayType(req.getPayType());
        condition.setPayGroup(req.getPayGroup());
        condition.setPayCode(req.getPayCode());
        condition.setHandleUser(req.getHandleUser());

        condition.setCompanyCode(req.getCompanyCode());
        condition.setSystemCode(req.getSystemCode());
        return vorderAO.queryVorderList(condition);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808667Req.class);
        StringValidater
            .validateBlank(req.getSystemCode(), req.getCompanyCode());

    }

}
