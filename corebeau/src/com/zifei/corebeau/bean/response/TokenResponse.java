package com.zifei.corebeau.bean.response;

import com.zifei.corebeau.common.net.Response;

public class TokenResponse extends Response{
	
	private String uploadToken;

	public String getUploadToken() {
		return uploadToken;
	}

	public void setUploadToken(String uploadToken) {
		this.uploadToken = uploadToken;
	}

}
