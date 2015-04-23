package com.zifei.corebeau.my.task;

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
import com.zifei.corebeau.my.qiniu.up.UpParam;
import com.zifei.corebeau.my.qiniu.up.UploadHandler;
import com.zifei.corebeau.my.qiniu.up.rs.UploadResultCallRet;
import com.zifei.corebeau.my.qiniu.up.slice.Block;
import com.zifei.corebeau.spot.bean.response.SpotListResponse;
import com.zifei.corebeau.utils.Utils;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

public class MyTask {

	private Context context;

	public MyTask(Context context) {
		this.context = context;
	}
	
	public void getUserInfo(){
		
	}
	
	public void setMyImage(
		final AsyncCallBacks.OneOne<Response, String> callback) {

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
			try {
				readValue = objectMapper
						.readValue(ret.getResponse(), Map.class);
				names.add((String) readValue.get("key"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				String sourceId = qiniuTask.generateSourceId(p, passParam);
				qiniuTask.clean(sourceId);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (names.size() == qiniuTask.getLength()) {
				uploadStatusListener.uploadFinish(true);
			}
		}

		@Override
		protected void onFailure(UploadResultCallRet ret, UpParam p,
				Object passParam) {
			Utils.showToast(context, "fail!!!! reupload plz");
			if (ret.getException() != null) {
				ret.getException().printStackTrace();
			}
			uploadStatusListener.uploadFinish(false);
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

	public void upload(String content,
			final AsyncCallBacks.ZeroOne<String> callback) {
		Map<String, Object> params = Utils.buildMap("title", content,
				"picUrls", names);

		NetworkExecutor.post(UrlConstants.UPLOAD, params, TokenResponse.class,
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
	
	public void upload(String message){
		
		upload(message, new AsyncCallBacks.ZeroOne<String>() {
	
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

}
