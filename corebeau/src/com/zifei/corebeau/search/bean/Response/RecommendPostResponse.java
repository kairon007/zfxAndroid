package com.zifei.corebeau.search.bean.Response;

import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.search.bean.PageBean;
@SuppressWarnings("rawtypes")
public class RecommendPostResponse extends Response {
	
	private PageBean pageBean;

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}
}
