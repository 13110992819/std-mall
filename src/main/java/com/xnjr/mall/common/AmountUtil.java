package com.xnjr.mall.common;

import java.math.BigDecimal;

public class AmountUtil {
    public static Long mul(Long amount, double rate) {
        BigDecimal a = new BigDecimal(Double.toString(amount));
        BigDecimal b = new BigDecimal(Double.toString(rate));
        return a.multiply(b).longValue();
    }

    public static Long rmbJinFen(Long amount) {
        Long divideAmount = amount % 10;
        if (divideAmount > 0) {
            Long addAmount = 10 - divideAmount;
            amount = amount + addAmount;
        }
        return amount;
    }

    public static void main(String[] args) {
        System.out.println(rmbJinFen(15L));
    }
}
