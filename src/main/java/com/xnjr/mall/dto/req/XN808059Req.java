package com.xnjr.mall.dto.req;

import java.util.List;

public class XN808059Req {

    // 编号（必填）
    private List<XN808059CReq> commentList;

    // 订单编号（必填）
    private String orderCode;

    // 评论人（必填）
    private String commenter;

    public List<XN808059CReq> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<XN808059CReq> commentList) {
        this.commentList = commentList;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }
}
