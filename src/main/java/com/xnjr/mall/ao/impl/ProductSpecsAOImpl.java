package com.xnjr.mall.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xnjr.mall.ao.IProductSpecsAO;
import com.xnjr.mall.bo.IProductBO;
import com.xnjr.mall.bo.IProductSpecsBO;
import com.xnjr.mall.core.OrderNoGenerater;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Product;
import com.xnjr.mall.domain.ProductSpecs;
import com.xnjr.mall.dto.req.XN808030Req;
import com.xnjr.mall.dto.req.XN808032Req;
import com.xnjr.mall.enums.EGeneratePrefix;

@Service
public class ProductSpecsAOImpl implements IProductSpecsAO {

    @Autowired
    private IProductSpecsBO productSpecsBO;

    @Autowired
    private IProductBO productBO;

    @Override
    public String addProductSpecs(XN808030Req req) {
        Product product = productBO.getProduct(req.getProductCode());
        String code = OrderNoGenerater.generateM(EGeneratePrefix.PRODUCT_SPECS
            .getCode());
        ProductSpecs productSpecs = new ProductSpecs();
        productSpecs.setCode(code);
        productSpecs.setName(req.getName());
        productSpecs.setProductCode(product.getCode());
        productSpecs.setOriginalPrice(StringValidater.toLong(req
            .getOriginalPrice()));
        productSpecs.setPrice1(StringValidater.toLong(req.getPrice1()));
        productSpecs.setPrice2(StringValidater.toLong(req.getPrice2()));
        productSpecs.setPrice3(StringValidater.toLong(req.getPrice3()));
        productSpecs.setQuantity(StringValidater.toInteger(req.getQuantity()));
        productSpecs.setOrderNo(StringValidater.toInteger(req.getOrderNo()));
        productSpecs.setCompanyCode(product.getCompanyCode());
        productSpecs.setSystemCode(product.getSystemCode());
        productSpecsBO.saveProductSpecs(productSpecs);

        return code;
    }

    @Override
    public void editProductSpecs(XN808032Req req) {
        ProductSpecs data = new ProductSpecs();
        data.setCode(req.getCode());
        data.setName(req.getName());
        data.setPrice1(StringValidater.toLong(req.getPrice1()));
        data.setPrice2(StringValidater.toLong(req.getPrice2()));
        data.setPrice3(StringValidater.toLong(req.getPrice3()));

        data.setQuantity(StringValidater.toInteger(req.getQuantity()));
        data.setOrderNo(StringValidater.toInteger(req.getOrderNo()));
        productSpecsBO.refreshProductSpecs(data);
    }

    @Override
    public void dropProductSpecs(String code) {
        productSpecsBO.removeProductSpecs(code);
    }

    @Override
    public ProductSpecs getProductSpecs(String code) {
        return productSpecsBO.getProductSpecs(code);
    }

    @Override
    public List<ProductSpecs> queryProductSpecsList(ProductSpecs condition) {
        return productSpecsBO.queryProductSpecsList(condition);
    }

}
