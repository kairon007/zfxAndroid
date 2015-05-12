package com.zifei.corebeau.ui.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;
import com.zifei.corebeau.R;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.PreferenceManager;
import com.zifei.corebeau.database.RegionDBHelper;
import com.zifei.corebeau.task.AccountTask;
import com.zifei.corebeau.task.ConfigTask;
import com.zifei.corebeau.task.UserInfoService;
import com.zifei.corebeau.utils.StringUtil;

public class NewSplashActivity extends Activity {
	
	private final int DELAY_MILLIS = 2000;
	private AccountTask accountTask;
	
	private Runnable run = new Runnable() {
		@Override
		public void run() {
			Intent intent = null;
//			boolean ifViewNavigation = !PreferenceManager.getInstance(NewSplashActivity.this).getPreferencesBoolean("first_enter",
//					true);
//			if (!StringUtil.isEmpty(UserInfoService.getLoginId())) {
//				if (ifViewNavigation) {
//					intent = new Intent(NewSplashActivity.this, MainActivity.class);
//				} else {
//					intent = new Intent(NewSplashActivity.this, IntroActivity.class);
//				}
//			} else if (!StringUtil.isEmpty(UserInfoService.getUserId())) {
//				if (ifViewNavigation) {
					intent = new Intent(NewSplashActivity.this, LoginActivity.class);
//				} else {
//					intent = new Intent(NewSplashActivity.this, IntroActivity.class);
//				}
//			}
//			if (intent != null) {
				startActivity(intent);
				finish();
//			} else {
//				loginByDevice(ifViewNavigation);
//			}

		}
	};
	
	final Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_new);
		
		init();
		
		new ConfigTask(this).getConfig();
		
		if (PreferenceManager.getInstance(this).getPreferencesBoolean("region_db_state", true)) {
			initRegionDatabase();
		}

		if (UserInfoService.getLoginId() != null || UserInfoService.getUserId() != null) {
			handler.postDelayed(run, DELAY_MILLIS);
		} else {
			handler.post(run);
		}
	}

	protected void init() {
		accountTask = new AccountTask(this);
	}

	private void initRegionDatabase() {
		try {
			RegionDBHelper.init(this);
			PreferenceManager.getInstance(this).savePreferencesBoolean("region_db_state", true);
		} catch (IOException e) {
			e.printStackTrace();
			PreferenceManager.getInstance(this).savePreferencesBoolean("region_db_state", false);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		handler.removeCallbacks(run);
	}

	private void loginByDevice(final boolean ifViewNavigation) {
		accountTask.loginByDevice(new AsyncCallBacks.TwoTwo<Integer, String, Integer, String>() {
			@Override
			public void onSuccess(Integer status, String msg) {
					Intent intent;
					if (ifViewNavigation) {
						intent = new Intent(NewSplashActivity.this, MainActivity.class);
					} else {
						intent = new Intent(NewSplashActivity.this, IntroActivity.class);
					}
					startActivity(intent);
					finish();
			}

			@Override
			public void onError(Integer status, String msg) {
				Log.i("","plz check network status");
				finish();
			}
		});
	}

	public void setLauncherMap() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
		MobclickAgent.onEvent(NewSplashActivity.this, "launcher", map);
	}

	public boolean hasNavigationBar() {
		return false;
	}

}
