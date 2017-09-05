/**
 * @Title XN808029Req.java 
 * @Package com.xnjr.mall.dto.req 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年4月6日 下午3:26:48 
 * @version V1.0   
 */
package com.xnjr.mall.dto.req;

/** 
 * @author: haiqingzheng 
 * @since: 2017年4月6日 下午3:26:48 
 * @history:
 */
public class XN808950Req {

    // 开始页数(必填)
    private String start;

    // 页面条数(必填)
    private String limit;

    // 用户编号(必填)
    private String userId;

    // 公司编号(必填)
    private String companyCode;

    // 系统编号(必填)
    private String systemCode;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
