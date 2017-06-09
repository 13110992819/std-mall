package com.xnjr.mall.ao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xnjr.mall.ao.ISproductAO;
import com.xnjr.mall.bo.ICategoryBO;
import com.xnjr.mall.bo.ISproductBO;
import com.xnjr.mall.bo.IStoreBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.core.OrderNoGenerater;
import com.xnjr.mall.domain.Sproduct;
import com.xnjr.mall.enums.EBoolean;
import com.xnjr.mall.enums.EGeneratePrefix;
import com.xnjr.mall.enums.ESproductStatus;
import com.xnjr.mall.exception.BizException;

@Service
public class SproductAOImpl implements ISproductAO {
    @Autowired
    private ISproductBO sproductBO;

    @Autowired
    private ICategoryBO categoryBO;

    @Autowired
    private IStoreBO storeBO;

    @Override
    public String saveSproductAO(String name, String category, String type,
            String storeCode, String slogan, String advPic, String pic,
            String description, Long amount, Integer totalNum,
            String companyCode, String systemCode) {
        categoryBO.getCategory(category);
        storeBO.getStore(storeCode);
        Sproduct data = new Sproduct();
        String code = OrderNoGenerater.generateM(EGeneratePrefix.SPRODUCT
            .getCode());
        data.setCode(code);
        data.setName(name);
        data.setCategory(category);
        data.setType(type);
        data.setStoreCode(storeCode);

        data.setSlogan(slogan);
        data.setAdvPic(advPic);
        data.setPic(pic);
        data.setDescription(description);
        data.setPrice(amount);

        data.setStatus(ESproductStatus.TO_APPROVE.getCode());
        data.setTotalNum(totalNum);
        data.setRemainNum(totalNum);
        data.setLocation(EBoolean.NO.getCode());
        data.setOrderNo(0);

        data.setCompanyCode(companyCode);
        data.setSystemCode(systemCode);
        sproductBO.addSproduct(data);
        return code;
    }

    @Override
    public void dropSproduct(String code) {
        Sproduct data = sproductBO.getSproduct(code);
        if (!ESproductStatus.TO_APPROVE.getCode().equals(data.getStatus())) {
            throw new BizException("xn0000", "改服务已上过架,不可删除");
        }
        sproductBO.removeSproduct(data);
    }

    @Override
    public void editSproduct(String code, String name, String category,
            String type, String slogan, String advPic, String pic,
            String description, Long amount, Integer totalNum) {
        categoryBO.getCategory(category);
        Sproduct data = sproductBO.getSproduct(code);
        if (ESproductStatus.PUBLISH_YES.getCode().equals(data.getStatus())) {
            throw new BizException("xn0000", "服务正在上架,不可修改");
        }
        Integer num = data.getTotalNum() - data.getRemainNum();
        if (totalNum < num) {
            throw new BizException("xn0000", "当前报名人数已超过总人数,请重新确认");
        }
        data.setName(name);
        data.setCategory(category);
        data.setType(type);
        data.setSlogan(slogan);
        data.setAdvPic(advPic);

        data.setPic(pic);
        data.setDescription(description);
        data.setPrice(amount);
        data.setTotalNum(totalNum);
        data.setRemainNum(totalNum - num);
        sproductBO.refreshSproduct(data);
    }

    @Override
    public void putOn(String code, String location, Integer orderNo) {
        Sproduct data = sproductBO.getSproduct(code);
        if (ESproductStatus.PUBLISH_YES.getCode().equals(data.getStatus())) {
            throw new BizException("xn0000", "服务不可重复发布");
        }
        List<Sproduct> sproductList = sproductBO.quarySproductList(location,
            orderNo);
        if (CollectionUtils.isNotEmpty(sproductList)) {
            throw new BizException("xn0000", "顺序重复");
        }
        sproductBO.putOn(data, location, orderNo);
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
}
