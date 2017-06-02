/**
 * @Title OrderAOImpl.java 
 * @Package com.xnjr.mall.ao.impl 
 * @Description 
 * @author xieyj  
 * @date 2016年5月25日 上午9:37:32 
 * @version V1.0   
 */
package com.xnjr.mall.ao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xnjr.mall.ao.IOrderAO;
import com.xnjr.mall.bo.IAccountBO;
import com.xnjr.mall.bo.ICartBO;
import com.xnjr.mall.bo.IDistributeBO;
import com.xnjr.mall.bo.IOrderBO;
import com.xnjr.mall.bo.IProductBO;
import com.xnjr.mall.bo.IProductOrderBO;
import com.xnjr.mall.bo.ISmsOutBO;
import com.xnjr.mall.bo.IStoreBO;
import com.xnjr.mall.bo.IUserBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.common.DateUtil;
import com.xnjr.mall.core.CalculationUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Account;
import com.xnjr.mall.domain.Cart;
import com.xnjr.mall.domain.Order;
import com.xnjr.mall.domain.Product;
import com.xnjr.mall.domain.ProductOrder;
import com.xnjr.mall.domain.Store;
import com.xnjr.mall.domain.User;
import com.xnjr.mall.dto.req.XN808050Req;
import com.xnjr.mall.dto.req.XN808051Req;
import com.xnjr.mall.dto.req.XN808054Req;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.enums.EBizType;
import com.xnjr.mall.enums.ECurrency;
import com.xnjr.mall.enums.EOrderStatus;
import com.xnjr.mall.enums.EPayType;
import com.xnjr.mall.enums.EProductStatus;
import com.xnjr.mall.enums.ESysUser;
import com.xnjr.mall.enums.ESystemCode;
import com.xnjr.mall.exception.BizException;

/** 
 * @author: xieyj 
 * @since: 2016年5月25日 上午9:37:32 
 * @history:
 */
@Service
public class OrderAOImpl implements IOrderAO {
    protected static final Logger logger = LoggerFactory
        .getLogger(OrderAOImpl.class);

    @Autowired
    private IOrderBO orderBO;

    @Autowired
    private IProductOrderBO productOrderBO;

    @Autowired
    private ICartBO cartBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IProductBO productBO;

    @Autowired
    private ISmsOutBO smsOutBO;

    @Autowired
    private IStoreBO storeBO;

    @Autowired
    private IDistributeBO distributeBO;

    @Override
    @Transactional
    public List<String> commitCartOrderZH(XN808051Req req) {
        List<String> result = new ArrayList<String>();
        // 按公司编号进行拆单, 遍历获取公司编号列表
        List<String> cartCodeList = req.getCartCodeList();
        Map<String, List<Cart>> cartList = cartBO.getCartMap(cartCodeList);
        // 遍历产品编号
        for (String companyCode : cartList.keySet()) {
            req.getPojo().setCompanyCode(companyCode);
            String orderCode = orderBO.saveOrder(cartList.get(companyCode),
                req.getPojo(), null);
            result.add(orderCode);
        }
        // @TODO清空购物车
        // 删除购物车选中记录
        for (String cartCode : cartCodeList) {
            cartBO.removeCart(cartCode);
        }
        return result;
    }

    @Override
    @Transactional
    public String commitCartOrderCG(XN808051Req req) {
        List<String> cartCodeList = req.getCartCodeList();
        List<Cart> cartList = cartBO.queryCartList(cartCodeList);
        // 验证产品是否有记录
        for (Cart cart : cartList) {
            Product product = productBO.getProduct(cart.getProductCode());
            if (!EProductStatus.PUBLISH_YES.getCode().equals(
                product.getStatus())) {
                throw new BizException("xn0000", "购物车中有未上架产品["
                        + product.getName() + "]无法下单");
            }
        }
        String orderCode = orderBO.saveOrder(cartList, req.getPojo(),
            req.getToUser());
        // 删除购物车选中记录
        for (String cartCode : cartCodeList) {
            cartBO.removeCart(cartCode);
        }
        return orderCode;
    }

    @Override
    @Transactional
    public String commitOrder(XN808050Req req) {
        // 立即下单，构造成购物车单个产品下单
        Product product = productBO.getProduct(req.getProductCode());
        if (!EProductStatus.PUBLISH_YES.getCode().equals(product.getStatus())) {
            throw new BizException("xn0000", "该产品未上架，无法下单");
        }
        req.getPojo().setCompanyCode(product.getCompanyCode());
        req.getPojo().setSystemCode(product.getSystemCode());

        Cart cart = new Cart();
        cart.setProductCode(req.getProductCode());
        cart.setQuantity(StringValidater.toInteger(req.getQuantity()));
        cart.setUserId(req.getPojo().getApplyUser());
        cart.setProduct(product);
        List<Cart> cartList = new ArrayList<Cart>();
        cartList.add(cart);

        return orderBO.saveOrder(cartList, req.getPojo(), req.getToUser());
    }

