package com.xnjr.mall.ao;

import org.springframework.stereotype.Component;

import com.xnjr.mall.bo.base.Page;
import com.xnjr.mall.dto.res.XN003001Res;

@Component
public interface IInteractAO {

    public Page<XN003001Res> queryProductSCPage(String start,
            String limit, String interacter, String companyCode,
            String systemCode);
}
