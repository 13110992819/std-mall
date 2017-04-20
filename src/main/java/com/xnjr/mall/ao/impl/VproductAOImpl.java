package com.xnjr.mall.ao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xnjr.mall.ao.IVproductAO;
import com.xnjr.mall.bo.IVproductBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.core.OrderNoGenerater;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Vproduct;
import com.xnjr.mall.dto.req.XN808600Req;
import com.xnjr.mall.dto.req.XN808602Req;
import com.xnjr.mall.dto.req.XN808603Req;
import com.xnjr.mall.enums.EGeneratePrefix;
import com.xnjr.mall.enums.EVproductStatus;
import com.xnjr.mall.exception.BizException;

@Service
public class VproductAOImpl implements IVproductAO {
    protected static final Logger logger = LoggerFactory
        .getLogger(VproductAOImpl.class);

    @Autowired
    private IVproductBO vproductBO;

    @Override
    public String addVproduct(XN808600Req req) {
        Vproduct data = new Vproduct();
        String code = OrderNoGenerater.generateM(EGeneratePrefix.PRODUCT
            .getCode());
        data.setCode(code);
        data.setName(req.getName());
        data.setSlogan(req.getSlogan());
        data.setAdvPic(req.getAdvPic());
        data.setPic(req.getPic());

        data.setDescription(req.getDescription());
        data.setPrice(req.getPrice());
        data.setStatus(EVproductStatus.TO_PUBLISH.getCode());

        data.setUpdater(req.getUpdater());
        data.setUpdateDatetime(new Date());
        data.setRemark(req.getRemark());
        data.setCompanyCode(req.getCompanyCode());
        data.setSystemCode(req.getSystemCode());
        vproductBO.saveVproduct(data);
        return code;
    }

    @Override
    public void dropVproduct(String code) {
        if (StringUtils.isNotBlank(code)) {
            Vproduct product = vproductBO.getVproduct(code);
            if (EVproductStatus.PUBLISH_YES.getCode().equals(
                product.getStatus())
                    || EVproductStatus.PUBLISH_NO.getCode().equals(
                        product.getStatus())) {
                vproductBO.removeVproduct(code);
            } else {
                throw new BizException("xn000000", "产品已上架过，不能删除");
            }
        }

    }

    @Override
    public void editVproduct(XN808602Req req) {
        Vproduct dbProduct = vproductBO.getVproduct(req.getCode());
        if (EVproductStatus.PUBLISH_YES.getCode().equals(dbProduct.getStatus())) {
            throw new BizException("xn000000", "该产品处于已上架状态，无法修改");
        }
        dbProduct.setName(req.getName());
        dbProduct.setSlogan(req.getSlogan());
        dbProduct.setAdvPic(req.getAdvPic());
        dbProduct.setPic(req.getPic());

        dbProduct.setDescription(req.getDescription());
        dbProduct.setPrice(req.getPrice());

        dbProduct.setUpdater(req.getUpdater());
        dbProduct.setUpdateDatetime(new Date());
        dbProduct.setRemark(req.getRemark());
        vproductBO.refreshVproduct(dbProduct);

    }

    @Override
    public void putOn(XN808603Req req) {
        String code = req.getCode();
        Vproduct dbProduct = vproductBO.getVproduct(req.getCode());
        if (EVproductStatus.PUBLISH_YES.getCode().equals(dbProduct.getStatus())) {
            throw new BizException("xn000000", "该产品处于已上架状态");
        }

        Vproduct product = new Vproduct();
        product.setCode(code);
        product.setLocation(req.getLocation());
        product.setOrderNo(StringValidater.toInteger(req.getOrderNo()));
        product.setRate(StringValidater.toDouble(req.getRate()));

        product.setUpdater(req.getUpdater());
        product.setUpdateDatetime(new Date());
        product.setStatus(EVproductStatus.PUBLISH_YES.getCode());
        product.setRemark(req.getRemark());
        vproductBO.putOn(product);

    }

    @Override
    public void putOff(String code, String updater, String remark) {
        Vproduct dbProduct = vproductBO.getVproduct(code);
        if (EVproductStatus.PUBLISH_YES.getCode().equals(dbProduct.getStatus())) {
            vproductBO.putOff(code, updater, remark);
        } else {
            throw new BizException("xn000000", "该产品状态不是上架状态，无法下架");
        }

    }

    @Override
    public Paginable<Vproduct> queryVproductPage(int start, int limit,
            Vproduct condition) {
        return vproductBO.getPaginable(start, limit, condition);
    }

    @Override
    public Vproduct getVproduct(String code) {
        return vproductBO.getVproduct(code);

    }

    @Override
    public List<Vproduct> queryVproductList(Vproduct condition) {
        return vproductBO.queryVproductList(condition);
    }

}
