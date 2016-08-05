/**
 * @Title IBuyGuide.java 
 * @Package com.xnjr.mall 
 * @Description 
 * @author xieyj  
 * @date 2016年5月24日 上午8:23:54 
 * @version V1.0   
 */
package com.xnjr.mall.bo;

import java.util.List;

import com.xnjr.mall.bo.base.IPaginableBO;
import com.xnjr.mall.domain.BuyGuide;

/** 
 * @author: xieyj 
 * @since: 2016年5月24日 上午8:23:54 
 * @history:
 */
public interface IBuyGuideBO extends IPaginableBO<BuyGuide> {

    /**
     * 判断是否已经存在
     * @param code
     * @return 
     * @create: 2016年5月24日 上午8:54:58 xieyj
     * @history:
     */
    public boolean isBuyGuideExist(String code);

    /**
     * 保存
     * @param data
     * @return 
     * @create: 2015年8月21日 下午5:06:38 xieyj
     * @history:
     */
    public String saveBuyGuide(BuyGuide data);

    /**
     * 删除
     * @param code
     * @return 
     * @create: 2016年5月23日 下午9:09:04 xieyj
     * @history:
     */
    public int deleteBuyGuide(String code);

    /**
     * 更新
     * @param data
     * @return 
     * @create: 2016年5月23日 下午9:08:49 xieyj
     * @history:
     */
    public int refreshBuyGuide(BuyGuide data);

    /**
     * 查询
     * @param data
     * @return 
     * @create: 2015年8月21日 下午5:07:41 xieyj
     * @history:
     */
    public List<BuyGuide> queryBuyGuideList(BuyGuide data);

    /**
     * 详细
     * @param code
     * @return 
     * @create: 2016年5月23日 下午9:08:14 xieyj
     * @history:
     */
    public BuyGuide getBuyGuide(String code);

    /**
     * 根据模型编号和用户等级查询折扣价
     * @param modeCode
     * @param level
     * @return 
     * @create: 2016年5月30日 下午4:04:42 xieyj
     * @history:
     */
    public Long getBuyGuidePrice(String modeCode, String level);
}
