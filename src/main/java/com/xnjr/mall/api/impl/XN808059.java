package com.xnjr.mall.api.impl;

import org.apache.commons.collections.CollectionUtils;

import com.xnjr.mall.ao.IOrderAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808059Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 评论产品
 * @author: xieyj 
 * @since: 2017年9月5日 下午5:06:50 
 * @history:
 */
public class XN808059 extends AProcessor {
    private IOrderAO orderAO = SpringContextHolder.getBean(IOrderAO.class);

    private XN808059Req req = null;

    /** 
     * @see com.xnjr.mall.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        return orderAO.comment(req.getOrderCode(), req.getCommentList(),
            req.getCommenter());
    }

    /** 
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808059Req.class);
        StringValidater.validateBlank(req.getOrderCode(), req.getCommenter());
        if (CollectionUtils.isEmpty(req.getCommentList())) {
            throw new BizException("xn000000", "评论列表不能为空");
        }
    }
}
