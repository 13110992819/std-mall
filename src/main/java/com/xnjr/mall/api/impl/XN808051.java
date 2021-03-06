package com.xnjr.mall.api.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.xnjr.mall.ao.IOrderAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808051Req;
import com.xnjr.mall.enums.ESystemCode;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 购物车批量下单
 * @author: xieyj 
 * @since: 2016年5月23日 上午9:04:12 
 * @history:
 */
public class XN808051 extends AProcessor {
    private IOrderAO orderAO = SpringContextHolder.getBean(IOrderAO.class);

    private XN808051Req req = null;

    /** 
     * @see com.xnjr.mall.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        Object result = null;
        if (ESystemCode.JKEG.getCode().equals(req.getPojo().getSystemCode())) {
            result = orderAO.commitCartOrderJKEG(req);
        } else {
            result = orderAO.commitCartOrder(req);
        }
        return result;
    }

    /** 
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808051Req.class);
        if (CollectionUtils.isEmpty(req.getCartCodeList())) {
            throw new BizException("xn702000", "请选择购物车中的货物");
        }
        if (null == req.getPojo()) {
            throw new BizException("xn702000", "订单基本信息不能为空");
        }
        if (StringUtils.isBlank(req.getToUser())) {
            StringValidater.validateBlank(req.getPojo().getReceiver(), req
                .getPojo().getReMobile(), req.getPojo().getReAddress());
        }
        StringValidater.validateBlank(req.getPojo().getApplyUser(), req
            .getPojo().getCompanyCode(), req.getPojo().getSystemCode());
    }
}
