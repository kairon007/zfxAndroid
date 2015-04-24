package com.zifei.corebeau.account.task;

import java.util.Map;

import android.content.Context;

import com.zifei.corebeau.account.bean.UserInfo;
import com.zifei.corebeau.account.bean.response.CheckAccountResponse;
import com.zifei.corebeau.account.bean.response.CheckNicknameResponse;
import com.zifei.corebeau.account.bean.response.FindPasswordResponse;
import com.zifei.corebeau.account.bean.response.LoginByDeviceResponse;
import com.zifei.corebeau.account.bean.response.LoginResponse;
import com.zifei.corebeau.account.bean.response.RegisterResponse;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.CorebeauApp;
import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.common.net.UrlConstants;
import com.zifei.corebeau.common.task.NetworkExecutor;
import com.zifei.corebeau.utils.DeviceUtils;
import com.zifei.corebeau.utils.Utils;

public class AccountTask {

	private UserInfoService userInfoService;

	public AccountTask(Context context) {
		userInfoService = new UserInfoService(context);
	}
	
	public void checkAccount(final String account,
			final AsyncCallBacks.TwoOne<Integer, String, String> callback) {

		Map<String, Object> params = Utils.buildMap("account", account);

		NetworkExecutor.post(UrlConstants.CHECK_ACCOUNT, params, CheckAccountResponse.class,
				new NetworkExecutor.CallBack<CheckAccountResponse>() {
					@Override
					public void onSuccess(CheckAccountResponse response) {

						int status = response.getStatusCode();
						String msg = response.getMsg();

						if (status == CheckAccountResponse.ACCOUNT_NOT_EXIST) {
							callback.onSuccess(status, null);
						} else if (status == CheckAccountResponse.ACCOUNT_EXIST) {
							callback.onError(msg);
						} else {
							callback.onError(msg);
						}
					}

					@Override
					public void onError(Integer status, String msg) {

						callback.onError(msg);
					}

				});

	}
	
	public void checkNickname(final String nickname,
			final AsyncCallBacks.TwoOne<Integer, String, String> callback) {

		Map<String, Object> params = Utils.buildMap("nickname", nickname);

		NetworkExecutor.post(UrlConstants.CHECK_NICKNAME, params, CheckNicknameResponse.class,
				new NetworkExecutor.CallBack<CheckNicknameResponse>() {
					@Override
					public void onSuccess(CheckNicknameResponse response) {

						int status = response.getStatusCode();
						String msg = response.getMsg();

						if (status == CheckNicknameResponse.NICKNAME_NOT_EXIST) {

							callback.onSuccess(status, null);
						} else if (status == CheckNicknameResponse.NICKNAME_EXIST) {
							callback.onError(msg);
						} else {
							callback.onError(msg);
						}
					}

					@Override
					public void onError(Integer status, String msg) {

						callback.onError(msg);
					}
				});
	}


	public void login(final String account, final String password,
			final AsyncCallBacks.TwoOne<Integer, String, String> callback) {

		Map<String, Object> params = Utils.buildMap("account", account, "password",
				password, "imei", DeviceUtils.getDeviceId(CorebeauApp.app),
				"manufacturer", DeviceUtils.getManufacturer(),
				"appVersion", Utils.getVersionCode(),"androidId",
				DeviceUtils.getAndroidId(), "macAddress",
				DeviceUtils.getMacAddress());

		NetworkExecutor.post(UrlConstants.LOGIN, params, LoginResponse.class,
				new NetworkExecutor.CallBack<LoginResponse>() {
					@Override
					public void onSuccess(LoginResponse response) {

						int status = response.getStatusCode();
						String msg = response.getMsg();

						if (status == LoginResponse.SUCCESS) {

							// if(userInfo.isEmailVerfied()==true){
							//
							// }else{
							//
							// }

							UserInfo userInfo = new UserInfo();
							userInfo.setUserId(response.getUserInfo()
									.getUserId());
							userInfo.setLoginId(response.getLoginId());
							userInfoService.updateCurentUserInfo(userInfo);

							callback.onSuccess(status, null);
						} else if (status == LoginResponse.PASSWORD_INCORRECT) {
							callback.onError(msg);
						} else if (status == LoginResponse.ACCOUNT_NOT_EXIST) {
							callback.onError(msg);
						} else if (status == LoginResponse.FAILED) {
							callback.onError(msg);
						} else {
							callback.onError(msg);
						}
					}

					@Override
					public void onError(Integer status, String msg) {

						callback.onError(msg);
					}

				});

	}

