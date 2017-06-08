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

    // 当天的状态（未满/已满）
    private String status;

    // 当天总数
    private Integer totalNum;

    // 当天剩余数
    private Integer remainNum;

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

}
