package com.zifei.corebeau.User.bean;

/**
 * Created by im14s_000 on 2015/3/23.
 */
public class OtherUserInfo {

    private String userId;

    private String email;

    private String nickName;

    private String picUrl;

    private String picThumbUrl;

    private short gender;

    private boolean isMailReceive;

    private String location;

    private short payLvl;

    private boolean emailVerfied;

    public boolean isEmailVerfied() {
        return emailVerfied;
    }

    public void setEmailVerfied(boolean emailVerfied) {
        this.emailVerfied = emailVerfied;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicThumbUrl() {
        return picThumbUrl;
    }

    public void setPicThumbUrl(String picThumbUrl) {
        this.picThumbUrl = picThumbUrl;
    }

    public short getGender() {
        return gender;
    }

    public void setGender(short gender) {
        this.gender = gender;
    }

    public boolean isMailReceive() {
        return isMailReceive;
    }

    public void setMailReceive(boolean isMailReceive) {
        this.isMailReceive = isMailReceive;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public short getPayLvl() {
        return payLvl;
    }

    public void setPayLvl(short payLvl) {
        this.payLvl = payLvl;
    }

}
