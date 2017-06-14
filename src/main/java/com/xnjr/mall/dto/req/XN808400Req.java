package com.xnjr.mall.dto.req;

/**
 * 新增服务
 * @author: asus 
 * @since: 2017年6月8日 下午3:42:03 
 * @history:
 */
public class XN808400Req {
    // 所属商家用户编号（必填）
    private String userId;

    // 名字（必填）
    private String name;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

}
