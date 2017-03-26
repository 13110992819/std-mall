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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xnjr.mall.ao.IProductAO;
import com.xnjr.mall.bo.ICategoryBO;
import com.xnjr.mall.bo.IProductBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.core.OrderNoGenerater;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Category;
import com.xnjr.mall.domain.Product;
import com.xnjr.mall.dto.req.XN808010Req;
import com.xnjr.mall.dto.req.XN808012Req;
import com.xnjr.mall.dto.req.XN808013Req;
import com.xnjr.mall.enums.EGeneratePrefix;
import com.xnjr.mall.enums.EProductStatus;
import com.xnjr.mall.enums.ESystemCode;
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

    @Override
    public String addProduct(XN808010Req req) {
        // 根据小类获取大类
        Category category = categoryBO.getCategory(req.getCategory());

        Product data = new Product();
        String code = OrderNoGenerater.generateM(EGeneratePrefix.PRODUCT
            .getCode());
        data.setCode(code);
        data.setCategory(category.getParentCode());
        data.setType(req.getCategory());
        data.setName(req.getName());
        data.setSlogan(req.getSlogan());

        data.setAdvPic(req.getAdvPic());
        data.setPic(req.getPic());
        data.setDescription(req.getDescription());
        if (ESystemCode.ZHPAY.getCode().equals(req.getSystemCode())) {
            data.setStatus(EProductStatus.TO_APPROVE.getCode());
        } else {
            data.setStatus(EProductStatus.APPROVE_YES.getCode());
        }

        data.setUpdater(req.getUpdater());
        data.setUpdateDatetime(new Date());
        data.setRemark(req.getRemark());
        data.setBoughtCount(0);
        data.setCompanyCode(req.getCompanyCode());
        data.setSystemCode(req.getSystemCode());
        productBO.saveProduct(data);
        return code;

    }

    @Override
    public void dropProduct(String code) {
        if (StringUtils.isNotBlank(code)) {
            Product product = productBO.getProduct(code);
            if (EProductStatus.TO_APPROVE.getCode().equals(product.getStatus())
                    || EProductStatus.APPROVE_YES.getCode().equals(
                        product.getStatus())
                    || EProductStatus.APPROVE_NO.getCode().equals(
                        product.getStatus())) {
                productBO.removeProduct(code);
            } else {
                throw new BizException("xn000000", "产品已上架过，不能删除");
            }
        }
    }

    @Override
    public void editProduct(XN808012Req req) {
        // 根据小类获取大类
        Category category = categoryBO.getCategory(req.getCategory());
        Product data = new Product();
        Product dbProduct = productBO.getProduct(req.getCode());
        if (ESystemCode.ZHPAY.getCode().equals(category.getSystemCode())) {
            if (!EProductStatus.TO_APPROVE.getCode().equals(
                dbProduct.getStatus())
                    && !EProductStatus.APPROVE_NO.getCode().equals(
                        dbProduct.getStatus())) {
                throw new BizException("xn000000", "该产品不是已提交,审核不通过和已下架状态，无法修改");
            }
            data.setStatus(EProductStatus.TO_APPROVE.getCode());
        } else {
            data.setStatus(dbProduct.getStatus());
        }

        data.setCode(req.getCode());
        data.setCategory(category.getParentCode());
        data.setType(req.getCategory());
        data.setName(req.getName());
        data.setSlogan(req.getSlogan());

        data.setAdvPic(req.getAdvPic());

        data.setPic(req.getPic());
        data.setDescription(req.getDescription());

        data.setUpdater(req.getUpdater());
        data.setUpdateDatetime(new Date());
        data.setRemark(req.getRemark());
        productBO.refreshProduct(data);

    }

    /** 
     * @see com.xnjr.mall.ao.IProductAO#queryProductPage(int, int, com.xnjr.mall.domain.Product)
     */
    @Override
    public Paginable<Product> queryProductPage(int start, int limit,
            Product condition) {
        return productBO.getPaginable(start, limit, condition);
    }

    /** 
     * @see com.xnjr.mall.ao.IProductAO#queryProductList(com.xnjr.mall.domain.Product)
     */
    @Override
    public List<Product> queryProductList(Product condition) {
        return productBO.queryProductList(condition);
    }

    /** 
     * @see com.xnjr.mall.ao.IProductAO#getProduct(java.lang.String)
     */
    @Override
    public Product getProduct(String code) {
        Product product = productBO.getProduct(code);
        return product;
    }

    @Transactional
    private void approveProduct(String code, String approveResult,
            String approver, String approveNote) {
        Product product = productBO.getProduct(code);
        if (!EProductStatus.TO_APPROVE.getCode().equals(product.getStatus())) {
            throw new BizException("xn000000", "该产品不是已提交状态");
        }
        productBO.approveProduct(code, approveResult, approver, approveNote);

    }

    @Override
    public void approveProduct(List<String> codeList, String approveResult,
            String approver, String approveNote) {
        for (String code : codeList) {
            this.approveProduct(code, approveResult, approver, approveNote);
        }
    }

    @Override
    public void putOn(XN808013Req req) {
        String code = req.getCode();
        Product dbProduct = productBO.getProduct(code);
        // 已提交，审核通过，已下架状态可上架；
        if (EProductStatus.APPROVE_YES.getCode().equals(dbProduct.getStatus())
                || EProductStatus.PUBLISH_NO.getCode().equals(
                    dbProduct.getStatus())) {
            Product product = new Product();
            product.setCode(code);
            product.setLocation(req.getLocation());
            product.setOrderNo(StringValidater.toInteger(req.getOrderNo()));
            product.setOriginalPrice(StringValidater.toLong(req
                .getOriginalPrice()));
            product.setPrice1(StringValidater.toLong(req.getPrice1()));
            product.setPrice2(StringValidater.toLong(req.getPrice2()));
            product.setPrice3(StringValidater.toLong(req.getPrice3()));
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
            throw new BizException("xn000000", "该产品状态不是上架状态，无法下架");
        }

    }
}
