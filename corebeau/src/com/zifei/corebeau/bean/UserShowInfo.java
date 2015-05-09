package com.zifei.corebeau.bean;

public class UserShowInfo {
	private String userId;
	private String nickName;
	private Short userAge;
	private Boolean userGender;
	private String userImageUrl;
	private String city;
	private int itemCount;
	private int followedCount;

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

	public Boolean getUserGender() {
		return userGender;
	}

	public void setUserGender(Boolean userGender) {
		this.userGender = userGender;
	}

	public String getUserImageUrl() {
		return userImageUrl;
	}

	public void setUserImageUrl(String userImageUrl) {
		this.userImageUrl = userImageUrl;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public int getFollowedCount() {
		return followedCount;
	}

	public void setFollowedCount(int followedCount) {
		this.followedCount = followedCount;
	}
}
