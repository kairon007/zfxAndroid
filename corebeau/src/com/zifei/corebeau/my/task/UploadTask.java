package com.zifei.corebeau.my.task;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.codehaus.jackson.map.ObjectMapper;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.UrlConstants;
import com.zifei.corebeau.common.task.NetworkExecutor;
import com.zifei.corebeau.my.bean.TokenResponse;
import com.zifei.corebeau.my.qiniu.QiniuTask;
import com.zifei.corebeau.my.qiniu.up.UpParam;
import com.zifei.corebeau.my.qiniu.up.UploadHandler;
import com.zifei.corebeau.my.qiniu.up.rs.UploadResultCallRet;
import com.zifei.corebeau.my.qiniu.up.slice.Block;
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
							for (String stringPath : stringPathList) {
								// syncAppLog(stringPath,
								// response.getUploadToken());
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

	private static Executor appSyncExecutor = Executors
			.newSingleThreadExecutor();

	public void imageReset(final ArrayList<String> stringPath,
			final ImageCropListener listener) {
		appSyncExecutor.execute(new Runnable() {
			@Override
			public void run() {
				for (String p : stringPath) {
					uriToBitmap(Uri.fromFile(new File(p)), listener);
				}

			}
		});
	}

	public interface ImageCropListener {
		public void onSucess(String fileUrl);

		public void onError();
	}

	private void uriToBitmap(Uri uri, ImageCropListener listener) {
		ContentResolver cr = context.getContentResolver();
		String filePath = null;
		try {
			Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
			filePath = saveBitmapToJpegFile(compressImage(bitmap), tempJpeg);
			listener.onSucess(filePath);
		} catch (Exception e) {
			e.printStackTrace();
			listener.onError();
		}
	}

	private Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) {
			baos.reset();
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);
			options -= 10;
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
		return bitmap;
	}

	private static final String fileName = "temp.jpg";
	private static final String tempJpeg = Environment
			.getExternalStorageDirectory().getPath() + "/" + fileName;

	// private String tempFilePath(Uri uri){
	// // uri.g
	// }

	public String saveBitmapToJpegFile(Bitmap bitmap, String filePath) {
		try {
			FileOutputStream fileOutStr = new FileOutputStream(filePath);
			BufferedOutputStream bufOutStr = new BufferedOutputStream(
					fileOutStr);
			bufOutStr.flush();
			bufOutStr.close();

		} catch (Exception exception) {
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
