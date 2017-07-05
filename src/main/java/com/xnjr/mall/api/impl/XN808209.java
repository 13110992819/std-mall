package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IStoreAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808209Req;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 代入驻的商店修改,直接至审核通过（OSS）
 * @author: myb858 
 * @since: 2017年4月3日 下午4:32:55 
 * @history:
 */
public class XN808209 extends AProcessor {
    private IStoreAO storeAO = SpringContextHolder.getBean(IStoreAO.class);

    private XN808209Req req = null;

    /**
     * @see com.xnjr.mall.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        storeAO.applyStoreFront(req);
        return new BooleanRes(true);
    }

    /**
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808209Req.class);
        StringValidater.validateBlank(req.getName(), req.getLevel(),
            req.getType(), req.getRate1(), req.getRate2(), req.getSlogan(),
            req.getAdvPic(), req.getPic(), req.getDescription(),
            req.getProvince(), req.getCity(), req.getArea(), req.getAddress(),
            req.getLongitude(), req.getLatitude(), req.getBookMobile(),
            req.getSmsMobile(), req.getMobile(), req.getUpdater(),
            req.getSystemCode(), req.getCompanyCode());
    }

}
