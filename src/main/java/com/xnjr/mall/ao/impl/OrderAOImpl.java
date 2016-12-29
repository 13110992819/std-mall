/**
 * @Title OrderAOImpl.java 
 * @Package com.xnjr.mall.ao.impl 
 * @Description 
 * @author xieyj  
 * @date 2016年5月25日 上午9:37:32 
 * @version V1.0   
 */
package com.xnjr.mall.ao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xnjr.mall.ao.IOrderAO;
import com.xnjr.mall.bo.IAccountBO;
import com.xnjr.mall.bo.ICartBO;
import com.xnjr.mall.bo.IOrderBO;
import com.xnjr.mall.bo.IProductBO;
import com.xnjr.mall.bo.IProductOrderBO;
import com.xnjr.mall.bo.ISYSConfigBO;
import com.xnjr.mall.bo.ISmsOutBO;
import com.xnjr.mall.bo.IUserBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.common.DateUtil;
import com.xnjr.mall.common.SysConstants;
import com.xnjr.mall.core.OrderNoGenerater;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Cart;
import com.xnjr.mall.domain.Order;
import com.xnjr.mall.domain.Product;
import com.xnjr.mall.domain.ProductOrder;
import com.xnjr.mall.enums.EGeneratePrefix;
import com.xnjr.mall.enums.EOrderStatus;
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
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private ISmsOutBO smsOutBO;

    /**
     * @see com.xnjr.mall.ao.IOrderAO#commitOrder(java.lang.String, java.lang.Integer, java.lang.Long, com.xnjr.mall.domain.Order)
     */
    @Override
    @Transactional
    public String commitOrder(String productCode, Integer quantity, Order data) {
        // 计算订单总价
        Product product = productBO.getProduct(productCode);
        if (product.getQuantity() != null) {
            if ((product.getQuantity() - quantity) < 0) {
                throw new BizException("xn0000", "该商品库存量不足，无法购买");
            }
            // 减去库存量
            productBO.refreshProductQuantity(productCode, quantity);
        }
        if (null != product.getPrice1()) {
            Long amount1 = quantity * product.getPrice1();
            data.setAmount1(amount1);
        }
        if (null != product.getPrice2()) {
            Long amount2 = quantity * product.getPrice2();
            data.setAmount2(amount2);
            // 计算订单运费
            Long yunfei = totalYunfei(data.getSystemCode(),
                product.getCompanyCode(), amount2);
            data.setYunfei(yunfei);
        }
        if (null != product.getPrice3()) {
            Long amount3 = quantity * product.getPrice3();
            data.setAmount3(amount3);
        }
        // 设置订单所属公司
        data.setCompanyCode(product.getCompanyCode());
        // 订单号生成
        String code = OrderNoGenerater.generateM(EGeneratePrefix.ORDER
            .getCode());
        data.setCode(code);
        data.setSystemCode(product.getSystemCode());
        orderBO.saveOrder(data);
        // 订单产品快照关联
        productOrderBO.saveProductOrder(code, productCode, quantity,
            product.getPrice1(), product.getPrice2(), product.getPrice3(),
            product.getSystemCode());
        return code;
    }

    /** 
     * @see com.xnjr.mall.ao.IOrderAO#commitOrder(com.xnjr.mall.domain.Order)
     */
    @Override
    @Transactional
    public String commitOrder(List<String> cartCodeList, Order data) {
        // 获取购物车中的记录，形成订单产品关联表
        if (CollectionUtils.isEmpty(cartCodeList)) {
            throw new BizException("xn0000", "请选择购物车中的货物");
        }
        // 订单号生成
        String code = OrderNoGenerater.generateM(EGeneratePrefix.ORDER
            .getCode());
        data.setCode(code);

        // 落地订单产品关联信息 计算订单总金额
        Long amount1 = 0L;
        Long amount2 = 0L;
        Long amount3 = 0L;
        String companyCode = "";
        for (String cartCode : cartCodeList) {
            Cart cart = cartBO.getCart(cartCode);
            Product product = productBO.getProduct(cart.getProductCode());
            if (product.getQuantity() != null) {
                if ((product.getQuantity() - cart.getQuantity()) < 0) {
                    throw new BizException("xn0000", "商品[" + product.getName()
                            + "]库存量不足，无法购买");
                }
                // 减去库存量
                productBO.refreshProductQuantity(product.getCode(),
                    cart.getQuantity());
            }
            if (null != product.getPrice1()) {
                amount1 = amount1 + (cart.getQuantity() * product.getPrice1());
            }
            if (null != product.getPrice2()) {
                amount2 = amount2 + (cart.getQuantity() * product.getPrice2());
            }
            if (null != product.getPrice3()) {
                amount3 = amount3 + (cart.getQuantity() * product.getPrice3());
            }
            companyCode = product.getCompanyCode();
            productOrderBO.saveProductOrder(code, cart.getProductCode(),
                cart.getQuantity(), product.getPrice1(), product.getPrice2(),
                product.getPrice3(), product.getSystemCode());
        }
        data.setAmount1(amount1);
        data.setAmount2(amount2);
        data.setAmount3(amount3);
        data.setCompanyCode(companyCode);
        // 计算订单运费
        Long yunfei = totalYunfei(data.getSystemCode(), companyCode, amount2);
        data.setYunfei(yunfei);
        // 保存订单
        orderBO.saveOrder(data);
        // 删除购物车选中记录
        for (String cartCode : cartCodeList) {
            cartBO.removeCart(cartCode);
        }
        return code;
    }

    private Long totalYunfei(String systemCode, String companyCode, Long amount) {
        Long yunfei = 0L;
        Long byje = StringValidater.toLong(sysConfigBO.getConfigValue(
            systemCode, companyCode, null, SysConstants.SP_BYJE)) * 1000;
        if (amount < byje) {
            yunfei = StringValidater.toLong(sysConfigBO.getConfigValue(
                systemCode, companyCode, null, SysConstants.SP_YUNFEI)) * 1000;
        }
        return yunfei;
    }

    @Override
    @Transactional
    public void toPayOrder(String code, String channel) {
        Order order = orderBO.getOrder(code);
        if (!EOrderStatus.TO_PAY.getCode().equals(order.getStatus())) {
            throw new BizException("xn000000", "订单不处于待支付状态");
        }
        Long payAmount1 = order.getAmount1(); // 购物币
        Long payAmount2 = order.getAmount2() + order.getYunfei(); // 人民币
        Long payAmount3 = order.getAmount3(); // 钱包币
        // if (EChannel.INTEGER.getCode().equals(channel)) {
        // }
        // userBO.doTransfer(order.getApplyUser(), EDirection.MINUS.getCode(),
        // payAmount, "购买商品", code);
        orderBO.refreshOrderPayAmount(code, payAmount1, payAmount2, payAmount3);
    }

    /** 
     * @see com.xnjr.mall.ao.IOrderAO#cancelOrder(java.lang.String, java.lang.String)
     */
    @Override
    public int cancelOrder(String code, String userId, String remark) {
        Order data = orderBO.getOrder(code);
        if (!userId.equals(data.getApplyUser())) {
            throw new BizException("xn0000", "订单申请人和取消操作用户不符");
        }
        if (!EOrderStatus.TO_PAY.getCode().equals(data.getStatus())) {
            throw new BizException("xn0000", "订单状态不是待支付状态");
        }
        return orderBO.cancelOrder(code, remark);
    }

    /**
     * @see com.xnjr.mall.ao.IOrderAO#cancelOrder(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public int cancelOrderOss(String code, String updater, String remark) {
        Order data = orderBO.getOrder(code);
        Long payAmount1 = data.getAmount1();
        Long payAmount2 = data.getAmount2() + data.getYunfei();
        Long payAmount3 = data.getAmount3();
        if (!EOrderStatus.TO_PAY.getCode().equals(data.getStatus())
                && !EOrderStatus.PAY_YES.getCode().equals(data.getStatus())
                && !EOrderStatus.SEND.getCode().equals(data.getStatus())) {
            throw new BizException("xn0000", "该订单不是待支付，支付成功或已发货状态，无法操作");
        }
        String status = null;
        if (EOrderStatus.TO_PAY.getCode().equals(data.getStatus())) {
            status = EOrderStatus.YHYC.getCode();
        } else {
            if (EOrderStatus.PAY_YES.getCode().equals(data.getStatus())) {
                status = EOrderStatus.SHYC.getCode();
            } else if (EOrderStatus.SEND.getCode().equals(data.getStatus())) {
                status = EOrderStatus.KDYC.getCode();
            }
            // 退回各种币 待实现
            // userBO.doTransfer(data.getApplyUser(), EDirection.PLUS.getCode(),
            // payAmount, remark, code);
            // 发送短信
            String userId = data.getApplyUser();
            smsOutBO.sentContent(userId, userId, "尊敬的用户，您的订单[" + data.getCode()
                    + "]已取消,退款原因:[" + remark + "],请及时查看退款。");
        }
        return orderBO.cancelOrder(code, updater, remark, status);
    }

    /** 
     * @see com.xnjr.mall.ao.IOrderAO#queryOrderPage(int, int, com.xnjr.mall.domain.Order)
     */
    @Override
    public Paginable<Order> queryOrderPage(int start, int limit, Order condition) {
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
        return order;
    }

    @Override
    public void deliverOrder(String code, String logisticsCompany,
            String logisticsCode, String deliverer, String deliveryDatetime,
            String pdf, String updater, String remark) {
        Order order = orderBO.getOrder(code);
        if (!EOrderStatus.PAY_YES.getCode().equalsIgnoreCase(order.getStatus())) {
            throw new BizException("xn000000", "订单不是已支付状态，无法操作");
        }
        orderBO.deliverOrder(code, logisticsCompany, logisticsCode, deliverer,
            deliveryDatetime, pdf, updater, remark);
        // 发送短信
        String userId = order.getApplyUser();
        smsOutBO.sentContent(userId, userId, "尊敬的用户，您的订单[" + order.getCode()
                + "]已发货,请注意查收。");
    }

    @Override
    public void deliverOrder(String code, String updater, String remark) {
        Order order = orderBO.getOrder(code);
        if (!EOrderStatus.PAY_YES.getCode().equalsIgnoreCase(order.getStatus())) {
            throw new BizException("xn000000", "该订单不是已支付状态，无法操作");
        }
        orderBO.approveOrder(code, updater, EOrderStatus.RECEIVE.getCode(),
            remark);
    }

    @Override
    public int confirmOrder(String code, String updater, String remark) {
        Order order = orderBO.getOrder(code);
        if (!EOrderStatus.SEND.getCode().equalsIgnoreCase(order.getStatus())) {
            throw new BizException("xn000000", "订单不是已发货状态，无法操作");
        }
        return orderBO.approveOrder(code, updater,
            EOrderStatus.RECEIVE.getCode(), remark);
    }

    /** 
     * @see com.xnjr.mall.ao.IOrderAO#expedOrder(java.lang.String)
     */
    @Override
    public void expedOrder(String code) {
        orderBO.expedOrder(code);
    }

    /** 
     * @see com.xnjr.mall.ao.IOrderAO#doChangeOrderStatusDaily()
     */
    @Override
    public void doChangeOrderStatusDaily() {
        doChangeNoPayOrder();
        doChangeNoReceiveOrder();
    }

    private void doChangeNoPayOrder() {
        logger.info("***************开始扫描未支付订单***************");
        Order condition = new Order();
        condition.setStatus(EOrderStatus.TO_PAY.getCode());
        // 前两小时还未支付的订单
        condition.setApplyDatetimeEnd(DateUtil.getRelativeDate(new Date(),
            -(60 * 60 * 24 * 3 + 1)));
        List<Order> orderList = orderBO.queryOrderList(condition);
        if (CollectionUtils.isNotEmpty(orderList)) {
            for (Order order : orderList) {
                orderBO.refreshOrderStatus(order.getCode(),
                    EOrderStatus.YHYC.getCode());
            }
        }
        logger.info("***************结束扫描未支付订单***************");
    }

    private void doChangeNoReceiveOrder() {
        logger.info("***************开始扫描已发货未确认订单***************");
        Order condition = new Order();
        condition.setStatus(EOrderStatus.SEND.getCode());
        // 已发货后15天内未收货，自动确认收货
        condition.setUpdateDatetimeEnd(DateUtil.getRelativeDate(new Date(),
            -(60 * 60 * 24 * 15 + 1)));
        List<Order> orderList = orderBO.queryOrderList(condition);
        if (CollectionUtils.isNotEmpty(orderList)) {
            for (Order order : orderList) {
                orderBO.approveOrder(order.getCode(), "soft",
                    EOrderStatus.RECEIVE.getCode(), "系统自动确认收货");
            }
        }
        logger.info("***************结束扫描已发货未确认订单***************");
    }
}
