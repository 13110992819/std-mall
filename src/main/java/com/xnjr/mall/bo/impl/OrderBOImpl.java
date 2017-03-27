/**
 * @Title OrderBOImpl.java 
 * @Package com.xnjr.mall.bo.impl 
 * @Description 
 * @author xieyj  
 * @date 2016年5月25日 上午8:15:46 
 * @version V1.0   
 */
package com.xnjr.mall.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.xnjr.mall.bo.IOrderBO;
import com.xnjr.mall.bo.ISYSConfigBO;
import com.xnjr.mall.bo.base.PaginableBOImpl;
import com.xnjr.mall.common.DateUtil;
import com.xnjr.mall.common.SysConstants;
import com.xnjr.mall.core.OrderNoGenerater;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dao.IOrderDAO;
import com.xnjr.mall.dao.IProductOrderDAO;
import com.xnjr.mall.domain.Cart;
import com.xnjr.mall.domain.CommitOrderPOJO;
import com.xnjr.mall.domain.Order;
import com.xnjr.mall.domain.Product;
import com.xnjr.mall.domain.ProductOrder;
import com.xnjr.mall.enums.EGeneratePrefix;
import com.xnjr.mall.enums.EOrderStatus;
import com.xnjr.mall.enums.EOrderType;
import com.xnjr.mall.exception.BizException;

/** 
 * @author: xieyj 
 * @since: 2016年5月25日 上午8:15:46 
 * @history:
 */
@Component
public class OrderBOImpl extends PaginableBOImpl<Order> implements IOrderBO {

    @Autowired
    private IOrderDAO orderDAO;

    @Autowired
    private IProductOrderDAO productOrderDAO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    /** 
     * @see com.xnjr.mall.bo.IBuyGuideBO#isBuyGuideExist(java.lang.String)
     */
    @Override
    public boolean isOrderExist(String code) {
        Order condition = new Order();
        condition.setCode(code);
        if (orderDAO.selectTotalCount(condition) == 1) {
            return true;
        }
        return false;
    }

    public void calculateAmount(Order order, Product product) {

    }

    /** 
     * @see com.xnjr.mall.bo.IOrderBO#saveOrder(com.xnjr.mall.domain.Order)
     */
    @Override
    public void saveOrder(Order data) {
        if (data != null) {
            data.setStatus(EOrderStatus.TO_PAY.getCode());
            data.setApplyDatetime(new Date());
            data.setPayAmount1(0L);
            data.setPayAmount2(0L);
            data.setPayAmount3(0L);
            data.setPromptTimes(0);
            orderDAO.insert(data);
        }
    }