    @Override
    @Transactional
    public Object toPayOrder(List<String> codeList, String payType,
            String tradePwd) {
        // 暂时只实现单笔订单支付
        String code = codeList.get(0);
        Order order = orderBO.getOrder(code);
        if (!EOrderStatus.TO_PAY.getCode().equals(order.getStatus())) {
            throw new BizException("xn000000", "订单不处于待支付状态");
        }
        // 验证产品是否有未上架的
        doCheckProductOnline(order);
        if (ESystemCode.Caigo.getCode().equals(order.getSystemCode())) {
            return toPayOrderCG(order, payType);
        } else if (ESystemCode.ZHPAY.getCode().equals(order.getSystemCode())) {
            return toPayOrderZH(order, payType, tradePwd);
        } else if (ESystemCode.CSW.getCode().equals(order.getSystemCode())) {
            return toPayOrderCSW(order, payType);
        } else if (ESystemCode.PIPE.getCode().equals(order.getSystemCode())) {
            return toPayOrderGD(order, payType);
        } else if (ESystemCode.YAOCHENG.getCode().equals(order.getSystemCode())) {
            return toPayOrderYC(order, payType);
        } else {
            throw new BizException("xn000000", "系统编号不能识别");
        }
    }

    private Object toPayOrderCG(Order order, String payType) {
        Long cgbAmount = order.getAmount2(); // 菜狗币
        Long jfAmount = order.getAmount3(); // 积分
        String systemCode = order.getSystemCode();
        String fromUserId = order.getApplyUser();
        // 余额支付(菜狗币+积分)
        if (EPayType.INTEGRAL.getCode().equals(payType)) {
            // 更新订单支付金额
            orderBO.refreshPaySuccess(order, 0L, 0L, cgbAmount, jfAmount, null);
            // 扣除金额
            if (StringUtils.isNotBlank(order.getToUser())) {// 付给加盟店
                accountBO.doCgbJfPay(fromUserId, order.getToUser(), cgbAmount,
                    jfAmount, EBizType.AJ_GW, order.getCode());
            } else {// 付钱给平台
                String systemUserId = userBO.getSystemUser(systemCode);
                accountBO.doCgbJfPay(fromUserId, systemUserId, cgbAmount,
                    jfAmount, EBizType.AJ_GW, order.getCode());
            }
        } else {
            throw new BizException("xn0000", "支付类型不支持");
        }
        return new BooleanRes(true);
    }

    private Object toPayOrderYC(Order order, String payType) {
        if (EPayType.YC_CB.getCode().equals(payType)) {
            return toPayOrderYCCB(order);
        } else if (EPayType.ZH_YE.getCode().equals(payType)) {
            return toPayOrderYCYE(order);
        } else if (EPayType.WEIXIN_H5.getCode().equals(payType)) {
            return toPayOrderYCWXH5(order);
        } else {
            throw new BizException("xn0000", "支付类型不支持");
        }

    }

    private Object toPayOrderYCWXH5(Order order) {
        Long rmbAmount = order.getAmount1();
        User user = userBO.getRemoteUser(order.getApplyUser());
        String payGroup = orderBO.addPayGroup(order.getCode());
        return accountBO.doWeiXinH5PayRemote(user.getUserId(),
            user.getOpenId(), order.getToUser(), payGroup, order.getCode(),
            EBizType.YC_MALL, "购物微信支付", rmbAmount);
    }

    private Object toPayOrderYCYE(Order order) {
        Long rmbAmount = order.getAmount1();
        String systemCode = order.getSystemCode();
        String fromUserId = order.getApplyUser();
        Account rmbAccount = accountBO.getRemoteAccount(fromUserId,
            ECurrency.CNY);
        if (rmbAmount > rmbAccount.getAmount()) {
            throw new BizException("xn0000", "人民币账户余额不足");
        }
        // 更新订单支付金额
        orderBO.refreshPaySuccess(order, rmbAmount, 0L, 0L, 0L, null);
        // 扣除金额
        if (StringUtils.isNotBlank(order.getToUser())) {// 付给加盟店
            accountBO.doTransferAmountRemote(fromUserId, order.getToUser(),
                ECurrency.CNY, rmbAmount, EBizType.YC_MALL,
                EBizType.YC_MALL.getValue(), EBizType.YC_MALL.getValue(),
                order.getCode());
        } else {// 付钱给平台
            String systemUserId = userBO.getSystemUser(systemCode);
            accountBO.doTransferAmountRemote(fromUserId, systemUserId,
                ECurrency.CNY, rmbAmount, EBizType.YC_MALL,
                EBizType.YC_MALL.getValue(), EBizType.YC_MALL.getValue(),
                order.getCode());
        }
        return new BooleanRes(true);
    }

