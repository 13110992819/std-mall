package com.xnjr.mall.ao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xnjr.mall.ao.IStoreAO;
import com.xnjr.mall.bo.IAccountBO;
import com.xnjr.mall.bo.ICategoryBO;
import com.xnjr.mall.bo.IOrderBO;
import com.xnjr.mall.bo.IProductBO;
import com.xnjr.mall.bo.ISmsOutBO;
import com.xnjr.mall.bo.IStoreActionBO;
import com.xnjr.mall.bo.IStoreBO;
import com.xnjr.mall.bo.IStorePurchaseBO;
import com.xnjr.mall.bo.IStoreTicketBO;
import com.xnjr.mall.bo.IUserBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.core.OrderNoGenerater;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Category;
import com.xnjr.mall.domain.Order;
import com.xnjr.mall.domain.Product;
import com.xnjr.mall.domain.Store;
import com.xnjr.mall.domain.StoreTicket;
import com.xnjr.mall.domain.User;
import com.xnjr.mall.dto.req.XN808200Req;
import com.xnjr.mall.dto.req.XN808201Req;
import com.xnjr.mall.dto.req.XN808203Req;
import com.xnjr.mall.dto.req.XN808204Req;
import com.xnjr.mall.dto.req.XN808208Req;
import com.xnjr.mall.dto.req.XN808209Req;
import com.xnjr.mall.dto.res.XN808219Res;
import com.xnjr.mall.dto.res.XN808276Res;
import com.xnjr.mall.enums.EOrderStatus;
import com.xnjr.mall.enums.EProductStatus;
import com.xnjr.mall.enums.EStoreLevel;
import com.xnjr.mall.enums.EStoreStatus;
import com.xnjr.mall.enums.EStoreTicketStatus;
import com.xnjr.mall.enums.ESystemCode;
import com.xnjr.mall.enums.EUserKind;
import com.xnjr.mall.exception.BizException;

/**
 * @author: xieyj 
 * @since: 2017年5月31日 下午9:18:16 
 * @history:
 */
@Service
public class StoreAOImpl implements IStoreAO {

    @Autowired
    private IStoreBO storeBO;

    @Autowired
    private IProductBO productBO;

    @Autowired
    private IOrderBO orderBO;

    @Autowired
    private IStoreTicketBO storeTicketBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private ISmsOutBO smsOutBO;

    @Autowired
    private IStorePurchaseBO storePurchaseBO;

    @Autowired
    private IStoreActionBO storeActionBO;

    @Autowired
    private ICategoryBO categoryBO;

    @Override
    @Transactional
    public String addStoreOss(XN808200Req req) {
        // 验证推荐手机号和店铺主人手机号(正汇等传入推荐人是手机号)
        if (req.getMobile().equals(req.getUserReferee())) {
            throw new BizException("xn000000", "推荐人手机号不能和申请人手机号一样");
        }
        // 验证推荐人是否是平台的已注册用户,将userReferee手机号转化为用户编号
        String userReferee = req.getUserReferee();
        String userRefereeUserId = storeBO.isUserRefereeExist(userReferee);
        // 验证B端用户
        String bUser = null;
        bUser = userBO.isUserExist(req.getMobile(), EUserKind.F2,
            req.getSystemCode());
        if (StringUtils.isBlank(bUser)) { // 注册B端用户
            bUser = userBO.doSaveBUser(req.getMobile(), req.getUserReferee(),
                req.getUpdater(), req.getSystemCode(), req.getCompanyCode());
        } else {
            // 判断该用户是否有店铺了
            storeBO.checkStoreByUser(bUser, null);
        }

        // 根据小类获取大类
        Category category = categoryBO.getCategory(req.getType());

        // 更新字段
        String code = OrderNoGenerater.generateM("SJ");
        Store store = new Store();
        store.setCode(code);
        store.setName(req.getName());
        store.setLevel(req.getLevel());
        store.setCategory(category.getParentCode());
        store.setType(req.getType());
        store.setSlogan(req.getSlogan());

        store.setAdvPic(req.getAdvPic());
        store.setPic(req.getPic());
        store.setDescription(req.getDescription());
        store.setProvince(req.getProvince());
        store.setCity(req.getCity());

        store.setArea(req.getArea());
        store.setAddress(req.getAddress());
        store.setLongitude(req.getLongitude());
        store.setLatitude(req.getLatitude());
        store.setBookMobile(req.getBookMobile());

        store.setSmsMobile(req.getSmsMobile());
        store.setPdf(req.getPdf());
        store.setLegalPersonName(req.getLegalPersonName());

        store.setUserReferee(userRefereeUserId);
        store.setRate1(StringValidater.toDouble(req.getRate1()));
        store.setRate2(StringValidater.toDouble(req.getRate2()));
        store.setRate3(StringValidater.toDouble(req.getRate3()));

        store.setStatus(EStoreStatus.PASS.getCode());
        store.setUpdater(req.getUpdater());
        store.setUpdateDatetime(new Date());
        store.setRemark(req.getRemark());
        store.setOwner(bUser);

        store.setContractNo(OrderNoGenerater.generateM("ZHS-"));
        store.setTotalRmbNum(0);
        store.setTotalJfNum(0);
        store.setTotalDzNum(0);
        store.setTotalScNum(0);

        store.setSystemCode(req.getSystemCode());
        store.setCompanyCode(req.getCompanyCode());
        storeBO.saveStoreOss(store);
        return code;
    }

