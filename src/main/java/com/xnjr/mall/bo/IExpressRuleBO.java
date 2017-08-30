package com.xnjr.mall.bo;

import com.xnjr.mall.bo.base.IPaginableBO;
import com.xnjr.mall.domain.ExpressRule;

public interface IExpressRuleBO extends IPaginableBO<ExpressRule> {

    public void isExistPoint(String startPoint, String endPoint,
            String companyCode, String systemCode);

    public int saveExpressRule(ExpressRule data);

    public int removeExpressRule(Long id);

    public int refreshExpressRule(ExpressRule data);

    public int refreshPrice(Long id, Long price, String updater, String remark);

    public ExpressRule getExpressRule(Long id);

    public long getPrice(String startPoint, String endPoint,
            String companyCode, String systemCode);
}
