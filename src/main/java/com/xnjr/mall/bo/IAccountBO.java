/**
 * @Title IAccountBO.java 
 * @Package com.ibis.account.bo 
 * @Description 
 * @author miyb  
 * @date 2015-3-15 下午3:15:49 
 * @version V1.0   
 */
package com.xnjr.mall.bo;

import com.xnjr.mall.domain.Account;
import com.xnjr.mall.dto.res.XN002500Res;
import com.xnjr.mall.dto.res.XN002510Res;
import com.xnjr.mall.enums.EBizType;
import com.xnjr.mall.enums.ECurrency;

/** 
 * @author: miyb 
 * @since: 2015-3-15 下午3:15:49 
 * @history:
 */
public interface IAccountBO {

    /**
    * 根据用户编号和币种获取账户
    * @param userId
    * @param currency
    * @return 
    * @create: 2017年3月23日 下午12:02:11 myb858
    * @history:
    */
    public Account getRemoteAccount(String userId, ECurrency currency);

    /**
     * 根据用户编号进行账户资金划转
     * @param fromUserId
     * @param toUserId
     * @param currency
     * @param amount
     * @param bizType
     * @param fromBizNote
     * @param toBizNote 
     * @create: 2017年3月26日 下午8:42:38 xieyj
     * @history:
     */
    public void doTransferAmountRemote(String fromUserId, String toUserId,
            ECurrency currency, Long amount, EBizType bizType,
            String fromBizNote, String toBizNote);

    /**
     * 获取虚拟币的价值：1人民币等于多少虚拟币
     * @param currency
     * @return 
     * @create: 2017年4月4日 下午12:13:25 myb858
     * @history:
     */
    public Double getExchangeRateRemote(ECurrency currency);

    public void doCgbJfPay(String fromUserId, String toUserId, Long cgbPrice,
            Long jfPrice, EBizType bizType);

    /**
     * 城市网积分支付
     * @param fromUserId
     * @param toUserId
     * @param jfAmount
     * @param bizType 
     * @create: 2017年3月31日 下午5:34:08 asus
     * @history:
     */
    public void doCSWJfPay(String fromUserId, String toUserId, Long jfAmount,
            EBizType bizType);

    /**
     * 微信支付
     * @param fromUserId
     * @param toUserId
     * @param amount
     * @param bizType
     * @param fromBizNote
     * @param toBizNote
     * @param payGroup
     * @return 
     * @create: 2017年3月23日 下午8:34:24 xieyj
     * @history:
     */
    public XN002500Res doWeiXinPayRemote(String fromUserId, String toUserId,
            Long amount, EBizType bizType, String fromBizNote,
            String toBizNote, String payGroup);

    public XN002510Res doAlipayRemote(String fromUserId, String toUserId,
            Long amount, EBizType bizType, String fromBizNote,
            String toBizNote, String payGroup);

    public void checkCgbJf(String userId, Long cgbAmount, Long jfAmount);

}
