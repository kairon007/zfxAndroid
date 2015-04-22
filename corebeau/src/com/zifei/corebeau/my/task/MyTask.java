package com.zifei.corebeau.my.task;

import java.util.HashMap;
import java.util.Map;

import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.common.net.UrlConstants;
import com.zifei.corebeau.common.task.NetworkExecutor;
import com.zifei.corebeau.my.bean.response.MyPostListResponse;
import com.zifei.corebeau.my.bean.response.TokenResponse;
import com.zifei.corebeau.spot.bean.response.SpotListResponse;

import android.content.Context;

public class MyTask {

	private Context context;

	public MyTask(Context context) {
		this.context = context;
	}

	public void getMyPostList(
			final AsyncCallBacks.OneOne<MyPostListResponse, String> callback) {

		Map<String, Object> params = new HashMap<String, Object>();

		NetworkExecutor.post(UrlConstants.GET_MY_POST, params,
				MyPostListResponse.class,
				new NetworkExecutor.CallBack<MyPostListResponse>() {
					@Override
					public void onSuccess(MyPostListResponse response) {

						int status = response.getStatusCode();
						String msg = response.getMsg();

						if (status == MyPostListResponse.SUCCESS) {
							callback.onSuccess(response);
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
	
//	public void getQiniuToken(
//			final AsyncCallBacks.OneOne<QiniuResponse, String> callback) {
//
//		Map<String, Object> params = new HashMap<String, Object>();
//
//		NetworkExecutor.post(UrlConstants.GET_QINIU_TOKEN, params,
//				QiniuResponse.class,
//				new NetworkExecutor.CallBack<QiniuResponse>() {
//			
//					@Override
//					public void onSuccess(QiniuResponse response) {
//
//						int status = response.getStatusCode();
//						String msg = response.getMsg();
//
//						if (status == QiniuResponse.SUCCESS) {
//							callback.onSuccess(response);
//						} else if (status == QiniuResponse.FAILED) {
//							callback.onError(msg);
//						} else {
//							callback.onError(msg);
//						}
//					}
//
//					@Override
//					public void onError(Integer status, String msg) {
//						callback.onError(msg);
//					}
//				});
//	}

	public void insertPost(
			final AsyncCallBacks.OneOne<Response, String> callback) {

		Map<String, Object> params = new HashMap<String, Object>();

		NetworkExecutor.post(UrlConstants.INSERT_POST, params, Response.class,
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

	public void modifyPost(
			final AsyncCallBacks.OneOne<Response, String> callback) {

		Map<String, Object> params = new HashMap<String, Object>();

		NetworkExecutor.post(UrlConstants.INSERT_POST, params, Response.class,
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

	public void deletePost(
			final AsyncCallBacks.OneOne<Response, String> callback) {

		Map<String, Object> params = new HashMap<String, Object>();

		NetworkExecutor.post(UrlConstants.INSERT_POST, params, Response.class,
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

	public void insertIcon(
			final AsyncCallBacks.OneOne<Response, String> callback) {

		Map<String, Object> params = new HashMap<String, Object>();

		NetworkExecutor.post(UrlConstants.INSERT_POST, params, Response.class,
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

	public void modifyNickname(
			final AsyncCallBacks.OneOne<Response, String> callback) {

		Map<String, Object> params = new HashMap<String, Object>();

		NetworkExecutor.post(UrlConstants.INSERT_POST, params, Response.class,
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
