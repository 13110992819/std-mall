package com.xnjr.mall.domain;

import java.util.Date;

import com.xnjr.mall.dao.base.ABaseDO;

/**
* 酒店预订
* @author: shan
* @since: 2017年03月08日
* @history:
*/
public class Sbook extends ABaseDO {

    private static final long serialVersionUID = 1L;

    // 编号
    private String code;

    // 服务编号
    private String sproductCode;

    // 日期
    private Date bookDate;

    // 剩余间数
    private Integer remain;

    // 开始日期
    private Date startDate;

    // 结束日期
    private Date endDate;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getSproductCode() {
        return sproductCode;
    }

    public void setSproductCode(String sproductCode) {
        this.sproductCode = sproductCode;
    }

    public void setRemain(Integer remain) {
        this.remain = remain;
    }

    public Integer getRemain() {
        return remain;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

}