    private Object toPayOrderYCCB(Order order) {
        Long cbAmount = order.getAmount2();
        String systemCode = order.getSystemCode();
        String fromUserId = order.getApplyUser();
        Account cbAccount = accountBO.getRemoteAccount(fromUserId,
            ECurrency.YC_CB);
        if (cbAmount > cbAccount.getAmount()) {
            throw new BizException("xn0000", "橙券不足");
        }
        // 更新订单支付金额
        orderBO.refreshPaySuccess(order, 0L, 0L, cbAmount, 0L, null);
        // 扣除金额
        if (StringUtils.isNotBlank(order.getToUser())) {// 付给加盟店
            accountBO.doTransferAmountRemote(fromUserId, order.getToUser(),
                ECurrency.YC_CB, cbAmount, EBizType.YC_MALL,
                EBizType.YC_MALL.getValue(), EBizType.YC_MALL.getValue(),
                order.getCode());
        } else {// 付钱给平台
            String systemUserId = userBO.getSystemUser(systemCode);
            accountBO.doTransferAmountRemote(fromUserId, systemUserId,
                ECurrency.YC_CB, cbAmount, EBizType.YC_MALL,
                EBizType.YC_MALL.getValue(), EBizType.YC_MALL.getValue(),
                order.getCode());
        }
        return new BooleanRes(true);
    }

    /** 
     * @param order 
     * @create: 2017年5月2日 下午5:19:38 xieyj
     * @history: 
     */
    private void doCheckProductOnline(Order order) {
        List<ProductOrder> prodList = productOrderBO
            .queryProductOrderList(order.getCode());
        if (CollectionUtils.isNotEmpty(prodList)) {
            for (ProductOrder productOrder : prodList) {
                Product product = productBO.getProduct(productOrder
                    .getProductCode());
                if (!EProductStatus.PUBLISH_YES.getCode().equals(
                    product.getStatus())) {
                    throw new BizException("xn0000", "订单中有未上架产品，不能支付");
                }
            }
        }
    }

    private Object toPayOrderZH(Order order, String payType, String tradePwd) {
        // 验证交易密码
        Long cnyAmount = order.getAmount1() + order.getYunfei();// 人民币+运费
        Long gwbAmount = order.getAmount2();// 购物币
        Long qbbAmount = order.getAmount3();// 钱包币
        String systemCode = order.getSystemCode();
        String fromUserId = order.getApplyUser();
        // 余额支付
        if (EPayType.ZH_YE.getCode().equals(payType)) {
            // 验证交易密码
            userBO.checkTradePwd(order.getApplyUser(), tradePwd);

            // 计算需要支付得分润和贡献值，以及余额校验
            order = calculateToPayFrbAndGxz(order);
            Long frbAmount = order.getPayAmount1();
            Long gxzAmount = order.getPayAmount11();
            // 更新订单支付金额
            orderBO.refreshPaySuccess(order, frbAmount, gxzAmount, gwbAmount,
                qbbAmount, null);

            // 付钱给平台
            String systemUserId = userBO.getSystemUser(systemCode);
            accountBO.doZHYEPay(fromUserId, systemUserId, frbAmount, gxzAmount,
                gwbAmount, qbbAmount, EBizType.AJ_GW, order.getCode());
            return new BooleanRes(true);
        } else if (EPayType.ALIPAY.getCode().equals(payType)) {
            // 检查购物币和钱包币是否充足
            accountBO.checkZHGwbQbb(fromUserId, gwbAmount, qbbAmount);
            // 生成payGroup,并把这组订单（orderCodeList）归为一组进行支付。
            String payGroup = orderBO.addPayGroup(order.getCode());
            return accountBO.doAlipayRemote(fromUserId,
                ESysUser.SYS_USER_ZHPAY.getCode(), payGroup, order.getCode(),
                EBizType.AJ_GW, EBizType.AJ_GW.getValue(), cnyAmount);
        } else if (EPayType.WEIXIN_APP.getCode().equals(payType)) {
            // 检查购物币和钱包币是否充足
            accountBO.checkZHGwbQbb(fromUserId, gwbAmount, qbbAmount);
            // 生成payGroup,并把这组订单（orderCodeList）归为一组进行支付。
            String payGroup = orderBO.addPayGroup(order.getCode());
            return accountBO.doWeiXinPayRemote(fromUserId,
                ESysUser.SYS_USER_ZHPAY.getCode(), payGroup, order.getCode(),
                EBizType.AJ_GW, EBizType.AJ_GW.getValue(), cnyAmount);
        } else {
            throw new BizException("xn0000", "支付类型不支持");
        }
    }

