package com.xnjr.mall.ao;

import java.util.List;

import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.ExpressRule;

public interface IExpressRuleAO {
    static final String DEFAULT_ORDER_COLUMN = "id";

    public void addExpressRule(String startPoint, String endPoint, Long price,
            String updater, String remark, String companyCode, String systemCode);

    public void dropExpressRule(Long id);

    public void editExpressRule(Long id, String startPoint, String endPoint,
            Long price, String updater, String remark);

    public void modifyPrices(List<String> idList, Long price, String updater,
            String remark);

    public Paginable<ExpressRule> queryExpressRulePage(int start, int limit,
            ExpressRule condition);

    public ExpressRule getExpressRule(Long id);

    public long getPrice(String startPoint, String endPoint,
            String companyCode, String systemCode);
}
