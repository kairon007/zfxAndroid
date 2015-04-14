package com.zifei.corebeau.other.task;

import android.content.Context;

import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.PreferenceManager;
import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.common.net.UrlConstants;
import com.zifei.corebeau.common.task.NetworkExecutor;
import com.zifei.corebeau.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by im14s_000 on 2015/3/30.
 */
public class OtherTask {

    private Context context;

    public OtherTask(Context context) {
        this.context = context;
    }

    public void logout(final AsyncCallBacks.ZeroOne<String> callBack) {
        Map<String, Object> params = new HashMap<String, Object>();

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
//                callBack.onError(msg);

                callBack.onSuccess();
                deletePreferUserIdLoginId();
            }
        });
    }

    public void deletePreferUserIdLoginId() {
        PreferenceManager.getInstance(context).savePreferencesString("userId", "");
        PreferenceManager.getInstance(context).savePreferencesString("loginId", "");
    }
}
