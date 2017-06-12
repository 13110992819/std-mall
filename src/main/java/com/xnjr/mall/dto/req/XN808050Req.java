package com.xnjr.mall.dto.req;

import com.xnjr.mall.domain.CommitOrderPOJO;

/**
 * 立即下单
 * @author: xieyj 
 * @since: 2016年5月23日 上午8:46:53 
 * @history:
 */
public class XN808050Req {

    // 商品规格编号（必填）
    private String productSpecsCode;

    // 数量（必填）
    private String quantity;

    // 向谁提货(选填)
    private String toUser;

    // 下单个人信息(必填)
    private CommitOrderPOJO pojo;

    public String getProductSpecsCode() {
        return productSpecsCode;
    }

    public void setProductSpecsCode(String productSpecsCode) {
        this.productSpecsCode = productSpecsCode;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public CommitOrderPOJO getPojo() {
        return pojo;
    }

    public void setPojo(CommitOrderPOJO pojo) {
        this.pojo = pojo;
    }

}
