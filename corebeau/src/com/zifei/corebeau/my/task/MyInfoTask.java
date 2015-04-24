package com.zifei.corebeau.my.task;

import java.util.HashMap;
import java.util.Map;

import com.zifei.corebeau.account.bean.UserInfo;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.common.net.UrlConstants;
import com.zifei.corebeau.common.task.NetworkExecutor;
import com.zifei.corebeau.my.bean.response.MyPostListResponse;
import com.zifei.corebeau.utils.Utils;

import android.content.Context;

public class MyInfoTask {
	
	public MyInfoTask(Context context){
		
	}
	
	public void updateUserInfo(UserInfo userInfo,
			final AsyncCallBacks.OneOne<Response, String> callback) {

				Map<String, Object> params = Utils.buildMap("userInfo", userInfo);

				NetworkExecutor.post(UrlConstants.UPDATE_USERINFO, params,
						Response.class,
						new NetworkExecutor.CallBack<Response>() {
							@Override
							public void onSuccess(Response response) {

								int status = response.getStatusCode();
								String msg = response.getMsg();

								if (status == Response.SUCCESS) {
									callback.onSuccess(response);
									// save userInfo on db
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
