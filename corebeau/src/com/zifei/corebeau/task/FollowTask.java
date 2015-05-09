package com.zifei.corebeau.task;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.zifei.corebeau.bean.response.FollowListResponse;
import com.zifei.corebeau.bean.response.MyPostListResponse;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.common.net.UrlConstants;
import com.zifei.corebeau.common.task.NetworkExecutor;
import com.zifei.corebeau.utils.Utils;

public class FollowTask {
	private Context context;

	public FollowTask(Context context) {
		this.context = context;
	}
	
	public void getFollowList(
			final AsyncCallBacks.OneOne<FollowListResponse, String> callback) {

		Map<String, Object> params = new HashMap<String, Object>();

		NetworkExecutor.post(UrlConstants.GET_FOLLOW_LIST, params,
				FollowListResponse.class,
				new NetworkExecutor.CallBack<FollowListResponse>() {
					@Override
					public void onSuccess(FollowListResponse response) {

						int status = response.getStatusCode();
						String msg = response.getMsg();

						if (status == FollowListResponse.SUCCESS) {
							callback.onSuccess(response);
						} else if (status == FollowListResponse.FAILED) {
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

	public void addFollow(String followUserId,
			final AsyncCallBacks.OneOne<Response, String> callback) {

		Map<String, Object> params = Utils.buildMap("followUserId", followUserId);

		NetworkExecutor.post(UrlConstants.FOLLOW_ADD, params,
				Response.class,
				new NetworkExecutor.CallBack<Response>() {
					@Override
					public void onSuccess(Response response) {

						int status = response.getStatusCode();
						String msg = response.getMsg();

						if (status == MyPostListResponse.SUCCESS) {
							callback.onSuccess(response);
						} else if (status == MyPostListResponse.FAILED) {
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
	
	public void cancelFollow(
			final AsyncCallBacks.OneOne<Response, String> callback) {

		Map<String, Object> params = new HashMap<String, Object>();

		NetworkExecutor.post(UrlConstants.GET_SPOT_LIST, params,
				Response.class,
				new NetworkExecutor.CallBack<Response>() {
					@Override
					public void onSuccess(Response response) {

						int status = response.getStatusCode();
						String msg = response.getMsg();

						if (status == MyPostListResponse.SUCCESS) {
							callback.onSuccess(response);
						} else if (status == MyPostListResponse.FAILED) {
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
	
	public void setFollowState(
			final AsyncCallBacks.OneOne<Response, String> callback) {

		Map<String, Object> params = new HashMap<String, Object>();

		NetworkExecutor.post(UrlConstants.GET_SPOT_LIST, params,
				Response.class,
				new NetworkExecutor.CallBack<Response>() {
					@Override
					public void onSuccess(Response response) {

						int status = response.getStatusCode();
						String msg = response.getMsg();

						if (status == MyPostListResponse.SUCCESS) {
							callback.onSuccess(response);
						} else if (status == MyPostListResponse.FAILED) {
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
