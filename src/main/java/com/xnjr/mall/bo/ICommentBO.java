/**
 * @Title ICommentBO.java 
 * @Package com.xnjr.mall.bo 
 * @Description 
 * @author xieyj  
 * @date 2017年9月5日 下午5:49:48 
 * @version V1.0   
 */
package com.xnjr.mall.bo;

import java.util.List;

import com.xnjr.mall.domain.Order;
import com.xnjr.mall.domain.User;
import com.xnjr.mall.dto.req.XN003010CReq;

/** 
 * @author: xieyj 
 * @since: 2017年9月5日 下午5:49:48 
 * @history:
 */
public interface ICommentBO {

    public Object comment(Order order, List<XN003010CReq> commentList,
            User commentUser);

}
