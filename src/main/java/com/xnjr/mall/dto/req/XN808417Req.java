package com.xnjr.mall.dto.req;

/**
 * 列表查询服务
 * @author: asus 
 * @since: 2017年6月8日 下午3:58:09 
 * @history:
 */
public class XN808417Req extends APageReq {

    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 6089168914557388493L;

    // 名字
    private String name;

    // 大类
    private String category;

    // 小类
    private String type;

    // 所属商家编号
    private String storeCode;

    // 状态（1 待上架，2 已上架，3 已下架）
    private String status;

    // 位置
    private String location;

    // 所属公司编号
    private String companyCode;

    // 所属系统编号
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