    @Override
    @Transactional
    public String applyStoreFront(XN808209Req req) {
        // 验证B端用户
        String bUser = null;
        EUserKind userKind = null;
        String level = null;
        if (ESystemCode.JKEG.getCode().equals(req.getSystemCode())) {
            if (EStoreLevel.MINGSU.getCode().equals(req.getLevel())) {
                userKind = EUserKind.JKEG_MINGSU;
                level = EStoreLevel.MINGSU.getCode();
            } else if (EStoreLevel.SUPPLIER.getCode().equals(req.getLevel())) {
                userKind = EUserKind.JKEG_SUPPLIER;
                level = EStoreLevel.SUPPLIER.getCode();
            } else if (EStoreLevel.NOMAL.getCode().equals(req.getLevel())) {
                userKind = EUserKind.JKEG_O2O;
                level = EStoreLevel.NOMAL.getCode();
            }
            bUser = userBO.isUserExist(req.getMobile(), userKind,
                req.getSystemCode());
            if (StringUtils.isBlank(bUser)) {
                bUser = userBO.doSaveUser(userKind, req.getMobile(),
                    req.getUserReferee(), req.getUpdater(),
                    req.getSystemCode(), req.getCompanyCode(),
                    userKind.getValue());
            } else {
                // 判断该用户是否有店铺了
                storeBO.checkStoreByUser(bUser, level);
            }
        }

        // 根据小类获取大类
        Category category = null;
        if (EStoreLevel.MINGSU.getCode().equals(req.getLevel())) {
            category = new Category();
            category.setType(EUserKind.JKEG_MINGSU.getCode());
            category.setParentCode(EUserKind.JKEG_MINGSU.getCode());
        } else {
            category = categoryBO.getCategory(req.getType());
        }

        // 更新字段
        String code = OrderNoGenerater.generateM("SJ");
        Store store = new Store();
        store.setCode(code);
        store.setName(req.getName());
        store.setLevel(req.getLevel());
        store.setCategory(category.getParentCode());
        store.setType(category.getType());
        store.setSlogan(req.getSlogan());

        store.setAdvPic(req.getAdvPic());
        store.setPic(req.getPic());
        store.setDescription(req.getDescription());
        store.setProvince(req.getProvince());
        store.setCity(req.getCity());

        store.setArea(req.getArea());
        store.setAddress(req.getAddress());
        store.setLongitude(req.getLongitude());
        store.setLatitude(req.getLatitude());
        store.setBookMobile(req.getBookMobile());

        store.setSmsMobile(req.getSmsMobile());
        store.setPdf(req.getPdf());
        store.setLegalPersonName(req.getLegalPersonName());

        store.setUserReferee(req.getUserReferee());
        store.setRate1(StringValidater.toDouble(req.getRate1()));
        store.setRate2(StringValidater.toDouble(req.getRate2()));
        store.setRate3(StringValidater.toDouble(req.getRate3()));

        store.setStatus(EStoreStatus.TOCHECK.getCode());
        store.setUpdater(req.getUpdater());
        store.setUpdateDatetime(new Date());
        store.setRemark(req.getRemark());
        store.setOwner(bUser);

        store.setContractNo(OrderNoGenerater.generateM("ZHS-"));
        store.setTotalRmbNum(0);
        store.setTotalJfNum(0);
        store.setTotalDzNum(0);
        store.setTotalScNum(0);

        store.setSystemCode(req.getSystemCode());
        store.setCompanyCode(req.getCompanyCode());
        storeBO.saveStoreOss(store);
        return code;
    }

