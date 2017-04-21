package com.xnjr.mall.bo;

import java.util.List;

import com.xnjr.mall.bo.base.IPaginableBO;
import com.xnjr.mall.domain.Vproduct;

public interface IVproductBO extends IPaginableBO<Vproduct> {
    public void saveVproduct(Vproduct product);

    public int removeVproduct(String code);

    public int refreshVproduct(Vproduct product);

    public int putOff(String code, String updater, String remark);

    public int putOn(Vproduct product);

    public List<Vproduct> queryVproductList(Vproduct condition);

    public Vproduct getVproduct(String code);

}
