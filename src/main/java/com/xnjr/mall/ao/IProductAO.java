/**
 * @Title IProductAO.java 
 * @Package com.xnjr.mall.ao 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年5月16日 下午9:33:00 
 * @version V1.0   
 */
package com.xnjr.mall.ao;

import java.util.List;

import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.Product;

/** 
 * @author: haiqingzheng 
 * @since: 2016年5月16日 下午9:33:00 
 * @history:
 */
public interface IProductAO {

    static final String DEFAULT_ORDER_COLUMN = "code";

    public String addProduct(Product product);

    public int dropProduct(String code);

    public int editProduct(Product product);

    public Paginable<Product> queryProductPage(int start, int limit,
            Product condition);

    public List<Product> queryProductList(Product condition);

    public Product getProduct(String code);

    /**
     * 审核产品
     * @param code
     * @param checkUser
     * @param checkResult
     * @param checkNote
     * @return 
     * @create: 2016年5月17日 上午11:27:16 haiqingzheng
     * @history:
     */
    public int checkProduct(String code, String checkUser, String checkResult,
            String checkNote);

    /**
     * 上架/下架产品
     * @param code
     * @param checkUser
     * @param checkResult 1上架 0下架
     * @param checkNote
     * @return 
     * @create: 2016年5月17日 上午11:28:12 haiqingzheng
     * @history:
     */
    public int putOnOffProduct(String code, String checkUser,
            String checkResult, String checkNote);

}
