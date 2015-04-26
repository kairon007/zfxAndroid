package com.zifei.corebeau.bean;

/**
 * Created by im14s_000 on 2015/3/23.
 */
public class UserInfo {
	
	private String loginId;

    private String userId;

    private String account;

    private String nickName;

    private String picUrl;

    private String picThumbUrl;
    
    private String userPhone;

    private short gender;

    private boolean isMailReceive;

    private String location;

    private short payLvl;

    private boolean emailVerfied;
    
    private Long userRegtime;
    
    private short state;
    
	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	public Long getUserRegtime() {
		return userRegtime;
	}

	public void setUserRegtime(Long userRegtime) {
		this.userRegtime = userRegtime;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

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
