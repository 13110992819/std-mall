package com.xnjr.mall.dto.res;

public class XN808268Res {

    // 还未使用数量
    private Long unUseCount;

    // 已使用数量
    private Long usedCount;

    // 已过期数量
    private Long invalidCount;

    public XN808268Res() {
    };

    public XN808268Res(Long unUseCount, Long usedCount, Long invalidCount) {
        this.unUseCount = unUseCount;
        this.usedCount = usedCount;
        this.invalidCount = invalidCount;
    }

    public Long getUnUseCount() {
        return unUseCount;
    }

    public void setUnUseCount(Long unUseCount) {
        this.unUseCount = unUseCount;
    }

    public Long getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(Long usedCount) {
        this.usedCount = usedCount;
    }

    public Long getInvalidCount() {
        return invalidCount;
    }

    public void setInvalidCount(Long invalidCount) {
        this.invalidCount = invalidCount;
    }
}
