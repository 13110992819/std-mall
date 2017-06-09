package com.xnjr.mall.ao;

import java.util.List;

import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.Sorder;

public interface ISorderAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public String commitSorder(String productCode, String startDate,
            String endDate, String reName, String reMobile, String applyUser,
            String applyNote);

    public Object payOrder(List<String> codeList, String payType);

    public void deliver(String code, String handleUser, String remark);

    public void cancelSorder(String code, String handleUser, String remark);

    public Paginable<Sorder> querySorderPage(int start, int limit,
            Sorder condition);

    public Sorder getSorder(String code);

    public List<Sorder> querySorderList(Sorder condition);

    public void paySuccess(String payGroup, String payCode, Long amount);
}
