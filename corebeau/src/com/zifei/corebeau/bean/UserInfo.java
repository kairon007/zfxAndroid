package com.zifei.corebeau.bean;

import java.io.Serializable;

public class UserInfo {
	public static final boolean GENDER_MALE = true;
	public static final boolean GENDER_FEMALE = false;

	private static final long serialVersionUID = -2159762043518297940L;

	private String userId;

	private String loginId;

	private String nickName;

	private Short userAge;

	private Boolean userGender;

	private Long userRegtime;

	private String url;

	private String userPhone;

	private String userEmail;

	private Short state;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Short getUserAge() {
		return userAge;
	}

	public void setUserAge(Short userAge) {
		this.userAge = userAge;
	}

	public Boolean getUserGender() {
		return userGender;
	}

	public void setUserGender(Boolean userGender) {
		this.userGender = userGender;
	}

	public Long getUserRegtime() {
		return userRegtime;
	}

	public void setUserRegtime(Long userRegtime) {
		this.userRegtime = userRegtime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}
}
