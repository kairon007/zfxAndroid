package com.zifei.corebeau.task;

import java.util.Map;

import android.content.Context;

import com.zifei.corebeau.bean.OtherUserInfo;
import com.zifei.corebeau.bean.UserInfo;
import com.zifei.corebeau.bean.response.CheckAccountResponse;
import com.zifei.corebeau.bean.response.MyPostListResponse;
import com.zifei.corebeau.bean.response.OtherUserInfoResponse;
import com.zifei.corebeau.bean.response.OtherUserItemListResponse;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.UrlConstants;
import com.zifei.corebeau.common.task.NetworkExecutor;
import com.zifei.corebeau.utils.Utils;

public class OtherUserTask {
	
	private Context context;

	public OtherUserTask(Context context) {
		this.context = context;
	}
	
	public void getOtherUserInfo(final String targetUserId,
			final AsyncCallBacks.TwoOne<Integer, OtherUserInfoResponse, String> callback) {

		Map<String, Object> params = Utils.buildMap("targetUserId", targetUserId);

		NetworkExecutor.post(UrlConstants.GET_OTHER_USERINFO, params, OtherUserInfoResponse.class,
				new NetworkExecutor.CallBack<OtherUserInfoResponse>() {
					@Override
					public void onSuccess(OtherUserInfoResponse response) {

						int status = response.getStatusCode();
						String msg = response.getMsg();

						if (status == OtherUserInfoResponse.SUCCESS) {
							callback.onSuccess(status, response);
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
	
	public void getOtherUserItem(final String targetUserId, int currentPage, 
			final AsyncCallBacks.OneOne<OtherUserItemListResponse, String> callback) {

		Map<String, Object> params = Utils.buildMap("targetUserId",targetUserId,"currentPage", currentPage);

		NetworkExecutor.post(UrlConstants.GET_MY_POST, params,
				OtherUserItemListResponse.class,
				new NetworkExecutor.CallBack<OtherUserItemListResponse>() {
					@Override
					public void onSuccess(OtherUserItemListResponse response) {

						int status = response.getStatusCode();
						String msg = response.getMsg();

						if (status == OtherUserItemListResponse.SUCCESS) {
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
