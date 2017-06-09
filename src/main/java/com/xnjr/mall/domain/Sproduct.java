package com.xnjr.mall.domain;

import com.xnjr.mall.dao.base.ABaseDO;

//这是服务，比如（房型，电影场次，热门活动等等）
public class Sproduct extends ABaseDO {

    private static final long serialVersionUID = -253022395005281615L;

    // 编号
    private String code;

    // 名字
    private String name;

    // 大类
    private String category;

    // 小类
    private String type;

    // 所属商家编号
    private String storeCode;

    // 广告语
    private String slogan;

    // 广告图片
    private String advPic;

    // pic
    private String pic;

    // 描述说明
    private String description;

    // 价格
    private Long price;

    // 状态（1 待上架，2 已上架，3 已下架）
    private String status;

    // 当天总数
    private Integer totalNum;

    // 当天剩余数
    private Integer remainNum;

    // 位置
    private String location;

    // 相对位置编号
    private Integer orderNo;

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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getRemainNum() {
        return remainNum;
    }

    public void setRemainNum(Integer remainNum) {
        this.remainNum = remainNum;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String toString() {
        return "Sproduct [code=" + code + ", name=" + name + ", category="
                + category + ", type=" + type + ", storeCode=" + storeCode
                + ", slogan=" + slogan + ", advPic=" + advPic + ", pic=" + pic
                + ", description=" + description + ", price=" + price
                + ", status=" + status + ", totalNum=" + totalNum
                + ", remainNum=" + remainNum + ", location=" + location
                + ", orderNo=" + orderNo + ", companyCode=" + companyCode
                + ", systemCode=" + systemCode + "]";
    }

}
