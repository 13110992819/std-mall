package com.xnjr.mall.bo;

import java.util.List;

import com.xnjr.mall.bo.base.IPaginableBO;
import com.xnjr.mall.domain.Vorder;

public interface IVorderBO extends IPaginableBO<Vorder> {

    void saveOrder(Vorder data);

    void payOrder(Vorder order);

    void cancelOrder(Vorder order, String updater, String remark);

    void deliverOrder(Vorder order, String updater, String remark);

    List<Vorder> queryVorderList(Vorder condition);

    Vorder getVorder(String code);

}