    private Order calculateToPayFrbAndGxz(Order order) {
        String fromUserId = order.getApplyUser();
        Long cnyAmount = order.getAmount1() + order.getYunfei();// 人民币+运费（可用分润币和贡献值抵用）
        Long gwbAmount = order.getAmount2();// 购物币
        Long qbbAmount = order.getAmount3();// 钱包币
        Account frbAccount = accountBO.getRemoteAccount(fromUserId,
            ECurrency.ZH_FRB);
        Double frbRate = accountBO.getExchangeRateRemote(ECurrency.ZH_FRB);
        Account gxzAccount = accountBO.getRemoteAccount(fromUserId,
            ECurrency.ZH_GXZ);
        Double gxzRate = accountBO.getExchangeRateRemote(ECurrency.ZH_GXZ);
        // 计算分润币和贡献值分别价值多少人民币
        Long frbAmount = frbAccount.getAmount() / frbRate.longValue();
        Long gxzAmount = gxzAccount.getAmount() / gxzRate.longValue();
        // 检验分润币、贡献值、购物币和钱包币是否充足
        accountBO.checkZHYE(fromUserId, frbAmount, gxzAmount, cnyAmount,
            gwbAmount, qbbAmount);
        // 计算需要扣除分贡献值和分润币，优先扣贡献值
        // 贡献值<0 直接扣分润
        if (gxzAmount <= 0L) {
            order.setPayAmount1(Double.valueOf((cnyAmount * frbRate))
                .longValue());
            order.setPayAmount11(0L);
        } else if (gxzAmount < cnyAmount) {
            // 0<贡献值<cnyAmount 先扣除所有贡献值，再扣分润
            order.setPayAmount1(Double.valueOf(
                (cnyAmount - gxzAmount) * frbRate).longValue());
            order.setPayAmount11(gxzAccount.getAmount());
        } else {// 贡献值>=cnyAmount 直接扣贡献值
            order.setPayAmount1(0L);
            order.setPayAmount11(Double.valueOf(cnyAmount * gxzRate)
                .longValue());
        }
        return order;
    }

    private Object toPayOrderCSW(Order order, String payType) {
        // amount1 代表人民币账户 amount2，amount3代表积分金额
        Long jfAmount = order.getAmount2(); // 积分
        String systemCode = order.getSystemCode();
        String fromUserId = order.getApplyUser();
        // 积分支付
        if (EPayType.INTEGRAL.getCode().equals(payType)) {
            // 更新订单支付金额
            orderBO.refreshPaySuccess(order, 0L, 0L, jfAmount, 0L, null);
            // 扣除金额
            String systemUserId = userBO.getSystemUser(systemCode);
            accountBO.doCSWJfPay(fromUserId, systemUserId, jfAmount,
                EBizType.CSW_PAY, order.getCode());
        } else {
            throw new BizException("xn0000", "支付类型不支持");
        }
        return new BooleanRes(true);
    }

    private Object toPayOrderGD(Order order, String payType) {
        Long jfAmount = order.getAmount3(); // 积分
        String systemCode = order.getSystemCode();
        String fromUserId = order.getApplyUser();
        // 积分支付
        if (EPayType.INTEGRAL.getCode().equals(payType)) {
            // 更新订单支付金额
            orderBO.refreshPaySuccess(order, 0L, 0L, 0L, jfAmount, null);
            // 扣除金额
            String systemUserId = userBO.getSystemUser(systemCode);
            accountBO.doCSWJfPay(fromUserId, systemUserId, jfAmount,
                EBizType.GD_MALL, order.getCode());
            return new BooleanRes(true);
        } else if (EPayType.WEIXIN_H5.getCode().equals(payType)) {
            Long rmbAmount = order.getAmount1();
            User user = userBO.getRemoteUser(order.getApplyUser());
            String payGroup = orderBO.addPayGroup(order.getCode());
            return accountBO.doWeiXinH5PayRemote(user.getUserId(),
                user.getOpenId(), order.getToUser(), payGroup, order.getCode(),
                EBizType.YC_MALL, "购物微信支付", rmbAmount);
        } else {
            throw new BizException("xn0000", "支付类型不支持");
        }
    }

