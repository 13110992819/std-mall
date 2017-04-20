package com.xnjr.mall.ao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xnjr.mall.ao.IVorderAO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.Vorder;
import com.xnjr.mall.dto.req.XN808650Req;

@Service
public class VorderAOImpl implements IVorderAO {
    protected static final Logger logger = LoggerFactory
        .getLogger(VorderAOImpl.class);

    @Override
    public String commitOrder(XN808650Req req) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object toPayOrder(List<String> codeList, String payType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void cancelOrder(List<String> codeList, String updater, String remark) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deliverOrder(List<String> codeList, String updater,
            String remark) {
        // TODO Auto-generated method stub

    }

    @Override
    public Paginable<Vorder> queryVorderPage(int start, int limit,
            Vorder condition) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vorder getVorder(String code) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Vorder> queryVorderList(Vorder condition) {
        // TODO Auto-generated method stub
        return null;
    }

}
