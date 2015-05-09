package com.zifei.corebeau;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.zifei.corebeau.utils.CommonConfig;
import com.zifei.corebeau.utils.PictureUtil;
import com.zifei.corebeau.utils.Utils;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

public class TestService extends Service {
	
	@Override
	public int onStartCommand(final Intent intent, int flags, final int startId) {
		
		Utils.showToast(TestService.this, "start!!");
		
		return Service.START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Utils.showToast(TestService.this, "killed!!");
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
