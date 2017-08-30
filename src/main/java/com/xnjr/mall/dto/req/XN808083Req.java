package com.xnjr.mall.dto.req;

import java.util.List;

public class XN808083Req {
    // 编号(必填)
    private List<String> idList;

    // 快递费(必填)
    private String price;

    // 更新人(必填)
    private String updater;

    // 备注(选填)
    private String remark;

    // 所属公司编号(必填)
    private String companyCode;

    // 系统编号(必填)
    private String systemCode;

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
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
}
