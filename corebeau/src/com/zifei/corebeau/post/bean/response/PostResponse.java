package com.zifei.corebeau.post.bean.response;

import java.util.List;

import com.zifei.corebeau.post.bean.Post;
import com.zifei.corebeau.common.net.Response;


/**
 * Created by im14s_000 on 2015/3/28.
 */
public class PostResponse extends Response {

	private List<String> pictureUrls;

	public List<String> getPictureUrls() {
		return pictureUrls;
	}

	public void setPictureUrls(List<String> pictureUrls) {
		this.pictureUrls = pictureUrls;
	}
	
}
