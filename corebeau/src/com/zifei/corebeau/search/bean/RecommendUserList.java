package com.zifei.corebeau.search.bean;

/**
 * Created by im14s_000 on 2015/4/2.
 */
public class RecommendUserList {

    private Integer userId;

    private String nickName;

    private String picThumbUrl;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPicThumbUrl() {
        return picThumbUrl;
    }

    public void setPicThumbUrl(String picThumbUrl) {
        this.picThumbUrl = picThumbUrl;
    }
}
