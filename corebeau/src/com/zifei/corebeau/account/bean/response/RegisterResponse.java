package com.zifei.corebeau.account.bean.response;

import com.zifei.corebeau.account.bean.UserInfo;
import com.zifei.corebeau.common.net.Response;

/**
 * Created by im14s_000 on 2015/3/24.
 */
public class RegisterResponse extends Response{

    private UserInfo userInfo;

    private String loginId;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public static final int ACCOUNT_EXIST = -100;  //手机号码已经存在
    
    public static final int NICKNAME_EXIST = -200;

}