    /** 
     * @see com.xnjr.mall.ao.IOrderAO#userCancel(java.lang.String, java.lang.String)
     */
    @Override
    public void userCancel(String code, String userId, String remark) {
        Order data = orderBO.getOrder(code);
        if (!userId.equals(data.getApplyUser())) {
            throw new BizException("xn0000", "订单申请人和取消操作用户不符");
        }
        if (!EOrderStatus.TO_PAY.getCode().equals(data.getStatus())) {
            throw new BizException("xn0000", "订单状态不是待支付状态，不能进行取消操作");
        }
        orderBO.userCancel(code, userId, remark);
    }

    /**
     * @see com.xnjr.mall.ao.IOrderAO#userCancel(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void platCancel(List<String> codeList, String updater, String remark) {
        for (String code : codeList) {
            platCancelSingle(code, updater, remark);
        }
    }

    @Transactional
    private void platCancelSingle(String code, String updater, String remark) {
        Order order = orderBO.getOrder(code);
        if (!EOrderStatus.PAY_YES.getCode().equals(order.getStatus())
                && !EOrderStatus.SEND.getCode().equals(order.getStatus())) {
            throw new BizException("xn0000", "该订单不是支付成功或已发货状态，无法操作");
        }
        String status = null;
        if (EOrderStatus.PAY_YES.getCode().equals(order.getStatus())) {
            status = EOrderStatus.SHYC.getCode();
        } else if (EOrderStatus.SEND.getCode().equals(order.getStatus())) {
            status = EOrderStatus.KDYC.getCode();
        }

        // 退款
        if (ESystemCode.Caigo.getCode().equals(order.getSystemCode())) {
            doBackAmountCG(order);
        } else if (ESystemCode.ZHPAY.getCode().equals(order.getSystemCode())) {
            doBackAmountZH(order);
        } else if (ESystemCode.CSW.getCode().equals(order.getSystemCode())) {
            // 订单作废，金额不退还
        } else if (ESystemCode.YAOCHENG.getCode().equals(order.getSystemCode())) {
            doBackAmountYC(order);
        } else {
            throw new BizException("xn000000", "系统编号不能识别");
        }

        // 更新订单信息
        orderBO.platCancel(code, updater, remark, status);

        String userId = order.getApplyUser();
        // 发送短信
        if (!ESystemCode.CSW.getCode().equals(order.getSystemCode())) {
            smsOutBO.sentContent(userId, "尊敬的用户，您的订单[" + order.getCode()
                    + "]已取消,请及时查看退款。");
        } else {
            smsOutBO.sentContent(userId, "尊敬的用户，您的订单[" + order.getCode()
                    + "]已取消。");
        }
    }

    private void doBackAmountCG(Order order) {
        Long cgbAmount = order.getPayAmount2(); // 菜狗币
        Long jfAmount = order.getPayAmount3(); // 积分
        if (cgbAmount > 0) {
            accountBO.doTransferAmountRemote(order.getToUser(),
                order.getApplyUser(), ECurrency.CG_CGB, cgbAmount,
                EBizType.AJ_GWTK, EBizType.AJ_GWTK.getValue(),
                EBizType.AJ_GWTK.getValue(), order.getCode());
        }
        if (jfAmount > 0) {
            accountBO.doTransferAmountRemote(order.getToUser(),
                order.getApplyUser(), ECurrency.CGJF, jfAmount,
                EBizType.AJ_GWTK, EBizType.AJ_GWTK.getValue(),
                EBizType.AJ_GWTK.getValue(), order.getCode());
        }
    }

    private void doBackAmountYC(Order order) {
        Long rmbAmount = order.getPayAmount1(); // 人民币
        Long cbAmount = order.getPayAmount2(); // 橙券
        if (rmbAmount > 0) {
            accountBO.doTransferAmountRemote(order.getToUser(),
                order.getApplyUser(), ECurrency.CNY, rmbAmount,
                EBizType.YC_MALL_BACK, EBizType.YC_MALL_BACK.getValue(),
                EBizType.YC_MALL_BACK.getValue(), order.getCode());
        }
        if (cbAmount > 0) {
            accountBO.doTransferAmountRemote(order.getToUser(),
                order.getApplyUser(), ECurrency.YC_CB, cbAmount,
                EBizType.YC_MALL_BACK, EBizType.YC_MALL_BACK.getValue(),
                EBizType.YC_MALL_BACK.getValue(), order.getCode());
        }
    }

    private void doBackAmountZH(Order order) {
        Long frbAmount = order.getPayAmount1(); // 支付分润
        Long gxzAmount = order.getPayAmount11(); // 支付贡献奖励
        Long gwbAmount = order.getPayAmount2();// 购物币
        Long qbbAmount = order.getPayAmount3(); // 钱宝币
        if (frbAmount > 0) {
            accountBO.doTransferAmountRemote(ESysUser.SYS_USER_ZHPAY.getCode(),
                order.getApplyUser(), ECurrency.ZH_FRB, frbAmount,
                EBizType.AJ_GWTK, EBizType.AJ_GWTK.getValue(),
                EBizType.AJ_GWTK.getValue(), order.getCode());
        }
        if (gxzAmount > 0) {
            accountBO.doTransferAmountRemote(ESysUser.SYS_USER_ZHPAY.getCode(),
                order.getApplyUser(), ECurrency.ZH_GXZ, gxzAmount,
                EBizType.AJ_GWTK, EBizType.AJ_GWTK.getValue(),
                EBizType.AJ_GWTK.getValue(), order.getCode());
        }
        if (gwbAmount > 0) {
            accountBO.doTransferAmountRemote(ESysUser.SYS_USER_ZHPAY.getCode(),
                order.getApplyUser(), ECurrency.ZH_GWB, gwbAmount,
                EBizType.AJ_GWTK, EBizType.AJ_GWTK.getValue(),
                EBizType.AJ_GWTK.getValue(), order.getCode());
        }
        if (qbbAmount > 0) {
            accountBO.doTransferAmountRemote(ESysUser.SYS_USER_ZHPAY.getCode(),
                order.getApplyUser(), ECurrency.ZH_QBB, qbbAmount,
                EBizType.AJ_GWTK, EBizType.AJ_GWTK.getValue(),
                EBizType.AJ_GWTK.getValue(), order.getCode());
        }
    }

    /** 
     * @see com.xnjr.mall.ao.IOrderAO#queryOrderPage(int, int, com.xnjr.mall.domain.Order)
     */
    @Override
    public Paginable<Order> queryOrderPage(int start, int limit, Order condition) {
        Paginable<Order> page = orderBO.getPaginable(start, limit, condition);
        if (page != null && CollectionUtils.isNotEmpty(page.getList())) {
            for (Order order : page.getList()) {
                order.setUser(userBO.getRemoteUser(order.getApplyUser()));
                ProductOrder imCondition = new ProductOrder();
                imCondition.setOrderCode(order.getCode());
                List<ProductOrder> productOrderList = productOrderBO
                    .queryProductOrderList(imCondition);
                order.setProductOrderList(productOrderList);
                if (ESystemCode.ZHPAY.getCode().equals(order.getSystemCode())) {
                    order.setStore(storeBO.getStoreByUser(order
                        .getCompanyCode()));
                }
            }
        }
        return page;
    }

