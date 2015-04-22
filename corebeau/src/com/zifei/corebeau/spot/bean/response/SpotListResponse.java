package com.zifei.corebeau.spot.bean.response;

import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.bean.PageBean;
import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.spot.bean.SpotList;

import java.util.List;

/**
 * Created by im14s_000 on 2015/3/28.
 */
public class SpotListResponse extends Response {

	private PageBean<ItemInfo> pageBean;

	public PageBean<ItemInfo> getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean<ItemInfo> pageBean) {
		this.pageBean = pageBean;
	}
}
