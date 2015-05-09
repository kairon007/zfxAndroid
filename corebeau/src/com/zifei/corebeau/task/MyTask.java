package com.zifei.corebeau.task;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.bean.response.MyPostListResponse;
import com.zifei.corebeau.bean.response.TokenResponse;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.CorebeauApp;
import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.common.net.UrlConstants;
import com.zifei.corebeau.common.task.NetworkExecutor;
import com.zifei.corebeau.extra.qiniu.QiniuTask;
import com.zifei.corebeau.extra.qiniu.up.UpParam;
import com.zifei.corebeau.extra.qiniu.up.UploadHandler;
import com.zifei.corebeau.extra.qiniu.up.rs.UploadResultCallRet;
import com.zifei.corebeau.extra.qiniu.up.slice.Block;
import com.zifei.corebeau.utils.CommonConfig;
import com.zifei.corebeau.utils.Utils;

public class MyTask {

	private Context context;

	public MyTask(Context context) {
		this.context = context;
	}
	
	public void getUserInfo(){
		
	}

	public void getMyItemList(int currentPage, 
			final AsyncCallBacks.OneOne<MyPostListResponse, String> callback) {

		Map<String, Object> params = Utils.buildMap("currentPage", currentPage);

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


	public void deleteItem(String itemId,
			final AsyncCallBacks.OneOne<MyPostListResponse, String> callback) {

		Map<String, Object> params = Utils.buildMap(
				"itemId", itemId);

		NetworkExecutor.post(UrlConstants.DELETE_ITEM, params, MyPostListResponse.class,
				new NetworkExecutor.CallBack<MyPostListResponse>() {
					@Override
					public void onSuccess(MyPostListResponse response) {

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
