package com.zifei.corebeau.post.bean.response;

import com.zifei.corebeau.post.bean.Post;
import com.zifei.corebeau.common.net.Response;


/**
 * Created by im14s_000 on 2015/3/28.
 */
public class PostResponse extends Response {

    private Post post;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
