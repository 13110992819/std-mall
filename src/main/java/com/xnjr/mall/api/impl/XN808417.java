package com.xnjr.mall.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.xnjr.mall.ao.ISproductAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.domain.Sproduct;
import com.xnjr.mall.dto.req.XN808417Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 列表查询服务产品
 * @author: myb858 
 * @since: 2017年6月7日 下午6:07:58 
 * @history:
 */
public class XN808417 extends AProcessor {
    private ISproductAO sproductAO = SpringContextHolder
        .getBean(ISproductAO.class);

    private XN808417Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Sproduct condition = new Sproduct();
        condition.setName(req.getName());
        condition.setCategory(req.getCategory());
        condition.setType(req.getType());
        condition.setStoreCode(req.getStoreCode());
        condition.setStatus(req.getStatus());
        condition.setLocation(req.getLocation());
        condition.setCompanyCode(req.getCompanyCode());
        condition.setSystemCode(req.getSystemCode());
        String orderColumn = req.getOrderColumn();
        if (StringUtils.isBlank(orderColumn)) {
            orderColumn = ISproductAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(orderColumn, req.getOrderDir());
        return sproductAO.querySproductList(condition);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808417Req.class);
    }

}
