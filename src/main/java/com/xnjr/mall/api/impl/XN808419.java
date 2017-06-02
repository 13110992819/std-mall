package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IStockAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808419Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 我今日收益统计,用户分红权个数统计(320版本前接口)
 * @author: myb858 
 * @since: 2017年3月31日 上午10:38:15 
 * @history:
 */
public class XN808419 extends AProcessor {
    private IStockAO stockAO = SpringContextHolder.getBean(IStockAO.class);

    private XN808419Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return stockAO.getMyTodayStocks(req.getUserId());
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808419Req.class);
        StringValidater.validateBlank(req.getUserId());
    }
}
