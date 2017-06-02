package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IStoreAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808276Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 获取产品和订单统计数据
 * @author: xieyj 
 * @since: 2017年5月31日 下午5:46:03 
 * @history:
 */
public class XN808276 extends AProcessor {
    private IStoreAO storeAO = SpringContextHolder.getBean(IStoreAO.class);

    private XN808276Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return storeAO.getTotalProductOrder(req.getUserId());
    }

    /** 
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808276Req.class);
        StringValidater.validateBlank(req.getUserId());
    }
}
