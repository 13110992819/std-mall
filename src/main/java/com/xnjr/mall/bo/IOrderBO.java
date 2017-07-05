package com.xnjr.mall.bo;

import java.util.List;

import com.xnjr.mall.bo.base.IPaginableBO;
import com.xnjr.mall.domain.Cart;
import com.xnjr.mall.domain.CommitOrderPOJO;
import com.xnjr.mall.domain.Order;

/** 
 * @author: xieyj 
 * @since: 2016年5月24日 上午8:23:54 
 * @history:
 */
public interface IOrderBO extends IPaginableBO<Order> {

    public String saveOrder(List<Cart> cartList, CommitOrderPOJO pojo,
            String toUser, String orderType);

    public int userCancel(String code, String userId, String remark);

    public int platCancel(String code, String updater, String remark,
            String status);

    public int deliverLogistics(String code, String logisticsCompany,
            String logisticsCode, String deliverer, String deliveryDatetime,
            String pdf, String updater, String remark);

    public int deliverXianchang(String code, String updater, String remark);

    public int refreshPaySuccess(Order order, Long payAmount1, Long payAmount2,
            Long payAmount3, String payCode, String payType);

    public int confirm(Order order, String updater, String remark);

    public List<Order> queryOrderList(Order data);

    public Order getOrder(String code);

    public String addPayGroup(String code);

    public List<Order> queryOrderListByPayGroup(String payGroup);

    public Long getTotalAmount(String userId);

    public Long selectXFAmount(String userId);

}
