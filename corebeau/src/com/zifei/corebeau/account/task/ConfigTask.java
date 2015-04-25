package com.zifei.corebeau.account.task;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import android.content.Context;

import com.zifei.corebeau.account.bean.ConfigInfo;
import com.zifei.corebeau.account.bean.response.ConfigResponse;
import com.zifei.corebeau.common.net.UrlConstants;
import com.zifei.corebeau.common.task.NetworkExecutor;
import com.zifei.corebeau.utils.FileUtils;
import com.zifei.corebeau.utils.Utils;

public class ConfigTask {

	public ConfigTask(Context context) {

	}

	public void getConfig() {

		String version = "-1";
		Map<String, String> currentConfig = getCurrentConfig();
		if (currentConfig != null) {
			version = getCurrentConfig().get(ConfigInfo.SYS_CONF_VERSION);
		}

		Map<String, Object> params = Utils.buildMap("version", version);

		NetworkExecutor.post(UrlConstants.GET_CONFIG, params,
				ConfigResponse.class,
				new NetworkExecutor.CallBack<ConfigResponse>() {
					@Override
					public void onSuccess(ConfigResponse response) {

						int status = response.getStatusCode();
						String msg = response.getMsg();

						if (status == ConfigResponse.UPDATE) {
							updateCurrentConfig(response.getConfMap());
						} else {

						}
					}

					@Override
					public void onError(Integer status, String msg) {

					}

				});

	}

	private static final ReadWriteLock userInfoLock = new ReentrantReadWriteLock();

	private static final Lock userInfoReadLock = userInfoLock.readLock();

	private static final Lock userInfoWriteLock = userInfoLock.writeLock();

	private static volatile Map<String, String> configInfo = null;

	private static volatile boolean userInfoLoaded = false;

	private static final String CONFIG_INFO_FILE = ".afeg.tjrj.wwee";

	@SuppressWarnings("unchecked")
	public Map<String, String> getCurrentConfig() {
		userInfoReadLock.lock();
		try {
			if (configInfo == null && userInfoLoaded == false) {
				synchronized (userInfoLock) {
					if (configInfo == null && userInfoLoaded == false) {
						configInfo = FileUtils.readJSON(CONFIG_INFO_FILE, true,
								Map.class);
						userInfoLoaded = true;
					}
				}
			}
		} finally {
			userInfoReadLock.unlock();
		}
		return configInfo;
	}

	public void updateCurrentConfig(Map<String, String> configInfo) {
		userInfoWriteLock.lock();
		try {
			if (configInfo != null) {
				ConfigTask.configInfo = configInfo;
				FileUtils.storeJSON(CONFIG_INFO_FILE, configInfo, true);
			}
		} finally {
			userInfoWriteLock.unlock();
		}

		reloadLocalConfFile();
	}

	@SuppressWarnings("unchecked")
	public void reloadLocalConfFile() {
		userInfoReadLock.lock();
		try {
			synchronized (userInfoLock) {
				configInfo = FileUtils.readJSON(CONFIG_INFO_FILE, true,
						Map.class);
				userInfoLoaded = true;
			}
		} finally {
			userInfoReadLock.unlock();
		}
	}

}
