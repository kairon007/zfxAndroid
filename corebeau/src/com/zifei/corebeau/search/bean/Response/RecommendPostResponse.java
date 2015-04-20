package com.zifei.corebeau.search.bean.Response;

import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.search.bean.ItemInfo;
import com.zifei.corebeau.search.bean.PageBean;
public class RecommendPostResponse extends Response {
	
	private PageBean<ItemInfo> pageBean;

	public PageBean<ItemInfo> getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean<ItemInfo> pageBean) {
		this.pageBean = pageBean;
	}
}
