package com.xnjr.mall.dto.req;

public class XN808665Req extends APageReq {

    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = -1335754015824945645L;

    // 类型(选填)
    private String productCode;

    // 收件人卡号(选填)
    private String reCardno;

    // 收件人姓名(选填)
    private String reName;

    // 收件人电话(选填)
    private String reMobile;

    // 下单人(选填)
    private String applyUser;

    // 状态(选填)
    private String status;

    // 支付方式(选填)
    private String payType;

    // 支付组号(选填)
    private String payGroup;

    // 支付编号(选填)
    private String payCode;

    // 处理人(选填)
    private String handleUser;

    // 所属公司编号(必填)
    private String companyCode;

    // 所属系统编号(必填)
    private String systemCode;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getReCardno() {
        return reCardno;
    }

    public void setReCardno(String reCardno) {
        this.reCardno = reCardno;
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
