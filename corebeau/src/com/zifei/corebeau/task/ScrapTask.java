package com.zifei.corebeau.task;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.zifei.corebeau.bean.response.MyPostListResponse;
import com.zifei.corebeau.bean.response.ScrapItemListResponse;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.UrlConstants;
import com.zifei.corebeau.common.task.NetworkExecutor;

public class ScrapTask {
	
	private Context context;

	public ScrapTask(Context context) {
		this.context = context;
	}
	
	public void getScrapList(
			final AsyncCallBacks.OneOne<ScrapItemListResponse, String> callback) {

		Map<String, Object> params = new HashMap<String, Object>();

		NetworkExecutor.post(UrlConstants.GET_SCRAP_LIST, params,
				ScrapItemListResponse.class,
				new NetworkExecutor.CallBack<ScrapItemListResponse>() {
					@Override
					public void onSuccess(ScrapItemListResponse response) {

						int status = response.getStatusCode();
						String msg = response.getMsg();

						if (status == ScrapItemListResponse.SUCCESS) {
							callback.onSuccess(response);
						} else if (status == ScrapItemListResponse.FAILED) {
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
