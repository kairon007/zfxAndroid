package com.zifei.corebeau.account.bean.response;

import com.zifei.corebeau.account.bean.UserInfo;
import com.zifei.corebeau.common.net.Response;

public class LoginByDeviceResponse extends Response{
	 private UserInfo userInfo;

	    private String loginId;

	    public String getLoginId() {
	        return loginId;
	    }

	    public void setLoginId(String loginId) {
	        this.loginId = loginId;
	    }

	    public UserInfo getUserInfo() {
	        return userInfo;
	    }

	    public void setUserInfo(UserInfo userInfo) {
	        this.userInfo = userInfo;
	    }

}
