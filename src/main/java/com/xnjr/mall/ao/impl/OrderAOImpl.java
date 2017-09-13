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
import com.xnjr.mall.bo.ICommentBO;
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
import com.xnjr.mall.common.AmountUtil;
import com.xnjr.mall.common.DateUtil;
import com.xnjr.mall.common.SysConstants;
import com.xnjr.mall.common.SystemUtil;
import com.xnjr.mall.core.CalculationUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Account;
import com.xnjr.mall.domain.Cart;
import com.xnjr.mall.domain.Order;
import com.xnjr.mall.domain.Product;
import com.xnjr.mall.domain.ProductOrder;
import com.xnjr.mall.domain.ProductSpecs;
import com.xnjr.mall.domain.SYSConfig;
import com.xnjr.mall.domain.Store;
import com.xnjr.mall.domain.User;
import com.xnjr.mall.dto.req.XN003010CReq;
import com.xnjr.mall.dto.req.XN808050Req;
import com.xnjr.mall.dto.req.XN808051Req;
import com.xnjr.mall.dto.req.XN808054Req;
import com.xnjr.mall.dto.req.XN808059CReq;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.enums.EBizType;
import com.xnjr.mall.enums.EBoolean;
import com.xnjr.mall.enums.ECurrency;
import com.xnjr.mall.enums.EOrderStatus;
import com.xnjr.mall.enums.EOrderType;
import com.xnjr.mall.enums.EPayType;
import com.xnjr.mall.enums.EProductKind;
import com.xnjr.mall.enums.EProductStatus;
import com.xnjr.mall.enums.ESysUser;
import com.xnjr.mall.enums.ESystemCode;
import com.xnjr.mall.enums.EUserLevel;
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

    @Autowired
    private ICommentBO commentBO;

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
            ProductSpecs productSpecs = productSpecsBO.getProductSpecs(cart
                .getProductSpecsCode());
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
        // 删除购物车选中记录
        for (String cartCode : cartCodeList) {
            cartBO.removeCart(cartCode);
        }
        return orderBO.saveOrder(cartList, req.getPojo(), toUser, null,
            EOrderType.SH_SALE.getCode());
    }

    @Override
    @Transactional
    public String commitCartOrder(XN808051Req req) {
        List<String> cartCodeList = req.getCartCodeList();
        List<Cart> cartList = cartBO.queryCartList(cartCodeList);
        String systemCode = null;
        // 验证产品是否有记录
        for (Cart cart : cartList) {
            Product product = productBO.getProduct(cart.getProductCode());
            if (!EProductStatus.PUBLISH_YES.getCode().equals(
                product.getStatus())) {
                throw new BizException("xn0000", "购物车中有未上架产品["
                        + product.getName() + "]无法下单");
            }
            // 判断库存是否充足
            ProductSpecs productSpecs = productSpecsBO.getProductSpecs(cart
                .getProductSpecsCode());
            Integer quantity = cart.getQuantity();
            if (productSpecs.getQuantity() - quantity < 0) {
                throw new BizException("xn0000", "产品[" + product.getName()
                        + "]库存不足，不能下单");
            }
            if (StringUtils.isBlank(systemCode)) {
                systemCode = cart.getSystemCode();
            }
        }
        String takeAddress = null;
        // 户外电商toUser为加盟商的userId, 其他系统toUser为产品所属的商家userId
        if (ESystemCode.HW.getCode().equals(systemCode)) {
            String toUser = req.getToUser();
            if (!toUser.startsWith("SYS_USER")) {
                User ztdUser = userBO.getRemoteUser(toUser);
                takeAddress = ztdUser.getProvince() + " " + ztdUser.getCity()
                        + " " + ztdUser.getArea() + " " + ztdUser.getAddress();
            }
        }
        String orderCode = orderBO.saveOrder(cartList, req.getPojo(),
            req.getToUser(), takeAddress, EOrderType.SH_SALE.getCode());
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
            throw new BizException("xn0000", "该产品未上架，不能下单");
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
        String takeAddress = null; // 自提地点
        String orderType = null;
        // 户外电商toUser为加盟商的userId, 其他系统toUser为产品所属的商家userId
        if (ESystemCode.HW.getCode().equals(product.getCompanyCode())) {
            toUser = req.getToUser();
            orderType = EOrderType.SH_SALE.getCode();
            if (!toUser.startsWith("SYS_USER")) {
                User ztdUser = userBO.getRemoteUser(toUser);
                takeAddress = ztdUser.getProvince() + ztdUser.getCity()
                        + ztdUser.getArea() + ztdUser.getAddress();
            }
        } else { // 适用健康e购/户外
            if (product.getStoreCode().startsWith("SYS_USER")) { // 平台的产品所属商家为系统用户编号
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
        return orderBO.saveOrder(cartList, req.getPojo(), toUser, takeAddress,
            orderType);
    }

    @Override
    @Transactional
    public Object toPayOrder(List<String> codeList, String payType,
            String tradePwd) {
        String code = codeList.get(0);
        Order order = orderBO.getOrder(code);
        if (!EOrderStatus.TO_PAY.getCode().equals(order.getStatus())) {
            throw new BizException("xn000000", "订单不处于待支付状态");
        }
        // 验证产品是否有未上架的
        doCheckProductOnline(order);
        if (ESystemCode.CAIGO.getCode().equals(order.getSystemCode())) {
            return toPayOrderCG(order, payType);
        } else if (ESystemCode.PIPE.getCode().equals(order.getSystemCode())) {
            return toPayOrderGD(order, payType);
        } else if (ESystemCode.YAOCHENG.getCode().equals(order.getSystemCode())) {
            return toPayOrderYC(order, payType);
        } else if (ESystemCode.JKEG.getCode().equals(order.getSystemCode())) {
            return toPayOrderJKEG(order, payType);
        } else if (ESystemCode.HW.getCode().equals(order.getSystemCode())) {
            return toPayOrderHW(order, payType);
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
        } else if (EPayType.WECHAT_APP.getCode().equals(payType)) {
            return toPayOrderJKEGWXAPP(order);
        } else if (EPayType.ALIPAY.getCode().equals(payType)) {
            return toPayOrderJKEGZFB(order);
        } else {
            throw new BizException("xn0000", "支付类型不支持");
        }
    }

    private Object toPayOrderJKEGYE(Order order) {
        ECurrency currency = ECurrency.CNY;
        EBizType bizType = EBizType.JKEG_MALL;
        if (EOrderType.INTEGRAL_EXCHANGE.getCode().equals(order.getType())) {
            currency = ECurrency.JF;
            bizType = EBizType.JKEG_JF_MALL;
        }

        Long rmbAmount = order.getAmount1();
        String fromUserId = order.getApplyUser();
        Account rmbAccount = accountBO.getRemoteAccount(fromUserId, currency);
        if (rmbAmount > rmbAccount.getAmount()) {
            throw new BizException("xn0000", "账户余额不足");
        }
        // 更新订单支付金额
        orderBO.refreshPaySuccess(order, rmbAmount, 0L, 0L, null);

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

        this.checkUpgrade(fromUserId);

        return new BooleanRes(true);
    }

    private Object toPayOrderJKEGWXAPP(Order order) {
        Long rmbAmount = order.getAmount1();
        User user = userBO.getRemoteUser(order.getApplyUser());
        String payGroup = orderBO.addPayGroup(order.getCode(),
            EPayType.WECHAT_APP);
        return accountBO.doWeiXinPayRemote(user.getUserId(),
            ESysUser.SYS_USER_JKEG.getCode(), payGroup, order.getCode(),
            EBizType.JKEG_MALL, EBizType.JKEG_MALL.getValue() + "-微信",
            rmbAmount);
    }

    private Object toPayOrderJKEGZFB(Order order) {
        Long rmbAmount = order.getAmount1();
        User user = userBO.getRemoteUser(order.getApplyUser());
        String payGroup = orderBO.addPayGroup(order.getCode(), EPayType.ALIPAY);
        return accountBO.doAlipayRemote(user.getUserId(),
            ESysUser.SYS_USER_JKEG.getCode(), payGroup, order.getCode(),
            EBizType.JKEG_MALL, EBizType.JKEG_MALL.getValue() + "-支付宝",
            rmbAmount);
    }

    private Object toPayOrderHW(Order order, String payType) {
        User user = userBO.getRemoteUser(order.getApplyUser());
        if (EPayType.YE.getCode().equals(payType)) {
            return toPayOrderHWYE(order, user);
        } else if (EPayType.WECHAT_H5.getCode().equals(payType)) {
            return toPayOrderHWH5(order, user);
        } else {
            throw new BizException("xn0000", "支付类型不支持");
        }
    }

    private Object toPayOrderHWYE(Order order, User user) {
        String buyUser = user.getUserId();
        EBizType bizType = EBizType.AJ_GW;
        Long orderAmount = order.getAmount1() + order.getYunfei();// 人民币金额
        Account rmbAccount = accountBO.getRemoteAccount(buyUser, ECurrency.CNY);
        Account hyXjkAccount = accountBO.getRemoteAccount(buyUser,
            ECurrency.HW_XJK);
        if (orderAmount > (rmbAccount.getAmount() + hyXjkAccount.getAmount())) {
            throw new BizException("xn0000", "账户余额不足");
        }
        Long jfAmount = order.getAmount2();// 积分金额
        Account jfAccount = accountBO.getRemoteAccount(buyUser, ECurrency.JF);
        if (jfAmount > jfAccount.getAmount()) {
            throw new BizException("xn0000", "积分不足");
        }
        // 更新订单支付金额
        orderBO.refreshPayYESuccess(order, orderAmount, jfAmount, 0L,
            EPayType.YE.getCode());

        // 先扣除小金库余额,再扣除账户余额
        if (orderAmount > hyXjkAccount.getAmount()) {
            Long xjkAmount = hyXjkAccount.getAmount();
            accountBO.doTransferAmountRemote(buyUser,
                ESysUser.SYS_USER_HW.getCode(), ECurrency.HW_XJK, xjkAmount,
                bizType, bizType.getValue(), bizType.getValue(),
                order.getCode());
            Long cnyAmount = orderAmount - xjkAmount;
            accountBO.doTransferAmountRemote(buyUser,
                ESysUser.SYS_USER_HW.getCode(), ECurrency.CNY, cnyAmount,
                bizType, bizType.getValue(), bizType.getValue(),
                order.getCode());
        } else {
            accountBO.doTransferAmountRemote(buyUser,
                ESysUser.SYS_USER_HW.getCode(), ECurrency.HW_XJK, orderAmount,
                bizType, bizType.getValue(), bizType.getValue(),
                order.getCode());
        }
        // 再扣除积分金额
        accountBO.doTransferAmountRemote(buyUser,
            ESysUser.SYS_USER_HW.getCode(), ECurrency.JF, jfAmount, bizType,
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

    private Object toPayOrderHWH5(Order order, User user) {
        String payGroup = orderBO.addPayGroup(order.getCode(),
            EPayType.WECHAT_H5);
        return accountBO.doWeiXinH5PayRemote(user.getUserId(),
            user.getOpenId(), ESysUser.SYS_USER_HW.getCode(), payGroup,
            order.getCode(), EBizType.AJ_GW, EBizType.AJ_GW.getValue(),
            order.getAmount1());
    }

    private Object toPayOrderYC(Order order, String payType) {
        if (EPayType.YC_CB.getCode().equals(payType)) {
            return toPayOrderYCCB(order);
        } else if (EPayType.YE.getCode().equals(payType)) {
            return toPayOrderYCYE(order);
        } else if (EPayType.WECHAT_H5.getCode().equals(payType)) {
            return toPayOrderYCWXH5(order);
        } else {
            throw new BizException("xn0000", "支付类型不支持");
        }
    }

    private Object toPayOrderYCWXH5(Order order) {
        Long rmbAmount = order.getAmount1();
        User user = userBO.getRemoteUser(order.getApplyUser());
        String payGroup = orderBO.addPayGroup(order.getCode(),
            EPayType.WECHAT_H5);
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
        orderBO.refreshPaySuccess(order, rmbAmount, 0L, 0L,
            EPayType.YE.getCode());
        // 扣除金额
        if (StringUtils.isNotBlank(order.getToUser())) {// 付给加盟店
            accountBO.doTransferAmountRemote(fromUserId, order.getToUser(),
                ECurrency.CNY, rmbAmount, EBizType.YC_MALL,
                EBizType.YC_MALL.getValue(), EBizType.YC_MALL.getValue(),
                order.getCode());
        } else {// 付钱给平台
            String systemUserId = SystemUtil.getSysUser(systemCode);
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
        orderBO.refreshPayYESuccess(order, 0L, cbAmount, 0L,
            EPayType.YC_CB.getCode());
        // 扣除金额
        if (StringUtils.isNotBlank(order.getToUser())) {// 付给加盟店
            accountBO.doTransferAmountRemote(fromUserId, order.getToUser(),
                ECurrency.YC_CB, cbAmount, EBizType.YC_MALL,
                EBizType.YC_MALL.getValue(), EBizType.YC_MALL.getValue(),
                order.getCode());
        } else {// 付钱给平台
            String systemUserId = SystemUtil.getSysUser(systemCode);
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

    private Object toPayOrderCG(Order order, String payType) {
        Long cgbAmount = order.getAmount2(); // 菜狗币
        Long jfAmount = order.getAmount3(); // 积分
        String systemCode = order.getSystemCode();
        String fromUserId = order.getApplyUser();
        // 余额支付(菜狗币+积分)
        if (EPayType.INTEGRAL.getCode().equals(payType)) {
            // 更新订单支付金额
            orderBO.refreshPayYESuccess(order, 0L, cgbAmount, jfAmount,
                EPayType.MIX_INTEGRAL.getCode());
            // 扣除金额
            if (StringUtils.isNotBlank(order.getToUser())) {// 付给加盟店
                accountBO.doCgbJfPay(fromUserId, order.getToUser(), cgbAmount,
                    jfAmount, EBizType.AJ_GW, order.getCode());
            } else {// 付钱给平台
                String systemUserId = SystemUtil.getSysUser(systemCode);
                accountBO.doCgbJfPay(fromUserId, systemUserId, cgbAmount,
                    jfAmount, EBizType.AJ_GW, order.getCode());
            }
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
            orderBO.refreshPayYESuccess(order, 0L, 0L, jfAmount,
                EPayType.INTEGRAL.getCode());
            // 扣除金额
            String systemUserId = SystemUtil.getSysUser(systemCode);
            accountBO.doCSWJfPay(fromUserId, systemUserId, jfAmount,
                EBizType.GD_MALL, order.getCode());
            return new BooleanRes(true);
        } else if (EPayType.WECHAT_H5.getCode().equals(payType)) {
            Long rmbAmount = order.getAmount1();
            User user = userBO.getRemoteUser(order.getApplyUser());
            String payGroup = orderBO.addPayGroup(order.getCode(),
                EPayType.WECHAT_H5);
            return accountBO.doWeiXinH5PayRemote(user.getUserId(),
                user.getOpenId(), order.getToUser(), payGroup, order.getCode(),
                EBizType.YC_MALL, "购物微信支付", rmbAmount);
        } else {
            throw new BizException("xn0000", "支付类型不支持");
        }
    }

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
    @Transactional
    public void platCancel(List<String> codeList, String updater, String remark) {
        for (String code : codeList) {
            platCancelSingle(code, updater, remark);
        }
    }

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
        if (ESystemCode.CAIGO.getCode().equals(order.getSystemCode())) {
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
        } else if (ESystemCode.HW.getCode().equals(order.getSystemCode())) {
            EBizType eBizType = EBizType.AJ_GWTK;
            accountBO.doTransferAmountRemote(ESysUser.SYS_USER_HW.getCode(),
                order.getApplyUser(), ECurrency.CNY, order.getPayAmount1(),
                eBizType, eBizType.getValue(), eBizType.getValue(),
                order.getCode());
            accountBO.doTransferAmountRemote(ESysUser.SYS_USER_HW.getCode(),
                order.getApplyUser(), ECurrency.JF, order.getPayAmount2(),
                eBizType, eBizType.getValue(), eBizType.getValue(),
                order.getCode());

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
    public void callSendLogistics(String code) {
        Order order = orderBO.getOrder(code);
        if (EOrderStatus.PAY_YES.getCode().equals(order.getStatus())) {
            orderBO.promptToSend(order);
            if (order.getPromptTimes() <= 1) {
                SYSConfig sysConfig = sysConfigBO.getSYSConfig(
                    SysConstants.HANDLER_ORDER, order.getSystemCode());
                if (null != sysConfig) {
                    User user = userBO.getRemoteUser(order.getApplyUser());
                    String content = "订单号[" + order.getCode() + "]用户"
                            + user.getMobile() + "催货，请及时发货。";
                    smsOutBO.sentContent(sysConfig.getCvalue(), content,
                        order.getCompanyCode(), order.getSystemCode());
                }
                orderBO.promptToSend(order);
            }
        } else {
            throw new BizException("xn000000", "订单当前状态不是支付成功，不能催货");
        }
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
        orderBO.deliverXianchang(order, updater, remark);
    }

    @Override
    @Transactional
    public void confirm(String code, String updater, String remark) {
        Order order = orderBO.getOrder(code);
        if (!EOrderStatus.SEND.getCode().equalsIgnoreCase(order.getStatus())) {
            throw new BizException("xn000000", "订单不是已发货状态，无法操作");
        }
        doConfirm(order, updater, remark);

        if (ESystemCode.JKEG.getCode().equals(order.getSystemCode())
                && EOrderType.SH_SALE.getCode().equals(order.getType())) {
            if (StringUtils.isNotBlank(order.getToUser())) {
                Store store = storeBO.getStoreByUser(order.getToUser());
                // 订单总额
                Long totalAmount = order.getAmount1();
                // 供应商应该拿到的金额
                Long amount = AmountUtil.eraseLiDown(AmountUtil.mul(
                    order.getAmount1(), store.getRate1()));
                // 分润金额
                Long frAmount = totalAmount - amount;
                // 平台划钱给供应商
                accountBO.doTransferAmountRemote(
                    ESysUser.SYS_USER_JKEG.getCode(), order.getToUser(),
                    ECurrency.CNY, amount, EBizType.JKEG_MALL,
                    EBizType.JKEG_MALL.getValue(),
                    EBizType.JKEG_MALL.getValue(), order.getCode());
                // 开始分赃
                User consumer = userBO.getRemoteUser(order.getApplyUser());
                distributeBO.distributeMall(consumer, store, frAmount,
                    order.getCode());
            }
            // 1:1返积分
            accountBO.doTransferAmountRemote(ESysUser.SYS_USER_JKEG.getCode(),
                order.getApplyUser(), ECurrency.JF, order.getAmount1(),
                EBizType.JKEG_MALL, EBizType.JKEG_MALL.getValue(),
                EBizType.JKEG_MALL.getValue(), order.getCode());
        } else if (ESystemCode.HW.getCode().equals(order.getSystemCode())
                && order.getPayAmount1() > 0) {// 人民币消费返现
            Map<String, String> resultMap = sysConfigBO
                .getConfigsMap(ESystemCode.HW.getCode());
            Double backJfRate = Double.valueOf(resultMap
                .get(SysConstants.BACK_JF_RATE));
            Long backJfAmount = AmountUtil.mul(order.getPayAmount1(),
                backJfRate);
            accountBO.doTransferAmountRemote(ESysUser.SYS_USER_HW.getCode(),
                order.getApplyUser(), ECurrency.JF, backJfAmount,
                EBizType.AJ_QRSH, "购物返利", "购物返利", order.getCode());
            User applyUser = userBO.getRemoteUser(order.getApplyUser());
            if (null != applyUser
                    && StringUtils.isNotBlank(applyUser.getUserReferee())) {
                Double backOneRefXjkRate = Double.valueOf(resultMap
                    .get(SysConstants.BACK_ONEREF_XJK_RATE));
                Long backOneRefXjkAmount = AmountUtil.mul(
                    order.getPayAmount1(), backOneRefXjkRate);
                accountBO.doTransferAmountRemote(
                    ESysUser.SYS_USER_HW.getCode(), ECurrency.CNY,
                    applyUser.getUserReferee(), ECurrency.HW_XJK,
                    backOneRefXjkAmount, EBizType.AJ_QRSH, "购物一级推荐人返利",
                    "购物一级推荐人返利", order.getCode());
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
    public void paySuccessHW(String payGroup, String payCode, Long amount) {
        List<Order> orderList = orderBO.queryOrderListByPayGroup(payGroup);
        if (CollectionUtils.isEmpty(orderList)) {
            throw new BizException("XN000000", "找不到对应的订单记录");
        }
        Order order = orderList.get(0);
        if (EOrderStatus.TO_PAY.getCode().equals(order.getStatus())) {
            // 更新订单支付金额
            orderBO.refreshPaySuccess(order, amount, order.getAmount2(), 0L,
                payCode);
            List<ProductOrder> productOrders = productOrderBO
                .queryProductOrderList(order.getCode());
            for (ProductOrder productOrder : productOrders) {
                // 更新库存
                productSpecsBO.refreshQuantity(
                    productOrder.getProductSpecsCode(),
                    productOrder.getQuantity());
            }
            // 扣除积分
            accountBO.doTransferAmountRemote(order.getApplyUser(),
                ESysUser.SYS_USER_HW.getCode(), ECurrency.JF,
                order.getAmount2(), EBizType.AJ_GW, EBizType.AJ_GW.getValue(),
                EBizType.AJ_GW.getValue(), order.getCode());
        } else {
            logger.info("订单号：" + order.getCode() + "已支付，重复回调");
        }
    }

    @Override
    @Transactional
    public void paySuccessJKEG(String payGroup, String payCode, Long amount) {
        List<Order> orderList = orderBO.queryOrderListByPayGroup(payGroup);
        if (CollectionUtils.isEmpty(orderList)) {
            throw new BizException("XN000000", "找不到对应的订单记录");
        }
        Order order = orderList.get(0);
        if (EOrderStatus.TO_PAY.getCode().equals(order.getStatus())) {
            // 更新订单支付金额
            orderBO.refreshPaySuccess(order, amount, 0L, 0L, payCode);
            List<ProductOrder> productOrders = productOrderBO
                .queryProductOrderList(order.getCode());
            for (ProductOrder productOrder : productOrders) {
                // 更新库存
                productSpecsBO.refreshQuantity(
                    productOrder.getProductSpecsCode(),
                    productOrder.getQuantity());
            }
            checkUpgrade(order.getApplyUser());
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
            orderBO.refreshPaySuccess(order, amount, 0L, 0L, payCode);
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
            orderBO.refreshPaySuccess(order, amount, 0L, 0L, payCode);
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

    @Override
    public void checkUpgrade(String userId) {
        try {
            User user = userBO.getRemoteUser(userId);
            if (EUserLevel.ZERO.getCode().equals(user.getLevel())) {
                Long xfAmount = orderBO.selectXFAmount(userId);
                SYSConfig config = sysConfigBO.getSYSConfig(
                    SysConstants.UPGRADE_AMOUNT, ESystemCode.JKEG.getCode());
                Long upgradeAmount = Long.valueOf(CalculationUtil
                    .multDown(config.getCvalue()));
                if (xfAmount >= upgradeAmount) {
                    userBO.doUpgrade(userId);
                }
            }
        } catch (Exception e) {
            logger.error("检查会员是否可以升级异常！");
        }

    }

    @Override
    @Transactional
    public Object comment(String orderCode, List<XN808059CReq> commentList,
            String commenter) {
        Order order = orderBO.getOrder(orderCode);
        if (!EOrderStatus.RECEIVE.getCode().equals(order.getStatus())) {
            throw new BizException("XN000000", "订单不是待评价状态");
        }
        if (!order.getApplyUser().equals(commenter)) {
            throw new BizException("XN000000", "评论人与当前订单下单人不一致");
        }
        User commentUser = userBO.getRemoteUser(commenter);
        orderBO.comment(order);
        List<XN003010CReq> commentRList = new ArrayList<XN003010CReq>();
        for (XN808059CReq xn808059cReq : commentList) {
            XN003010CReq cReq = new XN003010CReq();
            Product product = productBO.getProduct(xn808059cReq
                .getProductCode());
            cReq.setScore(xn808059cReq.getScore());
            cReq.setContent(xn808059cReq.getContent());
            cReq.setParentCode(EBoolean.NO.getCode());
            cReq.setEntityCode(product.getCode());
            cReq.setEntityName(product.getName());
            commentRList.add(cReq);
        }
        return commentBO.comment(order, commentRList, commentUser);
    }
}
