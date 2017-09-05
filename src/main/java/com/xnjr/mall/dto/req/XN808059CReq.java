package com.xnjr.mall.dto.req;

/**
 * 评价
 * @author: xieyj 
 * @since: 2017年9月5日 下午5:03:17 
 * @history:
 */
public class XN808059CReq {

    // 产品编号（必填）
    private String productCode;

    // 分数（必填）
    private String score;

    // 内容(选填)
    private String content;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
