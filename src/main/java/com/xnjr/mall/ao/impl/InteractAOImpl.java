package com.xnjr.mall.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xnjr.mall.ao.IInteractAO;
import com.xnjr.mall.bo.IInteractBO;
import com.xnjr.mall.bo.IProductBO;
import com.xnjr.mall.bo.base.Page;
import com.xnjr.mall.common.DateUtil;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.domain.Product;
import com.xnjr.mall.dto.res.XN003001Res;
import com.xnjr.mall.enums.EInteractType;
import com.xnjr.mall.exception.BizException;

@Service
public class InteractAOImpl implements IInteractAO {

    @Autowired
    private IInteractBO interactBO;

    @Autowired
    private IProductBO productBO;

    @Override
    public Page<XN003001Res> queryProductSCPage(String start, String limit,
            String interacter, String companyCode, String systemCode) {
        Page<XN003001Res> page = interactBO.queryInteractPage(start, limit,
            EInteractType.PRODUCT.getCode(), interacter, companyCode,
            systemCode);
        List<XN003001Res> list = null;
        try {
            list = JsonUtil.fromJsonArray(page.getList().toString(),
                XN003001Res.class);
        } catch (Exception e) {
            throw new BizException("xn00000", "json转化错误");
        }
        for (XN003001Res res : list) {
            Product product = productBO.getProduct(res.getEntityCode());
            res.setProduct(product);
            res.setInteractDatetime(DateUtil.stampToDate(res
                .getInteractDatetimeTimes()));
            res.setInteractDatetimeTimes(null);
        }
        page.setList(list);
        return page;
    }
}
