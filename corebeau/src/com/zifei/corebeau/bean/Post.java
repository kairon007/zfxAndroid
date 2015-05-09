package com.zifei.corebeau.bean;

import java.util.ArrayList;

/**
 * Created by im14s_000 on 2015/3/25.
 */
public class Post {

    public static final Short POST_VISIVLE = 0;
    public static final Short POST_UNVISIVLE = 1;
    public static final Short POST_ONLY_VISIBLE = 2;

    public static final Short POST_REPLIABLE = 0;
    public static final Short POST_UNREPLIABLE = 1;
    public static final Short POST_ONLY_REPLIABLE = 2;

    private Integer postId;

    private Integer userId;

    private String userIcon;

    private String userNickname;

    private String message;

    private ArrayList<String> pic;

    private ArrayList<String> picThumb;

    private ArrayList<String> picSub;

    private Integer countLike;

    private Integer countHit;

    private Integer countComment;

    private Short visible;

    private Short repliable;

    private String date;

    private String hashTag;

    private String location;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<String> getPic() {
        return pic;
    }

    public void setPic(ArrayList<String> pic) {
        this.pic = pic;
    }

    public ArrayList<String> getPicThumb() {
        return picThumb;
    }

    public void setPicThumb(ArrayList<String> picThumb) {
        this.picThumb = picThumb;
    }

    public ArrayList<String> getPicSub() {
        return picSub;
    }

    public void setPicSub(ArrayList<String> picSub) {
        this.picSub = picSub;
    }

    public Integer getCountLike() {
        return countLike;
    }

    public void setCountLike(Integer countLike) {
        this.countLike = countLike;
    }

    public Integer getCountHit() {
        return countHit;
    }

    public void setCountHit(Integer countHit) {
        this.countHit = countHit;
    }

    public Integer getCountComment() {
        return countComment;
    }

    public void setCountComment(Integer countComment) {
        this.countComment = countComment;
    }

    public Short getVisible() {
        return visible;
    }

    public void setVisible(Short visible) {
        this.visible = visible;
    }

    public Short getRepliable() {
        return repliable;
    }

    public void setRepliable(Short repliable) {
        this.repliable = repliable;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