    @Override
    public void editStoreOss(XN808208Req req) {
        Store dbStore = storeBO.getStore(req.getStoreCode());
        User owner = userBO.getRemoteUser(dbStore.getOwner());
        // 验证推荐手机号和店铺主人手机号(正汇等传入推荐人是手机号)
        if (owner.getMobile().equals(req.getUserReferee())) {
            throw new BizException("xn000000", "推荐人手机号不能和申请人手机号一样");
        }

        // 验证推荐人是否是平台的已注册用户,将userReferee手机号转化为用户编号
        String userRefereeUserId = storeBO.isUserRefereeExist(req
            .getUserReferee());

        // 根据小类获取大类
        Category category = null;
        if (EStoreLevel.MINGSU.getCode().equals(req.getLevel())) {
            category = new Category();
            category.setType(EUserKind.JKEG_MINGSU.getCode());
            category.setParentCode(EUserKind.JKEG_MINGSU.getCode());
        } else {
            category = categoryBO.getCategory(req.getType());
        }

        dbStore.setName(req.getName());
        dbStore.setLevel(req.getLevel());
        dbStore.setCategory(category.getParentCode());
        dbStore.setType(category.getType());
        dbStore.setSlogan(req.getSlogan());

        dbStore.setAdvPic(req.getAdvPic());
        dbStore.setPic(req.getPic());
        dbStore.setDescription(req.getDescription());
        dbStore.setProvince(req.getProvince());
        dbStore.setCity(req.getCity());

        dbStore.setArea(req.getArea());
        dbStore.setAddress(req.getAddress());
        dbStore.setLongitude(req.getLongitude());
        dbStore.setLatitude(req.getLatitude());
        dbStore.setBookMobile(req.getBookMobile());

        dbStore.setSmsMobile(req.getSmsMobile());
        dbStore.setPdf(req.getPdf());
        dbStore.setLegalPersonName(req.getLegalPersonName());

        dbStore.setUserReferee(userRefereeUserId);
        dbStore.setRate1(StringValidater.toDouble(req.getRate1()));
        dbStore.setRate2(StringValidater.toDouble(req.getRate2()));
        dbStore.setRate3(StringValidater.toDouble(req.getRate3()));

        dbStore.setStatus(dbStore.getStatus());
        dbStore.setUpdater(req.getUpdater());
        dbStore.setUpdateDatetime(new Date());
        dbStore.setRemark(req.getRemark());

        storeBO.refreshStoreOss(dbStore);

    }

