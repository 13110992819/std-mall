package com.xnjr.mall.dto.req;

/**
 * 订单分页查询
 * @author: asus 
 * @since: 2017年6月8日 下午4:17:56 
 * @history:
 */
public class XN808465Req extends APageReq {

    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = -2417435891349850405L;

    // 编号
    private String code;

    // 产品编号
    private String productCode;

    // 大类（同产品）
    private String category;

    // 小类（同产品）
    private String type;

    // 服务真正享受人姓名
    private String reName;

    // 服务真正享受人电话
    private String reMobile;

    // 下单人
    private String applyUser;

    // 状态
    private String status;

    // 支付方式
    private String payType;

    // 支付组号
    private String payGroup;

    // 支付编号
    private String payCode;

    // 处理人
    private String handleUser;

    // 所属公司编号
    private String companyCode;

    // 所属系统编号
    private String systemCode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReName() {
        return reName;
    }

    public void setReName(String reName) {
        this.reName = reName;
    }

    public String getReMobile() {
        return reMobile;
    }

    public void setReMobile(String reMobile) {
        this.reMobile = reMobile;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayGroup() {
        return payGroup;
    }

    public void setPayGroup(String payGroup) {
        this.payGroup = payGroup;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getHandleUser() {
        return handleUser;
    }

    public void setHandleUser(String handleUser) {
        this.handleUser = handleUser;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }
}
