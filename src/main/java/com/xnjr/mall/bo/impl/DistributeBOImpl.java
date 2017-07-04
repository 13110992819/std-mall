package com.xnjr.mall.bo.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xnjr.mall.bo.IAccountBO;
import com.xnjr.mall.bo.IDistributeBO;
import com.xnjr.mall.bo.ISYSConfigBO;
import com.xnjr.mall.bo.IUserBO;
import com.xnjr.mall.common.AmountUtil;
import com.xnjr.mall.common.SysConstants;
import com.xnjr.mall.domain.Store;
import com.xnjr.mall.domain.User;
import com.xnjr.mall.enums.EBizType;
import com.xnjr.mall.enums.ECurrency;
import com.xnjr.mall.enums.ESysUser;
import com.xnjr.mall.enums.ESystemCode;
import com.xnjr.mall.enums.EUserKind;
import com.xnjr.mall.enums.EUserLevel;

@Component
public class DistributeBOImpl implements IDistributeBO {

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

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
    public void distributeO2O(User consumer, Store store, Long frAmount,
            String refNo) {
        // 获取分销配置
        Map<String, String> configsMap = sysConfigBO
            .getConfigsMap(ESystemCode.JKEG.getCode());

        // 查询店铺推荐人（即签约人）
        if (store != null && StringUtils.isNotBlank(store.getUserReferee())) {
            User storeReferee = userBO.getRemoteUser(store.getUserReferee());
            Long FC1 = AmountUtil.mul(frAmount,
                Double.valueOf(configsMap.get(SysConstants.O2O_FC1)));
            if (FC1 > 0) {
                accountBO.doTransferAmountRemote(
                    ESysUser.SYS_USER_JKEG.getCode(), storeReferee.getUserId(),
                    ECurrency.CNY, FC1, EBizType.JKEG_O2O_FR,
                    EBizType.JKEG_O2O_FR.getValue(),
                    EBizType.JKEG_O2O_FR.getValue(), refNo);
            }
            // 签约人一级推荐人
            if (StringUtils.isNotBlank(storeReferee.getUserReferee())) {
                User storeRefereeI = userBO.getRemoteUser(storeReferee
                    .getUserReferee());
                Long FC2 = AmountUtil.mul(frAmount,
                    Double.valueOf(configsMap.get(SysConstants.O2O_FC2)));
                if (FC2 > 0) {
                    accountBO.doTransferAmountRemote(
                        ESysUser.SYS_USER_JKEG.getCode(),
                        storeRefereeI.getUserId(), ECurrency.CNY, FC2,
                        EBizType.JKEG_O2O_FR, EBizType.JKEG_O2O_FR.getValue(),
                        EBizType.JKEG_O2O_FR.getValue(), refNo);
                }
                // 签约人二级推荐人
                if (StringUtils.isNotBlank(storeRefereeI.getUserReferee())) {
                    User storeRefereeII = userBO.getRemoteUser(storeRefereeI
                        .getUserReferee());
                    Long FC3 = AmountUtil.mul(frAmount,
                        Double.valueOf(configsMap.get(SysConstants.O2O_FC3)));
                    if (FC3 > 0) {
                        accountBO.doTransferAmountRemote(
                            ESysUser.SYS_USER_JKEG.getCode(),
                            storeRefereeII.getUserId(), ECurrency.CNY, FC3,
                            EBizType.JKEG_O2O_FR,
                            EBizType.JKEG_O2O_FR.getValue(),
                            EBizType.JKEG_O2O_FR.getValue(), refNo);
                    }
                }
            }
        }

        // 查询商户所属市和区运营中心及运营中心推荐人
        User cityUser = userBO.getPartner(store.getProvince(), store.getCity(),
            store.getCity(), EUserKind.Partner);
        if (cityUser != null) {
            Long FC4 = AmountUtil.mul(frAmount,
                Double.valueOf(configsMap.get(SysConstants.O2O_FC4)));
            if (FC4 > 0) {
                accountBO.doTransferAmountRemote(
                    ESysUser.SYS_USER_JKEG.getCode(), cityUser.getUserId(),
                    ECurrency.CNY, FC4, EBizType.JKEG_O2O_FR,
                    EBizType.JKEG_O2O_FR.getValue(),
                    EBizType.JKEG_O2O_FR.getValue(), refNo);
            }
            if (StringUtils.isNotBlank(cityUser.getUserReferee())) {
                User cityUserReferee = userBO.getRemoteUser(cityUser
                    .getUserReferee());
                Long FC5 = AmountUtil.mul(frAmount,
                    Double.valueOf(configsMap.get(SysConstants.O2O_FC5)));
                if (FC5 > 0) {
                    accountBO.doTransferAmountRemote(
                        ESysUser.SYS_USER_JKEG.getCode(),
                        cityUserReferee.getUserId(), ECurrency.CNY, FC5,
                        EBizType.JKEG_O2O_FR, EBizType.JKEG_O2O_FR.getValue(),
                        EBizType.JKEG_O2O_FR.getValue(), refNo);
                }
            }
        }

        User areaUser = userBO.getPartner(store.getProvince(), store.getCity(),
            store.getArea(), EUserKind.Partner);
        if (areaUser != null) {
            Long FC6 = AmountUtil.mul(frAmount,
                Double.valueOf(configsMap.get(SysConstants.O2O_FC6)));
            if (FC6 > 0) {
                accountBO.doTransferAmountRemote(
                    ESysUser.SYS_USER_JKEG.getCode(), areaUser.getUserId(),
                    ECurrency.CNY, FC6, EBizType.JKEG_O2O_FR,
                    EBizType.JKEG_O2O_FR.getValue(),
                    EBizType.JKEG_O2O_FR.getValue(), refNo);
            }
            if (StringUtils.isNotBlank(cityUser.getUserReferee())) {
                User areaUserReferee = userBO.getRemoteUser(areaUser
                    .getUserReferee());
                Long FC7 = AmountUtil.mul(frAmount,
                    Double.valueOf(configsMap.get(SysConstants.O2O_FC7)));
                if (FC7 > 0) {
                    accountBO.doTransferAmountRemote(
                        ESysUser.SYS_USER_JKEG.getCode(),
                        areaUserReferee.getUserId(), ECurrency.CNY, FC7,
                        EBizType.JKEG_O2O_FR, EBizType.JKEG_O2O_FR.getValue(),
                        EBizType.JKEG_O2O_FR.getValue(), refNo);
                }
            }
        }

        // 查询消费会员推荐人
        if (consumer != null
                && StringUtils.isNotBlank(consumer.getUserReferee())) {
            User consumerReferee = userBO.getRemoteUser(consumer
                .getUserReferee());
            if (EUserKind.JKEG_O2O.getCode().equals(consumerReferee.getKind())
                    || EUserKind.JKEG_MINGSU.getCode().equals(// 推荐人是商户
                        consumerReferee.getKind())) {
                Long FC8 = AmountUtil.mul(frAmount,
                    Double.valueOf(configsMap.get(SysConstants.O2O_FC8)));
                if (FC8 > 0) {
                    accountBO.doTransferAmountRemote(
                        ESysUser.SYS_USER_JKEG.getCode(),
                        consumerReferee.getUserId(), ECurrency.CNY, FC8,
                        EBizType.JKEG_O2O_FR, EBizType.JKEG_O2O_FR.getValue(),
                        EBizType.JKEG_O2O_FR.getValue(), refNo);
                }
            } else if (EUserKind.JKEG_SUPPLIER.getCode().equals(// 推荐人是供应商
                consumerReferee.getKind())) {
                Long FC9 = AmountUtil.mul(frAmount,
                    Double.valueOf(configsMap.get(SysConstants.O2O_FC9)));
                if (FC9 > 0) {
                    accountBO.doTransferAmountRemote(
                        ESysUser.SYS_USER_JKEG.getCode(),
                        consumerReferee.getUserId(), ECurrency.CNY, FC9,
                        EBizType.JKEG_O2O_FR, EBizType.JKEG_O2O_FR.getValue(),
                        EBizType.JKEG_O2O_FR.getValue(), refNo);
                }
            } else if (EUserKind.F1.getCode().equals(consumerReferee.getKind())
                    && EUserLevel.ZERO.getCode().equals(// 推荐人是普通会员
                        consumerReferee.getLevel())) {
                Long FC10 = AmountUtil.mul(frAmount,
                    Double.valueOf(configsMap.get(SysConstants.O2O_FC10)));
                if (FC10 > 0) {
                    accountBO.doTransferAmountRemote(
                        ESysUser.SYS_USER_JKEG.getCode(),
                        consumerReferee.getUserId(), ECurrency.CNY, FC10,
                        EBizType.JKEG_O2O_FR, EBizType.JKEG_O2O_FR.getValue(),
                        EBizType.JKEG_O2O_FR.getValue(), refNo);
                }
                if (StringUtils.isNotBlank(consumerReferee.getUserReferee())) {
                    User consumerRefereeI = userBO
                        .getRemoteUser(consumerReferee.getUserReferee());
                    Long FC11 = AmountUtil.mul(frAmount,
                        Double.valueOf(configsMap.get(SysConstants.O2O_FC11)));
                    if (FC11 > 0) {
                        accountBO.doTransferAmountRemote(
                            ESysUser.SYS_USER_JKEG.getCode(),
                            consumerRefereeI.getUserId(), ECurrency.CNY, FC11,
                            EBizType.JKEG_O2O_FR,
                            EBizType.JKEG_O2O_FR.getValue(),
                            EBizType.JKEG_O2O_FR.getValue(), refNo);
                    }
                    if (StringUtils.isNotBlank(consumerRefereeI
                        .getUserReferee())) {
                        User consumerRefereeII = userBO
                            .getRemoteUser(consumerRefereeI.getUserReferee());
                        Long FC12 = AmountUtil.mul(frAmount, Double
                            .valueOf(configsMap.get(SysConstants.O2O_FC12)));
                        if (FC12 > 0) {
                            accountBO.doTransferAmountRemote(
                                ESysUser.SYS_USER_JKEG.getCode(),
                                consumerRefereeII.getUserId(), ECurrency.CNY,
                                FC12, EBizType.JKEG_O2O_FR,
                                EBizType.JKEG_O2O_FR.getValue(),
                                EBizType.JKEG_O2O_FR.getValue(), refNo);
                        }
                    }
                }
            } else if (EUserKind.F1.getCode().equals(consumerReferee.getKind())
                    && EUserLevel.ONE.getCode().equals(// 推荐人是VIP会员
                        consumerReferee.getLevel())) {
                Long FC13 = AmountUtil.mul(frAmount,
                    Double.valueOf(configsMap.get(SysConstants.O2O_FC13)));
                if (FC13 > 0) {
                    accountBO.doTransferAmountRemote(
                        ESysUser.SYS_USER_JKEG.getCode(),
                        consumerReferee.getUserId(), ECurrency.CNY, FC13,
                        EBizType.JKEG_O2O_FR, EBizType.JKEG_O2O_FR.getValue(),
                        EBizType.JKEG_O2O_FR.getValue(), refNo);
                }
                if (StringUtils.isNotBlank(consumerReferee.getUserReferee())) {
                    User consumerRefereeI = userBO
                        .getRemoteUser(consumerReferee.getUserReferee());
                    Long FC14 = AmountUtil.mul(frAmount,
                        Double.valueOf(configsMap.get(SysConstants.O2O_FC14)));
                    if (FC14 > 0) {
                        accountBO.doTransferAmountRemote(
                            ESysUser.SYS_USER_JKEG.getCode(),
                            consumerRefereeI.getUserId(), ECurrency.CNY, FC14,
                            EBizType.JKEG_O2O_FR,
                            EBizType.JKEG_O2O_FR.getValue(),
                            EBizType.JKEG_O2O_FR.getValue(), refNo);
                    }
                    if (StringUtils.isNotBlank(consumerRefereeI
                        .getUserReferee())) {
                        User consumerRefereeII = userBO
                            .getRemoteUser(consumerRefereeI.getUserReferee());
                        Long FC15 = AmountUtil.mul(frAmount, Double
                            .valueOf(configsMap.get(SysConstants.O2O_FC15)));
                        if (FC15 > 0) {
                            accountBO.doTransferAmountRemote(
                                ESysUser.SYS_USER_JKEG.getCode(),
                                consumerRefereeII.getUserId(), ECurrency.CNY,
                                FC15, EBizType.JKEG_O2O_FR,
                                EBizType.JKEG_O2O_FR.getValue(),
                                EBizType.JKEG_O2O_FR.getValue(), refNo);
                        }
                    }
                }
            }
        }
        // 查询消费会员所属市/区运营商
        User cityOperator = userBO.getPartner(consumer.getProvince(),
            consumer.getCity(), consumer.getCity(), EUserKind.JKEG_OPERATOR);
        if (cityOperator != null) {
            Long FC16 = AmountUtil.mul(frAmount,
                Double.valueOf(configsMap.get(SysConstants.O2O_FC16)));
            if (FC16 > 0) {
                accountBO.doTransferAmountRemote(
                    ESysUser.SYS_USER_JKEG.getCode(), cityOperator.getUserId(),
                    ECurrency.CNY, FC16, EBizType.JKEG_O2O_FR,
                    EBizType.JKEG_O2O_FR.getValue(),
                    EBizType.JKEG_O2O_FR.getValue(), refNo);
            }
        }

        User areaOperator = userBO.getPartner(consumer.getProvince(),
            consumer.getCity(), consumer.getArea(), EUserKind.JKEG_OPERATOR);
        if (areaOperator != null) {
            Long FC17 = AmountUtil.mul(frAmount,
                Double.valueOf(configsMap.get(SysConstants.O2O_FC17)));
            if (FC17 > 0) {
                accountBO.doTransferAmountRemote(
                    ESysUser.SYS_USER_JKEG.getCode(), areaOperator.getUserId(),
                    ECurrency.CNY, FC17, EBizType.JKEG_O2O_FR,
                    EBizType.JKEG_O2O_FR.getValue(),
                    EBizType.JKEG_O2O_FR.getValue(), refNo);
            }
        }
        // 会员本人
        if (consumer != null) {
            Long FC18 = AmountUtil.mul(frAmount,
                Double.valueOf(configsMap.get(SysConstants.O2O_FC18)));
            if (FC18 > 0) {
                accountBO.doTransferAmountRemote(
                    ESysUser.SYS_USER_JKEG.getCode(), consumer.getUserId(),
                    ECurrency.CNY, FC18, EBizType.JKEG_O2O_FR,
                    EBizType.JKEG_O2O_FR.getValue(),
                    EBizType.JKEG_O2O_FR.getValue(), refNo);
            }
        }
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
    public void distributeMall(User consumer, Store store, Long frAmount,
            String refNo) {
        // 获取分销配置
        Map<String, String> configsMap = sysConfigBO
            .getConfigsMap(ESystemCode.JKEG.getCode());

        // 查询供应商推荐人（即签约人）
        if (store != null && StringUtils.isNotBlank(store.getUserReferee())) {
            User storeReferee = userBO.getRemoteUser(store.getUserReferee());
            Long FC1 = AmountUtil.mul(frAmount,
                Double.valueOf(configsMap.get(SysConstants.MALL_FC1)));
            if (FC1 > 0) {
                accountBO.doTransferAmountRemote(
                    ESysUser.SYS_USER_JKEG.getCode(), storeReferee.getUserId(),
                    ECurrency.CNY, FC1, EBizType.JKEG_MALL_FR,
                    EBizType.JKEG_MALL_FR.getValue(),
                    EBizType.JKEG_MALL_FR.getValue(), refNo);
            }
            // 签约人一级推荐人
            if (StringUtils.isNotBlank(storeReferee.getUserReferee())) {
                User storeRefereeI = userBO.getRemoteUser(storeReferee
                    .getUserReferee());
                Long FC2 = AmountUtil.mul(frAmount,
                    Double.valueOf(configsMap.get(SysConstants.MALL_FC2)));
                if (FC2 > 0) {
                    accountBO.doTransferAmountRemote(
                        ESysUser.SYS_USER_JKEG.getCode(),
                        storeRefereeI.getUserId(), ECurrency.CNY, FC2,
                        EBizType.JKEG_MALL_FR,
                        EBizType.JKEG_MALL_FR.getValue(),
                        EBizType.JKEG_MALL_FR.getValue(), refNo);
                }
                // 签约人二级推荐人
                if (StringUtils.isNotBlank(storeRefereeI.getUserReferee())) {
                    User storeRefereeII = userBO.getRemoteUser(storeRefereeI
                        .getUserReferee());
                    Long FC3 = AmountUtil.mul(frAmount,
                        Double.valueOf(configsMap.get(SysConstants.MALL_FC3)));
                    if (FC3 > 0) {
                        accountBO.doTransferAmountRemote(
                            ESysUser.SYS_USER_JKEG.getCode(),
                            storeRefereeII.getUserId(), ECurrency.CNY, FC3,
                            EBizType.JKEG_MALL_FR,
                            EBizType.JKEG_MALL_FR.getValue(),
                            EBizType.JKEG_MALL_FR.getValue(), refNo);
                    }
                }
            }
        }

        // 查询消费会员推荐人
        if (consumer != null
                && StringUtils.isNotBlank(consumer.getUserReferee())) {
            User consumerReferee = userBO.getRemoteUser(consumer
                .getUserReferee());
            if (EUserKind.JKEG_O2O.getCode().equals(consumerReferee.getKind())
                    || EUserKind.JKEG_MINGSU.getCode().equals(// 推荐人是商户
                        consumerReferee.getKind())) {
                Long FC4 = AmountUtil.mul(frAmount,
                    Double.valueOf(configsMap.get(SysConstants.MALL_FC4)));
                if (FC4 > 0) {
                    accountBO.doTransferAmountRemote(
                        ESysUser.SYS_USER_JKEG.getCode(),
                        consumerReferee.getUserId(), ECurrency.CNY, FC4,
                        EBizType.JKEG_MALL_FR,
                        EBizType.JKEG_MALL_FR.getValue(),
                        EBizType.JKEG_MALL_FR.getValue(), refNo);
                }
            } else if (EUserKind.JKEG_SUPPLIER.getCode().equals(// 推荐人是供应商
                consumerReferee.getKind())) {
                Long FC5 = AmountUtil.mul(frAmount,
                    Double.valueOf(configsMap.get(SysConstants.MALL_FC5)));
                if (FC5 > 0) {
                    accountBO.doTransferAmountRemote(
                        ESysUser.SYS_USER_JKEG.getCode(),
                        consumerReferee.getUserId(), ECurrency.CNY, FC5,
                        EBizType.JKEG_MALL_FR,
                        EBizType.JKEG_MALL_FR.getValue(),
                        EBizType.JKEG_MALL_FR.getValue(), refNo);
                }
            } else if (EUserKind.F1.getCode().equals(consumerReferee.getKind())
                    && EUserLevel.ZERO.getCode().equals(// 推荐人是普通会员
                        consumerReferee.getLevel())) {
                Long FC6 = AmountUtil.mul(frAmount,
                    Double.valueOf(configsMap.get(SysConstants.MALL_FC6)));
                if (FC6 > 0) {
                    accountBO.doTransferAmountRemote(
                        ESysUser.SYS_USER_JKEG.getCode(),
                        consumerReferee.getUserId(), ECurrency.CNY, FC6,
                        EBizType.JKEG_MALL_FR,
                        EBizType.JKEG_MALL_FR.getValue(),
                        EBizType.JKEG_MALL_FR.getValue(), refNo);
                }
                if (StringUtils.isNotBlank(consumerReferee.getUserReferee())) {
                    User consumerRefereeI = userBO
                        .getRemoteUser(consumerReferee.getUserReferee());
                    Long FC7 = AmountUtil.mul(frAmount,
                        Double.valueOf(configsMap.get(SysConstants.MALL_FC7)));
                    if (FC7 > 0) {
                        accountBO.doTransferAmountRemote(
                            ESysUser.SYS_USER_JKEG.getCode(),
                            consumerRefereeI.getUserId(), ECurrency.CNY, FC7,
                            EBizType.JKEG_MALL_FR,
                            EBizType.JKEG_MALL_FR.getValue(),
                            EBizType.JKEG_MALL_FR.getValue(), refNo);
                    }
                    if (StringUtils.isNotBlank(consumerRefereeI
                        .getUserReferee())) {
                        User consumerRefereeII = userBO
                            .getRemoteUser(consumerRefereeI.getUserReferee());
                        Long FC8 = AmountUtil.mul(frAmount, Double
                            .valueOf(configsMap.get(SysConstants.MALL_FC8)));
                        if (FC8 > 0) {
                            accountBO.doTransferAmountRemote(
                                ESysUser.SYS_USER_JKEG.getCode(),
                                consumerRefereeII.getUserId(), ECurrency.CNY,
                                FC8, EBizType.JKEG_MALL_FR,
                                EBizType.JKEG_MALL_FR.getValue(),
                                EBizType.JKEG_MALL_FR.getValue(), refNo);
                        }
                    }
                }
            } else if (EUserKind.F1.getCode().equals(consumerReferee.getKind())
                    && EUserLevel.ONE.getCode().equals(// 推荐人是VIP会员
                        consumerReferee.getLevel())) {
                Long FC9 = AmountUtil.mul(frAmount,
                    Double.valueOf(configsMap.get(SysConstants.MALL_FC9)));
                if (FC9 > 0) {
                    accountBO.doTransferAmountRemote(
                        ESysUser.SYS_USER_JKEG.getCode(),
                        consumerReferee.getUserId(), ECurrency.CNY, FC9,
                        EBizType.JKEG_MALL_FR,
                        EBizType.JKEG_MALL_FR.getValue(),
                        EBizType.JKEG_MALL_FR.getValue(), refNo);
                }
                if (StringUtils.isNotBlank(consumerReferee.getUserReferee())) {
                    User consumerRefereeI = userBO
                        .getRemoteUser(consumerReferee.getUserReferee());
                    Long FC10 = AmountUtil.mul(frAmount,
                        Double.valueOf(configsMap.get(SysConstants.MALL_FC10)));
                    if (FC10 > 0) {
                        accountBO.doTransferAmountRemote(
                            ESysUser.SYS_USER_JKEG.getCode(),
                            consumerRefereeI.getUserId(), ECurrency.CNY, FC10,
                            EBizType.JKEG_MALL_FR,
                            EBizType.JKEG_MALL_FR.getValue(),
                            EBizType.JKEG_MALL_FR.getValue(), refNo);
                    }
                    if (StringUtils.isNotBlank(consumerRefereeI
                        .getUserReferee())) {
                        User consumerRefereeII = userBO
                            .getRemoteUser(consumerRefereeI.getUserReferee());
                        Long FC11 = AmountUtil.mul(frAmount, Double
                            .valueOf(configsMap.get(SysConstants.MALL_FC11)));
                        if (FC11 > 0) {
                            accountBO.doTransferAmountRemote(
                                ESysUser.SYS_USER_JKEG.getCode(),
                                consumerRefereeII.getUserId(), ECurrency.CNY,
                                FC11, EBizType.JKEG_MALL_FR,
                                EBizType.JKEG_MALL_FR.getValue(),
                                EBizType.JKEG_MALL_FR.getValue(), refNo);
                        }
                    }
                }
            }
        }
        // 查询消费会员所属市/区运营商
        User cityOperator = userBO.getPartner(consumer.getProvince(),
            consumer.getCity(), consumer.getCity(), EUserKind.JKEG_OPERATOR);
        if (cityOperator != null) {
            Long FC12 = AmountUtil.mul(frAmount,
                Double.valueOf(configsMap.get(SysConstants.MALL_FC12)));
            if (FC12 > 0) {
                accountBO.doTransferAmountRemote(
                    ESysUser.SYS_USER_JKEG.getCode(), cityOperator.getUserId(),
                    ECurrency.CNY, FC12, EBizType.JKEG_MALL_FR,
                    EBizType.JKEG_MALL_FR.getValue(),
                    EBizType.JKEG_MALL_FR.getValue(), refNo);
            }
            if (StringUtils.isNotBlank(cityOperator.getUserReferee())) {
                User cityOperatorReferee = userBO.getRemoteUser(cityOperator
                    .getUserReferee());
                Long FC13 = AmountUtil.mul(frAmount,
                    Double.valueOf(configsMap.get(SysConstants.MALL_FC13)));
                if (FC13 > 0) {
                    accountBO.doTransferAmountRemote(
                        ESysUser.SYS_USER_JKEG.getCode(),
                        cityOperatorReferee.getUserId(), ECurrency.CNY, FC13,
                        EBizType.JKEG_MALL_FR,
                        EBizType.JKEG_MALL_FR.getValue(),
                        EBizType.JKEG_MALL_FR.getValue(), refNo);
                }
            }
        }

        User areaOperator = userBO.getPartner(consumer.getProvince(),
            consumer.getCity(), consumer.getArea(), EUserKind.JKEG_OPERATOR);
        if (areaOperator != null) {
            Long FC14 = AmountUtil.mul(frAmount,
                Double.valueOf(configsMap.get(SysConstants.MALL_FC14)));
            if (FC14 > 0) {
                accountBO.doTransferAmountRemote(
                    ESysUser.SYS_USER_JKEG.getCode(), areaOperator.getUserId(),
                    ECurrency.CNY, FC14, EBizType.JKEG_MALL_FR,
                    EBizType.JKEG_MALL_FR.getValue(),
                    EBizType.JKEG_MALL_FR.getValue(), refNo);
            }
            if (StringUtils.isNotBlank(areaOperator.getUserReferee())) {
                User areaOperatorReferee = userBO.getRemoteUser(cityOperator
                    .getUserReferee());
                Long FC15 = AmountUtil.mul(frAmount,
                    Double.valueOf(configsMap.get(SysConstants.MALL_FC15)));
                if (FC15 > 0) {
                    accountBO.doTransferAmountRemote(
                        ESysUser.SYS_USER_JKEG.getCode(),
                        areaOperatorReferee.getUserId(), ECurrency.CNY, FC15,
                        EBizType.JKEG_MALL_FR,
                        EBizType.JKEG_MALL_FR.getValue(),
                        EBizType.JKEG_MALL_FR.getValue(), refNo);
                }
            }
        }
        // 会员本人
        if (consumer != null) {
            Long FC16 = AmountUtil.mul(frAmount,
                Double.valueOf(configsMap.get(SysConstants.MALL_FC16)));
            if (FC16 > 0) {
                accountBO.doTransferAmountRemote(
                    ESysUser.SYS_USER_JKEG.getCode(), consumer.getUserId(),
                    ECurrency.CNY, FC16, EBizType.JKEG_MALL_FR,
                    EBizType.JKEG_MALL_FR.getValue(),
                    EBizType.JKEG_MALL_FR.getValue(), refNo);
            }
        }
    }
}
