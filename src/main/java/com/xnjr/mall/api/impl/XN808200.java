package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IStoreAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Store;
import com.xnjr.mall.dto.req.XN808200Req;
import com.xnjr.mall.dto.res.PKCodeRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 商铺代入驻
 * @author: xieyj 
 * @since: 2016年12月27日 下午2:42:17 
 * @history:
 */
public class XN808200 extends AProcessor {
    private IStoreAO storeAO = SpringContextHolder.getBean(IStoreAO.class);

    private XN808200Req req = null;

    /**
     * @see com.xnjr.mall.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        Store data = new Store();
        data.setName(req.getName());
        data.setType(req.getType());
        data.setLegalPersonName(req.getLegalPersonName());
        data.setMobile(req.getMobile());
        data.setUserReferee(req.getUserReferee());
        data.setRate1(StringValidater.toDouble(req.getRate1()));
        data.setRate2(StringValidater.toDouble(req.getRate2()));
        data.setSlogan(req.getSlogan());
        data.setAdPic(req.getAdPic());
        data.setPic(req.getPic());
        data.setDescription(req.getDescription());
        data.setProvince(req.getProvince());
        data.setCity(req.getCity());
        data.setArea(req.getArea());
        data.setAddress(req.getAddress());
        data.setLongitude(req.getLongitude());
        data.setLatitude(req.getLatitude());
        data.setBookMobile(req.getBookMobile());
        data.setSmsMobile(req.getSmsMobile());
        data.setPdf(req.getPdf());
        data.setUpdater(req.getUpdater());
        data.setRemark(req.getRemark());
        data.setSystemCode(req.getSystemCode());
        return new PKCodeRes(storeAO.addStoreOss(data));
    }

    /**
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808200Req.class);
        StringValidater.validateBlank(req.getName(), req.getType(),
            req.getLegalPersonName(), req.getMobile(), req.getRate1(),
            req.getRate2(), req.getSlogan(), req.getAdPic(), req.getPic(),
            req.getDescription(), req.getProvince(), req.getCity(),
            req.getArea(), req.getAddress(), req.getLongitude(),
            req.getLatitude(), req.getBookMobile(), req.getUpdater(),
            req.getSystemCode());
    }
}
