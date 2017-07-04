package com.xnjr.mall.bo.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xnjr.mall.bo.IAccountBO;
import com.xnjr.mall.bo.IDistributeBO;
import com.xnjr.mall.bo.ISYSConfigBO;
import com.xnjr.mall.bo.IUserBO;
import com.xnjr.mall.domain.Store;
import com.xnjr.mall.domain.User;
import com.xnjr.mall.enums.ESystemCode;
import com.xnjr.mall.enums.EUserKind;

@Component
public class DistributeBOImpl implements IDistributeBO {

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    @Override
    // 分润总额来源
    // 例如：客户在O2O实际消费1000元，O2O商户与平台签约给会员打8折后让利给平台10%，消费会员应付给O2O商户800元，商家再拿出80元，供各个环节分佣使用
    /** 1、该O2O商户签约人10% ← 推荐人4% ←推荐人2%
    2、该O2O商户所在的市运营中心10%←推荐人2%
    3、该O2O商户所在的区运营中心10%←推荐人2%
    4、该消费会员是O2O商户推荐的，该O2O商户可得10%
    5、该消费会员是供货商推荐的，该供货商可得10%
    6、该消费会员是普通会员推荐5%←推荐人5%←推荐人3%
    7、该消费会员是VIP会员推荐10%←推荐人8%←推荐人5%
    8、该消费会员所属的市运营中心得2%
    9、该消费会员所属的区运营中心得2%
    10、该消费会员得10% **/
    public void distributeO2O(User consumer, Store store, Long frAmount) {
        // 获取分销配置
        Map<String, String> configsMap = sysConfigBO
            .getConfigsMap(ESystemCode.JKEG.getCode());

        // 查询店铺推荐人（即签约人），签约人一级推荐人和二级推荐人
        User storeReferee = null;
        User storeRefereeI = null;
        User storeRefereeII = null;
        if (store != null && StringUtils.isNotBlank(store.getUserReferee())) {
            storeReferee = userBO.getRemoteUser(store.getUserReferee());
        }
        if (storeReferee != null
                && StringUtils.isNotBlank(storeReferee.getUserReferee())) {
            storeRefereeI = userBO.getRemoteUser(storeReferee.getUserReferee());
        }
        if (storeRefereeI != null
                && StringUtils.isNotBlank(storeRefereeI.getUserReferee())) {
            storeRefereeII = userBO.getRemoteUser(storeRefereeI
                .getUserReferee());
        }

        // 查询商户所属市和区运营中心及运营中心推荐人
        User cityUser = userBO.getPartner(store.getProvince(), store.getCity(),
            store.getCity(), EUserKind.Partner);
        User cityUserReferee = null;
        if (cityUser != null
                && StringUtils.isNotBlank(cityUser.getUserReferee())) {
            cityUserReferee = userBO.getRemoteUser(cityUser.getUserReferee());
        }
        User areaUser = userBO.getPartner(store.getProvince(), store.getCity(),
            store.getArea(), EUserKind.Partner);
        User areaUserReferee = null;
        if (cityUser != null
                && StringUtils.isNotBlank(cityUser.getUserReferee())) {
            areaUserReferee = userBO.getRemoteUser(areaUser.getUserReferee());
        }

        // 查询消费会员推荐人
        User consumerReferee = null;
        if (consumer != null
                && StringUtils.isNotBlank(consumer.getUserReferee())) {
            consumerReferee = userBO.getRemoteUser(consumer.getUserReferee());
        }

        // 开始分赃

    }

    @Override
    // 分润总额来源 例如：客户在商城购买1000元商品，供货商8折供货，留下200元利润做各个环节分佣使用
    /** 1、该供应商签约人得10%  ← 推荐人3% ←推荐人2%
    2、该消费会员是O2O商户推荐的，该O2O商户可得15%
    3、该消费会员是供货商推荐的，该供货商可得15%
    4、该消费会员是普通会员推荐的10%←推荐人3%←推荐人2%
    5、该消费会员是VIP会员推荐的20%←推荐人8%←推荐人5%
    6、该消费会员所属的市运营中心10%←推荐人2%
    7、该消费会员所属的区运营中心10%←推荐人2%
    8、该消费会员得10% **/
    public void distributeMall(User consumer, Store store, Long frAmount) {

    }

}