    /** 
     * @see com.xnjr.mall.bo.IOrderBO#refreshOrderStatus(java.lang.String, java.lang.String)
     */
    @Override
    public int refreshOrderStatus(String code, String status) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            if (!isOrderExist(code)) {
                throw new BizException("xn0000", "发货单不存在");
            }
            Order data = new Order();
            data.setCode(code);
            data.setStatus(status);
            count = orderDAO.updateOrderStatus(data);
        }
        return count;
    }

    /** 
     * @see com.xnjr.mall.bo.IOrderBO#refreshOrderPayCode(java.lang.String, java.lang.String)
     */
    @Override
    public int refreshOrderPayCode(String code, String payCode) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            if (!isOrderExist(code)) {
                throw new BizException("xn0000", "发货单不存在");
            }
            Order data = new Order();
            data.setCode(code);
            data.setPayCode(payCode);
            count = orderDAO.updateOrderPayCode(data);
        }
        return count;
    }

    /** 
     * @see com.xnjr.mall.bo.IOrderBO#cancelOrder(java.lang.String, java.lang.String)
     */
    @Override
    public int cancelOrder(String code, String remark) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Order order = this.getOrder(code);
            Order data = new Order();
            data.setCode(code);
            data.setUpdater(order.getUpdater());
            data.setUpdateDatetime(new Date());
            data.setRemark(remark);
            data.setStatus(EOrderStatus.YHYC.getCode());
            count = orderDAO.updateOrderCancel(data);
        }
        return count;
    }

    /** 
     * @see com.xnjr.mall.bo.IOrderBO#cancelOrder(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public int cancelOrder(String code, String updater, String remark,
            String status) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            if (!isOrderExist(code)) {
                throw new BizException("xn0000", "订单不存在");
            }
            Order data = new Order();
            data.setCode(code);
            data.setStatus(status);
            data.setUpdater(updater);
            data.setUpdateDatetime(new Date());
            data.setRemark(remark);
            count = orderDAO.updateOrderCancel(data);
        }
        return count;
    }

    /** 
     * @see com.xnjr.mall.bo.IOrderBO#sendOrder(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public int approveOrder(String code, String updater, String status,
            String remark) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Order data = new Order();
            data.setCode(code);
            data.setUpdater(updater);
            data.setStatus(status);
            data.setUpdateDatetime(new Date());
            data.setRemark(remark);
            count = orderDAO.updateOrderApprove(data);
        }
        return count;
    }

    /** 
     * @see com.xnjr.mall.bo.IOrderBO#queryOrderList(com.xnjr.mall.domain.Order)
     */
    @Override
    public List<Order> queryOrderList(Order condition) {
        return orderDAO.selectList(condition);
    }

    /** 
     * @see com.xnjr.mall.bo.IOrderBO#getOrder(java.lang.String)
     */
    @Override
    public Order getOrder(String code) {
        Order data = null;
        if (StringUtils.isNotBlank(code)) {
            Order condition = new Order();
            condition.setCode(code);
            data = orderDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "订单编号不存在");
            }
            ProductOrder imCondition = new ProductOrder();
            imCondition.setOrderCode(code);
            List<ProductOrder> productOrderList = orderModelDAO
                .selectList(imCondition);
            data.setProductOrderList(productOrderList);
        }
        return data;
    }

    /** 
     * @see com.xnjr.mall.bo.IOrderBO#refreshOrderPayAmount(java.lang.String, java.lang.Long)
     */
    @Override
    public int refreshOrderPayAmount(String code, Long payAmount1,
            Long payAmount2, Long payAmount3) {
        int count = 0;
        if (isOrderExist(code)) {
            Order data = new Order();
            data.setCode(code);
            data.setStatus(EOrderStatus.PAY_YES.getCode());
            data.setPayDatetime(new Date());
            data.setPayAmount1(payAmount1);
            data.setPayAmount2(payAmount2);
            data.setPayAmount3(payAmount3);
            count = orderDAO.updateOrderPayAmount(data);
        }
        return count;
    }

    @Override
    public int deliverOrder(String code, String logisticsCompany,
            String logisticsCode, String deliverer, String deliveryDatetime,
            String pdf, String updater, String remark) {
        Order order = new Order();
        order.setCode(code);
        order.setLogisticsCode(logisticsCode);
        order.setLogisticsCompany(logisticsCompany);
        order.setDeliverer(deliverer);
        order.setDeliveryDatetime(DateUtil.strToDate(deliveryDatetime,
            DateUtil.DATA_TIME_PATTERN_1));
        order.setPdf(pdf);
        order.setStatus(EOrderStatus.SEND.getCode());
        order.setUpdater(updater);
        order.setUpdateDatetime(new Date());
        order.setRemark(remark);
        return orderDAO.updateOrderDeliver(order);
    }

    /** 
     * @see com.xnjr.mall.bo.IOrderBO#expedOrder(java.lang.String)
     */
    @Override
    public int expedOrder(String code) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Order order = this.getOrder(code);
            if (!EOrderStatus.PAY_YES.getCode().equalsIgnoreCase(
                order.getStatus())) {
                throw new BizException("xn000000", "该订单不是已支付状态，无法操作");
            }
            Order data = new Order();
            data.setCode(code);
            data.setUpdateDatetime(new Date());
            data.setRemark("催货次数:" + (order.getPromptTimes() + 1) + "次");
            count = orderDAO.updateOrderExped(data);
        }
        return count;
    }

    @Override
    @Transactional
    public String saveOrder(List<Cart> cartList, CommitOrderPOJO pojo,
            String toUser) {

        String systemCode = cartList.get(0).getSystemCode();
        String companyCode = cartList.get(0).getCompanyCode();

        // 生成订单基本信息
        Order order = new Order();
        String code = OrderNoGenerater.generateME(EGeneratePrefix.ORDER
            .getCode());
        order.setCode(code);
        order.setToUser(toUser);
        order.setApplyUser(pojo.getApplyUser());
        order.setApplyNote(pojo.getApplyNote());
        order.setReceiptType(pojo.getReceiptType());
        order.setReceiptTitle(pojo.getReceiptTitle());
        order.setType(EOrderType.SH_SALE.getCode());
        order.setReceiver(pojo.getReceiver());
        order.setReMobile(pojo.getReMobile());
        order.setReAddress(pojo.getReAddress());
        order.setStatus(EOrderStatus.TO_PAY.getCode());
        order.setApplyDatetime(new Date());
        order.setPromptTimes(0);
        order.setCompanyCode(systemCode);
        order.setSystemCode(companyCode);

        // 计算订单金额
        Long amount1 = 0L;
        Long amount2 = 0L;
        Long amount3 = 0L;
        for (Cart cart : cartList) {
            Product product = cart.getProduct();
            if (null != product.getPrice1()) {
                amount1 = amount1 + (cart.getQuantity() * product.getPrice1());
            }
            if (null != product.getPrice2()) {
                amount2 = amount2 + (cart.getQuantity() * product.getPrice2());
            }
            if (null != product.getPrice3()) {
                amount3 = amount3 + (cart.getQuantity() * product.getPrice3());
            }

            // 落地订单产品关联信息
            ProductOrder productOrder = new ProductOrder();
            productOrder.setCode(OrderNoGenerater
                .generateM(EGeneratePrefix.PRODUCT_ORDER.getCode()));
            productOrder.setOrderCode(code);
            productOrder.setProductCode(product.getCode());
            productOrder.setQuantity(cart.getQuantity());
            productOrder.setPrice1(product.getPrice1());
            productOrder.setPrice2(product.getPrice2());
            productOrder.setPrice3(product.getPrice3());
            productOrder.setCompanyCode(product.getCompanyCode());
            productOrder.setSystemCode(product.getSystemCode());
            productOrderDAO.insert(productOrder);
        }

        // 计算订单运费
        Long yunfei = totalYunfei(systemCode, companyCode, amount1);
        order.setYunfei(yunfei);

        // 落地订单信息
        order.setAmount1(amount1);
        order.setAmount2(amount2);
        order.setAmount3(amount3);
        order.setYunfei(yunfei);
        order.setPayAmount1(0L);
        order.setPayAmount2(0L);
        order.setPayAmount3(0L);

        orderDAO.insert(order);
        return code;
    }

    private Long totalYunfei(String systemCode, String companyCode, Long amount) {
        Long yunfei = 0L;
        Long byje = StringValidater.toLong(sysConfigBO.getConfigValue(
            systemCode, null, companyCode, SysConstants.SP_BYJE)) * 1000;
        if (amount < byje) {
            yunfei = StringValidater.toLong(sysConfigBO.getSYSConfig(
                SysConstants.SP_YUNFEI, systemCode, companyCode)) * 1000;
        }
        return yunfei;
    }
}
