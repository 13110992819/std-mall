package com.xnjr.mall.dto.req;

public class XN003010CReq {

    // 星级（必填）
    private String score;

    // 内容（选填）
    private String content;

    // 父级编号（必填）
    private String parentCode;

    // 实体编号（必填）
    private String entityCode;

    // 实体名称（必填）
    private String entityName;

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

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
}
