package com.zifei.corebeau.common.net;

public class Response {

    private int statusCode;
    
    private String msg;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
	public static final int PARAM_INVALID = -10;

    public static final int NOT_LOGIN = -20; // duplicate login

    public static final int DUPLICATE_REQUEST = -30;

    public static final int PARAM_PARSE_ERROR = -40;

    public static final int SUCCESS = 200;
    
    public final static int FAILED = -50;
    
    // updateNickname
    public static final int SUCCESS_FIRST_TIME = 210;  //更新成功，并且是第一次初始化
    
    public static final int PHONE_NAME_EXIST = -100;  //该昵称已经被使用
    
}
