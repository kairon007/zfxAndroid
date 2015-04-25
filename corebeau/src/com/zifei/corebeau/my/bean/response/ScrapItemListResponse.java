package com.zifei.corebeau.my.bean.response;

import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.bean.PageBean;
import com.zifei.corebeau.common.net.Response;

public class ScrapItemListResponse extends Response {
	
	private PageBean<ItemInfo> pageBean;

	public PageBean<ItemInfo> getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean<ItemInfo> pageBean) {
		this.pageBean = pageBean;
	}

}
