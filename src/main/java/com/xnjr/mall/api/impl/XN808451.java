package com.xnjr.mall.api.impl;

import org.apache.commons.collections.CollectionUtils;

import com.xnjr.mall.ao.ISorderAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808451Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 支付产品订单
 * @author: myb858 
 * @since: 2017年6月9日 下午12:42:54 
 * @history:
 */
public class XN808451 extends AProcessor {
    private ISorderAO sorderAO = SpringContextHolder.getBean(ISorderAO.class);

    private XN808451Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return sorderAO.toPayOrder(req.getCodeList(), req.getPayType());
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808451Req.class);
        if (CollectionUtils.isEmpty(req.getCodeList())) {
            throw new BizException("xn702000", "请选择您要支付的订单");
        }
        StringValidater.validateBlank(req.getPayType());
    }

}
