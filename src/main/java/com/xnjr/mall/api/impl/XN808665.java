package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IVorderAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.dto.req.XN808665Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 分页查询虚拟产品订单
 * @author: myb858 
 * @since: 2017年4月20日 下午3:55:04 
 * @history:
 */
public class XN808665 extends AProcessor {
    private IVorderAO vorderAO = SpringContextHolder.getBean(IVorderAO.class);

    private XN808665Req req = null;

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
