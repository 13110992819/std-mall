package com.xnjr.mall.common;

import java.math.BigDecimal;

import com.xnjr.mall.core.CalculationUtil;

public class AmountUtil {
    public static Long mul(Long amount, double rate) {
        BigDecimal a = new BigDecimal(Double.toString(amount));
        BigDecimal b = new BigDecimal(Double.toString(rate));
        return a.multiply(b).longValue();
    }

    /**
     * 人民币进分
     * @param amount
     * @return 
     * @create: 2017年4月21日 下午2:02:10 xieyj
     * @history:
     */
    public static Long mulRmbJinFen(Long amount, double rate) {
        BigDecimal a = new BigDecimal(Double.toString(amount));
        BigDecimal b = new BigDecimal(Double.toString(rate));
        return rmbJinFen(a.multiply(b).doubleValue());
    }

    public static Long rmbJinFen(Double amount) {
        // 保留到元
        double result = div(amount, 10L);
        result = Math.ceil(result);
        return mul(result, 10L);
    }

    /**
     * 虚拟币进个位数
     * @param amount
     * @param rate
     * @return 
     * @create: 2017年4月21日 下午2:02:31 xieyj
     * @history:
     */
    public static Long mulXnbJin1(Long amount, double rate) {
        BigDecimal a = new BigDecimal(Double.toString(amount));
        BigDecimal b = new BigDecimal(Double.toString(rate));
        return xnbJin1(a.multiply(b).doubleValue());
    }

    public static Long xnbJin1(Double amount) {
        // 保留到元
        double divAmount = div(amount, 1000L);
        double result = Math.ceil(divAmount);
        return mul(result, 1000L);
    }

    public static Long mul(Double amount, Long number) {
        BigDecimal a = new BigDecimal(Double.toString(amount));
        BigDecimal b = new BigDecimal(Double.toString(number));
        return a.multiply(b).longValue();
    }

    public static double div(Double amount, Long number) {
        BigDecimal a = new BigDecimal(Double.toString(amount));
        BigDecimal b = new BigDecimal(Double.toString(number));
        return a.divide(b).doubleValue();
    }

    // 保留两位小数，末尾数不管是几，前一位都加1
    public static Long eraseLiUp(Long amount) {
        String amountString = CalculationUtil.diviUp(amount);
        return Long.valueOf(CalculationUtil.multUp(amountString));
    }

    // 保留两位小数，末尾数不管是几，前一位都加1
    public static Long eraseLiDown(Long amount) {
        String amountString = CalculationUtil.diviDown(amount);
        return Long.valueOf(CalculationUtil.multDown(amountString));
    }

    public static void main(String[] args) {
        System.out.println(eraseLiDown(mul(10L, 0.8)));
    }
}
