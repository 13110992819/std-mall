package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IVorderAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.dto.req.XN808668Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 我的订单分页查询（前端）
 * @author: myb858 
 * @since: 2017年4月20日 下午3:59:38 
 * @history:
 */
public class XN808668 extends AProcessor {
    private IVorderAO vorderAO = SpringContextHolder.getBean(IVorderAO.class);

    private XN808668Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        // TODO Auto-generated method stub

    }

}