    @Override
    @Transactional
    public String addStoreFront(XN808201Req req) {
        // 验证当前用户是否已拥有店铺
        User owner = userBO.getRemoteUser(req.getOwner());
        storeBO.checkStoreByUser(owner.getUserId(), null);
        // 验证推荐手机号和店铺主人手机号(正汇等传入推荐人是手机号)
        if (owner.getMobile().equals(req.getUserReferee())) {
            throw new BizException("xn000000", "推荐人手机号不能和申请人手机号一样");
        }

        // 验证推荐人是否是平台的已注册用户,将userReferee手机号转化为用户编号

        String userReferee = req.getUserReferee();
        String userId = storeBO.isUserRefereeExist(userReferee);

        // 根据小类获取大类
        Category category = categoryBO.getCategory(req.getType());

        String code = OrderNoGenerater.generateM("SJ");
        Store data = new Store();
        data.setCode(code);
        data.setName(req.getName());
        data.setLevel(EStoreLevel.NOMAL.getCode());
        data.setCategory(category.getParentCode());
        data.setType(req.getType());
        data.setSlogan(req.getSlogan());

        data.setAdvPic(req.getAdvPic());
        data.setPic(req.getPic());
        data.setDescription(req.getDescription());
        data.setProvince(req.getProvince());
        data.setCity(req.getCity());

        data.setArea(req.getArea());
        data.setAddress(req.getAddress());
        data.setLongitude(req.getLongitude());
        data.setLatitude(req.getLatitude());
        data.setBookMobile(req.getBookMobile());

        data.setSmsMobile(req.getSmsMobile());
        data.setPdf(req.getPdf());
        data.setLegalPersonName(req.getLegalPersonName());

        data.setUserReferee(userId);
        data.setRate1(StringValidater.toDouble(req.getRate1()));
        data.setRate2(StringValidater.toDouble(req.getRate2()));
        data.setRate3(StringValidater.toDouble(req.getRate3()));

        data.setStatus(EStoreStatus.TOCHECK.getCode());
        data.setUpdater(req.getOwner());
        data.setUpdateDatetime(new Date());
        data.setRemark(req.getRemark());
        data.setOwner(req.getOwner());

        data.setTotalRmbNum(0);
        data.setTotalJfNum(0);
        data.setTotalDzNum(0);
        data.setTotalScNum(0);

        data.setSystemCode(req.getSystemCode());
        data.setCompanyCode(req.getCompanyCode());
        storeBO.saveStoreFront(data);
        return code;
    }

    @Override
    public void editStoreFront(XN808203Req req) {
        // 验证店铺是否存在
        storeBO.getStore(req.getCode());

        // 根据小类获取大类
        Category category = categoryBO.getCategory(req.getType());

        // 更新字段
        Store data = new Store();
        data.setCode(req.getCode());
        data.setName(req.getName());
        data.setCategory(category.getParentCode());
        data.setType(req.getType());
        data.setSlogan(req.getSlogan());

        data.setAdvPic(req.getAdvPic());
        data.setPic(req.getPic());
        data.setDescription(req.getDescription());
        data.setProvince(req.getProvince());
        data.setCity(req.getCity());

        data.setArea(req.getArea());
        data.setAddress(req.getAddress());
        data.setLongitude(req.getLongitude());
        data.setLatitude(req.getLatitude());
        data.setBookMobile(req.getBookMobile());

        data.setSmsMobile(req.getSmsMobile());
        data.setPdf(req.getPdf());
        data.setLegalPersonName(req.getLegalPersonName());

        data.setRate1(StringValidater.toDouble(req.getRate1()));
        data.setRate2(StringValidater.toDouble(req.getRate2()));
        data.setRate3(StringValidater.toDouble(req.getRate3()));

        data.setStatus(EStoreStatus.TOCHECK.getCode());
        data.setUpdater(req.getUpdater());
        data.setUpdateDatetime(new Date());
        data.setRemark(req.getRemark());
        storeBO.refreshStoreFront(data);
    }

    @Override
    @Transactional
    public void checkStore(String code, String checkResult, String checkUser,
            String remark) {
        Store dbStore = storeBO.getStore(code);
        if (!EStoreStatus.TOCHECK.getCode().equals(dbStore.getStatus())) {
            throw new BizException("xn000000", dbStore.getName()
                    + "不处于待审核状态，不能进行审核操作");
        }
        storeBO.checkStore(dbStore, checkResult, checkUser, remark);
        userBO.doApprove(dbStore.getOwner(), checkUser, checkResult, remark);
    }

