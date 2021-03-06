package com.xnjr.mall.domain;

import java.util.Date;
import java.util.List;

import com.xnjr.mall.dao.base.ABaseDO;

//（预约性质的）服务订单
public class Sorder extends ABaseDO {

    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 1390006077218411533L;

    // 编号
    private String code;

    // 产品编号
    private String productCode;

    // 大类（同产品）
    private String category;

    // 小类（同产品）
    private String type;

    // 所属商家编号（同产品）
    private String storeCode;

    // 所属商家主人编号（同产品）
    private String storeUser;

    // 预定时间起
    private Date startDate;

    // 预定时间止
    private Date endDate;

    // 服务真正享受人姓名
    private String reName;

    // 服务真正享受人电话
    private String reMobile;

    // 下单人
    private String applyUser;

    // 申请备注
    private String applyNote;

    // 申请时间
    private Date applyDatetime;

    // 订单金额(人民币)
    private Long amount1;

    // 订单金额
    private Long amount2;

    // 订单金额
    private Long amount3;

    // 实际支付金额1
    private Long payAmount1;

    // 实际支付金额2
    private Long payAmount2;

    // 实际支付金额3
    private Long payAmount3;

    // 状态
    private String status;

    // 支付方式
    private String payType;

    // 支付组号
    private String payGroup;

    // 支付编号
    private String payCode;

    // 实际支付时间
    private Date payDatetime;

    // 处理人
    private String handleUser;

    // 处理时间
    private Date handleDatetime;

    // 备注
    private String remark;

    // 所属公司编号
    private String companyCode;

    // 所属系统编号
    private String systemCode;

    // *************dp properties **************

    private Sproduct product;

    // 状态list
    private List<String> statusList;

    private Date today;

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

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

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreUser() {
        return storeUser;
    }

    public void setStoreUser(String storeUser) {
        this.storeUser = storeUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public String getApplyNote() {
        return applyNote;
    }

    public void setApplyNote(String applyNote) {
        this.applyNote = applyNote;
    }

    public Date getApplyDatetime() {
        return applyDatetime;
    }

    public void setApplyDatetime(Date applyDatetime) {
        this.applyDatetime = applyDatetime;
    }

    public Long getAmount1() {
        return amount1;
    }

    public void setAmount1(Long amount1) {
        this.amount1 = amount1;
    }

    public Long getAmount2() {
        return amount2;
    }

    public void setAmount2(Long amount2) {
        this.amount2 = amount2;
    }

    public Long getAmount3() {
        return amount3;
    }

    public void setAmount3(Long amount3) {
        this.amount3 = amount3;
    }

    public Long getPayAmount1() {
        return payAmount1;
    }

    public void setPayAmount1(Long payAmount1) {
        this.payAmount1 = payAmount1;
    }

    public Long getPayAmount2() {
        return payAmount2;
    }

    public void setPayAmount2(Long payAmount2) {
        this.payAmount2 = payAmount2;
    }

    public Long getPayAmount3() {
        return payAmount3;
    }

    public void setPayAmount3(Long payAmount3) {
        this.payAmount3 = payAmount3;
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

    public Date getPayDatetime() {
        return payDatetime;
    }

    public void setPayDatetime(Date payDatetime) {
        this.payDatetime = payDatetime;
    }

    public String getHandleUser() {
        return handleUser;
    }

    public void setHandleUser(String handleUser) {
        this.handleUser = handleUser;
    }

    public Date getHandleDatetime() {
        return handleDatetime;
    }

    public void setHandleDatetime(Date handleDatetime) {
        this.handleDatetime = handleDatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Sproduct getProduct() {
        return product;
    }

    public void setProduct(Sproduct product) {
        this.product = product;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    @Override
    public String toString() {
        return "Sorder [code=" + code + ", productCode=" + productCode
                + ", category=" + category + ", type=" + type + ", startDate="
                + startDate + ", endDate=" + endDate + ", reName=" + reName
                + ", reMobile=" + reMobile + ", applyUser=" + applyUser
                + ", applyNote=" + applyNote + ", applyDatetime="
                + applyDatetime + ", amount1=" + amount1 + ", amount2="
                + amount2 + ", amount3=" + amount3 + ", payAmount1="
                + payAmount1 + ", payAmount2=" + payAmount2 + ", payAmount3="
                + payAmount3 + ", status=" + status + ", payType=" + payType
                + ", payGroup=" + payGroup + ", payCode=" + payCode
                + ", payDatetime=" + payDatetime + ", handleUser=" + handleUser
                + ", handleDatetime=" + handleDatetime + ", remark=" + remark
                + ", companyCode=" + companyCode + ", systemCode=" + systemCode
                + "]";
    }
}
