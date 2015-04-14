package com.zifei.corebeau.search.bean;

import com.zifei.corebeau.common.net.Response;

/**
 * Created by im14s_000 on 2015/4/3.
 */
public class RecommendPostList extends Response{

    private Integer postId;
    
    private Integer likeCount;
    
    private Integer commentCount;

    private String message;

    private String picThumbUrl;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPicThumbUrl() {
        return picThumbUrl;
    }

    public void setPicThumbUrl(String picThumbUrl) {
        this.picThumbUrl = picThumbUrl;
    }

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
    
    
}
