package com.zifei.corebeau.post.bean.response;

import com.zifei.corebeau.post.bean.Comment;

import java.util.List;

/**
 * Created by im14s_000 on 2015/3/28.
 */
public class CommentListResponse extends CommentResponse{

    private List<Comment> commentList;

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