	public void loginByDevice(
			final AsyncCallBacks.TwoTwo<Integer, String, Integer, String> callback) {

		Map<String, Object> params = Utils.buildMap("imei",
				DeviceUtils.getDeviceId(CorebeauApp.app), "androidId",
				DeviceUtils.getAndroidId(), "macAddress",
				DeviceUtils.getMacAddress());

		NetworkExecutor.post(UrlConstants.LOGIN_BY_DEVICE, params,
				LoginByDeviceResponse.class,
				new NetworkExecutor.CallBack<LoginByDeviceResponse>() {
					@Override
					public void onSuccess(LoginByDeviceResponse response) {

						int status = response.getStatusCode();
						String msg = response.getMsg();

						if (status == LoginByDeviceResponse.SUCCESS || status == LoginByDeviceResponse.ACCOUNT_EXIST) {

							UserInfo userInfo = new UserInfo();
							userInfo.setUserId(response.getUserInfo()
									.getUserId());
							userInfo.setLoginId(response.getLoginId());
							userInfoService.updateCurentUserInfo(userInfo);
							callback.onSuccess(status, msg);
							
						} else if (status == LoginByDeviceResponse.IMEI_DUPLICATE){
							callback.onError(status, msg);
//						} else if (status == LoginByDeviceResponse.   ) {  // distinguish go where??
//						} else if (status == LoginByDeviceResponse.   ) {
//						} else if (status == LoginByDeviceResponse.   ) {
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

	public void register(final String account, final String password,
			final String nickname,
			final AsyncCallBacks.ZeroTwo<Integer, String> callBack) {

		Map<String, Object> params = Utils.buildMap("account", account,
				"password", password, "imei",
				DeviceUtils.getDeviceId(CorebeauApp.app), "model",
				DeviceUtils.getModel(), "manufacturer",
				DeviceUtils.getManufacturer(), "osVersion",
				DeviceUtils.getSDKVersion(), "appVersion",
				Utils.getVersionCode(), "androidId",
				DeviceUtils.getAndroidId(), "macAddress",
				DeviceUtils.getMacAddress());

		NetworkExecutor.post(UrlConstants.REGIST, params,
				RegisterResponse.class,
				new NetworkExecutor.CallBack<RegisterResponse>() {
					@Override
					public void onSuccess(RegisterResponse response) {

						int status = response.getStatusCode();
						String msg = response.getMsg();

						if (status == RegisterResponse.SUCCESS) {

							UserInfo userInfo = new UserInfo();
							userInfo.setUserId(response.getUserInfo()
									.getUserId());
							userInfo.setLoginId(response.getLoginId());
							userInfoService.updateCurentUserInfo(userInfo);

							callBack.onSuccess();

						} else if (status == RegisterResponse.ACCOUNT_EXIST) {
							callBack.onError(status, msg);
						} else if (status == RegisterResponse.NICKNAME_EXIST) {
							callBack.onError(status, msg);
						} else {
							callBack.onError(status, msg);
						}
					}

					@Override
					public void onError(Integer status, String msg) {
						callBack.onError(status, msg);
					}

				});
	}

	public void findPassword(String email,
			final AsyncCallBacks.ZeroOne<String> callBack) {
		Map<String, Object> params = Utils.buildMap("email", email);

		NetworkExecutor.post(UrlConstants.FINDPASS, params,
				FindPasswordResponse.class,
				new NetworkExecutor.CallBack<FindPasswordResponse>() {
					@Override
					public void onSuccess(FindPasswordResponse response) {

						int status = response.getStatusCode();
						String msg = response.getMsg();

						if (status == Response.SUCCESS) {
							callBack.onSuccess();
						} else if (status == FindPasswordResponse.ACCOUNT_NOT_EXIST) {
							callBack.onError(msg);
						} else {
							callBack.onError(msg);
						}
					}

					@Override
					public void onError(Integer status, String msg) {
						callBack.onError(msg);
					}
				});
	}

}
