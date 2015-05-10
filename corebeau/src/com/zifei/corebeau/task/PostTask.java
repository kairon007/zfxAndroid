package com.zifei.corebeau.task;

import java.util.Map;

import android.content.Context;

import com.zifei.corebeau.bean.UserInfo;
import com.zifei.corebeau.bean.response.CommentListResponse;
import com.zifei.corebeau.bean.response.ItemDetailResponse;
import com.zifei.corebeau.bean.response.LikeActionResponse;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.common.net.UrlConstants;
import com.zifei.corebeau.common.task.NetworkExecutor;
import com.zifei.corebeau.utils.Utils;

/**
 * Created by im14s_000 on 2015/3/23.
 */
public class PostTask {

    private Context context;

    public PostTask(Context context) {
        this.context = context;
    }
    
    public void getItemDetail(String itemId, final AsyncCallBacks.OneOne<ItemDetailResponse, String> callback) {

        Map<String, Object> params = Utils.buildMap("itemId",itemId);

        NetworkExecutor.post(UrlConstants.GET_ITEM_DETAIL, params, ItemDetailResponse.class, new NetworkExecutor.CallBack<ItemDetailResponse>() {
            @Override
            public void onSuccess(ItemDetailResponse response) {

                int status = response.getStatusCode();
                String msg = response.getMsg();

                if(status == ItemDetailResponse.SUCCESS){
                    callback.onSuccess(response);
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
    public void getComment(String itemId, final AsyncCallBacks.OneOne<CommentListResponse, String> callback) {
        Map<String, Object> params = Utils.buildMap("itemId",itemId);

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
    
    public void insertComment(String itemId, String content, String replyUserId, String replyUserNickName, final AsyncCallBacks.OneOne<CommentListResponse, String> callback) {
		
		UserInfo userInfo = UserInfoService.getCurentUserInfo();
		
        Map<String, Object> params = Utils.buildMap("itemId",itemId,"content",content,
        		"userNickName",userInfo.getNickName() !=null ? userInfo.getNickName() : "",
        		"userImageUrl",userInfo.getUrl() !=null ? userInfo.getUrl() : "",
        		"replyUserId",replyUserId !=null ? replyUserId : "",
        		"replyUserNickName",replyUserNickName !=null ? replyUserNickName : "");

        NetworkExecutor.post(UrlConstants.INSERT_COMMENT, params, CommentListResponse.class, new NetworkExecutor.CallBack<CommentListResponse>() {
            @Override
            public void onSuccess(CommentListResponse response) {

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

    public void deleteComment(String commentId, final AsyncCallBacks.OneOne<String, String> callback) {
        Map<String, Object> params = Utils.buildMap("commentId", commentId);

        NetworkExecutor.post(UrlConstants.DELETE_COMMENT, params, Response.class, new NetworkExecutor.CallBack<Response>() {
            @Override
            public void onSuccess(Response response) {

                int status = response.getStatusCode();
                String msg = response.getMsg();

                if(status == Response.SUCCESS){
                    callback.onSuccess(msg);
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

    
    // db에 쌓아놓고 나중에 서버와 싱크 맞춤
    public void addScrap(String itemId, final AsyncCallBacks.OneOne<Response, String> callback) {
        Map<String, Object> params = Utils.buildMap("itemId",itemId);

        NetworkExecutor.post(UrlConstants.ADD_SCRAP, params, Response.class, new NetworkExecutor.CallBack<Response>() {
            @Override
            public void onSuccess(Response response) {

                int status = response.getStatusCode();
                String msg = response.getMsg();

                if(status == Response.SUCCESS){
                    callback.onSuccess(response);
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
    public void cancelScrap(String itemId, final AsyncCallBacks.OneOne<Response, String> callback) {
        Map<String, Object> params = Utils.buildMap("itemId",itemId);

        NetworkExecutor.post(UrlConstants.CANCEL_SCRAP, params, Response.class, new NetworkExecutor.CallBack<Response>() {
            @Override
            public void onSuccess(Response response) {

                int status = response.getStatusCode();
                String msg = response.getMsg();

                if(status == Response.SUCCESS){
                    callback.onSuccess(response);
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
    public void insertLike(String itemId, final AsyncCallBacks.OneOne<LikeActionResponse, String> callback) {
        Map<String, Object> params = Utils.buildMap("itemId",itemId);

        NetworkExecutor.post(UrlConstants.LIKE, params, LikeActionResponse.class, new NetworkExecutor.CallBack<LikeActionResponse>() {
            @Override
            public void onSuccess(LikeActionResponse response) {

                int status = response.getStatusCode();
                String msg = response.getMsg();

                if(status == Response.SUCCESS){
                    callback.onSuccess(response);
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

    public void deleteLike(String itemId, final AsyncCallBacks.OneOne<LikeActionResponse, String> callback) {
        Map<String, Object> params = Utils.buildMap("itemId",itemId);

        NetworkExecutor.post(UrlConstants.LIKE_DEL, params, LikeActionResponse.class, new NetworkExecutor.CallBack<LikeActionResponse>() {
            @Override
            public void onSuccess(LikeActionResponse response) {

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
