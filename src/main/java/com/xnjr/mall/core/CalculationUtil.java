package com.xnjr.mall.core;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.xnjr.mall.exception.BizException;

public class CalculationUtil {

    public static String multUp(String number) {
        DecimalFormat df = new DecimalFormat(".00");
        df.setRoundingMode(RoundingMode.UP);
        Double money;
        try {
            String m = df.format(Double.parseDouble(number));
            money = Double.parseDouble(m) * 1000;
        } catch (Exception e) {
            throw new BizException("zc000001", "金额必须是数字类型");
        }
        return String.valueOf(money.longValue());
    }

    public static String diviUp(Long money) {
        DecimalFormat df = new DecimalFormat("#0.00");
        df.setRoundingMode(RoundingMode.UP);
        return df.format(money / 1000.0);
    }

    public static String multDown(String number) {
        DecimalFormat df = new DecimalFormat(".00");
        df.setRoundingMode(RoundingMode.DOWN);
        Double money;
        try {
            String m = df.format(Double.parseDouble(number));
            money = Double.parseDouble(m) * 1000;
        } catch (Exception e) {
            throw new BizException("zc000001", "金额必须是数字类型");
        }
        return String.valueOf(money.longValue());
    }

    public static String diviDown(Long money) {
        DecimalFormat df = new DecimalFormat("#0.00");
        df.setRoundingMode(RoundingMode.DOWN);
        return df.format(money / 1000.0);
    }
}