    @Override
    public Paginable<Order> queryMyOrderPage(int start, int limit,
            Order condition) {
        Paginable<Order> page = orderBO.getPaginable(start, limit, condition);
        if (page != null && CollectionUtils.isNotEmpty(page.getList())) {
            for (Order order : page.getList()) {
                ProductOrder imCondition = new ProductOrder();
                imCondition.setOrderCode(order.getCode());
                List<ProductOrder> productOrderList = productOrderBO
                    .queryProductOrderList(imCondition);
                order.setProductOrderList(productOrderList);
            }
        }
        return page;
    }

    /** 
     * @see com.xnjr.mall.ao.IOrderAO#queryOrderList(com.xnjr.mall.domain.Order)
     */
    @Override
    public List<Order> queryOrderList(Order condition) {
        List<Order> list = orderBO.queryOrderList(condition);
        if (CollectionUtils.isNotEmpty(list)) {
            for (Order order : list) {
                ProductOrder imCondition = new ProductOrder();
                imCondition.setOrderCode(order.getCode());
                List<ProductOrder> productOrderList = productOrderBO
                    .queryProductOrderList(imCondition);
                order.setProductOrderList(productOrderList);
            }
        }
        return list;
    }

    /** 
     * @see com.xnjr.mall.ao.IOrderAO#getOrder(java.lang.String)
     */
    @Override
    public Order getOrder(String code) {
        Order order = orderBO.getOrder(code);
        order.setUser(userBO.getRemoteUser(order.getApplyUser()));
        ProductOrder imCondition = new ProductOrder();
        imCondition.setOrderCode(order.getCode());
        List<ProductOrder> productOrderList = productOrderBO
            .queryProductOrderList(imCondition);
        order.setProductOrderList(productOrderList);
        if (ESystemCode.ZHPAY.getCode().equals(order.getSystemCode())) {
            order.setStore(storeBO.getStoreByUser(order.getCompanyCode()));
        }
        return order;
    }

