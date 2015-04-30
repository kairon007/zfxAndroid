package com.zifei.corebeau.post.bean.response;

import java.util.List;

import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.post.bean.UserUploadPicture;

public class ItemDetailResponse extends Response {

	private List<UserUploadPicture> pictureUrls;

	private boolean like;

	private boolean scrap;

	public boolean isScrap() {
		return scrap;
	}

	public void setScrap(boolean scrap) {
		this.scrap = scrap;
	}

	public boolean isLike() {
		return like;
	}

	public void setLike(boolean like) {
		this.like = like;
	}

	public List<UserUploadPicture> getPictureUrls() {
		return pictureUrls;
	}

	public void setPictureUrls(List<UserUploadPicture> pictureUrls) {
		this.pictureUrls = pictureUrls;
	}

}