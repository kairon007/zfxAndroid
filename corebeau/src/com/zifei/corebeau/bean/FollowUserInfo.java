package com.zifei.corebeau.bean;

import java.io.Serializable;

public class FollowUserInfo implements Serializable {
	
	private static final long serialVersionUID = -2159762043518297940L;

	private String userId;

	private String nickName;

	private Long userRegtime;

	private String url;
	
	private Short state; 

	public FollowUserInfo() {

	}

	public FollowUserInfo(String userId, String nickName, Short userAge,
			Boolean userGender, Long userRegtime, String url ,String userPhone ,String userEmail , Short state) {
		this.userId = userId;
		this.nickName = nickName;
		this.userRegtime = userRegtime;
		this.url = url;
		this.state = state;
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

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}
	
}
