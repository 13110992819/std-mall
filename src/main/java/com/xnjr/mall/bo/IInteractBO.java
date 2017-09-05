package com.xnjr.mall.bo;

import com.xnjr.mall.bo.base.Page;
import com.xnjr.mall.dto.res.XN003001Res;
import com.xnjr.mall.enums.EInteractType;

/** 
 * @author: xieyj 
 * @since: 2017年9月4日 下午10:41:31 
 * @history:
 */
public interface IInteractBO {

    public boolean isInteract(String userId, String entityCode,
            EInteractType interactType, String companyCode, String systemcode);

    public Page<XN003001Res> queryInteractPage(String start, String limit,
            String type, String interacter, String companyCode,
            String systemCode);

}
