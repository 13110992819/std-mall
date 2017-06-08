package com.xnjr.mall.ao;

import java.util.List;

import com.xnjr.mall.domain.ProductSpecs;
import com.xnjr.mall.dto.req.XN808030Req;
import com.xnjr.mall.dto.req.XN808032Req;

public interface IProductSpecsAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public String addProductSpecs(XN808030Req req);

    public void dropProductSpecs(String code);

    public void editProductSpecs(XN808032Req req);

    public ProductSpecs getProductSpecs(String code);

    public List<ProductSpecs> queryProductSpecsList(ProductSpecs condition);

}
