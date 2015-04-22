package com.zifei.corebeau.my.task;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.UrlConstants;
import com.zifei.corebeau.common.task.NetworkExecutor;
import com.zifei.corebeau.my.bean.response.TokenResponse;
import com.zifei.corebeau.my.qiniu.QiniuTask;
import com.zifei.corebeau.my.qiniu.up.UpParam;
import com.zifei.corebeau.my.qiniu.up.UploadHandler;
import com.zifei.corebeau.my.qiniu.up.rs.UploadResultCallRet;
import com.zifei.corebeau.my.qiniu.up.slice.Block;
import com.zifei.corebeau.utils.CommonConfig;
import com.zifei.corebeau.utils.Utils;

public class UploadTask {

	private OnUploadStatusListener uploadStatusListener;
	private Context context;
	private QiniuTask qiniuTask;
	private List<String> names = new ArrayList<String>();

	public UploadTask(Context context) {
		this.context = context;
		qiniuTask = new QiniuTask(context);
	}

	public void uploadImageQiniu() {
	}

	public void getToken(
			final ArrayList<String> stringPathList,
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
							for (String stringPath : stringPathList) {
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

//	public void imageReset(final ArrayList<String> stringPath,
//			final ImageCropListener listener) {
//		for (String p : stringPath) {
//			File file = new File(p);
//			uriToBitmap(Uri.fromFile(file), listener, file.getName());
//		}
//		// TODO enable submit
//	}

	public interface ImageCropListener {
		
		public void onSucess(String fileUrl);

		public void onError();
	}

//	private void uriToBitmap(Uri uri, ImageCropListener listener,
//			String fileName) {
//		ContentResolver cr = context.getContentResolver();
//		String filePath = null;
//		try {
//			Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
//			filePath = saveBitmapToJpegFile(compressImage(bitmap),
//					TEMP_ROOT_PATH + fileName);
//			listener.onSucess(filePath);
//		} catch (Exception e) {
//			e.printStackTrace();
//			listener.onError();
//		}
//	}

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

	private static final String TEMP_ROOT_PATH = Environment
			.getExternalStorageDirectory().getPath() + "/";

	// private String tempFilePath(Uri uri){
	// // uri.g
	// }

	public String saveBitmapToJpegFile(Bitmap bitmap, String filePath) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.PNG, 100, bos);
			byte[] bitmapdata = bos.toByteArray();

			// write the bytes in file
			FileOutputStream fos = new FileOutputStream(filePath);
			fos.write(bitmapdata);
			fos.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return filePath;
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

	public interface OnUploadStatusListener {
		void uploadFinish(boolean status);
	}

	public void setonTouchUpCallBackListener(
			OnUploadStatusListener uploadStatusListener) {
		this.uploadStatusListener = uploadStatusListener;
	}

	public OnUploadStatusListener getonTouchUpCallBackListener() {
		return uploadStatusListener;
	}
}
