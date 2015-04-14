package com.zifei.corebeau.account.task;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import android.content.Context;

import com.zifei.corebeau.account.bean.UserInfo;
import com.zifei.corebeau.utils.FileUtils;

public class UserInfoService {

	private static final ReadWriteLock userInfoLock = new ReentrantReadWriteLock();
	
	private static final Lock userInfoReadLock = userInfoLock.readLock();
	
	private static final Lock userInfoWriteLock = userInfoLock.writeLock();
	
	private static volatile UserInfo userInfo = null;
	
	private static volatile boolean userInfoLoaded = false;
	
	private static final String USER_INFO_FILE = ".uis.uif";

	public UserInfoService(Context context) {
	}
	
	public UserInfo getCurentUserInfo() {
		userInfoReadLock.lock();
		try {
			if (userInfo == null && userInfoLoaded == false) {
				synchronized (userInfoLock) {
					if (userInfo == null && userInfoLoaded == false) {
						userInfo = FileUtils.readJSON(USER_INFO_FILE, true, UserInfo.class);
						userInfoLoaded = true;
					}
				}
			}
		} finally {
			userInfoReadLock.unlock();
		}
		return userInfo;
	}
	
	public void updateCurentUserInfo(UserInfo userInfo) {
		userInfoWriteLock.lock();
		try {
			if (userInfo != null) {
				UserInfoService.userInfo = userInfo;
				FileUtils.storeJSON(USER_INFO_FILE, userInfo, true);
			}
		} finally {
			userInfoWriteLock.unlock();
		}
	}
	
	public String getLoginId() {
		UserInfo userInfo = getCurentUserInfo();
		if (userInfo != null) {
			return userInfo.getLoginId();
		}
		return null;
	}
	
	public String getUserId() {
		UserInfo userInfo = getCurentUserInfo();
		if (userInfo != null) {
			return userInfo.getUserId();
		}
		return null;
	}
}
