package com.zifei.corebeau.my.bean.response;

import java.util.List;

import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.my.bean.FollowUser;

public class FollowListResponse extends Response {
	
	private List<FollowUser> followUserList;

	public List<FollowUser> getFollowUserList() {
		return followUserList;
	}

	public void setFollowUserList(List<FollowUser> followUserList) {
		this.followUserList = followUserList;
	}
	
}
