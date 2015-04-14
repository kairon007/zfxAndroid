package com.zifei.corebeau.my.bean;

import java.util.List;

import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.post.bean.Post;

public class MyPostListResponse extends Response{
	
	private List<Post> myPostList;

	public List<Post> getMyPostList() {
		return myPostList;
	}

	public void setMyPostList(List<Post> myPostList) {
		this.myPostList = myPostList;
	}
	
}
