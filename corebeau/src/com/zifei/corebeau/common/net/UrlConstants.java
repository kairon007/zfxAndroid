package com.zifei.corebeau.common.net;

public class UrlConstants {

	 public static final String BASE_URL =
	 "http://115.28.175.29:8080/frontend";

//	public static final String BASE_URL = "http://192.168.91.103:8080/frontend";

	// config
	
	public static String GET_CONFIG = BASE_URL + "/config/fetch";

	// account

	public static String CHECK_ACCOUNT = BASE_URL + "/login/checkaccoint";

	public static String CHECK_NICKNAME = BASE_URL + "/login/checknickname";

	public static String LOGIN = BASE_URL + "/login/account";

	public static String LOGIN_BY_DEVICE = BASE_URL + "/login/device";

	public static String LOGOUT = BASE_URL + "/logout";

	public static String REGIST = BASE_URL + "/register";

	public static String FINDPASS = BASE_URL + "/findpass";

	// spot

	public static String GET_SPOT_LIST = BASE_URL + "/spot_post";

	// search

	public static String GET_SEARCH_RECOMMEND_USER = BASE_URL + "/spot_post";

	public static String GET_SEARCH_RECOMMEND_POST = BASE_URL
			+ "/post/getLatestItemInfo";
	
	public static String GET_SEARCH_SORT_BY_LIKE_CNT_POST = BASE_URL
			+ "/post/getSortByLikeCnt";
	
	public static String GET_SEARCH_SORT_BY_COMMENT_CNT_POST = BASE_URL
			+ "/post/getSortByCommentCnt";
	

	public static String SEARCH = BASE_URL + "/spot_post";

	// item

	public static String GET_UPLOAD_TOKEN = BASE_URL + "/post/getUploadToken";

	public static String UPLOAD_ITEM = BASE_URL + "/post/uploadItem";

	public static String GET_ITEM_DETAIL = BASE_URL + "/post/getItemDetails";

	public static String DELETE_ITEM = BASE_URL + "/post/deleteUploadItem";

	

	public static String LIKE = BASE_URL + "/post/doLikeItem";

	public static String LIKE_DEL = BASE_URL + "/post/disLikeItem";

	public static String GET_SCRAP_LIST = BASE_URL
			+ "/post/getUserScrapItemInfo";

	public static String ADD_SCRAP = BASE_URL + "/post/addFollowItem";

	public static String CANCEL_SCRAP = BASE_URL + "/post/deleteFollowItem";

	// my

	public static String GET_MY_POST = BASE_URL + "/post/getItemInfoByUserId";

	public static String UPLOAD_MY_ICON_IMAGE = BASE_URL + "/.../...";
	
	public static String MODIFY_NICKNAME = BASE_URL + "/.../...";
	
	// my info

	public static String UPDATE_USERINFO = BASE_URL + "/account/updateUserInfo";
	
	public static String BOUND_ACCOUNT = BASE_URL + "/account/bindAccount";
	
	public static String UPDATE_PASSWORD = BASE_URL + "/account/...";

	// comment

	public static String GET_COMMENT = BASE_URL
			+ "/post/getItemCommentsByItemId";

	public static String INSERT_COMMENT = BASE_URL + "/post/saveComment";

	public static String DELETE_COMMENT = BASE_URL + "/post/deleteComment";

	// follow

	public static String GET_FOLLOW_LIST = BASE_URL
			+ "/post/getUserFollowUserInfoList";

	public static String FOLLOW_ADD = BASE_URL + "/post/addFollowUser";

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

	public static String DELETE_ACCOUNT = BASE_URL + "get_user_page";

	public static String CHECK_VERSION = BASE_URL + "get_user_page";
	
	//otherUser
	
	public static String GET_OTHER_USERINFO = BASE_URL + "/account/getUserShowInfo";

}
