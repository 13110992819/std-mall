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
import com.xnjr.mall.bo.IProductSpecsBO;
import com.xnjr.mall.bo.ISYSConfigBO;
import com.xnjr.mall.bo.ISmsOutBO;
import com.xnjr.mall.bo.IStoreBO;
import com.xnjr.mall.bo.IUserBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.common.DateUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Account;
import com.xnjr.mall.domain.Cart;
import com.xnjr.mall.domain.Order;
import com.xnjr.mall.domain.Product;
import com.xnjr.mall.domain.ProductOrder;
import com.xnjr.mall.domain.ProductSpecs;
import com.xnjr.mall.domain.Store;
import com.xnjr.mall.domain.User;
import com.xnjr.mall.dto.req.XN808050Req;
import com.xnjr.mall.dto.req.XN808051Req;
import com.xnjr.mall.dto.req.XN808054Req;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.enums.EBizType;
import com.xnjr.mall.enums.ECurrency;
import com.xnjr.mall.enums.EOrderStatus;
import com.xnjr.mall.enums.EOrderType;
import com.xnjr.mall.enums.EPayType;
import com.xnjr.mall.enums.EProductKind;
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
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private IOrderBO orderBO;

    @Autowired
    private IProductOrderBO productOrderBO;

    @Autowired
    private IProductSpecsBO productSpecsBO;

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
    public String commitCartOrderJKEG(XN808051Req req) {
        List<String> cartCodeList = req.getCartCodeList();
        List<Cart> cartList = cartBO.queryCartList(cartCodeList);
        String storeCode = null;
        String toUser = null;
        // 验证产品是否有记录
        for (Cart cart : cartList) {
            Product product = productBO.getProduct(cart.getProductCode());
            ProductSpecs productSpecs = productSpecsBO.getProductSpecs(cart
                .getProductSpecsCode());
            if (StringUtils.isBlank(storeCode)) {
                storeCode = product.getStoreCode();
            }
            if (!EProductStatus.PUBLISH_YES.getCode().equals(
                product.getStatus())) {
                throw new BizException("xn0000", "购物车中有未上架产品["
                        + product.getName() + "]无法下单");
            }
            // 判断库存是否充足
            Integer quantity = cart.getQuantity();
            if (productSpecs.getQuantity() - quantity < 0) {
                throw new BizException("xn0000", "产品[" + product.getName()
                        + "]库存不足，不能下单");
            }
        }
        if (StringUtils.isNotBlank(storeCode)) {
            if (storeCode.startsWith("SYS_USER")) {
                toUser = storeCode;
            } else {
                Store store = storeBO.getStore(storeCode);
                toUser = store.getOwner();
            }
        } else {
            throw new BizException("xn0000", "下单产品所属商家不能为空");
        }
        String orderCode = orderBO.saveOrder(cartList, req.getPojo(), toUser,
            EOrderType.SH_SALE.getCode());
        // 删除购物车选中记录
        for (String cartCode : cartCodeList) {
            cartBO.removeCart(cartCode);
        }
        return orderCode;
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
            req.getToUser(), EOrderType.SH_SALE.getCode());
        // 删除购物车选中记录
        for (String cartCode : cartCodeList) {
            cartBO.removeCart(cartCode);
        }
        return orderCode;
    }

    @Override
    @Transactional
    public String commitOrder(XN808050Req req) {
        ProductSpecs productSpecs = productSpecsBO.getProductSpecs(req
            .getProductSpecsCode());
        // 立即下单，构造成购物车单个产品下单
        Product product = productBO.getProduct(productSpecs.getProductCode());
        if (!EProductStatus.PUBLISH_YES.getCode().equals(product.getStatus())) {
            throw new BizException("xn0000", "该产品未上架，无法下单");
        }
        // 判断库存是否充足
        Integer quantity = StringValidater.toInteger(req.getQuantity());
        if (productSpecs.getQuantity() - quantity < 0) {
            throw new BizException("xn0000", "库存不够，不能下单");
        }
        req.getPojo().setCompanyCode(product.getCompanyCode());
        req.getPojo().setSystemCode(product.getSystemCode());

        Cart cart = new Cart();
        cart.setUserId(req.getPojo().getApplyUser());
        cart.setProductSpecsCode(req.getProductSpecsCode());
        cart.setQuantity(StringValidater.toInteger(req.getQuantity()));
        cart.setProductSpecs(productSpecs);
        List<Cart> cartList = new ArrayList<Cart>();
        cartList.add(cart);

        String toUser = null;
        String orderType = null;
        // 菜狗和姚橙 toUser为加盟商的userId, 其他系统toUser为产品所属的商家userId
        if (ESystemCode.Caigo.getCode().equals(product.getCompanyCode())
                || ESystemCode.YAOCHENG.getCode().equals(
                    product.getCompanyCode())) {
            toUser = req.getToUser();
            orderType = EOrderType.SH_SALE.getCode();
        } else if (ESystemCode.JKEG.getCode().equals(product.getCompanyCode())) {
            // 平台的产品所属商家为系统用户编号
            if (product.getStoreCode().startsWith("SYS_USER")) {
                toUser = product.getStoreCode();
            } else { // 获取产品所属商家userId
                Store store = storeBO.getStore(product.getStoreCode());
                toUser = store.getOwner();
            }
            if (EProductKind.NORMAL.getCode().equals(product.getKind())) {
                orderType = EOrderType.SH_SALE.getCode();
            } else if (EProductKind.INTEGRAL.getCode()
                .equals(product.getKind())) {
                orderType = EOrderType.INTEGRAL_EXCHANGE.getCode();
            }
        }

        return orderBO.saveOrder(cartList, req.getPojo(), toUser, orderType);
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
        } else if (ESystemCode.PIPE.getCode().equals(order.getSystemCode())) {
            return toPayOrderGD(order, payType);
        } else if (ESystemCode.YAOCHENG.getCode().equals(order.getSystemCode())) {
            return toPayOrderYC(order, payType);
        } else if (ESystemCode.JKEG.getCode().equals(order.getSystemCode())) {
            return toPayOrderJKEG(order, payType);
        } else {
            throw new BizException("xn000000", "系统编号不能识别");
        }
    }

    private Object toPayOrderJKEG(Order order, String payType) {
        if (EOrderType.INTEGRAL_EXCHANGE.getCode().equals(order.getType())
                && !EPayType.YE.getCode().equals(payType)) {
            throw new BizException("xn0000", "积分兑换订单只支持积分支付");
        }
        if (EPayType.YE.getCode().equals(payType)) {
            return toPayOrderJKEGYE(order);
        } else if (EPayType.WEIXIN_APP.getCode().equals(payType)) {
            return toPayOrderJKEGWXAPP(order);
        } else if (EPayType.ALIPAY.getCode().equals(payType)) {
            return toPayOrderJKEGZFB(order);
        } else {
            throw new BizException("xn0000", "支付类型不支持");
        }
    }

    private Object toPayOrderJKEGYE(Order order) {
        Long rmbAmount = order.getAmount1();
        String fromUserId = order.getApplyUser();
        Account rmbAccount = accountBO.getRemoteAccount(fromUserId,
            ECurrency.CNY);
        if (rmbAmount > rmbAccount.getAmount()) {
            throw new BizException("xn0000", "健康币不足");
        }
        // 更新订单支付金额
        orderBO.refreshPaySuccess(order, rmbAmount, 0L, 0L, null,
            EPayType.YE.getCode());

        ECurrency currency = ECurrency.CNY;
        EBizType bizType = EBizType.JKEG_MALL;
        if (EOrderType.INTEGRAL_EXCHANGE.getCode().equals(order.getType())) {
            currency = ECurrency.JF;
            bizType = EBizType.JKEG_JF_MALL;
        }

        // 付钱给平台，确认收货之后，平台给钱至商家
        accountBO.doTransferAmountRemote(fromUserId,
            ESysUser.SYS_USER_JKEG.getCode(), currency, rmbAmount, bizType,
            bizType.getValue(), bizType.getValue(), order.getCode());

        List<ProductOrder> productOrders = productOrderBO
            .queryProductOrderList(order.getCode());
        for (ProductOrder productOrder : productOrders) {
            // 更新库存
            productSpecsBO.refreshQuantity(productOrder.getProductSpecsCode(),
                productOrder.getQuantity());
        }

        return new BooleanRes(true);
    }

    private Object toPayOrderJKEGWXAPP(Order order) {
        Long rmbAmount = order.getAmount1();
        User user = userBO.getRemoteUser(order.getApplyUser());
        String payGroup = orderBO.addPayGroup(order.getCode());
        return accountBO.doWeiXinH5PayRemote(user.getUserId(),
            user.getOpenId(), order.getToUser(), payGroup, order.getCode(),
            EBizType.JKEG_MALL, EBizType.JKEG_MALL.getValue() + "-微信",
            rmbAmount);
    }

    private Object toPayOrderJKEGZFB(Order order) {
        Long rmbAmount = order.getAmount1();
        User user = userBO.getRemoteUser(order.getApplyUser());
        String payGroup = orderBO.addPayGroup(order.getCode());
        return accountBO.doAlipayRemote(user.getUserId(),
            ESysUser.SYS_USER_JKEG.getCode(), payGroup, order.getCode(),
            EBizType.JKEG_MALL, EBizType.JKEG_MALL.getValue() + "-支付宝",
            rmbAmount);
    }

    private Object toPayOrderCG(Order order, String payType) {
        Long cgbAmount = order.getAmount2(); // 菜狗币
        Long jfAmount = order.getAmount3(); // 积分
        String systemCode = order.getSystemCode();
        String fromUserId = order.getApplyUser();
        // 余额支付(菜狗币+积分)
        if (EPayType.INTEGRAL.getCode().equals(payType)) {
            // 更新订单支付金额
            orderBO.refreshPaySuccess(order, 0L, cgbAmount, jfAmount, null,
                null);
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
        } else if (EPayType.YE.getCode().equals(payType)) {
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
        orderBO.refreshPaySuccess(order, rmbAmount, 0L, 0L, null,
            EPayType.YE.getCode());
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
        orderBO.refreshPaySuccess(order, 0L, cbAmount, 0L, null,
            EPayType.YC_CB.getCode());
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

    private Object toPayOrderGD(Order order, String payType) {
        Long jfAmount = order.getAmount3(); // 积分
        String systemCode = order.getSystemCode();
        String fromUserId = order.getApplyUser();
        // 积分支付
        if (EPayType.INTEGRAL.getCode().equals(payType)) {
            // 更新订单支付金额
            orderBO.refreshPaySuccess(order, 0L, 0L, jfAmount, null,
                EPayType.INTEGRAL.getCode());
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
        } else if (ESystemCode.YAOCHENG.getCode().equals(order.getSystemCode())) {
            doBackAmountYC(order);
        } else if (ESystemCode.PIPE.getCode().equals(order.getSystemCode())) {
            // 管道项目线上不退款，线下处理
        } else if (ESystemCode.JKEG.getCode().equals(order.getSystemCode())) {
            doBackAmountJKEG(order);
            List<ProductOrder> productOrders = productOrderBO
                .queryProductOrderList(order.getCode());
            for (ProductOrder productOrder : productOrders) {
                // 更新库存
                productSpecsBO.refreshQuantity(
                    productOrder.getProductSpecsCode(),
                    productOrder.getQuantity());
            }
        } else {
            throw new BizException("xn000000", "系统编号不能识别");
        }

        // 更新订单信息
        orderBO.platCancel(code, updater, remark, status);

        String userId = order.getApplyUser();
        // 发送短信
        if (ESystemCode.PIPE.getCode().equals(order.getSystemCode())) {
            smsOutBO.sentContent(userId, "尊敬的用户，您的订单[" + order.getCode()
                    + "]已取消，请联系平台处理退款事宜。");
        } else {
            smsOutBO.sentContent(userId, "尊敬的用户，您的订单[" + order.getCode()
                    + "]已取消,请及时查看退款。");
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

    private void doBackAmountJKEG(Order order) {
        Long rmbAmount = order.getPayAmount1(); // 人民币
        if (rmbAmount > 0) {
            ECurrency currency = ECurrency.CNY;
            EBizType bizType = EBizType.JKEG_MALL_BACK;
            if (EOrderType.INTEGRAL_EXCHANGE.getCode().equals(order.getType())) {
                currency = ECurrency.JF;
                bizType = EBizType.JKEG_JF_MALL_BACK;
            }
            accountBO.doTransferAmountRemote(ESysUser.SYS_USER_JKEG.getCode(),
                order.getApplyUser(), currency, rmbAmount, bizType,
                bizType.getValue(), bizType.getValue(), order.getCode());
        }
    }

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
        doConfirm(order, updater, remark);

        if (ESystemCode.JKEG.getCode().equals(order.getSystemCode())) {
            // 平台付钱给商户
            Long rmbAmount = order.getAmount1();
            ECurrency currency = ECurrency.CNY;
            EBizType bizType = EBizType.JKEG_MALL;
            if (EOrderType.INTEGRAL_EXCHANGE.getCode().equals(order.getType())) {
                currency = ECurrency.JF;
                bizType = EBizType.JKEG_JF_MALL;
            }
            if (StringUtils.isNotBlank(order.getToUser())
                    && !order.getToUser().startsWith("SYS_USER")) {
                accountBO.doTransferAmountRemote(
                    ESysUser.SYS_USER_JKEG.getCode(), order.getToUser(),
                    currency, rmbAmount, bizType, bizType.getValue(),
                    bizType.getValue(), order.getCode());
                if (EBizType.JKEG_MALL.equals(bizType)) {
                    // 平台返积分给用户
                    accountBO.doTransferAmountRemote(
                        ESysUser.SYS_USER_JKEG.getCode(), order.getApplyUser(),
                        ECurrency.JF, rmbAmount, bizType, bizType.getValue(),
                        bizType.getValue(), order.getCode());
                }
            }

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

    @Override
    @Transactional
    public void paySuccessJKEG(String payGroup, String payType, String payCode,
            Long amount) {
        List<Order> orderList = orderBO.queryOrderListByPayGroup(payGroup);
        if (CollectionUtils.isEmpty(orderList)) {
            throw new BizException("XN000000", "找不到对应的订单记录");
        }
        Order order = orderList.get(0);
        if (EOrderStatus.TO_PAY.getCode().equals(order.getStatus())) {
            // 更新订单支付金额
            orderBO.refreshPaySuccess(order, amount, 0L, 0L, null, payType);

            List<ProductOrder> productOrders = productOrderBO
                .queryProductOrderList(order.getCode());
            for (ProductOrder productOrder : productOrders) {
                // 更新库存
                productSpecsBO.refreshQuantity(
                    productOrder.getProductSpecsCode(),
                    productOrder.getQuantity());
            }
        } else {
            logger.info("订单号：" + order.getCode() + "已支付，重复回调");
        }
    }

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
            orderBO.refreshPaySuccess(order, amount, 0L, 0L, null, null);
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
            orderBO.refreshPaySuccess(order, amount, 0L, 0L, null, null);
        } else {
            logger.info("订单号：" + order.getCode() + "已支付，重复回调");
        }
    }

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
