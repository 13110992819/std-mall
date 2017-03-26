package com.xnjr.mall.dto.req;

import java.util.List;

/**
 * 确认收货Req
 * @author: xieyj 
 * @since: 2016年6月12日 上午9:27:04 
 * @history:
 */
public class XN808057Req {

    // 编号(必填)
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
