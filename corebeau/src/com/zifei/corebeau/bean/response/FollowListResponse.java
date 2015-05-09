package com.zifei.corebeau.bean.response;

import com.zifei.corebeau.bean.FollowUserInfo;
import com.zifei.corebeau.bean.PageBean;
import com.zifei.corebeau.common.net.Response;

public class FollowListResponse extends Response {
	
	private PageBean<FollowUserInfo> pageBean;

	public PageBean<FollowUserInfo> getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean<FollowUserInfo> pageBean) {
		this.pageBean = pageBean;
	}
}
