package com.xnjr.mall.dto.req;

/** 
 * @author: xieyj 
 * @since: 2015年8月19日 下午7:41:26 
 * @history:
 */
public class XN808040Req {

    // 用户编号（必填）
    private String userId;

    // 产品型号编号（必填）
    private String productSpecsCode;

    // 数量（必填）
    private String quantity;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

}
