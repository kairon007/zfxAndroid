package com.zifei.corebeau.my.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.UserInfo;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.common.ui.BarActivity;
import com.zifei.corebeau.my.task.MyInfoTask;
import com.zifei.corebeau.utils.Utils;

public class MyInfoActivity extends BarActivity implements OnClickListener{
	
	private MyInfoTask myInfoTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_info);
		init();
		myInfoTask = new MyInfoTask(this);
	}
	
	private void init(){
		setActivityStatus();
		RelativeLayout rlBoundAccount = (RelativeLayout)findViewById(R.id.my_info_bind_account);
		RelativeLayout rlChangePassword = (RelativeLayout)findViewById(R.id.my_info_change_password);
		EditText etNickname = (EditText)findViewById(R.id.et_my_info_nickname);
		ImageView ivMale = (ImageView)findViewById(R.id.iv_sex_male);
		ImageView ivFemale = (ImageView)findViewById(R.id.iv_sex_female);
		RelativeLayout rlCity = (RelativeLayout)findViewById(R.id.my_info_city);
		
		rlBoundAccount.setOnClickListener(this);
		rlChangePassword.setOnClickListener(this);
		ivMale.setOnClickListener(this);
		ivFemale.setOnClickListener(this);
		rlCity.setOnClickListener(this);
	}
	
	private void setActivityStatus(){
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
	
	private void getUserInfo(){
		// from db
	}
	
	private void updateUserInfo(UserInfo userInfo){
//		progressBar.setVisibility(View.VISIBLE); // 셀렉션 부분에다가 조그만하게 프로그레스바 돌리기
		myInfoTask.updateUserInfo(userInfo, new AsyncCallBacks.OneOne<Response, String>() {

			@Override
			public void onSuccess(Response response) {
//				progressBar.setVisibility(View.INVISIBLE);
				Utils.showToast(MyInfoActivity.this, "");
				// 셋팅창 userinfo 셋팅
			}

			@Override
			public void onError(String msg) {
//				progressBar.setVisibility(View.INVISIBLE);
				Utils.showToast(MyInfoActivity.this, msg);
			}
		});
	}
}
