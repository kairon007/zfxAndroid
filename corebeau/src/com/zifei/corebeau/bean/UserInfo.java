package com.zifei.corebeau.bean;

import java.io.Serializable;

/**
 * Created by im14s_000 on 2015/3/23.
 */
public class UserInfo implements Serializable {

	public static final boolean GENDER_MALE = true;
	public static final boolean GENDER_FEMALE = false;

	private static final long serialVersionUID = 6720546254540185313L;

	private String userId;
	
	private String loginId;

	private String nickName;

	private Short appVersion;

	private Short state;

	private String userEmail;

	private Short userAge;

	private Integer userBirthday;

	private String userCity;

	private Boolean userGender;

	private String userPassword;

	private String userPhone;

	private String userProvince;

	private Long userRegtime;

	private String userZone;

	private String userManufacturer;

	private String userImei;

	private String userImageUrl;

	private String androidId;

	private String macAddress;

	public UserInfo() {

	}

	public UserInfo(String userId, String nickName, Short userAge) {
		this.userId = userId;
		this.nickName = nickName;
		this.userAge = userAge;
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

	public Short getUserAge() {
		return userAge;
	}

	public void setUserAge(Short userAge) {
		this.userAge = userAge;
	}

	public Short getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(Short appVersion) {
		this.appVersion = appVersion;
	}

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Integer getUserBirthday() {
		return userBirthday;
	}

	public void setUserBirthday(Integer userBirthday) {
		this.userBirthday = userBirthday;
	}

	public String getUserCity() {
		return userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}

	public Boolean getUserGender() {
		return userGender;
	}

	public void setUserGender(Boolean userGender) {
		this.userGender = userGender;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserProvince() {
		return userProvince;
	}

	public void setUserProvince(String userProvince) {
		this.userProvince = userProvince;
	}

	public Long getUserRegtime() {
		return userRegtime;
	}

	public void setUserRegtime(Long userRegtime) {
		this.userRegtime = userRegtime;
	}

	public String getUserZone() {
		return userZone;
	}

	public void setUserZone(String userZone) {
		this.userZone = userZone;
	}

	public String getUserManufacturer() {
		return userManufacturer;
	}

	public void setUserManufacturer(String userManufacturer) {
		this.userManufacturer = userManufacturer;
	}

	public String getUserImei() {
		return userImei;
	}

	public void setUserImei(String userImei) {
		this.userImei = userImei;
	}

	public String getUserImageUrl() {
		return userImageUrl;
	}

	public void setUserImageUrl(String userImageUrl) {
		this.userImageUrl = userImageUrl;
	}

	public String getAndroidId() {
		return androidId;
	}

	public void setAndroidId(String androidId) {
		this.androidId = androidId;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
}
