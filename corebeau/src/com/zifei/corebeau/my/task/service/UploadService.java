package com.zifei.corebeau.my.task.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;

import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.CorebeauApp;
import com.zifei.corebeau.my.qiniu.QiniuTask;
import com.zifei.corebeau.my.qiniu.up.UpParam;
import com.zifei.corebeau.my.qiniu.up.UploadHandler;
import com.zifei.corebeau.my.qiniu.up.rs.UploadResultCallRet;
import com.zifei.corebeau.my.qiniu.up.slice.Block;
import com.zifei.corebeau.my.task.UploadTask;
import com.zifei.corebeau.utils.Utils;

public class UploadService extends Service{
	
	private UploadTask uploadTask = new UploadTask(UploadService.this);
	private QiniuTask qiniuTask = new QiniuTask(UploadService.this);
	private List<String> picUrls = new ArrayList<String>();
	private String message;
	
	@Override
	public int onStartCommand(final Intent intent, int flags, final int startId) {

		ArrayList<String> stringPathList = intent.getStringArrayListExtra("stringPathList");
		final String token = intent.getStringExtra("token");
		message = intent.getStringExtra("message");
		ContentResolver cr = this.getContentResolver();
		for (String stringPath : stringPathList) {
			Bitmap bitmap;
			try {
				BitmapFactory.Options option = new BitmapFactory.Options();
				option.inSampleSize = UploadTask.getImageScale(stringPath);
				bitmap = BitmapFactory.decodeStream(cr
						.openInputStream(Uri
								.fromFile(new File(
										stringPath))),null,option);
				Uri uri = Uri.parse(MediaStore.Images.Media
						.insertImage(cr,
								uploadTask.compressImage(bitmap),
								null, null));
				qiniuTask.preUpload(uri, uploadHandler,
						token);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				stopSelf();
			}
		}
		qiniuTask.doUpload();
		return Service.START_STICKY;
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
				picUrls.add((String) readValue.get("key"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				String sourceId = qiniuTask.generateSourceId(p, passParam);
				qiniuTask.clean(sourceId);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (picUrls.size() == qiniuTask.getLength()) {
//				uploadStatusListener.uploadFinish(true);
				upload();
			}
		}

		@Override
		protected void onFailure(UploadResultCallRet ret, UpParam p,
				Object passParam) {
			Utils.showToast(UploadService.this, "fail!!!! reupload plz");
			if (ret.getException() != null) {
				ret.getException().printStackTrace();
			}
//			uploadStatusListener.uploadFinish(false);
			stopSelf();
		}

		@Override
		protected void onBlockSuccess(List<Block> uploadedBlocks, Block block,
				UpParam p, Object passParam) {
			Utils.showToast(UploadService.this, "block success!!");
			try {
				String sourceId = qiniuTask.generateSourceId(p, passParam);
				qiniuTask.addBlock(sourceId, block);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	private void upload(){
		uploadTask.upload(message, picUrls, new AsyncCallBacks.ZeroOne<String>() {

			@Override
			public void onSuccess() {
				Utils.showToast(CorebeauApp.app, "upload success!!");
				stopSelf();
			}

			@Override
			public void onError(String msg) {
				Utils.showToast(CorebeauApp.app, "upload failed");
				stopSelf();
			}
		});
	}
	
}
