package com.xnjr.mall.dto.req;

import java.util.List;

public class XN003010Req {

    // 类型（必填）
    private String type;

    // 评论内容列表（必填）
    private List<XN003010CReq> commentList;

    // 评论人（必填）
    private String commenter;

    // 评论人名称（必填）
    private String commenterName;

    // 订单编号（选填）
    private String orderCode;

    // 公司编号（必填）
    private String companyCode;

    // 系统编号（必填）
    private String systemCode;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
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

    public List<XN003010CReq> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<XN003010CReq> commentList) {
        this.commentList = commentList;
    }
}
