package com.zifei.corebeau.service;

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
import com.zifei.corebeau.extra.qiniu.QiniuTask;
import com.zifei.corebeau.extra.qiniu.up.UpParam;
import com.zifei.corebeau.extra.qiniu.up.UploadHandler;
import com.zifei.corebeau.extra.qiniu.up.rs.UploadResultCallRet;
import com.zifei.corebeau.extra.qiniu.up.slice.Block;
import com.zifei.corebeau.task.UploadTask;
import com.zifei.corebeau.utils.Utils;

public class ProfileImageService extends Service {

	private UploadTask uploadTask = new UploadTask(ProfileImageService.this);
	private QiniuTask qiniuTask = new QiniuTask(ProfileImageService.this);

	@Override
	public int onStartCommand(final Intent intent, int flags, final int startId) {

		final String stringPath = intent.getStringExtra("stringPath");
		final String token = intent.getStringExtra("token");
		
		return Service.START_STICKY;
	}


	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
