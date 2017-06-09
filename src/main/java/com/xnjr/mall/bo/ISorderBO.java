package com.xnjr.mall.bo;

import java.util.Date;
import java.util.List;

import com.xnjr.mall.bo.base.IPaginableBO;
import com.xnjr.mall.domain.Sorder;
import com.xnjr.mall.domain.Sproduct;
import com.xnjr.mall.enums.EPayType;

public interface ISorderBO extends IPaginableBO<Sorder> {

    public String saveSorder(Sproduct sproduct, Date date, Date date2,
            String reName, String reMobile, String applyUser, String applyNote);

    public Sorder getSorder(String code);

    public String addPayGroup(Sorder order, EPayType payType);

    public int refreshPaySuccess(Sorder order, Long payAmount1,
            Long payAmount2, Long payAmount3, String payCode);

    public void deliver(Sorder order, String handleUser, String remark);

    public void cancelSorder(Sorder order, String handleUser, String remark);

    public List<Sorder> querySorderList(Sorder condition);

    public List<Sorder> queryOrderListByPayGroup(String payGroup);
}
