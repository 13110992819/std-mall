/**
 * @Title XN808228.java 
 * @Package com.xnjr.mall.api.impl 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年12月18日 下午11:28:37 
 * @version V1.0   
 */
package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IUserTicketAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808268Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 店家售卖折扣券统计数据
 * @author: xieyj 
 * @since: 2017年5月27日 下午5:34:52 
 * @history:
 */
public class XN808268 extends AProcessor {
    private IUserTicketAO userTicketAO = SpringContextHolder
        .getBean(IUserTicketAO.class);

    private XN808268Req req = null;

    /** 
     * @see com.xnjr.mall.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        return userTicketAO.getMyStoreTicketCount(req.getStoreCode(),
            req.getSystemCode());
    }

    /** 
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808268Req.class);
        StringValidater.validateBlank(req.getStoreCode(), req.getSystemCode());
    }
}
