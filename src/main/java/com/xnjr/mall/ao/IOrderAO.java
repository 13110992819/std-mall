package com.xnjr.mall.ao;

import java.util.List;

import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.Order;
import com.xnjr.mall.dto.req.XN808050Req;
import com.xnjr.mall.dto.req.XN808051Req;
import com.xnjr.mall.dto.req.XN808054Req;
import com.xnjr.mall.dto.req.XN808059CReq;

/** 
 * @author: xieyj 
 * @since: 2015年8月27日 上午10:39:37 
 * @history:
 */
public interface IOrderAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public String commitOrder(XN808050Req req);

    public String commitCartOrderJKEG(XN808051Req req);

    public String commitCartOrder(XN808051Req req);

    public Object toPayOrder(List<String> codeList, String payType,
            String tradePwd);

    public void userCancel(String code, String userId, String remark);

    public void platCancel(List<String> codeList, String updater, String remark);

    public void deliverLogistics(XN808054Req req);

    public void callSendLogistics(String code);

    public void deliverXianchang(String code, String updater, String remark);

    public void confirm(String code, String updater, String remark);

    public Object comment(String orderCode, List<XN808059CReq> commentList,
            String commenter);

    public Paginable<Order> queryOrderPage(int start, int limit, Order condition);

    public Paginable<Order> queryMyOrderPage(int start, int limit,
            Order condition);

    public List<Order> queryOrderList(Order condition);

    public Order getOrder(String code);

    public void doChangeOrderStatusDaily();

    public void paySuccessHW(String payGroup, String payCode, Long amount);

    public void paySuccessJKEG(String payGroup, String payCode, Long amount);

    public void paySuccessYC(String payGroup, String payCode, Long amount);

    public void paySuccessGD(String payGroup, String payCode, Long amount);

    public void checkUpgrade(String userId);

}
