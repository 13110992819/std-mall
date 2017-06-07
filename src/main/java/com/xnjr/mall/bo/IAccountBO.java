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
import com.xnjr.mall.dto.res.XN002501Res;
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
     * @param refNo 
     * @create: 2017年5月17日 上午10:46:30 xieyj
     * @history:
     */
    public void doTransferAmountRemote(String fromUserId, String toUserId,
            ECurrency currency, Long amount, EBizType bizType,
            String fromBizNote, String toBizNote, String refNo);

    public void doTransferAmountRemote(String fromUserId,
            ECurrency fromCurrency, String toUserId, ECurrency toCurrency,
            Long amount, EBizType bizType, String fromBizNote,
            String toBizNote, String refNo);

    /**
     * 获取虚拟币的价值：1人民币等于多少虚拟币
     * @param currency
     * @return 
     * @create: 2017年4月4日 下午12:13:25 myb858
     * @history:
     */
    public Double getExchangeRateRemote(ECurrency currency);

    public XN002500Res doWeiXinPayRemote(String applyUser, String toUser,
            String payGroup, String refNo, EBizType bizType, String bizNote,
            Long amount);

    public XN002501Res doWeiXinH5PayRemote(String applyUser, String openId,
            String toUser, String payGroup, String refNo, EBizType bizType,
            String bizNote, Long amount);

    public XN002510Res doAlipayRemote(String applyUser, String toUser,
            String payGroup, String refNo, EBizType bizType, String bizNote,
            Long amount);

    // ************************************菜狗************************************
    public void doCgbJfPay(String fromUserId, String toUserId, Long cgbPrice,
            Long jfPrice, EBizType bizType, String refNo);

    public void checkCgbJf(String userId, Long cgbAmount, Long jfAmount);

    public void checkRmbJf(String userId, Long rmbAmount, Long jfAmount);

    // ************************************菜狗************************************

    // ************************************城市网************************************
    public void doCSWJfPay(String fromUserId, String toUserId, Long jfAmount,
            EBizType bizType, String refNo);
    // ************************************城市网************************************
}
