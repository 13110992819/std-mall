package com.xnjr.mall.dto.res;

import java.util.Date;

import com.xnjr.mall.dao.base.ABaseDO;
import com.xnjr.mall.domain.Product;

public class XN003001Res extends ABaseDO {

    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 1L;

    // 编号
    private String code;

    // 类型(1 点赞 2 收藏 3 浏览)
    private String type;

    // 实体编号
    private String entityCode;

    // 交互人
    private String interacter;

    // 交互时间
    private Date interactDatetime;

    // 交互时间
    private Long interactDatetimeTimes;

    // 公司编号
    private String companyCode;

    // 系统编号
    private String systemCode;

    // 产品
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getEntityCode() {
        return entityCode;
    }

    public void setInteracter(String interacter) {
        this.interacter = interacter;
    }

    public String getInteracter() {
        return interacter;
    }

    public Long getInteractDatetimeTimes() {
        return interactDatetimeTimes;
    }

    public void setInteractDatetimeTimes(Long interactDatetimeTimes) {
        this.interactDatetimeTimes = interactDatetimeTimes;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public Date getInteractDatetime() {
        return interactDatetime;
    }

    public void setInteractDatetime(Date interactDatetime) {
        this.interactDatetime = interactDatetime;
    }
}
