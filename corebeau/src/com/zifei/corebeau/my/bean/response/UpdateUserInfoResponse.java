package com.zifei.corebeau.my.bean.response;

import com.zifei.corebeau.bean.UserInfo;
import com.zifei.corebeau.common.net.Response;

public class UpdateUserInfoResponse extends Response {
	
	private UserInfo userInfoSimple;

	public UserInfo getUserInfoSimple() {
		return userInfoSimple;
	}

	public void setUserInfoSimple(UserInfo userInfoSimple) {
		this.userInfoSimple = userInfoSimple;
	}
	
}
