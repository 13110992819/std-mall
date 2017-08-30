package com.xnjr.mall.bo;

public interface ISmsOutBO {
    public void sentContent(String userId, String content);

    public void sentContent(String mobile, String content, String companyCode,
            String systemCode);
}
