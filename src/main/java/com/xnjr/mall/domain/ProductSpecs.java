package com.xnjr.mall.domain;

import com.xnjr.mall.dao.base.ABaseDO;

public class ProductSpecs extends ABaseDO {
    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 2484198146787764549L;

    // ******************db properties ********************
    // 编号
    private String code;

    // 名称
    private String name;

    // 产品编号
    private String productCode;

    // 市场参考价，原价
    private Long originalPrice;

    // 价格1(人民币)
    private Long price1;

    // 价格2(虚拟币1)
    private Long price2;

    // 价格3(虚拟币2)
    private Long price3;

    // 数量
    private Integer quantity;

    // 发货地（ 精确到省份）
    private String province;

    // 重量（kg）
    private Double weight;

    // 相对位置编号
    private Integer orderNo;

    // 所属公司编号
    private String companyCode;

    // 所属系统编号
    private String systemCode;

    // ******************db properties ********************

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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Long getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Long originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Long getPrice1() {
        return price1;
    }

    public void setPrice1(Long price1) {
        this.price1 = price1;
    }

    public Long getPrice2() {
        return price2;
    }

    public void setPrice2(Long price2) {
        this.price2 = price2;
    }

    public Long getPrice3() {
        return price3;
    }

    public void setPrice3(Long price3) {
        this.price3 = price3;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
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
