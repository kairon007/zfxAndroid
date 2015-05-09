package com.zifei.corebeau.task;

import java.util.Map;

import android.content.Context;

import com.zifei.corebeau.bean.UserInfoDetail;
import com.zifei.corebeau.bean.UserInfo;
import com.zifei.corebeau.bean.response.UpdateUserInfoResponse;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.common.net.UrlConstants;
import com.zifei.corebeau.common.task.NetworkExecutor;
import com.zifei.corebeau.utils.Utils;

public class MyInfoTask {

	private UserInfoService userInfoService;

	public MyInfoTask(Context context) {
		userInfoService = new UserInfoService(context);
	}

	public void updateUserInfo(final UserInfoDetail userInfoDetail,
			final AsyncCallBacks.OneOne<UpdateUserInfoResponse, String> callback) {

		Map<String, Object> params = Utils.buildMap("userInfoDetail", userInfoDetail);

		NetworkExecutor.post(UrlConstants.UPDATE_USERINFO, params,
				UpdateUserInfoResponse.class, new NetworkExecutor.CallBack<UpdateUserInfoResponse>() {
					@Override
					public void onSuccess(UpdateUserInfoResponse response) {

						int status = response.getStatusCode();
						String msg = response.getMsg();

						if (status == Response.SUCCESS) {
							callback.onSuccess(response);
							UserInfo userInfo = userInfoService.getCurentUserInfo();
							
							if(userInfoDetail.getUserGender()!= null ){
								userInfo.setUserGender(userInfoDetail.getUserGender());
							}
							if(userInfoDetail.getNickName()!= null){
								userInfo.setNickName(userInfoDetail.getNickName());
							}
							if(userInfoDetail.getUserImageUrl()!= null){
								userInfo.setUrl(userInfoDetail.getUserImageUrl());
							}
							
							userInfoService.updateCurentUserInfo(userInfo);
							
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

	public void bindAccount(final String account, String password, String verifyCode, final AsyncCallBacks.OneOne<Response, String> callback){
		
		Map<String, Object> params = Utils.buildMap("account", account, "password", password, "verifyCode", verifyCode);
		NetworkExecutor.post(UrlConstants.BOUND_ACCOUNT, params,
				Response.class, new NetworkExecutor.CallBack<Response>() {
					@Override
					public void onSuccess(Response response) {

						int status = response.getStatusCode();
						String msg = response.getMsg();

						if (status == Response.SUCCESS) {
							callback.onSuccess(response);
							// save userInfo on db
							UserInfo userInfo = userInfoService.getCurentUserInfo();
							userInfo.setUserEmail(account);
							userInfoService.updateCurentUserInfo(userInfo);
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
	
	public void updatePassword(String oldPassword, String newPassword, final AsyncCallBacks.OneOne<Response, String> callback){
		
		Map<String, Object> params = Utils.buildMap("oldPassword", oldPassword, "newPassword", newPassword);
		NetworkExecutor.post(UrlConstants.BOUND_ACCOUNT, params,
				Response.class, new NetworkExecutor.CallBack<Response>() {
					@Override
					public void onSuccess(Response response) {

						int status = response.getStatusCode();
						String msg = response.getMsg();

						if (status == Response.SUCCESS) {
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
}
