package com.zifei.corebeau.task;

import android.content.Context;

import com.zifei.corebeau.bean.response.SpotListResponse;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.UrlConstants;
import com.zifei.corebeau.common.task.NetworkExecutor;
import com.zifei.corebeau.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by im14s_000 on 2015/3/23.
 */
public class SpotTask {

    private Context context;

    public SpotTask(Context context) {
        this.context = context;
    }

    public void getSpotList(int currentPage, final AsyncCallBacks.OneOne<SpotListResponse, String> callback) {

        Map<String, Object> params = Utils.buildMap("currentPage", currentPage);

        NetworkExecutor.post(UrlConstants.GET_SEARCH_RECOMMEND_POST, params, SpotListResponse.class, new NetworkExecutor.CallBack<SpotListResponse>() {
            @Override
            public void onSuccess(SpotListResponse response) {

                int status = response.getStatusCode();
                String msg = response.getMsg();

                if(status == SpotListResponse.SUCCESS){
                    callback.onSuccess(response);
                }else if(status == SpotListResponse.FAILED){
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