    @Override
    public void putOn(XN808204Req req) {
        Store dbStore = storeBO.getStore(req.getCode());
        if (EStoreStatus.PASS.getCode().equals(dbStore.getStatus())
                || EStoreStatus.OFF.getCode().equals(dbStore.getStatus())) {
            dbStore.setUiLocation(req.getUiLocation());
            dbStore.setUiOrder(req.getUiOrder());
            dbStore.setRate1(StringValidater.toDouble(req.getRate1()));
            dbStore.setRate2(StringValidater.toDouble(req.getRate2()));
            dbStore.setRate3(StringValidater.toDouble(req.getRate3()));
            dbStore.setIsDefault(req.getIsDefault());

            dbStore.setStatus(EStoreStatus.ON_OPEN.getCode());
            dbStore.setUpdater(req.getUpdater());
            dbStore.setUpdateDatetime(new Date());
            dbStore.setRemark(req.getRemark());
            storeBO.putOn(dbStore);
        } else {
            throw new BizException("xn000000", "当前店铺不是可以上架状态，不能上架操作");
        }
    }

    @Override
    public void putOff(String code, String updater, String remark) {
        Store dbStore = storeBO.getStore(code);
        if (EStoreStatus.ON_OPEN.getCode().equals(dbStore.getStatus())
                || EStoreStatus.ON_CLOSE.getCode().equals(dbStore.getStatus())) {
            dbStore.setStatus(EStoreStatus.OFF.getCode());
            dbStore.setUpdater(updater);
            dbStore.setUpdateDatetime(new Date());
            dbStore.setRemark(remark);
            storeBO.putOff(dbStore);
        } else {
            throw new BizException("xn000000", "当前店铺不是可以下架状态，不能下架操作");
        }

    }

    @Override
    public void closeOpen(String code) {
        Store dbStore = storeBO.getStore(code);
        if (EStoreStatus.ON_OPEN.getCode().equals(dbStore.getStatus())) {
            dbStore.setStatus(EStoreStatus.ON_CLOSE.getCode());
            dbStore.setRemark("商家自行关店");
        } else if (EStoreStatus.ON_CLOSE.getCode().equals(dbStore.getStatus())) {
            dbStore.setStatus(EStoreStatus.ON_OPEN.getCode());
            dbStore.setRemark("商家自行开店");
        } else {
            throw new BizException("xn000000", "上架的店铺才能进行开关店操作");
        }
        dbStore.setUpdater(dbStore.getOwner());
        dbStore.setUpdateDatetime(new Date());
        storeBO.openClose(dbStore);
    }

    @Override
    public Paginable<Store> queryStorePageFront(int start, int limit,
            Store condition) {
        Paginable<Store> paginable = storeBO.queryFrontPage(start, limit,
            condition);
        List<Store> storeList = paginable.getList();
        if (CollectionUtils.isNotEmpty(storeList)) {
            for (Store store : storeList) {
                StoreTicket stCondition = new StoreTicket();
                stCondition.setStoreCode(store.getCode());
                stCondition.setStatus(EStoreTicketStatus.ON.getCode());
                List<StoreTicket> storeTickets = storeTicketBO
                    .queryStoreTicketList(stCondition);
                store.setStoreTickets(storeTickets);
                if (StringUtils.isNotBlank(condition.getFromUser())) {
                    store.setIsDZ(storeActionBO.isDz(condition.getFromUser(),
                        store.getCode()));
                }
            }
        }
        return paginable;
    }

