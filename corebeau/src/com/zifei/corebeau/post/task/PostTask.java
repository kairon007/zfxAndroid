package com.zifei.corebeau.post.task;

import android.content.Context;

import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.post.bean.response.CommentListResponse;
import com.zifei.corebeau.post.bean.response.CommentResponse;
import com.zifei.corebeau.post.bean.response.PostResponse;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.UrlConstants;
import com.zifei.corebeau.common.task.NetworkExecutor;
import com.zifei.corebeau.utils.Utils;

import java.util.Map;

/**
 * Created by im14s_000 on 2015/3/23.
 */
public class PostTask {

    private Context context;

    public PostTask(Context context) {
        this.context = context;
    }

    public void getPost(Integer postId, final AsyncCallBacks.OneOne<PostResponse, String> callback) {

        Map<String, Object> params = Utils.buildMap("postId",postId);

        NetworkExecutor.post(UrlConstants.GET_POST, params, PostResponse.class, new NetworkExecutor.CallBack<PostResponse>() {
            @Override
            public void onSuccess(PostResponse response) {

                int status = response.getStatusCode();
                String msg = response.getMsg();

                if(status == PostResponse.SUCCESS){
                    callback.onSuccess(response);
                }else if(status == PostResponse.FAILED){
                    callback.onError(msg);
                }else{
                    callback.onError(msg);
                }
            }

            @Override
            public void onError(Integer status, String msg) {
                callback.onError(msg);
            }
        });
    }

    public void getPostComment(Integer postId, final AsyncCallBacks.OneOne<CommentListResponse, String> callback) {
        Map<String, Object> params = Utils.buildMap("postId",postId);

        NetworkExecutor.post(UrlConstants.GET_COMMENT, params, CommentListResponse.class, new NetworkExecutor.CallBack<CommentListResponse>() {
            @Override
            public void onSuccess(CommentListResponse response) {

                int status = response.getStatusCode();
                String msg = response.getMsg();

                if(status == CommentListResponse.SUCCESS){
                    callback.onSuccess(response);
                }else if(status == CommentResponse.FAILED){
                    callback.onError(msg);
                }else{
                    callback.onError(msg);
                }
            }

            @Override
            public void onError(Integer status, String msg) {
                callback.onError(msg);
            }
        });
    }


    // endless comment  // 서버에 어떤식으로 구별해서 보낼지 생각해보기
    public void getComment(Integer postId, final AsyncCallBacks.OneOne<CommentListResponse, String> callback) {
        Map<String, Object> params = Utils.buildMap("postId",postId);

        NetworkExecutor.post(UrlConstants.GET_COMMENT, params, CommentListResponse.class, new NetworkExecutor.CallBack<CommentListResponse>() {
            @Override
            public void onSuccess(CommentListResponse response) {

                int status = response.getStatusCode();
                String msg = response.getMsg();

                if(status == CommentListResponse.SUCCESS){
                    callback.onSuccess(response);
                }else if(status == CommentListResponse.FAILED){
                    callback.onError(msg);
                }else{
                    callback.onError(msg);
                }
            }

            @Override
            public void onError(Integer status, String msg) {
                callback.onError(msg);
            }
        });
    }

    public void insertComment(Integer postId, String message, final AsyncCallBacks.OneOne<String, String> callback) {
        Map<String, Object> params = Utils.buildMap("postId",postId,"message",message);

        NetworkExecutor.post(UrlConstants.INSERT_COMMENT, params, CommentResponse.class, new NetworkExecutor.CallBack<CommentResponse>() {
            @Override
            public void onSuccess(CommentResponse response) {

                int status = response.getStatusCode();
                String msg = response.getMsg();

                if(status == CommentResponse.SUCCESS){
                    callback.onSuccess(msg);
                }else if(status == CommentResponse.FAILED){
                    callback.onError(msg);
                }else{
                    callback.onError(msg);
                }
            }

            @Override
            public void onError(Integer status, String msg) {
                callback.onError(msg);
            }
        });
    }

    public void deleteComment(Integer commentId, final AsyncCallBacks.OneOne<CommentResponse, String> callback) {
        Map<String, Object> params = Utils.buildMap("commentId", commentId);

        NetworkExecutor.post(UrlConstants.DELETE_COMMENT, params, CommentResponse.class, new NetworkExecutor.CallBack<CommentResponse>() {
            @Override
            public void onSuccess(CommentResponse response) {

                int status = response.getStatusCode();
                String msg = response.getMsg();

                if(status == CommentResponse.SUCCESS){
                    callback.onSuccess(response);
                }else if(status == CommentResponse.FAILED){
                    callback.onError(msg);
                }else{
                    callback.onError(msg);
                }
            }

            @Override
            public void onError(Integer status, String msg) {
                callback.onError(msg);
            }
        });
    }


    // db에 쌓아놓고 나중에 서버와 싱크 맞춤
    public void insertLike(Integer postId, final AsyncCallBacks.OneOne<Response, String> callback) {
        Map<String, Object> params = Utils.buildMap("postId",postId);

        NetworkExecutor.post(UrlConstants.DELETE_COMMENT, params, Response.class, new NetworkExecutor.CallBack<Response>() {
            @Override
            public void onSuccess(Response response) {

                int status = response.getStatusCode();
                String msg = response.getMsg();

                if(status == Response.SUCCESS){
                    callback.onSuccess(response);
                }else if(status == Response.FAILED){
                    callback.onError(msg);
                }else{
                    callback.onError(msg);
                }
            }

            @Override
            public void onError(Integer status, String msg) {
                callback.onError(msg);
            }
        });
    }

    public void deleteLike(Integer postId, final AsyncCallBacks.OneOne<Response, String> callback) {
        Map<String, Object> params = Utils.buildMap("postId",postId);

        NetworkExecutor.post(UrlConstants.DELETE_COMMENT, params, Response.class, new NetworkExecutor.CallBack<Response>() {
            @Override
            public void onSuccess(Response response) {

                int status = response.getStatusCode();
                String msg = response.getMsg();

                if(status == Response.SUCCESS){
                    callback.onSuccess(response);
                }else if(status == Response.FAILED){
                    callback.onError(msg);
                }else{
                    callback.onError(msg);
                }
            }

            @Override
            public void onError(Integer status, String msg) {
                callback.onError(msg);
            }
        });
    }

}
