/**
 * @Title ProductAOImpl.java 
 * @Package com.xnjr.mall.ao.impl 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年5月16日 下午9:37:38 
 * @version V1.0   
 */
package com.xnjr.mall.ao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xnjr.mall.ao.IProductAO;
import com.xnjr.mall.bo.ICategoryBO;
import com.xnjr.mall.bo.IProductBO;
import com.xnjr.mall.bo.IProductSpecsBO;
import com.xnjr.mall.bo.IStoreBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.common.SystemUtil;
import com.xnjr.mall.core.OrderNoGenerater;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Category;
import com.xnjr.mall.domain.Product;
import com.xnjr.mall.domain.ProductSpecs;
import com.xnjr.mall.domain.Store;
import com.xnjr.mall.dto.req.XN808010Req;
import com.xnjr.mall.dto.req.XN808012Req;
import com.xnjr.mall.dto.req.XN808013Req;
import com.xnjr.mall.dto.req.XN808030Req;
import com.xnjr.mall.enums.EGeneratePrefix;
import com.xnjr.mall.enums.EProductStatus;
import com.xnjr.mall.enums.EStoreStatus;
import com.xnjr.mall.exception.BizException;

/** 
 * @author: haiqingzheng 
 * @since: 2016年5月16日 下午9:37:38 
 * @history:
 */
@Service
public class ProductAOImpl implements IProductAO {
    @Autowired
    private ICategoryBO categoryBO;

    @Autowired
    private IProductBO productBO;

    @Autowired
    private IProductSpecsBO productSpecsBO;

    @Autowired
    private IStoreBO storeBO;

    @Override
    @Transactional
    public String addProduct(XN808010Req req) {
        String storeCode = null;
        if (StringUtils.isNotBlank(req.getStoreCode())) {
            Store store = storeBO.getStore(req.getStoreCode());
            if (!EStoreStatus.ON_OPEN.getCode().equals(store.getStatus())) {
                throw new BizException("xn000000", "店铺还未审核通过，暂不能发布产品");
            }
            storeCode = req.getStoreCode();
        } else {
            storeCode = SystemUtil.getSysUser(req.getSystemCode());
        }

        Product data = new Product();
        // 根据小类获取大类
        if (StringUtils.isNotBlank(req.getType())) {
            Category category = categoryBO.getCategory(req.getType());
            data.setCategory(category.getParentCode());
            data.setType(req.getType());
        }
        String code = OrderNoGenerater.generateM(EGeneratePrefix.PRODUCT
            .getCode());
        data.setCode(code);
        data.setStoreCode(storeCode);
        data.setKind(req.getKind());
        data.setName(req.getName());
        data.setSlogan(req.getSlogan());
        data.setAdvPic(req.getAdvPic());
        data.setPic(req.getPic());
        data.setDescription(req.getDescription());

        data.setStatus(EProductStatus.TO_PUBLISH.getCode());
        data.setUpdater(req.getUpdater());
        data.setUpdateDatetime(new Date());
        data.setRemark(req.getRemark());
        data.setBoughtCount(0);

        data.setCompanyCode(req.getCompanyCode());
        data.setSystemCode(req.getSystemCode());
        productBO.saveProduct(data);

        // 保存规格参数
        List<XN808030Req> productSpecsList = req.getProductSpecsList();
        if (CollectionUtils.isNotEmpty(productSpecsList)) {
            for (XN808030Req xn808030Req : productSpecsList) {
                String psCode = OrderNoGenerater
                    .generateM(EGeneratePrefix.PRODUCT_SPECS.getCode());
                ProductSpecs productSpecs = new ProductSpecs();
                productSpecs.setCode(psCode);
                productSpecs.setName(xn808030Req.getName());
                productSpecs.setProductCode(code);
                productSpecs.setOriginalPrice(StringValidater
                    .toLong(xn808030Req.getOriginalPrice()));
                productSpecs.setPrice1(StringValidater.toLong(xn808030Req
                    .getPrice1()));
                productSpecs.setPrice2(StringValidater.toLong(xn808030Req
                    .getPrice2()));
                productSpecs.setPrice3(StringValidater.toLong(xn808030Req
                    .getPrice3()));

                productSpecs.setQuantity(StringValidater.toInteger(xn808030Req
                    .getQuantity()));
                productSpecs.setProvince(xn808030Req.getProvince());
                productSpecs.setWeight(StringValidater.toDouble(xn808030Req
                    .getWeight()));
                productSpecs.setOrderNo(StringValidater.toInteger(xn808030Req
                    .getOrderNo()));
                productSpecs.setCompanyCode(req.getCompanyCode());
                productSpecs.setSystemCode(req.getSystemCode());
                productSpecsBO.saveProductSpecs(productSpecs);
            }
        }
        return code;

    }

    @Override
    @Transactional
    public void dropProduct(String code) {
        if (StringUtils.isNotBlank(code)) {
            Product product = productBO.getProduct(code);
            if (EProductStatus.TO_PUBLISH.getCode().equals(product.getStatus())) {
                productBO.removeProduct(code);
                productSpecsBO.removeProductSpecsByProductCode(code);
            } else {
                throw new BizException("xn000000", "产品已上架过，不能删除");
            }
        }
    }

