/**
 * @Title XN808270.java 
 * @Package com.xnjr.mall.api.impl 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年5月24日 下午2:10:52 
 * @version V1.0   
 */
package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IStorePurchaseAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808270Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/** 
 * 姚橙O2O支付
 * @author: haiqingzheng 
 * @since: 2017年5月24日 下午2:10:52 
 * @history:
 */
public class XN808270 extends AProcessor {

    private IStorePurchaseAO storePurchaseAO = SpringContextHolder
        .getBean(IStorePurchaseAO.class);

    private XN808270Req req = null;

    /** 
     * @see com.xnjr.mall.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        Long amount = StringValidater.toLong(req.getAmount());
        return storePurchaseAO.storePurchaseYC(req.getUserId(),
            req.getStoreCode(), amount, req.getPayType());
    }

    /** 
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808270Req.class);
        StringValidater.validateBlank(req.getUserId(), req.getStoreCode(),
            req.getAmount(), req.getAmount());
    }

}
