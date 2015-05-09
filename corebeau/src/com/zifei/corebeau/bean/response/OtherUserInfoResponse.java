package com.zifei.corebeau.bean.response;

import com.zifei.corebeau.bean.OtherUserInfo;
import com.zifei.corebeau.common.net.Response;

public class OtherUserInfoResponse extends Response{
	
	private OtherUserInfo otherUserInfo;

	public OtherUserInfo getOtherUserInfo() {
		return otherUserInfo;
	}

	public void setOtherUserInfo(OtherUserInfo otherUserInfo) {
		this.otherUserInfo = otherUserInfo;
	}
	
}
