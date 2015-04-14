package com.zifei.corebeau.search.task;

import android.content.Context;

import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.UrlConstants;
import com.zifei.corebeau.common.task.NetworkExecutor;
import com.zifei.corebeau.search.bean.RecommendPostList;
import com.zifei.corebeau.search.bean.Response.RecommendUserResponse;
import com.zifei.corebeau.spot.bean.response.SpotListResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by im14s_000 on 2015/4/2.
 */
public class SearchTask {
    private Context context;

    public SearchTask(Context context) {
        this.context = context;
    }

    public void getRecommendUserList(final AsyncCallBacks.OneOne<RecommendUserResponse, String> callback) {

        Map<String, Object> params = new HashMap<String, Object>();

        NetworkExecutor.post(UrlConstants.GET_SEARCH_RECOMMEND_USER, params, RecommendUserResponse.class, new NetworkExecutor.CallBack<RecommendUserResponse>() {
            @Override
            public void onSuccess(RecommendUserResponse response) {

                int status = response.getStatusCode();
                String msg = response.getMsg();

                if (status == RecommendUserResponse.SUCCESS) {
                    callback.onSuccess(response);
                } else if (status == RecommendUserResponse.FAILED) {
                    callback.onError(msg);
                } else {
                    callback.onError(msg);
                }
            }

            @Override
            public void onError(Integer status, String msg) {
                callback.onError(msg);
            }
        });
    }


    public void getRecommendPostList(final AsyncCallBacks.OneOne<RecommendPostList, String> callback) {

        Map<String, Object> params = new HashMap<String, Object>();

        NetworkExecutor.post(UrlConstants.GET_SEARCH_RECOMMEND_USER, params, RecommendPostList.class, new NetworkExecutor.CallBack<RecommendPostList>() {
            @Override
            public void onSuccess(RecommendPostList response) {

                int status = response.getStatusCode();
                String msg = response.getMsg();

                if (status == RecommendPostList.SUCCESS) {
                    callback.onSuccess(response);
                } else if (status == RecommendPostList.FAILED) {
                    callback.onError(msg);
                } else {
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
