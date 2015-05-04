package com.zifei.corebeau.my.task;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.UrlConstants;
import com.zifei.corebeau.common.task.NetworkExecutor;
import com.zifei.corebeau.my.bean.response.TokenResponse;
import com.zifei.corebeau.my.task.service.ProfileImageService;
import com.zifei.corebeau.my.task.service.UploadService;
import com.zifei.corebeau.utils.CommonConfig;
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

	public interface ImageCropListener {

		public void onSucess(String fileUrl);

		public void onError();
	}

	@SuppressLint("NewApi")
	public Bitmap compressImage(Bitmap image) {
//		return image;
		Log.i("image compressImage before", image.getByteCount() + " ");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//		if (baos.toByteArray().length / 1024 <= CommonConfig.UPLOAD_IMAGE_QUALITY) {
//			return null;
//		}
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
