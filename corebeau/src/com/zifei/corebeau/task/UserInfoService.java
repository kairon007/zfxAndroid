package com.zifei.corebeau.task;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import android.content.Context;

import com.zifei.corebeau.bean.UserInfo;
import com.zifei.corebeau.utils.FileUtils;

public class UserInfoService {

	private static final ReadWriteLock userInfoLock = new ReentrantReadWriteLock();
	
	private static final Lock userInfoReadLock = userInfoLock.readLock();
	
	private static final Lock userInfoWriteLock = userInfoLock.writeLock();
	
	private static volatile UserInfo userInfo = null;
	
	private static final String USER_INFO_FILE = ".uis.uif";

	public UserInfoService(Context context) {
	}
	
	public static UserInfo getCurentUserInfo() {
		userInfoReadLock.lock();
		try {
			if (userInfo == null ) {
				synchronized (userInfoLock) {
					if (userInfo == null ) {
						userInfo = FileUtils.readJSON(USER_INFO_FILE, true, UserInfo.class);
					}
				}
			}
		} finally {
			userInfoReadLock.unlock();
		}
		return userInfo;
	}
	
	public static void updateCurentUserInfo(UserInfo userInfo) {
		userInfoWriteLock.lock();
		try {
				UserInfoService.userInfo = userInfo;
				FileUtils.storeJSON(USER_INFO_FILE, userInfo, true);
		} finally {
			userInfoWriteLock.unlock();
		}
	}
	
	public static String getLoginId() {
		UserInfo userInfo = getCurentUserInfo();
		if (userInfo != null) {
			return userInfo.getLoginId();
		}
		return null;
	}
	
	public static String getUserId() {
		UserInfo userInfo = getCurentUserInfo();
		if (userInfo != null) {
			return userInfo.getUserId();
		}
		return null;
	}
}
