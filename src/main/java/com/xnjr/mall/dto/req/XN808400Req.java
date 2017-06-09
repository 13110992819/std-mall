package com.xnjr.mall.dto.req;

/**
 * 新增服务
 * @author: asus 
 * @since: 2017年6月8日 下午3:42:03 
 * @history:
 */
public class XN808400Req {

    // 名字（必填）
    private String name;

    // 大类（必填）
    private String category;

    // 小类（必填）
    private String type;

    // 所属商家编号（必填）
    private String storeCode;

    // 广告语（必填）
    private String slogan;

    // 广告图片（必填）
    private String advPic;

    // pic（必填）
    private String pic;

    // 描述说明（必填）
    private String description;

    // 价格（必填）
    private String price;

    // 当天总数（必填）
    private String totalNum;

    // 所属公司编号（必填）
    private String companyCode;

    // 所属系统编号（必填）
    private String systemCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
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

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
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
