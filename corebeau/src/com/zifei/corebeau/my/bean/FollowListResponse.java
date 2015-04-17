package com.zifei.corebeau.my.bean;

import java.util.List;

import com.zifei.corebeau.common.net.Response;

public class FollowListResponse extends Response {
	
	private List<FollowUser> followUserList;

	public List<FollowUser> getFollowUserList() {
		return followUserList;
	}

	public void setFollowUserList(List<FollowUser> followUserList) {
		this.followUserList = followUserList;
	}
	
}
