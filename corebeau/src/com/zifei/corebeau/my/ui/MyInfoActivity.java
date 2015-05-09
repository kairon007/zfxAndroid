package com.zifei.corebeau.my.ui;

import java.io.File;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zifei.corebeau.R;
import com.zifei.corebeau.account.task.UserInfoService;
import com.zifei.corebeau.bean.UserInfo;
import com.zifei.corebeau.bean.UserInfoDetail;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.CorebeauApp;
import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.common.ui.view.CircularImageView;
import com.zifei.corebeau.database.RegionDBHelper;
import com.zifei.corebeau.my.bean.response.UpdateUserInfoResponse;
import com.zifei.corebeau.my.task.MyInfoTask;
import com.zifei.corebeau.my.task.ProfileImageTask;
import com.zifei.corebeau.my.task.ProfileImageTask.OnProfileImgStatusListener;
import com.zifei.corebeau.my.ui.adapter.RegionAdapter;
import com.zifei.corebeau.my.ui.crop.Crop;
import com.zifei.corebeau.utils.StringUtil;
import com.zifei.corebeau.utils.Utils;

public class MyInfoActivity extends SherlockActivity implements
		OnClickListener, OnFocusChangeListener, OnProfileImgStatusListener {

	private MyInfoTask myInfoTask;
	private UserInfoService userInfoService;

	private EditText nickname;
	private RelativeLayout rlChangePassword;
	private RelativeLayout rlBoundAccount;
	private RelativeLayout rlIcon, rlGender;
	private ImageView userCoverImageView;
	private CircularImageView icon;
	private RelativeLayout rlCity;
	private ProfileImageTask profileImageTask;
	private DisplayImageOptions imageOptions;
	private ImageLoader imageLoader;
	private ImageLoaderConfiguration config;
	private UserInfo simpleUserInfo;
	private UserInfoDetail uploadUserInfo;
	private TextView tvUserIcon, tvGender;
	private GenderSettingDialog genderDialog;
	private CitySettingDialog cityDialog;
	private PassWordSettingDialog passwordDialog;
	private BoundAccountDialog boundDialog;
	private Boolean genderShort;
	public static int THEME = R.style.Theme_Sherlock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_info);
		myInfoTask = new MyInfoTask(this);
		profileImageTask = new ProfileImageTask(this);
		initActionBar();
		initLoader();
		init();

	}

	private void initActionBar() {
		getSupportActionBar().setTitle(" update infomation");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
	}

	private void init() {

		setActivityStatus();

		rlBoundAccount = (RelativeLayout) findViewById(R.id.my_info_bind_account);
		rlChangePassword = (RelativeLayout) findViewById(R.id.my_info_change_password);
		nickname = (EditText) findViewById(R.id.et_my_info_nickname);
		rlGender = (RelativeLayout) findViewById(R.id.rl_gender);
		tvGender = (TextView) findViewById(R.id.tv_gender);
		rlCity = (RelativeLayout) findViewById(R.id.my_info_city);
		rlIcon = (RelativeLayout) findViewById(R.id.rl_my_info_user_icon);
		icon = (CircularImageView) findViewById(R.id.my_info_user_icon);
		tvUserIcon = (TextView) findViewById(R.id.tv_my_info_user_icon);
		userCoverImageView = (ImageView) findViewById(R.id.my_info_user_cover);

		rlIcon.setOnClickListener(this);
		rlGender.setOnClickListener(this);
		rlBoundAccount.setOnClickListener(this);
		rlChangePassword.setOnClickListener(this);
		rlCity.setOnClickListener(this);
		nickname.setOnFocusChangeListener(this);
		profileImageTask.setOnProfileImgStatusListener(this);
		getUserInfo();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("submit").setShowAsAction(
				MenuItem.SHOW_AS_ACTION_IF_ROOM
						| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().equals("submit")) {
			updateUserInfo(uploadUserInfo);
		} else if (item.getTitle().equals(" update infomation")) {
			finish();
		}
		return true;
	}

	private void setActivityStatus() {
		// setNavTitle("my info");
		// setNavRightText("submit");
		// navi.setRightTextVisible(true);
		// navi.rightTextClicker.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_info_bind_account:
			break;
		case R.id.my_info_change_password:
			showChangePassWordDialog();
			break;
		case R.id.rl_gender:
			showChangeGenderDialog();
			break;
		case R.id.my_info_city:
			showChangeCityDialog();
			break;
		// case R.id.navi_bar_text_clicker:
		// updateUserInfo(uploadUserInfo);
		// break;
		case R.id.rl_my_info_user_icon:
			Crop.pickImage(this);
			break;
		}
	}

	private void initLoader() {
		imageLoader = ImageLoader.getInstance();
		config = new ImageLoaderConfiguration.Builder(this).threadPoolSize(3)
				.build();
		imageLoader.init(config);
		imageOptions = new DisplayImageOptions.Builder()
				.delayBeforeLoading(200) // 载入之前的延迟时间
				.cacheInMemory(false).cacheOnDisk(true).build();
	}

	private void getUserInfo() {
		// from db
		userInfoService = new UserInfoService(this);
		this.simpleUserInfo = userInfoService.getCurentUserInfo();
		if (simpleUserInfo == null) {
			return;
		}

		Boolean tmpGender = simpleUserInfo.getUserGender();
		if (tmpGender == null) {
			tvGender.setText("plz set gender");
			genderShort = true;
		} else {
			tvGender.setText(tmpGender == UserInfo.GENDER_MALE ? "male"
					: "femal");
			genderShort = tmpGender;
		}

		String tmpNick = simpleUserInfo.getNickName();
		if (tmpNick != null) {
			nickname.setHint(tmpNick);
		} else {
			nickname.setHint("guest");
		}

		tvUserIcon.setText("plz update my icon image");
		String iconUrl = simpleUserInfo.getUrl();
		if (iconUrl == null || StringUtil.isEmpty(iconUrl)) {
			imageLoader.displayImage("drawable://" + R.drawable.my_default,
					icon, imageOptions);
		} else {
			imageLoader.displayImage(iconUrl, icon, imageOptions);
		}

	}

	private void updateUserInfo(final UserInfoDetail userInfo) {
		// progressBar.setVisibility(View.VISIBLE); // 셀렉션 부분에다가 조그만하게 프로그레스바
		// 돌리기
		if (uploadUserInfo == null) {
			uploadUserInfo = new UserInfoDetail();
		}
		String tmpNick = nickname.getText().toString();
		if (!StringUtil.isEmpty(tmpNick)) {
			if (tmpNick.length() < 3 || tmpNick.length() > 8) {
				Utils.showToast(this, "nickname 5~10 word");
				return;
			} else {
				uploadUserInfo.setNickName(tmpNick);
			}
		}
		myInfoTask.updateUserInfo(userInfo,
				new AsyncCallBacks.OneOne<UpdateUserInfoResponse, String>() {

					@Override
					public void onSuccess(UpdateUserInfoResponse response) {
						// progressBar.setVisibility(View.INVISIBLE);
						Utils.showToast(MyInfoActivity.this, "update success");
						// 셋팅창 userinfo 셋팅

						// nickname.setText("");
						// nickname.setHint(userInfo.getNickName());
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
			if (!isFocus) {

			}
			break;

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
			beginCrop(data.getData());
		} else if (requestCode == Crop.REQUEST_CROP) {
			handleCrop(resultCode, data);
		}
	}

	private void beginCrop(Uri source) {
		Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
		Crop.of(source, destination).asSquare().start(this);
	}

	private void handleCrop(int resultCode, Intent result) {
		if (resultCode == RESULT_OK) {
			Uri uri = Crop.getOutput(result);
			setProfileImage(uri);
		} else if (resultCode == Crop.RESULT_ERROR) {
			Toast.makeText(this, Crop.getError(result).getMessage(),
					Toast.LENGTH_SHORT).show();
		}
	}

	private void setProfileImage(Uri uri) {
		icon.setImageURI(uri);
		profileImageTask.getProfileToken(uri.getPath(),
				new AsyncCallBacks.TwoTwo<Integer, String, Integer, String>() {

					@Override
					public void onSuccess(Integer state, String msg) {
						// Utils.showToast(MyInfoActivity.this, msg);
						// start progress bar
					}

					@Override
					public void onError(Integer state, String msg) {
						Utils.showToast(MyInfoActivity.this, msg);
					}
				});
	}

	@Override
	public void uploadFinish(String url) {
		if (uploadUserInfo == null) {
			uploadUserInfo = new UserInfoDetail();
		}
		uploadUserInfo.setUserImageUrl(CorebeauApp.getImageBaseUrl() + url);
	}

	private void showChangeGenderDialog() {
		genderDialog = new GenderSettingDialog(MyInfoActivity.this,
				R.style.new_setting_dialog, genderShort);
		genderDialog.show();
	}

	public class GenderSettingDialog extends Dialog {
		private ImageView maleRadio;
		private ImageView femaleRadio;
		private Boolean sexShort;

		public GenderSettingDialog(Context context, int theme,
				Boolean sex_status) {
			super(context, theme);
			sexShort = sex_status;
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.dialog_update_gender);

			maleRadio = (ImageView) findViewById(R.id.gender_setting_im_male);
			femaleRadio = (ImageView) findViewById(R.id.gender_setting_im_female);

			RelativeLayout M = (RelativeLayout) findViewById(R.id.gender_setting_rl_male_m);
			RelativeLayout F = (RelativeLayout) findViewById(R.id.gender_setting_rl_female_f);
			if (uploadUserInfo == null) {
				uploadUserInfo = new UserInfoDetail();
			}
			// 判断初始的性别
			if (sexShort == UserInfoDetail.GENDER_FEMALE) {
				femaleRadio.setVisibility(View.VISIBLE);
				maleRadio.setVisibility(View.GONE);
			} else {
				maleRadio.setVisibility(View.VISIBLE);
				femaleRadio.setVisibility(View.GONE);
			}

			M.setOnClickListener(new android.view.View.OnClickListener() {

				@Override
				public void onClick(View v) {
					maleRadio.setVisibility(View.VISIBLE);
					femaleRadio.setVisibility(View.GONE);
					uploadUserInfo.setUserGender(sexShort);
					tvGender.setText("male");
					genderDialog.dismiss();

				}
			});

			F.setOnClickListener(new android.view.View.OnClickListener() {

				@Override
				public void onClick(View v) {
					femaleRadio.setVisibility(View.VISIBLE);
					maleRadio.setVisibility(View.GONE);
					uploadUserInfo.setUserGender(sexShort);
					tvGender.setText("female");
					genderDialog.dismiss();
				}
			});
		}
	}

	private void showChangeCityDialog() {
		cityDialog = new CitySettingDialog(MyInfoActivity.this,
				R.style.new_setting_dialog);
		cityDialog.show();
	}

	public class CitySettingDialog extends Dialog {

		private RegionAdapter regionAdapter;

		public CitySettingDialog(Context context, int theme) {
			super(context, theme);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.dialog_update_city);

			ListView cityList = (ListView) findViewById(R.id.lv_city);
			List<String> city = RegionDBHelper.getInstance()
					.getAllProvinceRegion();
			regionAdapter = new RegionAdapter(MyInfoActivity.this);
			regionAdapter.setData(city);
			cityList.setAdapter(regionAdapter);
			cityList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					String region = regionAdapter.getData().get(position);
					if (uploadUserInfo == null) {
						uploadUserInfo = new UserInfoDetail();
					}
					// tmpUserInfo.setUserLocation(region); ....
					cityDialog.dismiss();
				}
			});
		}
	}

	private void showChangePassWordDialog() {
		passwordDialog = new PassWordSettingDialog(MyInfoActivity.this,
				R.style.new_setting_dialog);
		passwordDialog.show();
	}

	public class PassWordSettingDialog extends Dialog {

		private EditText etBeforePassword, etNewPassword, etNewPasswordConfirm;
		private RelativeLayout submit;

		public PassWordSettingDialog(Context context, int theme) {
			super(context, theme);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.dialog_update_password);

			etBeforePassword = (EditText) findViewById(R.id.et_before_password);
			etNewPassword = (EditText) findViewById(R.id.et_new_password);
			etNewPasswordConfirm = (EditText) findViewById(R.id.et_new_password_confirm);
			submit = (RelativeLayout) findViewById(R.id.rl_submit_password);
			submit.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {

					String beforePass = etBeforePassword.getText().toString();
					String newPass = etNewPassword.getText().toString();
					String newPassConfirm = etNewPasswordConfirm.getText()
							.toString();
					if (passCheck(beforePass, newPass, newPassConfirm))
						updatePassword(beforePass, newPass);
				}
			});
		}

		private boolean passCheck(String beforePass, String newPass,
				String newPassConfirm) {
			if (TextUtils.isEmpty(beforePass)) {
				Utils.showToast(MyInfoActivity.this, "beforePass empty");
				return false;
			} else if (TextUtils.isEmpty(newPass)) {
				Utils.showToast(MyInfoActivity.this, "newPass empty");
				return false;
			} else if (TextUtils.isEmpty(newPassConfirm)) {
				Utils.showToast(MyInfoActivity.this, "newPassConfirm empty");
				return false;
			} else if (newPass.length() < 8 || newPass.length() > 16) {
				Utils.showToast(MyInfoActivity.this,
						"password must be 8~16 word");
				return false;
			} else if (!newPass.equals(newPassConfirm)) {
				Utils.showToast(MyInfoActivity.this,
						"newPassConfirm not equal with password");
				return false;
			}
			return true;
		}

		private void updatePassword(String oldPassword, String newPassword) {
			myInfoTask.updatePassword(oldPassword, newPassword,
					new AsyncCallBacks.OneOne<Response, String>() {

						@Override
						public void onSuccess(Response state) {
							Utils.showToast(MyInfoActivity.this,
									"change success");
							passwordDialog.dismiss();
						}

						@Override
						public void onError(String msg) {
							Utils.showToast(MyInfoActivity.this, "change fail");
						}
					});
		}
	}

	private void showBoundAccountDialog() {
		boundDialog = new BoundAccountDialog(MyInfoActivity.this,
				R.style.new_setting_dialog);
		boundDialog.show();
	}

	public class BoundAccountDialog extends Dialog {

		private EditText etBoundPassword, etBoundAccount;
		private RelativeLayout submit;

		public BoundAccountDialog(Context context, int theme) {
			super(context, theme);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.dialog_bound_account);

			etBoundAccount = (EditText) findViewById(R.id.et_bound_account);
			etBoundPassword = (EditText) findViewById(R.id.et_bound_password);
			submit = (RelativeLayout) findViewById(R.id.rl_submit_password);
			submit.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {

					String boundAccount = etBoundAccount.getText().toString();
					String boundPassword = etBoundPassword.getText().toString();
					if (paramCheck(boundAccount, boundPassword))
						boundAccount(boundAccount, boundPassword);
				}
			});
		}

		private boolean paramCheck(String boundAccount, String boundPassword) {
			if (TextUtils.isEmpty(boundAccount)) {
				Utils.showToast(MyInfoActivity.this, "boundAccount empty");
				return false;
			} else if (TextUtils.isEmpty(boundPassword)) {
				Utils.showToast(MyInfoActivity.this, "boundPassword empty");
				return false;
			} else if (!StringUtil.isEmail(boundAccount)) {
				Utils.showToast(MyInfoActivity.this, "please set right email");
				return false;
			}
			return true;
		}

		private void boundAccount(String boundAccount, String boundPassword) {
			myInfoTask.bindAccount(boundAccount, boundPassword, "",
					new AsyncCallBacks.OneOne<Response, String>() {

						@Override
						public void onSuccess(Response state) {
							Utils.showToast(MyInfoActivity.this,
									"change success");
							passwordDialog.dismiss();
						}

						@Override
						public void onError(String msg) {
							Utils.showToast(MyInfoActivity.this, "change fail");
						}
					});
		}
	}

}
