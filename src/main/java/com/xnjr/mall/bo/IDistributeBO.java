package com.xnjr.mall.bo;

import com.xnjr.mall.domain.Store;
import com.xnjr.mall.domain.User;

/**
 * 分销BO，专门用于分销
 * @author: myb858 
 * @since: 2017年4月4日 下午3:33:00 
 * @history:
 */
public interface IDistributeBO {

    // 商城消费分销
    public void distributeMall(User consumer, Store store, Long frAmount);

    // O2O消费分销
    public void distributeO2O(User consumer, Store store, Long frAmount);
}
