package com.xnjr.mall.dto.res;

import com.xnjr.mall.dto.req.APageReq;

public class XN003001Req extends APageReq {

    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 4097932178415630345L;

    // 类型(1 点赞 2 收藏 3 浏览)（选填）
    private String type;

    // 实体编号（选填）
    private String entityCode;

    // 交互人（选填）
    private String interacter;

    // 公司编号（选填）
    private String companyCode;

    // 系统编号（必填）
    private String systemCode;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getInteracter() {
        return interacter;
    }

    public void setInteracter(String interacter) {
        this.interacter = interacter;
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
