package com.zifei.corebeau.bean.response;

import com.zifei.corebeau.common.net.Response;

public class LikeActionResponse extends Response {
	private int likeCnt;

	public int getLikeCnt() {
		return likeCnt;
	}

	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}

}
