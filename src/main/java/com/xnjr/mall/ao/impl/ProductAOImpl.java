/**
 * @Title ProductAOImpl.java 
 * @Package com.xnjr.mall.ao.impl 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年5月16日 下午9:37:38 
 * @version V1.0   
 */
package com.xnjr.mall.ao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xnjr.mall.ao.IProductAO;
import com.xnjr.mall.bo.IProductBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.Product;
import com.xnjr.mall.enums.EProductStatus;
import com.xnjr.mall.exception.BizException;

/** 
 * @author: haiqingzheng 
 * @since: 2016年5月16日 下午9:37:38 
 * @history:
 */
@Service
public class ProductAOImpl implements IProductAO {

    @Autowired
    private IProductBO productBO;

    /** 
     * @see com.xnjr.mall.ao.IProductAO#addProduct(com.xnjr.mall.domain.Product)
     */
    @Override
    public String addProduct(Product product) {
        Product condition = new Product();
        condition.setName(product.getName());
        condition.setCompanyCode(product.getCompanyCode());
        condition.setSystemCode(product.getSystemCode());
        List<Product> list = productBO.queryProductList(condition);
        if (!CollectionUtils.sizeIsEmpty(list)) {
            throw new BizException("jd00001", "产品名称已存在");
        }
        return productBO.saveProduct(product);
    }

    /** 
     * @see com.xnjr.mall.ao.IProductAO#dropProduct(java.lang.String)
     */
    @Override
    public int dropProduct(String code) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Product product = productBO.getProduct(code);
            if (EProductStatus.TO_PUBLISH.getCode().equals(product.getStatus())) {
                count = productBO.removeProduct(code);
            } else {
                throw new BizException("xn000000", "产品已有上架记录，不能删除");
            }

        }
        return count;
    }

    /** 
     * @see com.xnjr.mall.ao.IProductAO#updateProduct(com.xnjr.mall.domain.Product)
     */
    @Override
    public int editProduct(Product product) {
        Product dbProduct = productBO.getProduct(product.getCode());
        Product condition = new Product();
        condition.setName(product.getName());
        condition.setCompanyCode(product.getCompanyCode());
        condition.setSystemCode(product.getSystemCode());
        List<Product> list = productBO.queryProductList(condition);
        if (!CollectionUtils.sizeIsEmpty(list)
                && !dbProduct.getName().equals(list.get(0).getName())) {
            throw new BizException("jd00001", "产品名称已存在");
        }
        if (!EProductStatus.TO_PUBLISH.getCode().equals(dbProduct.getStatus())
                && !EProductStatus.APPROVE_NO.getCode().equals(
                    dbProduct.getStatus())
                && !EProductStatus.PUBLISH_NO.getCode().equals(
                    dbProduct.getStatus())) {
            throw new BizException("xn000000", "该产品不是已提交,审核不通过和已下架状态，无法修改");
        }
        int count = 0;
        if (product != null) {
            count = productBO.refreshProduct(product);
        }
        return count;
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
        // 查询已购买人数
        product.setBoughtCount(productBO.getBoughtCount(code));
        return product;
    }

    @Override
    public void approveProduct(String code, String approveResult,
            String approver, String approveNote) {
        Product product = productBO.getProduct(code);
        if (!EProductStatus.TO_PUBLISH.getCode().equals(product.getStatus())) {
            throw new BizException("xn000000", "该产品不是已提交状态");
        }
        productBO.approveProduct(code, approveResult, approver, approveNote);
    }

    @Override
    @Transactional
    public void approveMoreProduct(List<String> codeList, String approveResult,
            String approver, String approveNote) {
        for (String code : codeList) {
            this.approveProduct(code, approveResult, approver, approveNote);
        }
    }

    @Override
    public int putOnProduct(String code, Long price1, Long price2, Long price3,
            String location, Integer orderNo, String updater, String remark) {
        int count = 0;
        Product product = productBO.getProduct(code);
        // 已提交，审核通过，已下架状态可上架；
        if (!EProductStatus.TO_PUBLISH.getCode().equals(product.getStatus())
                && !EProductStatus.APPROVE_YES.getCode().equals(
                    product.getStatus())
                && !EProductStatus.PUBLISH_NO.getCode().equals(
                    product.getStatus())) {
            throw new BizException("xn000000", "该产品状态不符合上架条件，无法上架");
        }
        count = productBO.putOn(code, price1, price2, price3, location,
            orderNo, updater, remark);
        return count;
    }

    @Override
    public int putOffProduct(String code, String updater, String remark) {
        int count = 0;
        Product product = productBO.getProduct(code);
        if (!EProductStatus.PUBLISH_YES.getCode().equals(product.getStatus())) {
            throw new BizException("xn000000", "该产品不处于上架状态，不能下架");
        }
        count = productBO.putOff(code, updater, remark);
        return count;
    }
}
