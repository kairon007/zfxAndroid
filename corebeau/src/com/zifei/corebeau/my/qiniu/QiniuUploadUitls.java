package com.zifei.corebeau.my.qiniu;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Random;

import javax.crypto.Mac;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.util.Log;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

/**
 * @date 2014年12月26日 上午11:00:43
 * @author Zheng Haibo
 * @web http://www.mobctrl.net
 * @Description: 七牛云上传图片
 */
public class QiniuUploadUitls {

	private static final String fileName = "temp.jpg";

	private static final String tempJpeg = Environment.getExternalStorageDirectory().getPath() + "/" + fileName;

	private int maxWidth = 720;
	private int maxHeight = 1080;

	public interface QiniuUploadUitlsListener {
		public void onSucess(String fileUrl);
		public void onError(int errorCode, String msg);
		public void onProgress(int progress);
	}

	private QiniuUploadUitls() {

	}

	private static QiniuUploadUitls qiniuUploadUitls = null;

	public static QiniuUploadUitls getInstance() {
		if (qiniuUploadUitls == null) {
			qiniuUploadUitls = new QiniuUploadUitls();
		}
		return qiniuUploadUitls;
	}

	public boolean saveBitmapToJpegFile(Bitmap bitmap, String filePath) {
		return saveBitmapToJpegFile(bitmap, filePath, 75);
	}

	public boolean saveBitmapToJpegFile(Bitmap bitmap, String filePath, int quality) {
		try {
			FileOutputStream fileOutStr = new FileOutputStream(filePath);
			BufferedOutputStream bufOutStr = new BufferedOutputStream(fileOutStr);
			resizeBitmap(bitmap).compress(CompressFormat.JPEG, quality, bufOutStr);
			bufOutStr.flush();
			bufOutStr.close();
		} catch (Exception exception) {
			return false;
		}
		return true;
	}

	/**
	 * 缩小图片
	 * 
	 * @param bitmap
	 * @return
	 */
	public Bitmap resizeBitmap(Bitmap bitmap) {
		if (bitmap != null) {
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			if (width > maxWidth) {
				int pWidth = maxWidth;
				int pHeight = maxWidth * height / width;
				Bitmap result = Bitmap.createScaledBitmap(bitmap, pWidth, pHeight, false);
				bitmap.recycle();
				return result;
			}
			if (height > maxHeight) {
				int pHeight = maxHeight;
				int pWidth = maxHeight * width / height;
				Bitmap result = Bitmap.createScaledBitmap(bitmap, pWidth, pHeight, false);
				bitmap.recycle();
				return result;
			}
		}
		return bitmap;
	}

	public void uploadImage(Bitmap bitmap, QiniuUploadUitlsListener listener) {
		saveBitmapToJpegFile(bitmap, tempJpeg);
//		uploadImage(tempJpeg, listener);
	}

//	public void uploadImage(String filePath, final QiniuUploadUitlsListener listener) {
//		final String fileUrlUUID = getFileUrlUUID();
//		String token = getToken();
//		if (token == null) {
//			if (listener != null) {
//				listener.onError(-1, "token is null");
//			}
//			return;
//		}
//		uploadManager.put(filePath, fileUrlUUID, token, new UpCompletionHandler() {
//			public void complete(String key, ResponseInfo info, JSONObject response) {
//				System.out.println("debug:info = " + info + ",response = " + response);
//				
//				if (info != null && info.statusCode == 200) {// 上传成功
//					String fileRealUrl = getRealUrl(fileUrlUUID);
//					System.out.println("debug:fileRealUrl = " + fileRealUrl);
//					Log.i("","fileRealUrl!! : "+fileRealUrl);
//					if (listener != null) {
//						
//						listener.onSucess(fileRealUrl);
//					}
//				} else {
//					if (listener != null) {
//						listener.onError(info.statusCode, info.error);
//					}
//				}
//			}
//		}, new UploadOptions(null, null, false, new UpProgressHandler() {
//			public void progress(String key, double percent) {
//				if (listener != null) {
//					listener.onProgress((int) (percent * 100));
//				}
//			}
//		}, null));
//
//	}


}
