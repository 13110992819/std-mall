package com.xnjr.mall.bo.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.xnjr.mall.bo.ICommentBO;
import com.xnjr.mall.domain.Order;
import com.xnjr.mall.domain.User;
import com.xnjr.mall.dto.req.XN003010CReq;
import com.xnjr.mall.dto.req.XN003010Req;
import com.xnjr.mall.enums.ECommentType;
import com.xnjr.mall.http.BizConnecter;
import com.xnjr.mall.http.JsonUtils;

/** 
 * @author: xieyj 
 * @since: 2017年9月4日 下午10:45:04 
 * @history:
 */
@Component
public class CommentBOImpl implements ICommentBO {

    @Override
    public Object comment(Order order, List<XN003010CReq> commentList,
            User commentUser) {
        XN003010Req req = new XN003010Req();
        req.setType(ECommentType.PRODUCT.getCode());
        req.setCommentList(commentList);
        req.setCommenter(commentUser.getUserId());
        req.setCommenterName(commentUser.getMobile());
        req.setOrderCode(order.getCode());
        req.setCompanyCode(order.getCompanyCode());
        req.setSystemCode(order.getSystemCode());
        return BizConnecter.getBizData("003010", JsonUtils.object2Json(req),
            Object.class);
    }
}
