package com.xnjr.mall.dto.req;

import java.util.List;

public class XN808652Req {

    // 编号（必填）
    private List<String> codeList;

    // 更新人(必填)
    private String updater;

    // 备注(必填)
    private String remark;

    public List<String> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<String> codeList) {
        this.codeList = codeList;
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

}
