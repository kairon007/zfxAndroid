package com.zifei.corebeau.post.bean;

import java.io.Serializable;

public class ItemComment implements Serializable {

	/**
	 * 
	 */
private static final long serialVersionUID = 9093900807423012398L;
	
	private int id;
	
	private String itemId;  
	
	private String userId;
	
	private String replyUserId; 
	
	private Long logTime;
	
	private String content;   
	
	private String userNickName;   
	
	private String replyUserNickName;    
	
	private String userImageUrl;  
	

	public ItemComment() {
		
	}


	public String getUserImageUrl() {
		return userImageUrl;
	}


	public void setUserImageUrl(String userImageUrl) {
		this.userImageUrl = userImageUrl;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getItemId() {
		return itemId;
	}


	public void setItemId(String itemId) {
		this.itemId = itemId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getReplyUserId() {
		return replyUserId;
	}


	public void setReplyUserId(String replyUserId) {
		this.replyUserId = replyUserId;
	}


	public Long getLogTime() {
		return logTime;
	}


	public void setLogTime(Long logTime) {
		this.logTime = logTime;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getUserNickName() {
		return userNickName;
	}


	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}


	public String getReplyUserNickName() {
		return replyUserNickName;
	}


	public void setReplyUserNickName(String replyUserNickName) {
		this.replyUserNickName = replyUserNickName;
	}
	
	
}