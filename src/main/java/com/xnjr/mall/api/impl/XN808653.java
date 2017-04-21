package com.xnjr.mall.api.impl;

import org.apache.commons.collections.CollectionUtils;

import com.xnjr.mall.ao.IVorderAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808653Req;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 兑现虚拟产品
 * @author: myb858 
 * @since: 2017年4月20日 下午3:54:13 
 * @history:
 */
public class XN808653 extends AProcessor {
    private IVorderAO vorderAO = SpringContextHolder.getBean(IVorderAO.class);

    private XN808653Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        vorderAO.deliverOrder(req.getCodeList(), req.getUpdater(),
            req.getRemark());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808653Req.class);
        if (CollectionUtils.isEmpty(req.getCodeList())) {
            throw new BizException("xn000000", "订单编号列表不能为空");
        }
        StringValidater.validateBlank(req.getUpdater(), req.getRemark());

    }

}
