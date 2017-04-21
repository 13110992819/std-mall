package com.xnjr.mall.api.impl;

import org.apache.commons.collections.CollectionUtils;

import com.xnjr.mall.ao.IVorderAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808651Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 支付虚拟产品订单
 * @author: myb858 
 * @since: 2017年4月20日 下午3:45:03 
 * @history:
 */
public class XN808651 extends AProcessor {
    private IVorderAO vorderAO = SpringContextHolder.getBean(IVorderAO.class);

    private XN808651Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return vorderAO.toPayOrder(req.getCodeList(), req.getPayType());
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808651Req.class);
        if (CollectionUtils.isEmpty(req.getCodeList())) {
            throw new BizException("xn702000", "请选择您要支付的订单");
        }
        StringValidater.validateBlank(req.getPayType());

    }

}
