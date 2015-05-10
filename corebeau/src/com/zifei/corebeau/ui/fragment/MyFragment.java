package com.zifei.corebeau.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.OtherUserInfo;
import com.zifei.corebeau.bean.UserInfo;
import com.zifei.corebeau.bean.UserShowInfo;
import com.zifei.corebeau.bean.response.OtherUserInfoResponse;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.extra.CircularImageView;
import com.zifei.corebeau.task.OtherUserTask;
import com.zifei.corebeau.task.UserInfoService;
import com.zifei.corebeau.ui.activity.FollowActivity;
import com.zifei.corebeau.ui.activity.MyInfoActivity;
import com.zifei.corebeau.ui.activity.MyPageActivity;
import com.zifei.corebeau.ui.activity.OptionActivity;
import com.zifei.corebeau.ui.activity.OtherUserActivity;
import com.zifei.corebeau.utils.StringUtil;
import com.zifei.corebeau.utils.Utils;

public class MyFragment extends Fragment implements View.OnClickListener {

	private RelativeLayout rlFollow, rlProfile, rlSetting, rlLogout;
	private CircularImageView iconImg;
	private TextView followCnt, likeCnt, itemCnt, nickName;
	private UserInfoService userInfoService;
	private DisplayImageOptions iconImageOptions;
	private ImageLoader imageLoader;
	private String targetUserId;
	
	public static MyFragment newInstance(String param1, String param2) {
		MyFragment fragment = new MyFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public MyFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my, container, false);
		rlFollow = (RelativeLayout) view.findViewById(R.id.rl_my_follow);
		rlProfile = (RelativeLayout) view.findViewById(R.id.rl_my_profile);
		rlSetting = (RelativeLayout) view.findViewById(R.id.rl_my_setting);
		rlLogout = (RelativeLayout) view.findViewById(R.id.rl_my_logout);
		iconImg = (CircularImageView) view.findViewById(R.id.my_icon);
		followCnt = (TextView) view.findViewById(R.id.tv_my_follow_cnt);
		likeCnt = (TextView) view.findViewById(R.id.tv_my_like_cnt);
		itemCnt = (TextView) view.findViewById(R.id.tv_my_item_cnt);
		nickName = (TextView) view.findViewById(R.id.my_nickname);
		iconImg.setOnClickListener(this);
		rlFollow.setOnClickListener(this);
		rlProfile.setOnClickListener(this);
		rlSetting.setOnClickListener(this);
		rlLogout.setOnClickListener(this);
		setLoader();
		setUserInfo();
		getUserShowInfo();
		return view;
	}
	
	private void setLoader(){
		imageLoader = ImageLoader.getInstance();
		iconImageOptions = new DisplayImageOptions.Builder()
		.cacheInMemory(true)
		.showImageOnFail(R.drawable.user_icon_default)
		.showImageForEmptyUri(R.drawable.user_icon_default)
		.showImageOnLoading(R.drawable.user_icon_default)
		.build();
	}
	
	private void setUserInfo(){
		userInfoService = new UserInfoService(getActivity());
		UserInfo userInfo = userInfoService.getCurentUserInfo();
		targetUserId = userInfo.getUserId();
		String nickNameString = userInfo.getNickName();
		String iconUrl = userInfo.getUrl();
		
		if(nickNameString!=null){
			nickName.setText(String.valueOf(nickNameString));
		}else{
			nickName.setText(String.valueOf("guest"));
		}
		
		if (!StringUtil.isEmpty(iconUrl)) {
			imageLoader.displayImage(iconUrl, iconImg, iconImageOptions);
		} else {
			imageLoader.displayImage("drawable://" + R.drawable.user_icon_default, iconImg, iconImageOptions);
		}
		
	}
	
	private void getUserShowInfo(){
		new OtherUserTask(getActivity()).getOtherUserInfo(targetUserId, new AsyncCallBacks.TwoOne<Integer, OtherUserInfoResponse, String>() {

			@Override
			public void onSuccess(Integer status, OtherUserInfoResponse response) {
				UserShowInfo userShowInfo = response.getUserShowInfo();
				
				followCnt.setText(String.valueOf(userShowInfo.getFollowedCount()));
//				likeCnt.setText(String.valueOf(userShowInfo.l));
				itemCnt.setText(String.valueOf(userShowInfo.getItemCount()));
			}

			@Override
			public void onError(String msg) {
				Utils.showToast(getActivity(), msg);
			}

		});
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_icon:
			Intent intent1 = new Intent(getActivity(), MyPageActivity.class);
			startActivity(intent1);
			break;
		case R.id.rl_my_setting:
			Intent intent2 = new Intent(getActivity(), OptionActivity.class);
			startActivity(intent2);
			break;
		case R.id.rl_my_follow:
			Intent intent4 = new Intent(getActivity(), FollowActivity.class);
			startActivity(intent4);
			break;
		case R.id.rl_my_profile:
			Intent intent5 = new Intent(getActivity(), MyInfoActivity.class);
			startActivity(intent5);
			break;
		default:
			break;
		}
	}

}
