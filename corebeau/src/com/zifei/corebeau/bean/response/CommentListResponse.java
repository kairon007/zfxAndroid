package com.zifei.corebeau.bean.response;

import com.zifei.corebeau.bean.ItemComment;
import com.zifei.corebeau.bean.PageBean;
import com.zifei.corebeau.common.net.Response;

/**
 * Created by im14s_000 on 2015/3/28.
 */
public class CommentListResponse extends Response{

	private PageBean<ItemComment> pageBean;

	public PageBean<ItemComment> getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean<ItemComment> pageBean) {
		this.pageBean = pageBean;
	}
}
