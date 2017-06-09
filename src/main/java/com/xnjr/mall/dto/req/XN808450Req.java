package com.xnjr.mall.dto.req;


/**
 * 服务下单
 * @author: asus 
 * @since: 2017年6月8日 下午4:05:11 
 * @history:
 */
public class XN808450Req {
    // 产品编号
    private String productCode;

    // 预定时间起
    private String startDate;

    // 预定时间止
    private String endDate;

    // 服务真正享受人姓名
    private String reName;

    // 服务真正享受人电话
    private String reMobile;

    // 下单人
    private String applyUser;

    // 申请备注
    private String applyNote;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
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

}
