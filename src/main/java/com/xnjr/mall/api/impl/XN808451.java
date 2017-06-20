package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.ISorderAO;
import com.xnjr.mall.ao.ISproductAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Sorder;
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

    private ISproductAO sproductAO = SpringContextHolder
        .getBean(ISproductAO.class);

    private XN808451Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Object result = sorderAO.toPayOrder(req.getCode(), req.getPayType());
        // 更新房间剩余数量
        Sorder sorder = sorderAO.getSorder(req.getCode());
        sproductAO.resetAvaliableNumbers(sorder.getProductCode());
        return result;
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808451Req.class);
        StringValidater.validateBlank(req.getCode(), req.getPayType());
    }

}
