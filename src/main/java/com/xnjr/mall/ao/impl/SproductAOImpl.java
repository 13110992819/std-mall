package com.xnjr.mall.ao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xnjr.mall.ao.ISproductAO;
import com.xnjr.mall.bo.ICategoryBO;
import com.xnjr.mall.bo.ISorderBO;
import com.xnjr.mall.bo.ISproductBO;
import com.xnjr.mall.bo.IStoreBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.common.DateUtil;
import com.xnjr.mall.core.OrderNoGenerater;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Sorder;
import com.xnjr.mall.domain.Sproduct;
import com.xnjr.mall.domain.Store;
import com.xnjr.mall.dto.req.XN808400Req;
import com.xnjr.mall.dto.req.XN808402Req;
import com.xnjr.mall.enums.EGeneratePrefix;
import com.xnjr.mall.enums.ESproductStatus;
import com.xnjr.mall.enums.EVorderStatus;
import com.xnjr.mall.exception.BizException;

@Service
public class SproductAOImpl implements ISproductAO {
    @Autowired
    private ISproductBO sproductBO;

    @Autowired
    private ICategoryBO categoryBO;

    @Autowired
    private IStoreBO storeBO;

    @Autowired
    private ISorderBO sorderBO;

    @Override
    public String saveSproduct(XN808400Req req) {
        Store store = storeBO.getStoreByUser(req.getUserId());
        Sproduct data = new Sproduct();
        String code = OrderNoGenerater.generateM(EGeneratePrefix.SPRODUCT
            .getCode());
        data.setCode(code);
        data.setName(req.getName());
        data.setCategory("0");
        data.setType("0");
        data.setStoreCode(store.getCode());
        data.setStoreUser(store.getOwner());

        data.setSlogan(req.getSlogan());
        data.setAdvPic(req.getAdvPic());
        data.setPic(req.getPic());
        data.setDescription(req.getDescription());
        data.setPrice(StringValidater.toLong(req.getPrice()));

        data.setStatus(ESproductStatus.TO_PUBLISH.getCode());
        data.setTotalNum(StringValidater.toInteger(req.getTotalNum()));
        data.setRemainNum(StringValidater.toInteger(req.getTotalNum()));
        data.setCompanyCode(store.getCompanyCode());
        data.setSystemCode(store.getSystemCode());
        sproductBO.addSproduct(data);
        return code;
    }

    @Override
    public void dropSproduct(String code) {
        Sproduct data = sproductBO.getSproduct(code);
        if (!ESproductStatus.TO_PUBLISH.getCode().equals(data.getStatus())) {
            throw new BizException("xn0000", "该服务已上过架,不可删除");
        }
        sproductBO.removeSproduct(data);
    }

    @Override
    public void editSproduct(XN808402Req req) {
        Sproduct data = sproductBO.getSproduct(req.getCode());
        if (ESproductStatus.PUBLISH_YES.getCode().equals(data.getStatus())) {
            throw new BizException("xn0000", "该服务已上架,不可修改");
        }
        Integer num = data.getTotalNum() - data.getRemainNum();
        Integer totalNum = StringValidater.toInteger(req.getTotalNum());
        if (totalNum < num) {
            throw new BizException("xn0000", "总人数小于已报名人数");
        }

        data.setName(req.getName());
        data.setCategory("0");
        data.setType("0");

        data.setSlogan(req.getSlogan());
        data.setAdvPic(req.getAdvPic());
        data.setPic(req.getPic());
        data.setDescription(req.getDescription());
        data.setPrice(StringValidater.toLong(req.getPrice()));

        data.setTotalNum(totalNum);
        data.setRemainNum(totalNum - num);
        sproductBO.refreshSproduct(data);
    }

    @Override
    public void putOn(String code, String location, Integer orderNo, Long price) {
        Sproduct data = sproductBO.getSproduct(code);
        if (ESproductStatus.PUBLISH_YES.getCode().equals(data.getStatus())) {
            throw new BizException("xn000000", "该产品处于已上架状态");
        }
        sproductBO.putOn(data, location, orderNo, price);
    }

    @Override
    public void putOff(String code) {
        Sproduct data = sproductBO.getSproduct(code);
        if (!ESproductStatus.PUBLISH_YES.getCode().equals(data.getStatus())) {
            throw new BizException("xn0000", "服务未上架");
        }
        sproductBO.putOff(data);
    }

    @Override
    public Paginable<Sproduct> querySproductPage(int start, int limit,
            Sproduct condition) {
        return sproductBO.getPaginable(start, limit, condition);
    }

    @Override
    public Sproduct getSproduct(String code) {
        return sproductBO.getSproduct(code);
    }

    @Override
    public List<Sproduct> querySproductList(Sproduct condition) {
        return sproductBO.querySproductList(condition);
    }

    @Override
    public void resetAvaliableNumbers(String code) {
        Sproduct sproduct = sproductBO.getSproduct(code);
        List<String> status = new ArrayList<String>();
        status.add(EVorderStatus.PAYED.getCode());
        status.add(EVorderStatus.DELIVER.getCode());

        Sorder condition = new Sorder();
        condition.setProductCode(code);
        condition.setStatusList(status);
        condition.setToday(DateUtil.getTodayStart());
        Long count = sorderBO.getTotalCount(condition);
        sproductBO.refreshSproduct(sproduct,
            sproduct.getTotalNum() - count.intValue());
    }

    @Override
    public void doResetAvaliableNumbersDaily() {
        List<Sproduct> sproductList = sproductBO
            .querySproductList(new Sproduct());
        for (Sproduct sproduct : sproductList) {
            resetAvaliableNumbers(sproduct.getCode());
        }
    }

}
