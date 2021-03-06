package com.xnjr.mall.bo.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xnjr.mall.bo.IUserBO;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.User;
import com.xnjr.mall.dto.req.XN001100Req;
import com.xnjr.mall.dto.req.XN001102Req;
import com.xnjr.mall.dto.req.XN001301Req;
import com.xnjr.mall.dto.req.XN001302Req;
import com.xnjr.mall.dto.req.XN001350Req;
import com.xnjr.mall.dto.req.XN001351Req;
import com.xnjr.mall.dto.req.XN001400Req;
import com.xnjr.mall.dto.req.XN001403Req;
import com.xnjr.mall.dto.req.XN805183Req;
import com.xnjr.mall.dto.res.XN001102Res;
import com.xnjr.mall.dto.res.XN001400Res;
import com.xnjr.mall.dto.res.XN001403Res;
import com.xnjr.mall.dto.res.XNUserRes;
import com.xnjr.mall.enums.ESysUser;
import com.xnjr.mall.enums.ESystemCode;
import com.xnjr.mall.enums.EUserKind;
import com.xnjr.mall.enums.EUserLevel;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.http.BizConnecter;
import com.xnjr.mall.http.JsonUtils;

/**
 * @author: xieyj 
 * @since: 2016年5月30日 上午9:28:30 
 * @history:
 */
@Component
public class UserBOImpl implements IUserBO {

    @Override
    public void checkTradePwd(String userId, String tradePwd) {
        if (StringUtils.isBlank(tradePwd)) {
            throw new BizException("XN000000", "请输入支付密码");
        }
        XN001100Req req = new XN001100Req();
        req.setTokenId(userId);
        req.setUserId(userId);
        req.setTradePwd(tradePwd);
        BizConnecter.getBizData("001100", JsonUtils.object2Json(req),
            Object.class);
    }

    @Override
    public User getRemoteUser(String userId) {
        User user = null;
        if (StringUtils.isNotBlank(userId)) {
            XN001400Req req = new XN001400Req();
            req.setTokenId(userId);
            req.setUserId(userId);
            XN001400Res res = BizConnecter.getBizData("001400",
                JsonUtils.object2Json(req), XN001400Res.class);
            if (res == null) {
                throw new BizException("XN000000", "编号为" + userId + "的用户不存在");
            }
            user = new User();
            user.setUserId(res.getUserId());
            user.setOpenId(res.getOpenId());
            user.setKind(res.getKind());
            user.setLoginName(res.getLoginName());
            user.setNickname(res.getNickname());
            user.setPhoto(res.getPhoto());
            user.setMobile(res.getMobile());
            user.setIdentityFlag(res.getIdentityFlag());
            user.setUserReferee(res.getUserReferee());
            user.setLevel(res.getLevel());
            user.setProvince(res.getProvince());
            user.setCity(res.getCity());
            user.setArea(res.getArea());
            user.setAddress(res.getAddress());
            user.setDivRate(StringValidater.toDouble(res.getDivRate()));
        }
        return user;
    }

    @Override
    public String isUserExist(String mobile, EUserKind kind, String systemCode) {
        String userId = null;
        XN001102Req req = new XN001102Req();
        req.setMobile(mobile);
        req.setKind(kind.getCode());
        req.setSystemCode(systemCode);
        XN001102Res res = BizConnecter.getBizData("001102",
            JsonUtils.object2Json(req), XN001102Res.class);
        if (res != null) {
            userId = res.getUserId();
        }
        return userId;
    }

    @Override
    public String doSaveBUser(String mobile, String userReferee,
            String updater, String systemCode, String companyCode) {
        XN001350Req req = new XN001350Req();
        req.setLoginName(mobile);
        req.setMobile(mobile);
        req.setUserReferee(userReferee);
        req.setUpdater(updater);
        req.setRemark("代注册商家");

        req.setSystemCode(systemCode);
        req.setCompanyCode(companyCode);
        XNUserRes res = BizConnecter.getBizData("001350",
            JsonUtils.object2Json(req), XNUserRes.class);
        return res.getUserId();
    }

    @Override
    public String doSaveUser(EUserKind kind, String mobile, String userReferee,
            String updater, String systemCode, String companyCode, String remark) {
        XN001351Req req = new XN001351Req();
        req.setKind(kind.getCode());
        req.setLoginName(mobile);
        req.setMobile(mobile);
        req.setUserReferee(userReferee);
        req.setUpdater(updater);
        req.setRemark(remark);
        req.setSystemCode(systemCode);
        req.setCompanyCode(companyCode);
        XNUserRes res = BizConnecter.getBizData("001351",
            JsonUtils.object2Json(req), XNUserRes.class);
        return res.getUserId();
    }

    @Override
    public String doSaveCUser(String mobile, String loginPwd, String updater,
            String remark, String systemCode) {
        XN001301Req req = new XN001301Req();
        req.setMobile(mobile);
        req.setLoginPwd(loginPwd);
        req.setUpdater(updater);
        req.setUserReferee(ESysUser.SYS_USER_CAIGO.getCode());
        req.setRemark(remark);
        req.setCompanyCode(systemCode);
        req.setSystemCode(systemCode);
        XNUserRes res = BizConnecter.getBizData("001301",
            JsonUtils.object2Json(req), XNUserRes.class);
        return res.getUserId();
    }

    @Override
    public User getPartner(String province, String city, String area,
            EUserKind kind) {
        if (StringUtils.isBlank(city) && StringUtils.isBlank(area)) {
            city = province;
            area = province;
        } else if (StringUtils.isBlank(area)) {
            area = city;
        }
        XN001403Req req = new XN001403Req();
        req.setProvince(province);
        req.setCity(city);
        req.setArea(area);
        req.setKind(kind.getCode());
        req.setSystemCode(ESystemCode.JKEG.getCode());
        req.setCompanyCode(ESystemCode.JKEG.getCode());
        XN001403Res result = null;
        String jsonStr = BizConnecter.getBizData("001403",
            JsonUtils.object2Json(req));
        Gson gson = new Gson();
        List<XN001403Res> list = gson.fromJson(jsonStr,
            new TypeToken<List<XN001403Res>>() {
            }.getType());
        User user = null;
        if (CollectionUtils.isNotEmpty(list)) {
            result = list.get(0);
            user = new User();
            user.setUserId(result.getUserId());
            user.setLoginName(result.getLoginName());
            user.setMobile(result.getMobile());
            user.setUserReferee(result.getUserReferee());
        }
        return user;
    }

    @Override
    public void doUpgrade(String userId) {
        XN001302Req req = new XN001302Req();
        req.setUserId(userId);
        req.setLevel(EUserLevel.ONE.getCode());
        BizConnecter.getBizData("001302", JsonUtils.object2Json(req),
            XNUserRes.class);
    }

    @Override
    public void doApprove(String userId, String approver, String approveResult,
            String remark) {
        XN805183Req req = new XN805183Req();
        req.setUserId(userId);
        req.setApprover(approver);
        req.setApproveResult(approveResult);
        req.setRemark(remark);
        BizConnecter.getBizData("805183", JsonUtils.object2Json(req));
    }
}
