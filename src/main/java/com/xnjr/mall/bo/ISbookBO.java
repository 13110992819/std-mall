package com.xnjr.mall.bo;

import java.util.Date;
import java.util.List;

import com.xnjr.mall.bo.base.IPaginableBO;
import com.xnjr.mall.domain.Sbook;

public interface ISbookBO extends IPaginableBO<Sbook> {

    public boolean isSbookExist(String code);

    public String saveSbook(String sproductCode, Date date, Integer remain);

    public int removeSbook(String code);

    public int refreshSbook(String code, Integer remain);

    public List<Sbook> querySbookList(Sbook condition);

    public Sbook getSbook(String code);

    public List<Sbook> querySbookList(String sproductCode, Date date);
}
