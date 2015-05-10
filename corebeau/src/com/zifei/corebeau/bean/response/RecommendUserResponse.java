package com.zifei.corebeau.bean.response;

import java.util.List;

import com.zifei.corebeau.bean.UserInfo;
import com.zifei.corebeau.common.net.Response;
public class RecommendUserResponse extends Response {

	private List<UserInfo> recommendUsers;

	public List<UserInfo> getRecommendUsers() {
		return recommendUsers;
	}

	public void setRecommendUsers(List<UserInfo> recommendUsers) {
		this.recommendUsers = recommendUsers;
	}
	
}
