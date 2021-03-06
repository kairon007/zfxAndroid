package com.zifei.corebeau.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.zifei.corebeau.bean.ConfigInfo;
import com.zifei.corebeau.task.ConfigTask;
import com.zifei.corebeau.task.UserInfoService;

/**
 * Created by im14s_000 on 2015/3/23.
 */
public class CorebeauApp extends Application {

	public static CorebeauApp app;
	private LocationManager locationManager;
	private Location currentLocation;
	private Map<String, Integer> appNotifications;
	private ArrayList<Activity> activities;
	private Map<String, Long> enterAppTimeMap;
	private static Map<String, String> config;

	@Override
	public void onCreate() {
		super.onCreate();
		app = this;
		activities = new ArrayList<Activity>();
		appNotifications = new HashMap<String, Integer>();

		initLocation();
		enterAppTimeMap = new HashMap<String, Long>();
		initImageLoader(getApplicationContext());
		loadInitData();
		initObservers();
	}

	private void loadInitData() {
		new Thread() {
			@Override
			public void run() {
				UserInfoService.getCurentUserInfo();
			}
		};
	}

	public void setConfigData(Map<String, String> cfg){
		config = cfg;
	}
	
	public static String getImageBaseUrl() {
		return config!=null ? config.get(ConfigInfo.IMAGE_BASE_URL) : "";
	}

	public static String getBigImageConfig() {
		return config!=null ? config.get(ConfigInfo.BIG_PIC_STYLE_URL) : "";
	}

	public static String getMiddleImageConfig() {
		return config!=null ? config.get(ConfigInfo.MID_PIC_STYLE_URL) : "";
	}

	public static String getSmallImageConfig() {
		return config!=null ? config.get(ConfigInfo.SMALL_PIC_STYLE_URL) : "";
	}

	private void initObservers() {
		// ObserverCenter.register(AccountService.LOGIN_EVENT, new
		// EnbleLockScreenObserver());
		// ObserverCenter.register(AccountService.LOGIN_EVENT, new
		// UpdateBaiduIdObserver(this.getApplicationContext()));
	}

	private void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).threadPriority(Thread.MAX_PRIORITY)
				.memoryCache(new WeakMemoryCache())
				.denyCacheImageMultipleSizesInMemory().threadPoolSize(5)
				.tasksProcessingOrder(QueueProcessingType.LIFO)// .enableLogging()
				.build();

		ImageLoader.getInstance().init(config);
	}

	private void initLocation() {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 10 * 60 * 1000, 1000,
					locationListener);
			currentLocation = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
	}

	public Location getLocation() {

		return currentLocation;
	}

	private LocationListener locationListener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			currentLocation = location;
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onProviderDisabled(String provider) {
		}
	};

	public void add(Activity activity) {
		activities.add(activity);
	}

	public void remove(Activity activity) {
		activities.remove(activity);
	}

}
