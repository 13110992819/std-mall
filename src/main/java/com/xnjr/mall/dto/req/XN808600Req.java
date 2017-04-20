package com.xnjr.mall.dto.req;

public class XN808600Req {

    // 产品名称(必填)
    private String name;

    // 广告语(必填)
    private String slogan;

    // 广告图片(必填)
    private String advPic;

    // pic(必填)
    private String pic;

    // 产品详情(必填)
    private String description;

    // 价格档(必填)
    private String price;

    // 更新人(必填)
    private String updater;

    // 备注(选填)
    private String remark;

    // 所属公司编号(必填)
    private String companyCode;

    // 所属系统编号(必填)
    private String systemCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getAdvPic() {
        return advPic;
    }

    public void setAdvPic(String advPic) {
        this.advPic = advPic;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
