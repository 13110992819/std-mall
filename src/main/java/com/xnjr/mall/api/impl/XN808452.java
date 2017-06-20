package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.ISorderAO;
import com.xnjr.mall.ao.ISproductAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Sorder;
import com.xnjr.mall.dto.req.XN808452Req;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 取消订单
 * @author: myb858 
 * @since: 2017年6月9日 下午12:43:30 
 * @history:
 */
public class XN808452 extends AProcessor {
    private ISorderAO sorderAO = SpringContextHolder.getBean(ISorderAO.class);

    private ISproductAO sproductAO = SpringContextHolder
        .getBean(ISproductAO.class);

    private XN808452Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        sorderAO.cancelOrder(req.getCode(), req.getHandleUser(),
            req.getRemark());
        // 更新房间剩余数量
        Sorder sorder = sorderAO.getSorder(req.getCode());
        sproductAO.resetAvaliableNumbers(sorder.getProductCode());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808452Req.class);
        StringValidater.validateBlank(req.getCode(), req.getHandleUser());
    }

}
