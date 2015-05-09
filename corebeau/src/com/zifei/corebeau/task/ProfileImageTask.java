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
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.zifei.corebeau.bean.UserInfoDetail;
import com.zifei.corebeau.bean.response.TokenResponse;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.common.net.UrlConstants;
import com.zifei.corebeau.common.task.NetworkExecutor;
import com.zifei.corebeau.extra.qiniu.QiniuTask;
import com.zifei.corebeau.extra.qiniu.up.UpParam;
import com.zifei.corebeau.extra.qiniu.up.UploadHandler;
import com.zifei.corebeau.extra.qiniu.up.rs.UploadResultCallRet;
import com.zifei.corebeau.extra.qiniu.up.slice.Block;
import com.zifei.corebeau.service.ProfileImageService;
import com.zifei.corebeau.service.UploadService;
import com.zifei.corebeau.utils.CommonConfig;
import com.zifei.corebeau.utils.PictureUtil;
import com.zifei.corebeau.utils.Utils;

public class ProfileImageTask {
	
	private Context context;
	private QiniuTask qiniuTask;
    private OnProfileImgStatusListener profileImgStatusListener;

	
	public ProfileImageTask(Context context) {
		this.context = context;
		qiniuTask = new QiniuTask(context);
	}

	public void uploadImageQiniu() {
	}

	private static int IMAGE_MAX_WIDTH = 480;
	private static int IMAGE_MAX_HEIGHT = 960;

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

	public void getProfileToken(
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
							
							Uri uri = null;
							File file = new File(stringPath);
							try {
								
								if(file.length() / 1024 <=CommonConfig.UPLOAD_IMAGE_QUALITY * 1.5){
									uri = Uri.fromFile(file);
								} else {
									
									 uri = Uri.fromFile(PictureUtil.compressImage(context, stringPath, file.getName(), 90));
								}
								
								qiniuTask.preUpload(uri, uploadHandler, response.getUploadToken());
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
							qiniuTask.doUpload();
							
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
			String picUrls = "";
			try {
				readValue = objectMapper
						.readValue(ret.getResponse(), Map.class);
				picUrls = (String) readValue.get("key");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				String sourceId = qiniuTask.generateSourceId(p, passParam);
				qiniuTask.clean(sourceId);
			} catch (IOException e) {
				e.printStackTrace();
			}

			profileImgStatusListener.uploadFinish(picUrls);
		}

		@Override
		protected void onFailure(UploadResultCallRet ret, UpParam p,
				Object passParam) {
			Utils.showToast(context, "fail!!!! reupload plz");
			if (ret.getException() != null) {
				ret.getException().printStackTrace();
			}
			// uploadStatusListener.uploadFinish(false);
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
	
	public interface ImageCropListener {

		public void onSucess(String fileUrl);

		public void onError();
	}

	@SuppressLint("NewApi")
	public Bitmap compressImage(Bitmap image) {
		
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	        int options = 100;  
	        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
	        while (baos.toByteArray().length / 1024 >  CommonConfig.UPLOAD_IMAGE_QUALITY) {   
	            baos.reset();  
	            options -= 10;  
	            image.compress(Bitmap.CompressFormat.JPEG, options, baos);  
	        }  
	        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());  
	        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);  
	        return bitmap;  
	}

	
	public void updateUserInfo(UserInfoDetail userInfo,
			final AsyncCallBacks.ZeroOne<String> callback) {
		Map<String, Object> params = Utils.buildMap("userInfo", userInfo);

		NetworkExecutor.post(UrlConstants.UPDATE_USERINFO, params, Response.class,
				new NetworkExecutor.CallBack<Response>() {
					@Override
					public void onSuccess(Response response) {
						callback.onSuccess();
					}

					@Override
					public void onError(Integer status, String msg) {
						callback.onError(msg);
					}

				});
	}
	
    public interface OnProfileImgStatusListener {
        void uploadFinish(String url);
    }

    public void setOnProfileImgStatusListener(
    		OnProfileImgStatusListener profileImgStatusListener) {
        this.profileImgStatusListener = profileImgStatusListener;
    }

}
