package com.xnjr.mall.bo;

import java.util.List;

import com.xnjr.mall.bo.base.IPaginableBO;
import com.xnjr.mall.domain.Sproduct;

public interface ISproductBO extends IPaginableBO<Sproduct> {

    public void addSproduct(Sproduct data);

    public Sproduct getSproduct(String code);

    public void removeSproduct(Sproduct data);

    public void refreshSproduct(Sproduct data);

    public List<Sproduct> quarySproductList(String location, Integer orderNo);

    public void putOn(Sproduct data, String location, Integer orderNo,
            Long price);

    public void putOff(Sproduct data);

    public List<Sproduct> querySproductList(Sproduct condition);

    public void refreshSproduct(Sproduct sproduct, Integer remainNum);

}
