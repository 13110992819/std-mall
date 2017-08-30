package com.xnjr.mall.ao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xnjr.mall.ao.IExpressRuleAO;
import com.xnjr.mall.bo.IExpressRuleBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.ExpressRule;

@Service
public class ExpressRuleAOImpl implements IExpressRuleAO {

    @Autowired
    private IExpressRuleBO expressRuleBO;

    @Override
    public void addExpressRule(String startPoint, String endPoint, Long price,
            String updater, String remark, String companyCode, String systemCode) {
        // 验证是否存在
        expressRuleBO.isExistPoint(startPoint, endPoint, companyCode,
            systemCode);

        // 新增
        ExpressRule data = new ExpressRule();
        data.setStartPoint(startPoint);
        data.setEndPoint(endPoint);
        data.setPrice(price);
        data.setUpdater(updater);
        data.setUpdateDatetime(new Date());
        data.setRemark(remark);
        data.setCompanyCode(companyCode);
        data.setSystemCode(systemCode);
        expressRuleBO.saveExpressRule(data);
    }

    @Override
    public void editExpressRule(Long id, String startPoint, String endPoint,
            Long price, String updater, String remark) {
        ExpressRule dbExpressRule = expressRuleBO.getExpressRule(id);
        // 起点终点修改，验证唯一
        if (!startPoint.equals(dbExpressRule.getStartPoint())
                && !endPoint.equals(dbExpressRule.getEndPoint())) {
            // 验证是否存在
            expressRuleBO.isExistPoint(startPoint, endPoint,
                dbExpressRule.getCompanyCode(), dbExpressRule.getSystemCode());
        }

        // 更新
        dbExpressRule.setStartPoint(startPoint);
        dbExpressRule.setEndPoint(endPoint);
        dbExpressRule.setPrice(price);
        dbExpressRule.setUpdater(updater);
        dbExpressRule.setUpdateDatetime(new Date());
        dbExpressRule.setRemark(remark);
        expressRuleBO.refreshExpressRule(dbExpressRule);
    }

    @Override
    public void dropExpressRule(Long id) {
        expressRuleBO.removeExpressRule(id);
    }

    @Override
    @Transactional
    public void modifyPrices(List<String> idList, Long price, String updater,
            String remark) {
        for (String ids : idList) {
            Long id = Long.valueOf(ids);
            expressRuleBO.refreshPrice(id, price, updater, remark);
        }
    }

    @Override
    public Paginable<ExpressRule> queryExpressRulePage(int start, int limit,
            ExpressRule condition) {
        return expressRuleBO.getPaginable(start, limit, condition);
    }

    @Override
    public ExpressRule getExpressRule(Long id) {
        return expressRuleBO.getExpressRule(id);
    }

    @Override
    public long getPrice(String startPoint, String endPoint,
            String companyCode, String systemCode) {
        return expressRuleBO.getPrice(startPoint, endPoint, companyCode,
            systemCode);
    }
}
