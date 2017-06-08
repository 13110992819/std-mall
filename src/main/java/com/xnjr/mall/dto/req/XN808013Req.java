/**
 * @Title XN601003Req.java 
 * @Package com.xnjr.mall.dto.req 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年5月17日 上午11:18:56 
 * @version V1.0   
 */
package com.xnjr.mall.dto.req;

/** 
 * @author: haiqingzheng 
 * @since: 2016年5月17日 上午11:18:56 
 * @history:
 */
public class XN808013Req {
    // 产品编号(必填)
    private String code;

    // 位置(必填)
    private String location;

    // 相对位置编号(必填)
    private String orderNo;

    // 更新人(必填)
    private String updater;

    // 备注(选填)
    private String remark;

    public String getCode() {
        return code;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
