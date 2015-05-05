package com.zifei.corebeau.user.bean.response;

import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.user.bean.OtherUserInfo;

public class OtherUserInfoResponse extends Response{
	
	private OtherUserInfo otherUserInfo;

	public OtherUserInfo getOtherUserInfo() {
		return otherUserInfo;
	}

	public void setOtherUserInfo(OtherUserInfo otherUserInfo) {
		this.otherUserInfo = otherUserInfo;
	}
	
}
