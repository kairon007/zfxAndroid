package com.zifei.corebeau.common.net;

public class UrlConstants {
	 
//	 public static final String BASE_URL = "http://api.huaqianapp.com";
	 
	 public static final String BASE_URL = "http://192.168.91.104:8080/frontend";

    //account
	 
	 public static String LOGIN = BASE_URL + "/login/account";
	 
	 public static String LOGIN_BY_DEVICE = BASE_URL + "/login/device";
	 
	 public static String LOGOUT = BASE_URL + "/logout";

    public static String REGIST = BASE_URL + "/register";

    public static String FINDPASS = BASE_URL + "/findpass";

    // spot

    public static String GET_SPOT_LIST = BASE_URL + "/spot_post";

    // search

    public static String GET_SEARCH_RECOMMEND_USER = BASE_URL + "/spot_post";

    public static String GET_SEARCH_POST = BASE_URL + "/spot_post";

    public static String SEARCH = BASE_URL + "/spot_post";

    //post
    
    public static String GET_QINIU_TOKEN = "";

    public static String GET_POST = BASE_URL + "/get_post";

    public static String DELETE_POST = BASE_URL + "/delete_post";
    
    public static String INSERT_POST = BASE_URL + "modify";

    public static String MODIFY_POST = BASE_URL + "modify";

    public static String LIKE = BASE_URL + "/like";

    public static String LIKE_DEL = BASE_URL + "/comment";

    // comment

    public static String GET_COMMENT = BASE_URL + "/comment";

    public static String INSERT_COMMENT = BASE_URL + "/comment";

    public static String DELETE_COMMENT = BASE_URL + "/comment";

    // follow

    public static String GET_FOLLOW = BASE_URL + "/comment";

    public static String FOLLOW = BASE_URL + "/comment";

    public static String FOLLOW_DEL = BASE_URL + "/comment";

    public static String FOLLOW_SET = BASE_URL + "/comment";

    public static String FOLLOW_UNSET = BASE_URL + "/comment";

    // user

    public static String GET_USER_PAGE = BASE_URL + "get_user_page";

    public static String GET_MAIL = BASE_URL + "get_user_page";

    public static String SEND_MAIL = BASE_URL + "get_user_page";

    public static String DELETE_MAIL = BASE_URL + "get_user_page";

    // setting

    public static String MODIFY_PASSWORD = BASE_URL + "get_user_page";

    public static String MODIFY_NICKNAME = BASE_URL + "get_user_page";

    public static String DELETE_ACCOUNT = BASE_URL + "get_user_page";

    public static String CHECK_VERSION = BASE_URL + "get_user_page";


}
