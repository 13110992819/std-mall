package com.xnjr.mall.dto.res;

/**
 * 查询交互是否关注
 * @author: xieyj 
 * @since: 2017年9月4日 下午10:55:03 
 * @history:
 */
public class XN003000Req {

    // 用户编号(必填)
    private String userId;

    // 对象编号(必填)
    private String entityCode;

    // 交互类型(必填)
    private String interactType;

    // 公司编号(必填)
    private String companyCode;

    // 系统编号(必填)
    private String systemCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getInteractType() {
        return interactType;
    }

    public void setInteractType(String interactType) {
        this.interactType = interactType;
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