    @Override
    @Transactional
    public void deliverLogistics(XN808054Req req) {
        Order order = orderBO.getOrder(req.getCode());
        if (!EOrderStatus.PAY_YES.getCode().equalsIgnoreCase(order.getStatus())) {
            throw new BizException("xn000000", "订单不是已支付状态，无法操作");
        }
        orderBO.deliverLogistics(req.getCode(), req.getLogisticsCompany(),
            req.getLogisticsCode(), req.getDeliverer(),
            req.getDeliveryDatetime(), req.getPdf(), req.getUpdater(),
            req.getRemark());

        // 发送短信
        String userId = order.getApplyUser();
        String notice = "";
        if (CollectionUtils.isNotEmpty(order.getProductOrderList())) {
            if (order.getProductOrderList().size() > 1) {
                notice = "尊敬的用户，您的订单["
                        + order.getCode()
                        + "]中的商品["
                        + order.getProductOrderList().get(0).getProduct()
                            .getName() + "等]已发货，请注意查收。";
            } else {
                notice = "尊敬的用户，您的订单["
                        + order.getCode()
                        + "]中的商品["
                        + order.getProductOrderList().get(0).getProduct()
                            .getName() + "]已发货，请注意查收。";
            }
            smsOutBO.sentContent(userId, notice);
        }
    }

    @Override
    public void deliverXianchang(String code, String updater, String remark) {
        Order order = orderBO.getOrder(code);
        if (!EOrderStatus.PAY_YES.getCode().equalsIgnoreCase(order.getStatus())) {
            throw new BizException("xn000000", "该订单不是已支付状态，无法操作");
        }
        orderBO.deliverXianchang(code, updater, remark);
    }

    @Override
    @Transactional
    public void confirm(String code, String updater, String remark) {
        Order order = orderBO.getOrder(code);
        if (!EOrderStatus.SEND.getCode().equalsIgnoreCase(order.getStatus())) {
            throw new BizException("xn000000", "订单不是已发货状态，无法操作");
        }

        if (ESystemCode.ZHPAY.getCode().equals(order.getSystemCode())) {
            doConfirmZH(order, updater, remark);
        } else {
            doConfirm(order, updater, remark);
        }

    }

    private void doConfirm(Order order, String updater, String remark) {
        orderBO.confirm(order, updater, remark);
        // 更新产品的已购买人数
        List<ProductOrder> productOrders = order.getProductOrderList();
        for (ProductOrder productOrder : productOrders) {
            productBO.updateBoughtCount(productOrder.getProductCode(),
                productOrder.getQuantity());
        }
    }

    private void doConfirmZH(Order order, String updater, String remark) {
        orderBO.confirm(order, updater, remark);
        // 更新产品的已购买人数
        List<ProductOrder> productOrders = order.getProductOrderList();
        for (ProductOrder productOrder : productOrders) {
            productBO.updateBoughtCount(productOrder.getProductCode(),
                productOrder.getQuantity());
        }

        // 平台将人民币转分润给商家（购物币和钱包币由平台回收）
        accountBO.doTransferAmountRemote(ESysUser.SYS_USER_ZHPAY.getCode(),
            ECurrency.CNY, order.getCompanyCode(), ECurrency.ZH_FRB,
            order.getAmount1(), EBizType.AJ_QRSH, "确认收货，平台人民币转给商家分润",
            EBizType.AJ_QRSH.getValue(), order.getCode());

        Double frbRate = accountBO.getExchangeRateRemote(ECurrency.ZH_FRB);
        Long frbAmount = Double.valueOf(order.getAmount1() * frbRate)
            .longValue();
        // 分销
        Store store = storeBO.getStoreByUser(order.getCompanyCode());
        User user = userBO.getRemoteUser(order.getApplyUser());
        distributeBO.distribute25Amount(frbAmount, order.getPayAmount1(),
            store, user, order.getCode());
        // 短信通知商户
        smsOutBO.sentContent(order.getCompanyCode(),
            "尊敬的商户，订单号[" + order.getCode() + "]的用户已确认收货,本次收入分润："
                    + CalculationUtil.divi(frbAmount) + ",请注意查收!");
    }

