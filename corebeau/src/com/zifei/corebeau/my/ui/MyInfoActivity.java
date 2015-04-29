package com.zifei.corebeau.my.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zifei.corebeau.R;
import com.zifei.corebeau.account.task.UserInfoService;
import com.zifei.corebeau.bean.UserInfo;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.common.ui.BarActivity;
import com.zifei.corebeau.my.task.MyInfoTask;
import com.zifei.corebeau.utils.StringUtil;
import com.zifei.corebeau.utils.Utils;

public class MyInfoActivity extends BarActivity implements OnClickListener,
		OnFocusChangeListener {

	private MyInfoTask myInfoTask;
	private UserInfoService userInfoService;
	private UserInfo userInfo;
	private EditText nickname;
	RelativeLayout rlChangePassword;
	RelativeLayout rlBoundAccount;
	ImageView ivMale;
	ImageView ivFemale;
	RelativeLayout rlCity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_info);
		init();
		myInfoTask = new MyInfoTask(this);
	}

	private void init() {
		setActivityStatus();
		rlBoundAccount = (RelativeLayout) findViewById(R.id.my_info_bind_account);
		rlChangePassword = (RelativeLayout) findViewById(R.id.my_info_change_password);
		nickname = (EditText) findViewById(R.id.et_my_info_nickname);
		ivMale = (ImageView) findViewById(R.id.iv_sex_male);
		ivFemale = (ImageView) findViewById(R.id.iv_sex_female);
		rlCity = (RelativeLayout) findViewById(R.id.my_info_city);

		rlBoundAccount.setOnClickListener(this);
		rlChangePassword.setOnClickListener(this);
		ivMale.setOnClickListener(this);
		ivFemale.setOnClickListener(this);
		rlCity.setOnClickListener(this);
		nickname.setOnFocusChangeListener(this);
	}

	private void setActivityStatus() {
		setNavTitle("my info");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_info_bind_account:
			break;
		case R.id.my_info_change_password:
			break;
		case R.id.iv_sex_male:
			break;
		case R.id.iv_sex_female:
			break;
		case R.id.my_info_city:
			break;
		}
	}

	private void getUserInfo() {
		// from db
		userInfoService = new UserInfoService(this);
		userInfo = userInfoService.getCurentUserInfo();
		if (userInfo == null) {
			return;
		}

		String tmpNick = userInfo.getNickName();
		if (tmpNick != null) {
			nickname.setHint(tmpNick);
		} else {
			nickname.setHint("guest");
		}

		//
		// String iconUrl = userInfo.getPicThumbUrl();
		// if (iconUrl == null || StringUtil.isEmpty(iconUrl)) {
		// imageLoader.displayImage("drawable://" + R.drawable.my_default,
		// circularImageView, imageOptions);
		// } else {
		// imageLoader.displayImage(iconUrl, circularImageView, imageOptions);
		// }
	}

	private void updateUserInfo(UserInfo userInfo) {
		// progressBar.setVisibility(View.VISIBLE); // 셀렉션 부분에다가 조그만하게 프로그레스바
		// 돌리기
		myInfoTask.updateUserInfo(userInfo,
				new AsyncCallBacks.OneOne<Response, String>() {

					@Override
					public void onSuccess(Response response) {
						// progressBar.setVisibility(View.INVISIBLE);
						Utils.showToast(MyInfoActivity.this, "");
						// 셋팅창 userinfo 셋팅
					}

					@Override
					public void onError(String msg) {
						// progressBar.setVisibility(View.INVISIBLE);
						Utils.showToast(MyInfoActivity.this, msg);
					}
				});
	}

	@Override
	public void onFocusChange(View v, boolean isFocus) {
		switch (v.getId()) {
		case R.id.my_info_bind_account:
			if(!isFocus){
				String tmpNick = nickname.getText().toString();
				if(!userInfo.getNickName().equals(tmpNick)){
					
				}
			}
			break;
		}
	}
	
	private void updateNickName(final String nickName){
		myInfoTask.updateNickName(nickName,
				new AsyncCallBacks.OneOne<Response, String>() {

			@Override
			public void onSuccess(Response response) {
				nickname.setText("");
				nickname.setHint(nickName);
			}

			@Override
			public void onError(String msg) {
				
				Utils.showToast(MyInfoActivity.this, "update fail");
			}
		});
	}
}