    @Override
    public Paginable<Store> queryStorePageOss(int start, int limit,
            Store condition) {
        Paginable<Store> page = storeBO.queryOssPage(start, limit, condition);
        List<Store> list = page.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            for (Store ele : list) {
                // 设置推荐人手机号
                String refereeUserId = ele.getUserReferee();
                if (StringUtils.isNotBlank(refereeUserId)) {
                    User remoteRes = userBO.getRemoteUser(refereeUserId);
                    ele.setRefereeMobile(remoteRes.getMobile());
                    ele.setReferrer(remoteRes);
                }
                // 设置店铺主人手机号
                String ownerId = ele.getOwner();
                if (StringUtils.isNotBlank(ownerId)) {
                    User remoteRes = userBO.getRemoteUser(ownerId);
                    ele.setMobile(remoteRes.getMobile());
                    ele.setUser(remoteRes);
                }
            }
        }
        return page;
    }

    @Override
    public Store getStoreOss(String code) {
        Store ele = storeBO.getStore(code);
        // 设置推荐人手机号
        String refereeUserId = ele.getUserReferee();
        if (StringUtils.isNotBlank(refereeUserId)) {
            User remoteRes = userBO.getRemoteUser(refereeUserId);
            ele.setRefereeMobile(remoteRes.getMobile());
            ele.setReferrer(remoteRes);
        }
        // 设置店铺主人手机号
        String ownerId = ele.getOwner();
        if (StringUtils.isNotBlank(ownerId)) {
            User remoteRes = userBO.getRemoteUser(ownerId);
            ele.setMobile(remoteRes.getMobile());
        }
        return ele;
    }

    @Override
    public Store getStoreFront(String code, String fromUser) {
        Store store = storeBO.getStore(code);
        // 设置推荐人手机号
        String refereeUserId = store.getUserReferee();
        if (StringUtils.isNotBlank(refereeUserId)) {
            User remoteRes = userBO.getRemoteUser(refereeUserId);
            store.setRefereeMobile(remoteRes.getMobile());
        }
        StoreTicket condition = new StoreTicket();
        condition.setStoreCode(store.getCode());
        condition.setStatus(EStoreTicketStatus.ON.getCode());
        store.setStoreTickets(storeTicketBO.queryStoreTicketList(condition));
        if (StringUtils.isNotBlank(fromUser)) {
            store.setIsDZ(storeActionBO.isDz(fromUser, store.getCode()));
        }
        return store;
    }

    @Override
    public List<XN808219Res> getMyStore(String userId) {
        List<XN808219Res> resultList = new ArrayList<XN808219Res>();
        Store condition = new Store();
        condition.setOwner(userId);
        List<Store> storeList = storeBO.queryStoreList(condition);
        for (Store store : storeList) {
            // 设置推荐人手机号
            String refereeUserId = store.getUserReferee();
            if (StringUtils.isNotBlank(refereeUserId)) {
                User remoteRes = userBO.getRemoteUser(refereeUserId);
                store.setRefereeMobile(remoteRes.getMobile());
                store.setReferrer(remoteRes);
            }
            XN808219Res result = new XN808219Res();
            result.setStore(store);
            // 获取人民币总消费记录
            result.setTotalIncome(storePurchaseBO.getTotalIncome(store
                .getCode()));
            resultList.add(result);
        }
        return resultList;
    }

    /** 
     * @see com.xnjr.mall.ao.IStoreAO#getTotalProductOrder(java.lang.String)
     */
    @Override
    public XN808276Res getTotalProductOrder(String userId) {
        XN808276Res res = new XN808276Res();
        Product condition = new Product();
        condition.setCompanyCode(userId);
        Long productCount = productBO.getTotalCount(condition);
        res.setProductCount(productCount.intValue());// 所有产品数

        condition.setStatus(EProductStatus.PUBLISH_YES.getCode());
        Long putOnProductCount = productBO.getTotalCount(condition);
        res.setPutOnProductCount(putOnProductCount.intValue());// 上架产品数
        res.setPutOffProductCount((productCount.intValue() - putOnProductCount
            .intValue()));// 下架产品数

        Order orderCondition = new Order();
        orderCondition.setCompanyCode(userId);
        Long orderCount = orderBO.getTotalCount(orderCondition);// 订单总数
        res.setOrderCount(orderCount.intValue());
        orderCondition.setStatus(EOrderStatus.PAY_YES.getCode());
        Long toSendOrderCount = orderBO.getTotalCount(orderCondition);// 待发货总数
        res.setToSendOrderCount(toSendOrderCount.intValue());
        orderCondition.setStatus(EOrderStatus.SEND.getCode());
        Long toReceiveOrderCount = orderBO.getTotalCount(orderCondition);// 待收货总数
        res.setToReceiveOrderCount(toReceiveOrderCount.intValue());
        return res;
    }
}
