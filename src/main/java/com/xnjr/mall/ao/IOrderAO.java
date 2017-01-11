package com.xnjr.mall.ao;

import java.util.List;

import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.Order;

/** 
 * @author: xieyj 
 * @since: 2015年8月27日 上午10:39:37 
 * @history:
 */
public interface IOrderAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    /**
     * 立即购买
     * @param userId
     * @param modelCode
     * @param quantity
     * @param salePrice
     * @param data
     * @return 
     * @create: 2016年5月25日 上午10:48:20 xieyj
     * @history:
     */
    public String commitOrder(String productCode, Integer quantity, Order data);

    /**
     * 批量提交订单
     * @param cartCodeList
     * @param data 
     * @create: 2017年1月3日 下午8:16:11 xieyj
     * @history:
     */
    public void commitOrder(List<String> cartCodeList, Order data);

    /**
     * 支付订单,待发货
     * @param code
     * @param payType 
     * @create: 2017年1月10日 下午4:17:36 xieyj
     * @history:
     */
    public Object toPayOrder(String code, String payType);

    /**
     * 支付订单,待发货
     * @param code
     * @param payType
     * @param ip 
     * @create: 2017年1月11日 上午10:17:31 xieyj
     * @history:
     */
    public Object toPayMixOrder(String code, String payType, String ip);

    /**
     * 批量支付订单，待发货
     * @param codeList
     * @param channel 
     * @create: 2017年1月4日 下午3:38:10 xieyj
     * @history:
     */
    public void toPayOrderList(List<String> codeList, String channel);

    /**
     * 催货
     * @param code
     * @return 
     * @create: 2016年11月23日 下午2:07:38 xieyj
     * @history:
     */
    public void expedOrder(String code);

    /**
     * 取消订单
     * @param code
     * @param userId
     * @param applyNote
     * @return 
     * @create: 2016年6月1日 下午6:09:54 xieyj
     * @history:
     */
    public int cancelOrder(String code, String userId, String applyNote);

    /**
     * 取消订单
     * @param code
     * @param updater
     * @param remark
     * @return 
     * @create: 2016年11月17日 下午3:18:07 haiqingzheng
     * @history:
     */
    public int cancelOrderOss(String code, String updater, String remark);

    /**
     * 确认收货
     * @param code
     * @param updater
     * @param remark
     * @return 
     * @create: 2016年11月17日 下午3:26:14 haiqingzheng
     * @history:
     */
    public int confirmOrder(String code, String updater, String remark);

    /**
     * 订单发货
     * @param code
     * @param logisticsCompany
     * @param logisticsCode
     * @param deliverer
     * @param deliveryDatetime
     * @param pdf
     * @param updater
     * @param remark 
     * @create: 2016年11月17日 下午2:41:49 haiqingzheng
     * @history:
     */
    public void deliverOrder(String code, String logisticsCompany,
            String logisticsCode, String deliverer, String deliveryDatetime,
            String pdf, String updater, String remark);

    /**
     * 现场发货
     * @param code
     * @param updater
     * @param remark 
     * @create: 2016年11月17日 下午3:07:43 haiqingzheng
     * @history:
     */
    public void deliverOrder(String code, String updater, String remark);

    /**
     * 订单分页查询
     * @param condition
     * @return 
     * @create: 2015年8月27日 下午2:22:56 xieyj
     * @history:
     */
    public Paginable<Order> queryOrderPage(int start, int limit, Order condition);

    /**
     * 订单列表查询
     * @param condition
     * @return 
     * @create: 2015年8月27日 下午2:22:56 xieyj
     * @history:
     */
    public List<Order> queryOrderList(Order condition);

    /**
     * 订单详情
     * @param code
     * @return 
     * @create: 2016年5月24日 上午9:07:33 xieyj
     * @history:
     */
    public Order getOrder(String code);

    /**
     * 根据流水编号，找到对应消费记录，更新支付状态
     * @param jourCode
     * @return 
     * @create: 2017年1月10日 下午7:48:09 xieyj
     * @history:
     */
    public void paySuccess(String payCode);

    /**
     *  订单未支付，扫描取消订单
     * @create: 2016年11月25日 下午3:19:04 xieyj
     * @history:
     */
    public void doChangeOrderStatusDaily();

}
