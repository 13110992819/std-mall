package com.xnjr.mall.bo.impl;

import org.springframework.stereotype.Component;

import com.xnjr.mall.bo.IUserBO;
import com.xnjr.mall.bo.base.PaginableBOImpl;
import com.xnjr.mall.domain.User;
import com.xnjr.mall.dto.req.XN805042Req;
import com.xnjr.mall.dto.req.XN805300Req;
import com.xnjr.mall.dto.req.XN805901Req;
import com.xnjr.mall.dto.req.XN805902Req;
import com.xnjr.mall.dto.req.XN805910Req;
import com.xnjr.mall.dto.res.XN805042Res;
import com.xnjr.mall.dto.res.XN805901Res;
import com.xnjr.mall.dto.res.XN805910Res;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.http.BizConnecter;
import com.xnjr.mall.http.JsonUtils;

/**
 * @author: xieyj 
 * @since: 2016年5月30日 上午9:28:30 
 * @history:
 */
@Component
public class UserBOImpl extends PaginableBOImpl<User> implements IUserBO {

    /** 
     * @see com.xnjr.mall.bo.IUserBO#getRemoteUser(java.lang.String)
     */
    @Override
    public XN805901Res getRemoteUser(String tokenId, String userId) {
        XN805901Req req = new XN805901Req();
        req.setTokenId(tokenId);
        req.setUserId(userId);
        XN805901Res res = BizConnecter.getBizData("805901",
            JsonUtils.object2Json(req), XN805901Res.class);
        if (res == null) {
            throw new BizException("XN000000", "编号为" + userId + "的用户不存在");
        }
        return res;
    }

    /** 
     * @see com.xnjr.mall.bo.IUserBO#checkTradePwd(java.lang.String, java.lang.String)
     */
    @Override
    public void checkTradePwd(String userId, String tradePwd) {
        XN805902Req req = new XN805902Req();
        req.setUserId(userId);
        req.setTradePwd(tradePwd);
        BizConnecter.getBizData("805902", JsonUtils.object2Json(req),
            Object.class);
    }

    /** 
     * @see com.xnjr.mall.bo.IUserBO#doTransfer(java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.lang.String)
     */
    @Override
    public void doTransfer(String userId, String direction, Long amount,
            String remark, String refNo) {
        XN805300Req req = new XN805300Req();
        req.setUserId(userId);
        req.setDirection(direction);
        req.setAmount(String.valueOf(amount));
        req.setRemark(remark);
        req.setRefNo(refNo);
        BizConnecter.getBizData("805300", JsonUtils.object2Json(req),
            Object.class);
    }

    /** 
     * @see com.xnjr.mall.bo.IUserBO#doSaveUser(com.xnjr.mall.dto.req.XN805042Req)
     */
    @Override
    public String doSaveUser(XN805042Req req) {
        XN805042Res res = BizConnecter.getBizData("805042",
            JsonUtils.object2Json(req), XN805042Res.class);
        return res.getUserId();
    }

    @Override
    public String getUserId(String mobile, String kind, String systemCode) {
        String userId = null;
        XN805910Req req = new XN805910Req();
        req.setMobile(mobile);
        req.setKind(kind);
        req.setSystemCode(systemCode);
        XN805910Res res = BizConnecter.getBizData("805910",
            JsonUtils.object2Json(req), XN805910Res.class);
        if (res != null) {
            userId = res.getUserId();
        }
        return userId;
    }
}
