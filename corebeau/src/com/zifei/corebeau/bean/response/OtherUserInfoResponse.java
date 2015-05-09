package com.zifei.corebeau.bean.response;

import com.zifei.corebeau.bean.UserShowInfo;
import com.zifei.corebeau.common.net.Response;

public class OtherUserInfoResponse extends Response{
	
	private UserShowInfo userShowInfo;
	
	private Boolean isFollowed;

	public UserShowInfo getUserShowInfo() {
		return userShowInfo;
	}

	public void setUserShowInfo(UserShowInfo userShowInfo) {
		this.userShowInfo = userShowInfo;
	}

	public Boolean getIsFollowed() {
		return isFollowed;
	}

	public void setIsFollowed(Boolean isFollowed) {
		this.isFollowed = isFollowed;
	}

	
}
