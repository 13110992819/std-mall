package com.xnjr.mall.dto.res;

public class FlagRes {
    // 是否成功
    private boolean flag;

    public FlagRes() {
    }

    public FlagRes(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
