package com.zifei.corebeau.account.task;

import android.content.Context;

import com.zifei.corebeau.account.bean.UserInfo;
import com.zifei.corebeau.account.bean.response.LoginResponse;
import com.zifei.corebeau.account.bean.response.RegisterResponse;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.PreferenceManager;
import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.common.net.UrlConstants;
import com.zifei.corebeau.common.task.NetworkExecutor;
import com.zifei.corebeau.utils.DeviceUtils;
import com.zifei.corebeau.utils.Utils;

import java.util.Map;

/**
 * Created by im14s_000 on 2015/3/23.
 */
public class AccountTask {

    private Context context;

    public AccountTask(Context context) {
        this.context = context;
    }

    public void login(final String email, final String password, final AsyncCallBacks.TwoOne<Integer, String, String> callback) {

        Map<String, Object> params = Utils.buildMap("email", email, "password", password, "osVersion", DeviceUtils.getSDKVersion(),
                "appVersion", Utils.getVersionCode());

        NetworkExecutor.post(UrlConstants.LOGIN, params, LoginResponse.class, new NetworkExecutor.CallBack<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse response) {
                PreferenceManager preferenceManager = PreferenceManager.getInstance(context);

                int status = response.getStatusCode();
                String msg = response.getMsg();

                if (status == LoginResponse.SUCCESS) {

                    UserInfo userInfo = response.getUserInfo();

                    if(userInfo.isEmailVerfied()==true){

                    }else{

                    }
                    savePreferUserIdLoginId(response.getUserInfo().getUserId(), response.getLoginId());
//                    AppEnvironment.setToken(response.getLoginId());
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

//                callback.onError(msg);
                callback.onSuccess(status, null);
                savePreferUserIdLoginId(email, password);
            }

        });

    }

    public void register(final String phone, final String password, final String nickname, final AsyncCallBacks.ZeroOne<String> callBack) {

        Map<String, Object> params = Utils.buildMap("phone", phone, "password", password);

        NetworkExecutor.post(UrlConstants.REGIST, params, RegisterResponse.class, new NetworkExecutor.CallBack<RegisterResponse>() {
            @Override
            public void onSuccess(RegisterResponse response) {

                int status = response.getStatusCode();
                String msg = response.getMsg();
                UserInfo userInfo = response.getUserInfo();

                if (status == RegisterResponse.SUCCESS) {

                    savePreferUserIdLoginId(userInfo.getUserId(), response.getLoginId());

//                    HuaqianApplication.app.insertUserInfo(userInfo);

                    callBack.onSuccess();

                } else if (status == RegisterResponse.EMAIL_EXIST) {
                    callBack.onError(msg);
                } else if (status == RegisterResponse.NICKNAME_EXIST) {
                    callBack.onError(msg);
                } else if (status == RegisterResponse.FAILED) {
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

    public void findPassword(String email,final AsyncCallBacks.ZeroOne<String> callBack) {
        Map<String, Object> params = Utils.buildMap( "email", email);

        NetworkExecutor.post(UrlConstants.FINDPASS, params, Response.class, new NetworkExecutor.CallBack<Response>() {
            @Override
            public void onSuccess(Response response) {

                int status = response.getStatusCode();
                String msg = response.getMsg();

                if (status == Response.SUCCESS) {
                    callBack.onSuccess();
                } else if (status == Response.FAILED) {
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

    private void savePreferUserIdLoginId(String userId, String loginId){
        PreferenceManager.getInstance(context).savePreferencesString("userId",userId);
        PreferenceManager.getInstance(context).savePreferencesString("loginId",loginId);
    }

    public void deletePreferUserIdLoginId(){
        PreferenceManager.getInstance(context).savePreferencesString("userId","");
        PreferenceManager.getInstance(context).savePreferencesString("loginId","");
    }

}
