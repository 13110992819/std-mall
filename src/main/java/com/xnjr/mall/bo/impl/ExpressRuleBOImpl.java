package com.xnjr.mall.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xnjr.mall.bo.IExpressRuleBO;
import com.xnjr.mall.bo.base.PaginableBOImpl;
import com.xnjr.mall.dao.IExpressRuleDAO;
import com.xnjr.mall.domain.ExpressRule;
import com.xnjr.mall.exception.BizException;

@Component
public class ExpressRuleBOImpl extends PaginableBOImpl<ExpressRule> implements
        IExpressRuleBO {

    @Autowired
    private IExpressRuleDAO expressRuleDAO;

    @Override
    public void isExistPoint(String startPoint, String endPoint,
            String companyCode, String systemCode) {
        ExpressRule condition = new ExpressRule();
        condition.setStartPoint(startPoint);
        condition.setEndPoint(endPoint);
        condition.setCompanyCode(companyCode);
        condition.setSystemCode(systemCode);
        long count = expressRuleDAO.selectTotalCount(condition);
        if (count > 0) {
            throw new BizException("xn0000", startPoint + ":" + endPoint
                    + "出发地和目的地已存在");
        }
    }

    @Override
    public int saveExpressRule(ExpressRule data) {
        int count = 0;
        if (data != null) {
            expressRuleDAO.insert(data);
        }
        return count;
    }

    @Override
    public int removeExpressRule(Long id) {
        int count = 0;
        if (null != id) {
            ExpressRule data = new ExpressRule();
            data.setId(id);
            count = expressRuleDAO.delete(data);
        }
        return count;
    }

    @Override
    public int refreshExpressRule(ExpressRule data) {
        int count = 0;
        if (null != data && null != data.getId()) {
            count = expressRuleDAO.update(data);
        }
        return count;
    }

    @Override
    public int refreshPrice(Long id, Long price, String updater, String remark) {
        int count = 0;
        if (null != id) {
            ExpressRule data = new ExpressRule();
            data.setId(id);
            data.setPrice(price);
            data.setUpdater(updater);
            data.setUpdateDatetime(new Date());
            data.setRemark(remark);
            count = expressRuleDAO.updatePrice(data);
        }
        return count;
    }

    @Override
    public ExpressRule getExpressRule(Long id) {
        ExpressRule data = null;
        if (null != id) {
            ExpressRule condition = new ExpressRule();
            condition.setId(id);
            data = expressRuleDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "快递规则不存在");
            }
        }
        return data;
    }

    @Override
    public long getPrice(String startPoint, String endPoint,
            String companyCode, String systemCode) {
        long price = 0;
        ExpressRule condition = new ExpressRule();
        condition.setStartPoint(startPoint);
        condition.setEndPoint(endPoint);
        condition.setCompanyCode(companyCode);
        condition.setSystemCode(systemCode);
        List<ExpressRule> list = expressRuleDAO.selectList(condition);
        if (CollectionUtils.isNotEmpty(list)) {
            ExpressRule expressRule = list.get(0);
            price = expressRule.getPrice();
        }
        return price;
    }
}