    @Override
    @Transactional
    public void editProduct(XN808012Req req) {
        Product dbProduct = productBO.getProduct(req.getCode());
        if (EProductStatus.PUBLISH_YES.getCode().equals(dbProduct.getStatus())) {
            throw new BizException("xn000000", "该产品已上架，请先下架");
        }
        Product data = new Product();
        // 根据小类获取大类
        if (StringUtils.isNotBlank(req.getType())) {
            Category category = categoryBO.getCategory(req.getType());
            data.setCategory(category.getParentCode());
            data.setType(req.getType());
        }
        data.setCode(req.getCode());
        data.setName(req.getName());
        data.setSlogan(req.getSlogan());
        data.setAdvPic(req.getAdvPic());
        data.setPic(req.getPic());
        data.setDescription(req.getDescription());
        data.setUpdater(req.getUpdater());
        data.setUpdateDatetime(new Date());
        data.setRemark(req.getRemark());
        productBO.refreshProduct(data);

        // 删除原来的规格参数，重新插入
        productSpecsBO.removeProductSpecsByProductCode(req.getCode());
        List<XN808030Req> productSpecsList = req.getProductSpecsList();
        if (CollectionUtils.isNotEmpty(productSpecsList)) {
            for (XN808030Req xn808030Req : productSpecsList) {
                String psCode = OrderNoGenerater
                    .generateM(EGeneratePrefix.PRODUCT_SPECS.getCode());
                ProductSpecs productSpecs = new ProductSpecs();
                productSpecs.setCode(psCode);
                productSpecs.setName(xn808030Req.getName());
                productSpecs.setProductCode(req.getCode());
                productSpecs.setOriginalPrice(StringValidater
                    .toLong(xn808030Req.getOriginalPrice()));
                productSpecs.setPrice1(StringValidater.toLong(xn808030Req
                    .getPrice1()));
                productSpecs.setPrice2(StringValidater.toLong(xn808030Req
                    .getPrice2()));
                productSpecs.setPrice3(StringValidater.toLong(xn808030Req
                    .getPrice3()));

                productSpecs.setQuantity(StringValidater.toInteger(xn808030Req
                    .getQuantity()));
                productSpecs.setProvince(xn808030Req.getProvince());
                productSpecs.setWeight(StringValidater.toDouble(xn808030Req
                    .getWeight()));
                productSpecs.setOrderNo(StringValidater.toInteger(xn808030Req
                    .getOrderNo()));
                productSpecs.setCompanyCode(dbProduct.getCompanyCode());
                productSpecs.setSystemCode(dbProduct.getSystemCode());
                productSpecsBO.saveProductSpecs(productSpecs);
            }
        }
    }

    @Override
    public void putOn(XN808013Req req) {
        String code = req.getCode();
        Product dbProduct = productBO.getProduct(code);
        // 已提交，审核通过，已下架状态可上架；
        if (EProductStatus.TO_PUBLISH.getCode().equals(dbProduct.getStatus())
                || EProductStatus.PUBLISH_NO.getCode().equals(
                    dbProduct.getStatus())) {
            List<ProductSpecs> productSpecsList = productSpecsBO
                .queryProductSpecsList(code);
            if (CollectionUtils.isEmpty(productSpecsList)) {
                throw new BizException("xn000000", "产品规格为空，请先添加");
            }
            Product product = new Product();
            product.setCode(code);
            product.setLocation(req.getLocation());
            product.setOrderNo(StringValidater.toInteger(req.getOrderNo()));
            product.setUpdater(req.getUpdater());
            product.setUpdateDatetime(new Date());
            product.setStatus(EProductStatus.PUBLISH_YES.getCode());
            product.setRemark(req.getRemark());
            productBO.putOn(product);
        } else {
            throw new BizException("xn000000", "该产品状态不符合上架条件，无法上架");
        }

    }

    @Override
    public void putOff(String code, String updater, String remark) {
        Product dbProduct = productBO.getProduct(code);
        if (EProductStatus.PUBLISH_YES.getCode().equals(dbProduct.getStatus())) {
            productBO.putOff(code, updater, remark);
        } else {
            throw new BizException("xn000000", "该产品不是上架状态，无法下架");
        }
    }

    @Override
    public Paginable<Product> queryProductPage(int start, int limit,
            Product condition) {
        Paginable<Product> results = productBO.getPaginable(start, limit,
            condition);
        if (CollectionUtils.isNotEmpty(results.getList())) {
            for (Product product : results.getList()) {
                List<ProductSpecs> productSpecsList = productSpecsBO
                    .queryProductSpecsList(product.getCode());
                product.setProductSpecsList(productSpecsList);
            }
        }
        return results;
    }

    @Override
    public List<Product> queryProductList(Product condition) {
        return productBO.queryProductList(condition);
    }

    @Override
    public Product getProduct(String code) {
        Product product = productBO.getProduct(code);
        if (null != product) {
            List<ProductSpecs> productSpecsList = productSpecsBO
                .queryProductSpecsList(code);
            product.setProductSpecsList(productSpecsList);
        }
        return product;
    }
}
