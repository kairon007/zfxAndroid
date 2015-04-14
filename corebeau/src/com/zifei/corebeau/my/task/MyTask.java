package com.zifei.corebeau.my.task;

import java.util.HashMap;
import java.util.Map;

import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.UrlConstants;
import com.zifei.corebeau.common.task.NetworkExecutor;
import com.zifei.corebeau.my.bean.MyPostListResponse;
import com.zifei.corebeau.spot.bean.response.SpotListResponse;

import android.content.Context;

public class MyTask {

	private Context context;

	public MyTask(Context context) {
		this.context = context;
	}
	
	 public void getMyPostList(final AsyncCallBacks.OneOne<MyPostListResponse, String> callback) {

	        Map<String, Object> params = new HashMap<String, Object>();

	        NetworkExecutor.post(UrlConstants.GET_SPOT_LIST, params, MyPostListResponse.class, new NetworkExecutor.CallBack<MyPostListResponse>() {
	            @Override
	            public void onSuccess(MyPostListResponse response) {

	                int status = response.getStatusCode();
	                String msg = response.getMsg();

	                if(status == MyPostListResponse.SUCCESS){
	                    callback.onSuccess(response);
	                }else if(status == MyPostListResponse.FAILED){
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
