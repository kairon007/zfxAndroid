package com.zifei.corebeau.my.task;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.CorebeauApp;
import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.common.net.UrlConstants;
import com.zifei.corebeau.common.task.NetworkExecutor;
import com.zifei.corebeau.my.bean.response.MyPostListResponse;
import com.zifei.corebeau.my.bean.response.TokenResponse;
import com.zifei.corebeau.my.qiniu.QiniuTask;
import com.zifei.corebeau.my.qiniu.up.UpParam;
import com.zifei.corebeau.my.qiniu.up.UploadHandler;
import com.zifei.corebeau.my.qiniu.up.rs.UploadResultCallRet;
import com.zifei.corebeau.my.qiniu.up.slice.Block;
import com.zifei.corebeau.spot.bean.response.SpotListResponse;
import com.zifei.corebeau.utils.CommonConfig;
import com.zifei.corebeau.utils.Utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class MyTask {

	private Context context;
	private QiniuTask qiniuTask;

	public MyTask(Context context) {
		this.context = context;
		qiniuTask = new QiniuTask(context);
	}
	
	public void getUserInfo(){
		
	}

	public void getMyItemList(
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


	public void deletePost(
			final AsyncCallBacks.OneOne<MyPostListResponse, String> callback) {

		Map<String, Object> params = new HashMap<String, Object>();

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

	public void insertIcon(
			final AsyncCallBacks.OneOne<Response, String> callback) {

		Map<String, Object> params = new HashMap<String, Object>();

		NetworkExecutor.post(UrlConstants.UPLOAD_MY_ICON_IMAGE, params, Response.class,
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

		NetworkExecutor.post(UrlConstants.MODIFY_NICKNAME, params, Response.class,
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
	
	
	public void getToken(
			final String stringPath,
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
							ContentResolver cr = context.getContentResolver();
								Bitmap bitmap;
								try {
									bitmap = BitmapFactory.decodeStream(cr
											.openInputStream(Uri
													.fromFile(new File(
															stringPath))));
									Uri uri = Uri.parse(MediaStore.Images.Media
											.insertImage(cr,
													compressImage(bitmap),
													null, null));
									qiniuTask.preUpload(uri, uploadHandler,
											response.getUploadToken());
								} catch (FileNotFoundException e) {
									e.printStackTrace();
								}
							qiniuTask.doUpload();
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
	
	public UploadHandler uploadHandler = new UploadHandler() {
		@Override
		protected void onProcess(long contentLength, long currentUploadLength,
				long lastUploadLength, UpParam p, Object passParam) {
		}

		@Override
		protected void onSuccess(UploadResultCallRet ret, UpParam p,
				Object passParam) {
			ObjectMapper objectMapper = new ObjectMapper();
			Map readValue;
			String responseUrl = null;
			try {
				readValue = objectMapper
						.readValue(ret.getResponse(), Map.class);
				responseUrl = (String) readValue.get("key");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				String sourceId = qiniuTask.generateSourceId(p, passParam);
				qiniuTask.clean(sourceId);
			} catch (IOException e) {
				e.printStackTrace();
			}
			uploadProfileImage(responseUrl);
		}

		@Override
		protected void onFailure(UploadResultCallRet ret, UpParam p,
				Object passParam) {
			Utils.showToast(context, "fail!!!! reupload plz");
			if (ret.getException() != null) {
				ret.getException().printStackTrace();
			}
		}

		@Override
		protected void onBlockSuccess(List<Block> uploadedBlocks, Block block,
				UpParam p, Object passParam) {
			Utils.showToast(context, "block success!!");
			try {
				String sourceId = qiniuTask.generateSourceId(p, passParam);
				qiniuTask.addBlock(sourceId, block);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	public void uploadProfileImage(String iconpicUrls,
			final AsyncCallBacks.ZeroOne<String> callback) {
		Map<String, Object> params = Utils.buildMap(
				"iconpicUrls", iconpicUrls);

		NetworkExecutor.post(UrlConstants.UPLOAD_MY_ICON_IMAGE, params, TokenResponse.class,
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
	
	public void uploadProfileImage(String url){
		
		uploadProfileImage(url, new AsyncCallBacks.ZeroOne<String>() {
	
			@Override
			public void onSuccess() {
				Utils.showToast(CorebeauApp.app, "upload success!!");
				
			}
	
			@Override
			public void onError(String msg) {
				Utils.showToast(CorebeauApp.app, "upload failed");
			}
		});
	}
	
	@SuppressLint("NewApi")
	private Bitmap compressImage(Bitmap image) {
		Log.i("image compressImage before", image.getByteCount() + " ");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		if (baos.toByteArray().length / 1024 <= CommonConfig.UPLOAD_IMAGE_QUALITY) {
			ByteArrayInputStream isBm = new ByteArrayInputStream(
					baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
			Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
			return bitmap;
		}
		int options = 100;
		while (baos.toByteArray().length / 1024 > CommonConfig.UPLOAD_IMAGE_QUALITY) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

}
