package com.xnjr.mall.api.impl;

import org.apache.commons.collections.CollectionUtils;

import com.xnjr.mall.ao.ISorderAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808453Req;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 兑现订单
 * @author: myb858 
 * @since: 2017年6月9日 下午12:43:47 
 * @history:
 */
public class XN808453 extends AProcessor {
    private ISorderAO sorderAO = SpringContextHolder.getBean(ISorderAO.class);

    private XN808453Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        for (String code : req.getCodeList()) {
            sorderAO.deliverOrder(code, req.getHandleUser(), req.getRemark());
        }
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808453Req.class);
        if (CollectionUtils.isEmpty(req.getCodeList())) {
            throw new BizException("xn000000", "订单编号列表不能为空");
        }
        StringValidater.validateBlank(req.getHandleUser());
    }

}
