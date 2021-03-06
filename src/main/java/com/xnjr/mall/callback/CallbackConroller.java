package com.xnjr.mall.callback;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xnjr.mall.ao.IOrderAO;
import com.xnjr.mall.ao.ISorderAO;
import com.xnjr.mall.ao.IStorePurchaseAO;
import com.xnjr.mall.enums.EBizType;
import com.xnjr.mall.enums.ESystemCode;

/** 
 * @author: haiqingzheng 
 * @since: 2016年12月26日 下午1:44:16 
 * @history:
 */
@Controller
public class CallbackConroller {

    private static Logger logger = Logger.getLogger(CallbackConroller.class);

    @Autowired
    IOrderAO orderAO;

    @Autowired
    IStorePurchaseAO storePurchaseAO;

    @Autowired
    ISorderAO sorderAO;

    @RequestMapping("/thirdPay/callback")
    public synchronized void doCallbackZhpay(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        boolean isSuccess = Boolean.valueOf(request.getParameter("isSuccess"));
        String payGroup = request.getParameter("payGroup");
        String payCode = request.getParameter("payCode");
        Long amount = Long.valueOf(request.getParameter("transAmount"));
        String bizType = request.getParameter("bizType");
        String systemCode = request.getParameter("systemCode");
        // 支付成功，商户处理后同步返回给微信参数
        if (!isSuccess) {
            logger.info("****业务类型<" + bizType + "> payGroup <" + payGroup
                    + "> payCode <" + payCode + ">回调失败****");
        } else {
            try {
                if (EBizType.AJ_GW.getCode().equals(bizType)) {
                    if (ESystemCode.HW.getCode().equals(systemCode)) {
                        logger.info("**** 户外商城人民币支付回调 payGroup <" + payGroup
                                + "> payCode <" + payCode + ">start****");
                        orderAO.paySuccessHW(payGroup, payCode, amount);
                        logger.info("**** 户外商城人民币支付回调 payGroup <" + payGroup
                                + "> payCode <" + payCode + ">end****");
                    } else {
                        logger.error("**** 系统编号" + systemCode
                                + " 购物支付回调 payGroup <" + payGroup
                                + "> payCode <" + payCode + ">业务类型不存在");
                    }
                } else if (EBizType.CG_O2O_RMB.getCode().equals(bizType)) {
                    logger.info("**** 菜狗O2O人民币支付回调 payGroup <" + payGroup
                            + "> payCode <" + payCode + ">start****");
                    storePurchaseAO.paySuccessCG(payGroup, payCode, amount);
                    logger.info("**** 菜狗O2O人民币支付回调 payGroup <" + payGroup
                            + "> payCode <" + payCode + ">end****");
                } else if (EBizType.YC_O2O_RMB.getCode().equals(bizType)) {
                    logger.info("**** 姚橙O2O人民币支付回调 payGroup <" + payGroup
                            + "> payCode <" + payCode + ">start****");
                    storePurchaseAO.paySuccessYC(payGroup, payCode, amount);
                    logger.info("**** 姚橙O2O人民币支付回调 payGroup <" + payGroup
                            + "> payCode <" + payCode + ">end****");

                } else if (EBizType.YC_MALL.getCode().equals(bizType)) {
                    logger.info("**** 姚橙商城人民币支付回调 payGroup <" + payGroup
                            + "> payCode <" + payCode + ">start****");
                    orderAO.paySuccessYC(payGroup, payCode, amount);
                    logger.info("**** 姚橙商城人民币支付回调 payGroup <" + payGroup
                            + "> payCode <" + payCode + ">end****");
                } else if (EBizType.GD_MALL.getCode().equals(bizType)) {
                    logger.info("**** 管道积分商城人民币支付回调 payGroup <" + payGroup
                            + "> payCode <" + payCode + ">start****");
                    orderAO.paySuccessGD(payGroup, payCode, amount);
                    logger.info("**** 管道积分商城人民币支付回调 payGroup <" + payGroup
                            + "> payCode <" + payCode + ">end****");
                    // 健康e购
                } else if (EBizType.JKEG_FW.getCode().equals(bizType)) {
                    logger.info("**** 服务人民币支付回调 payGroup <" + payGroup
                            + "> payCode <" + payCode + ">start****");
                    sorderAO.paySuccess(payGroup, payCode, amount);
                    // 更新房间剩余数量
                    logger.info("**** 服务人民币支付回调 payGroup <" + payGroup
                            + "> payCode <" + payCode + ">end****");
                } else if (EBizType.JKEG_MALL.getCode().equals(bizType)) {
                    logger.info("**** 健康e购健康商城人民币支付回调 payGroup <" + payGroup
                            + "> payCode <" + payCode + ">start****");
                    orderAO.paySuccessJKEG(payGroup, payCode, amount);
                    logger.info("**** 健康e购健康商城人民币支付回调 payGroup <" + payGroup
                            + "> payCode <" + payCode + ">end****");
                } else if (EBizType.JKEG_O2O_RMB.getCode().equals(bizType)) {
                    logger.info("**** 健康e购周边人民币支付回调 payGroup <" + payGroup
                            + "> payCode <" + payCode + ">start****");
                    storePurchaseAO.paySuccessJKEG(payGroup, payCode, amount);
                    logger.info("**** 健康e购周边人民币支付回调 payGroup <" + payGroup
                            + "> payCode <" + payCode + ">end****");
                }
            } catch (Exception e) {
                logger.error("支付回调异常payGroup <" + payGroup + "> payCode <"
                        + payCode + ">异常如下：" + e.getMessage());
            }
        }
    }
}