    /**
     * @see com.xnjr.mall.ao.IOrderAO#paySuccess(java.lang.String)
     */
    @Override
    @Transactional
    public void paySuccessZH(String payGroup, String payCode, Long payAmount) {
        List<Order> orderList = orderBO.queryOrderListByPayGroup(payGroup);
        if (CollectionUtils.isEmpty(orderList)) {
            throw new BizException("XN000000", "找不到对应的订单记录");
        }
        Order order = orderList.get(0);
        if (EOrderStatus.TO_PAY.getCode().equals(order.getStatus())) {
            Long cnyAmount = order.getAmount1();// 人民币
            Long gwbAmount = order.getAmount2();// 购物币
            Long qbbAmount = order.getAmount3();// 钱包币
            // 扣除购物币和钱包币
            accountBO.doZHYEPay(order.getApplyUser(),
                ESysUser.SYS_USER_ZHPAY.getCode(), 0L, 0L, gwbAmount,
                qbbAmount, EBizType.AJ_GW, order.getCode());
            // 更新支付金额
            orderBO.refreshPaySuccess(order, cnyAmount, 0L, gwbAmount,
                qbbAmount, null);
            smsOutBO.sentContent(order.getApplyUser(), "您的订单" + order.getCode()
                    + "已支付成功，支付金额为" + CalculationUtil.divi(payAmount) + "元");
        } else {
            logger.info("订单号：" + order.getCode() + "已支付，重复回调");
        }

        // Order condition = new Order();
        // condition.setPayCode(payCode);
        // List<Order> result = orderBO.queryOrderList(condition);
        // if (CollectionUtils.isEmpty(result)) {
        // throw new BizException("XN000000", "找不到对应的消费记录");
        // }
        // String systemCode = null;
        // String applyUser = null;
        // Long gwAmount = 0L; // 购物币
        // Long qbAmount = 0L; // 钱包币
        // for (Order order : result) {
        // if (EOrderStatus.TO_PAY.getCode().equals(order.getStatus())) {
        // // 更新支付金额
        // orderBO.refreshOrderPayAmount(order.getCode(),
        // order.getAmount1(), order.getAmount2(), order.getAmount3());
        // } else {
        // logger.info("订单号：" + order.getCode() + "已支付，重复回调");
        // }
        // gwAmount += order.getAmount2(); // 购物币
        // qbAmount += order.getAmount3(); // 钱包币
        // if (StringUtils.isBlank(applyUser)) {
        // applyUser = order.getApplyUser();
        // systemCode = order.getSystemCode();
        // }
        // }
        // // 扣除金额(购物币和钱包币)
        // accountBO.doGWBQBBPay(systemCode, applyUser,
        // ESysUser.SYS_USER.getCode(), gwAmount, qbAmount, EBizType.AJ_GW);
    }

    /**
     * @see com.xnjr.mall.ao.IOrderAO#paySuccess(java.lang.String)
     */
    @Override
    @Transactional
    public void paySuccessYC(String payGroup, String payCode, Long amount) {
        List<Order> orderList = orderBO.queryOrderListByPayGroup(payGroup);
        if (CollectionUtils.isEmpty(orderList)) {
            throw new BizException("XN000000", "找不到对应的订单记录");
        }
        Order order = orderList.get(0);
        if (EOrderStatus.TO_PAY.getCode().equals(order.getStatus())) {
            // 更新订单支付金额
            orderBO.refreshPaySuccess(order, amount, 0L, 0L, 0L, null);
        } else {
            logger.info("订单号：" + order.getCode() + "已支付，重复回调");
        }
    }

    @Override
    @Transactional
    public void paySuccessGD(String payGroup, String payCode, Long amount) {
        List<Order> orderList = orderBO.queryOrderListByPayGroup(payGroup);
        if (CollectionUtils.isEmpty(orderList)) {
            throw new BizException("XN000000", "找不到对应的订单记录");
        }
        Order order = orderList.get(0);
        if (EOrderStatus.TO_PAY.getCode().equals(order.getStatus())) {
            // 更新订单支付金额
            orderBO.refreshPaySuccess(order, amount, 0L, 0L, 0L, null);
        } else {
            logger.info("订单号：" + order.getCode() + "已支付，重复回调");
        }
    }

    /** 
     * @see com.xnjr.mall.ao.IOrderAO#doChangeOrderStatusDaily()
     */
    @Override
    public void doChangeOrderStatusDaily() {
        doChangeNoPayOrder();
    }

    private void doChangeNoPayOrder() {
        logger.info("***************开始扫描未支付订单***************");
        Order condition = new Order();
        condition.setStatus(EOrderStatus.TO_PAY.getCode());
        // 前3天还未支付的订单
        condition.setApplyDatetimeEnd(DateUtil.getRelativeDate(new Date(),
            -(60 * 60 * 24 * 3 + 1)));
        List<Order> orderList = orderBO.queryOrderList(condition);
        if (CollectionUtils.isNotEmpty(orderList)) {
            for (Order order : orderList) {
                orderBO.userCancel(order.getCode(), "system", "超时未支付，系统自动取消");

            }
        }
        logger.info("***************结束扫描未支付订单***************");
    }

}
