package com.zifei.corebeau.bean.response;

import com.zifei.corebeau.bean.UserInfo;
import com.zifei.corebeau.common.net.Response;

public class LoginResponse extends Response {

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

    public static final int PASSWORD_INCORRECT = -200; //密码错误

    public static final int ACCOUNT_NOT_EXIST = -300; // 账户被冻结

    public static final int ACCOUNT_CHANGE_FREQUENTLY = -400;  //账户切换太频繁

    public static final int SUCCESS = 200; //登录成功
}
