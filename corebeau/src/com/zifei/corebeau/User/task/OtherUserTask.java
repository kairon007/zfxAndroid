package com.zifei.corebeau.user.task;

import java.util.Map;

import android.content.Context;

import com.zifei.corebeau.account.bean.response.CheckAccountResponse;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.UrlConstants;
import com.zifei.corebeau.common.task.NetworkExecutor;
import com.zifei.corebeau.user.bean.OtherUserInfo;
import com.zifei.corebeau.user.bean.response.OtherUserInfoResponse;
import com.zifei.corebeau.utils.Utils;

public class OtherUserTask {
	
	private Context context;

	public OtherUserTask(Context context) {
		this.context = context;
	}
	
	public void getOtherUserInfo(final String userId,
			final AsyncCallBacks.TwoOne<Integer, OtherUserInfo, String> callback) {

		Map<String, Object> params = Utils.buildMap("userId", userId);

		NetworkExecutor.post(UrlConstants.GET_OTHER_USERINFO, params, OtherUserInfoResponse.class,
				new NetworkExecutor.CallBack<OtherUserInfoResponse>() {
					@Override
					public void onSuccess(OtherUserInfoResponse response) {

						int status = response.getStatusCode();
						String msg = response.getMsg();

						if (status == OtherUserInfoResponse.SUCCESS) {
							callback.onSuccess(status, response.getOtherUserInfo());
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
