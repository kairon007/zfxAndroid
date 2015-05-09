package com.zifei.corebeau.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import com.zifei.corebeau.bean.response.TokenResponse;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.UrlConstants;
import com.zifei.corebeau.common.task.NetworkExecutor;
import com.zifei.corebeau.service.UploadService;
import com.zifei.corebeau.utils.Utils;

public class UploadTask {

	private Context context;

	public UploadTask(Context context) {
		this.context = context;
	}

	public void uploadImageQiniu() {
	}

	private static int IMAGE_MAX_WIDTH = 600;
	private static int IMAGE_MAX_HEIGHT = 800;

	public static int getImageScale(String imagePath) {
		BitmapFactory.Options option = new BitmapFactory.Options();
		option.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imagePath, option);

		int scale = 1;
		while (option.outWidth / scale >= IMAGE_MAX_WIDTH
				|| option.outHeight / scale >= IMAGE_MAX_HEIGHT) {
			scale *= 2;
		}
		return scale;
	}

	public void getToken(
			final ArrayList<String> stringPathList, 
			final String message,
			final AsyncCallBacks.TwoTwo<Integer, String, Integer, String> callback) {
		Map<String, Object> params = new HashMap<String, Object>();

		NetworkExecutor.post(UrlConstants.GET_UPLOAD_TOKEN, params,
				TokenResponse.class,
				new NetworkExecutor.CallBack<TokenResponse>() {
					@Override
					public void onSuccess(TokenResponse response) {

						int status = response.getStatusCode();
						String msg = response.getMsg();

						if (status == TokenResponse.SUCCESS) {
							startUpdateService(stringPathList, message, response.getUploadToken());
							callback.onSuccess(status, msg);
							
						} else {
							callback.onError(status, msg);
						}
					}

					@Override
					public void onError(Integer status, String msg) {
						callback.onError(status, msg);
					}

				});
	}
	
	public void startUpdateService(ArrayList<String> stringPathList, String message, String token){
		Intent intent = new Intent(context, UploadService.class);
		intent.putStringArrayListExtra("stringPathList", stringPathList);
		intent.putExtra("message", message);
		intent.putExtra("token", token);
		context.startService(intent);
	}

	public void upload(String content, List<String> picUrls,
			final AsyncCallBacks.ZeroOne<String> callback) {
		Map<String, Object> params = Utils.buildMap("title", content,
				"picUrls", picUrls);

		NetworkExecutor.post(UrlConstants.UPLOAD_ITEM, params, TokenResponse.class,
				new NetworkExecutor.CallBack<TokenResponse>() {
					@Override
					public void onSuccess(TokenResponse response) {
						callback.onSuccess();
					}

					@Override
					public void onError(Integer status, String msg) {
						callback.onError(msg);
					}

				});
	}

}
