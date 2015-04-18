package com.zifei.corebeau.common.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zifei.corebeau.R;
import com.zifei.corebeau.account.bean.response.RegisterResponse;
import com.zifei.corebeau.account.task.AccountTask;
import com.zifei.corebeau.account.task.UserInfoService;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.CorebeauApp;
import com.zifei.corebeau.common.PreferenceManager;
import com.zifei.corebeau.utils.StringUtil;
import com.zifei.corebeau.utils.Utils;

/**
 * Created by im14s_000 on 2015/3/26.
 */
public class SplashActivity extends CommonFragmentActvity implements
		View.OnClickListener {

	private final int DELAY_MILLIS = 1000;
	private CorebeauApp app;
	private EditText email, password, nickname;
	private TextView typeChange, submit, findpassType;
	private AccountTask accountTask;
	private ProgressBar progressBar;
	private TaskType taskType;
	private TextView logo;
	private UserInfoService userInfoService;

	private enum TaskType {
		LOGIN, REGISTER, FINDPASS
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		init();
		logo = (TextView) findViewById(R.id.logo);
		logo.setText("zfi");
		logo.setTextColor(Color.WHITE);
		logo.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
		logo.setTextSize(50);

		new Thread(new Runnable() {
			@Override
			public void run() {
				Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
				MobclickAgent.onError(SplashActivity.this);
			}

		}).start();

		handler.postDelayed(run, DELAY_MILLIS);
	}
	
	protected void init() {
		accountTask = new AccountTask(this);
		userInfoService = new UserInfoService(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		handler.removeCallbacks(run);
	}

	final Handler handler = new Handler();
	private Runnable run = new Runnable() {
		@Override
		public void run() {

			if (!StringUtil.isEmpty(userInfoService.getLoginId()) && !StringUtil.isEmpty(userInfoService.getLoginId())) {
				goMainActivity();

			} else {

				loginByDevice();
			}
		}
	};
	
	private void goMainActivity(){
		Intent intent = new Intent(SplashActivity.this,
				MainActivity.class);
		startActivity(intent);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.getDefault()).format(new Date()));
		MobclickAgent.onEvent(SplashActivity.this, "launcher", map);

		finish();
	}
	
	private void lauchLoginPage(){
		email = (EditText) findViewById(R.id.et_login_email);
		password = (EditText) findViewById(R.id.et_login_pass);
		nickname = (EditText) findViewById(R.id.et_login_nickname);
		findpassType = (TextView) findViewById(R.id.tv_change_findpass);
		typeChange = (TextView) findViewById(R.id.tv_type_change);
		submit = (TextView) findViewById(R.id.tv_submit);
		progressBar = (ProgressBar) findViewById(R.id.pb_splash);

		findpassType.setOnClickListener(SplashActivity.this);
		typeChange.setOnClickListener(SplashActivity.this);
		submit.setOnClickListener(SplashActivity.this);

		Animation appear1 = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.ani_login_form);
		email.setVisibility(View.VISIBLE);
		email.setAnimation(appear1);
		password.setVisibility(View.VISIBLE);
		password.setAnimation(appear1);
		nickname.setVisibility(View.VISIBLE);
		nickname.setAnimation(appear1);

		Animation appear3 = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.ani_logo);
		logo.setAnimation(appear3);
		logo.setVisibility(View.GONE);
		Animation appear2 = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.ani_login_register);

		typeChange.setVisibility(View.VISIBLE);
		typeChange.setAnimation(appear2);

		submit.setVisibility(View.VISIBLE);
		submit.setAnimation(appear2);

		taskType = TaskType.REGISTER;
		setTextByType();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_submit:
			paramCheck();
			break;
		case R.id.tv_type_change:
			if (taskType == TaskType.REGISTER) {
				this.taskType = TaskType.LOGIN;
				setTextByType();
			} else if (taskType == TaskType.LOGIN) {
				this.taskType = TaskType.REGISTER;
				setTextByType();
			} else {
				this.taskType = TaskType.LOGIN;
				setTextByType();
			}
			break;
		case R.id.tv_change_findpass:
			this.taskType = TaskType.FINDPASS;
			setTextByType();
			break;
		}
	}

	private void setTextByType() {
		switch (taskType) {
		case LOGIN:
			findpassType.setVisibility(View.VISIBLE);
			findpassType.setText("are you forget password?");
			typeChange.setText("register");
			submit.setText("login");
			password.setVisibility(View.VISIBLE);
			nickname.setVisibility(View.GONE);

			break;

		case REGISTER:
			findpassType.setVisibility(View.GONE);
			typeChange.setText("are you already joined?");
			submit.setText("register");
			password.setVisibility(View.VISIBLE);
			nickname.setVisibility(View.VISIBLE);
			break;

		case FINDPASS:
			findpassType.setVisibility(View.GONE);
			typeChange.setText("are you already joined?");
			submit.setText("send");
			password.setVisibility(View.GONE);
			nickname.setVisibility(View.GONE);

			break;
		}
	}

	private String trim_email;
	private String trim_password;
	private String trim_nickname;

	private void paramCheck() {

		trim_email = email.getText().toString().trim();
		trim_password = password.getText().toString().trim();
		trim_nickname = password.getText().toString().trim();

		switch (taskType) {
		case LOGIN:
			if (loginParamCheck()) {
				loginTask(trim_email, trim_password);
			}
			;
			break;

		case REGISTER:
			if (registerParamCheck()) {
				registerTask(trim_email, trim_password, trim_nickname);
			}
			break;

		case FINDPASS:
			if (findPasswordParamCheck()) {
				findPasswordTask(trim_email);
			}
			break;
		}
	}

	private boolean loginParamCheck() {
		if (TextUtils.isEmpty(trim_email)) {
			Utils.showToast(SplashActivity.this, "email empty");
			return false;
		} else if (!StringUtil.isEmail(trim_email)
				&& !StringUtil.isPhoneNum(trim_email)) {
			Utils.showToast(SplashActivity.this,
					"please input email or phone number");
			return false;
		} else if (TextUtils.isEmpty(trim_password)) {
			Utils.showToast(SplashActivity.this, "password empty");
			return false;
		} else if (trim_password.length() < 8 || trim_password.length() > 16) {
			Utils.showToast(SplashActivity.this, "password must be 8~16 word");
			return false;
		}
		return true;
	}

	private boolean registerParamCheck() {
		if (TextUtils.isEmpty(trim_email)) {
			Utils.showToast(SplashActivity.this, "email empty");
			return false;
		} else if (!StringUtil.isEmail(trim_email)
				&& !StringUtil.isPhoneNum(trim_email)) {
			Utils.showToast(SplashActivity.this,
					"please input email or phone number");
			return false;
		} else if (TextUtils.isEmpty(trim_password)) {
			Utils.showToast(SplashActivity.this, "password empty");
			return false;
		} else if (trim_password.length() < 8 || trim_password.length() > 16) {
			Utils.showToast(SplashActivity.this, "password must be 8~16 word");
			return false;
		} else if (taskType == TaskType.REGISTER & trim_nickname.isEmpty()) {
			Utils.showToast(SplashActivity.this, "nickname empty");
			return false;
		}
		return true;
	}

	private boolean findPasswordParamCheck() {
		if (TextUtils.isEmpty(trim_email)) {
			Utils.showToast(SplashActivity.this, "email empty");
			return false;
		} else if (!StringUtil.isEmail(trim_email)
				&& !StringUtil.isPhoneNum(trim_email)) {
			Utils.showToast(SplashActivity.this,
					"please input email or phone number");
			return false;
		}
		return true;
	}

	private void loginTask(String account, String password) {
		progressBar.setVisibility(View.VISIBLE);
		accountTask.login(account, password,
				new AsyncCallBacks.TwoOne<Integer, String, String>() {

					@Override
					public void onSuccess(Integer state, String msg) {
						progressBar.setVisibility(View.GONE);
						Intent intent = new Intent(SplashActivity.this,
								MainActivity.class);

						startActivity(intent);
						finish();
					}

					@Override
					public void onError(String errorMsg) {
						progressBar.setVisibility(View.GONE);
						Utils.showToast(SplashActivity.this, errorMsg);
					}
				});
	}

	private void registerTask(String account, String password, String nickname) {
		progressBar.setVisibility(View.VISIBLE);
		accountTask.register(account, password, nickname,
				new AsyncCallBacks.ZeroTwo<Integer, String>() {

					@Override
					public void onSuccess() {
						progressBar.setVisibility(View.GONE);
						Intent intent = new Intent(SplashActivity.this,
								MainActivity.class);

						startActivity(intent);
						finish();
					}

					@Override
					public void onError(Integer status, String msg) {
						progressBar.setVisibility(View.GONE);

						if (status == RegisterResponse.ACCOUNT_EXIST) {
							Utils.showToast(SplashActivity.this,
									"already registered");
						} else if (status == RegisterResponse.NICKNAME_EXIST) {
							Utils.showToast(SplashActivity.this,
									"please write another nickname");
						}

						Utils.showToast(SplashActivity.this, msg);
					}
				});
	}

	private void findPasswordTask(String account) {
		progressBar.setVisibility(View.VISIBLE);
		accountTask.findPassword(account, new AsyncCallBacks.ZeroOne<String>() {

			@Override
			public void onSuccess() {
				progressBar.setVisibility(View.GONE);
				Utils.showToast(SplashActivity.this,
						"we send success to your email, check your new pass on email");
			}

			@Override
			public void onError(String errorMsg) {
				progressBar.setVisibility(View.GONE);
				Utils.showToast(SplashActivity.this, errorMsg);
			}
		});
	}
	
	private void loginByDevice(){
		accountTask.loginByDevice(new AsyncCallBacks.TwoTwo<Integer,String,Integer,String>(){
			@Override
			public void onSuccess(Integer status, String msg) {
				goMainActivity();
			}

			@Override
			public void onError(Integer status, String errorMsg) {
				lauchLoginPage();
			}
		});
	}

}
