package com.xnjr.mall.bo.impl;

import org.springframework.stereotype.Component;

import com.xnjr.mall.bo.IInteractBO;
import com.xnjr.mall.bo.base.Page;
import com.xnjr.mall.dto.req.XN003000Req;
import com.xnjr.mall.dto.req.XN003001Req;
import com.xnjr.mall.dto.res.FlagRes;
import com.xnjr.mall.dto.res.XN003001Res;
import com.xnjr.mall.enums.EInteractType;
import com.xnjr.mall.http.BizConnecter;
import com.xnjr.mall.http.JsonUtils;

/** 
 * @author: xieyj 
 * @since: 2017年9月4日 下午10:45:04 
 * @history:
 */
@Component
public class InteractBOImpl implements IInteractBO {

    @Override
    public boolean isInteract(String userId, String entityCode,
            EInteractType interactType, String companyCode, String systemcode) {
        XN003000Req req = new XN003000Req();
        req.setUserId(userId);
        req.setEntityCode(entityCode);
        req.setInteractType(interactType.getCode());
        req.setCompanyCode(companyCode);
        req.setSystemCode(systemcode);
        FlagRes res = BizConnecter.getBizData("003000",
            JsonUtils.object2Json(req), FlagRes.class);
        return res.isFlag();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<XN003001Res> queryInteractPage(String start, String limit,
            String type, String interacter, String companyCode,
            String systemCode) {
        XN003001Req req = new XN003001Req();
        req.setStart(start);
        req.setLimit(limit);
        req.setType(type);
        req.setInteracter(interacter);
        req.setSystemCode(companyCode);
        req.setCompanyCode(systemCode);
        return BizConnecter.getBizData("003001", JsonUtils.object2Json(req),
            Page.class);
    }
}
