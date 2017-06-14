package com.xnjr.mall.dto.req;


/**
 * 取消订单
 * @author: asus 
 * @since: 2017年6月8日 下午4:16:15 
 * @history:
 */
public class XN808452Req {
    // 编号（必填）
    private String code;

    // 处理人
    private String handleUser;

    // 备注
    private String remark;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHandleUser() {
        return handleUser;
    }

    public void setHandleUser(String handleUser) {
        this.handleUser = handleUser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
