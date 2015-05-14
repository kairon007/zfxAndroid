package com.zifei.corebeau.ui.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.response.RegisterResponse;
import com.zifei.corebeau.common.AppConstant;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.task.AccountTask;
import com.zifei.corebeau.utils.StringUtil;
import com.zifei.corebeau.utils.Utils;

public class LoginActivity extends Activity implements OnClickListener {

	private AccountLoginDialog loginDialog;
	private RegisterDialog registerDialog;
	private FindPassDialog findPassDialog;
	private TextView tvQQLogin;
	private TextView tvAccountLogin;
	private TextView tvRegister;
	private TextView tvFindPass;
	private TextView tvGuestLogin;
	private AccountTask accountTask;

	Button loginButton;
	private Tencent mTencent;
	public static String mAppid;
	public static String openidString;
	public static String TAG = "MainActivity";
	Bitmap bitmap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		accountTask = new AccountTask(this);
		init();
	}

	private void init() {
		tvQQLogin = (TextView) findViewById(R.id.iv_qq_login);
		tvAccountLogin = (TextView) findViewById(R.id.iv_account_login);
		tvRegister = (TextView) findViewById(R.id.iv_register);
		tvFindPass = (TextView) findViewById(R.id.iv_find_pass);
		tvGuestLogin = (TextView) findViewById(R.id.iv_guest_login);
		tvQQLogin.setOnClickListener(this);
		tvAccountLogin.setOnClickListener(this);
		tvRegister.setOnClickListener(this);
		tvFindPass.setOnClickListener(this);
		tvGuestLogin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_qq_login:
			LoginQQ();
			break;
		case R.id.iv_account_login:
			showAccountLoginDialog();
			break;
		case R.id.iv_register:
			showRegisterDialog();
			break;
		case R.id.iv_find_pass:
			showFindPassDialog();
			break;
		case R.id.iv_guest_login:
			guestLogin();
			break;
		default:
			break;
		}
	}

	private void guestLogin() {
		showWaitDialog("guestLogin...");
		accountTask
				.loginByDevice(new AsyncCallBacks.TwoTwo<Integer, String, Integer, String>() {
					@Override
					public void onSuccess(Integer status, String msg) {
						Intent intent = new Intent(LoginActivity.this,
								MainActivity.class);
						startActivity(intent);
						finish();
					}

					@Override
					public void onError(Integer status, String errorMsg) {
						dismissWaitDialog();
						Utils.showToast(LoginActivity.this, errorMsg);
					}
				});
	}

	private void showRegisterDialog() {
		registerDialog = new RegisterDialog(LoginActivity.this,
				R.style.new_setting_dialog);
		registerDialog.show();
	}

	public class RegisterDialog extends Dialog {

		private EditText etRegisterAccount, etRegisterNickname,
				etRegisterPassword, etRegisterPasswordConfirm;
		private RelativeLayout submit;

		public RegisterDialog(Context context, int theme) {
			super(context, theme);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.dialog_register);

			etRegisterAccount = (EditText) findViewById(R.id.et_register_account);
			etRegisterNickname = (EditText) findViewById(R.id.et_register_nickname);
			etRegisterPassword = (EditText) findViewById(R.id.et_bound_password);
			etRegisterPasswordConfirm = (EditText) findViewById(R.id.et_bound_password_re);
			submit = (RelativeLayout) findViewById(R.id.rl_submit_bound);
			submit.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {

					String strAccount = etRegisterAccount.getText().toString();
					String strNickName = etRegisterNickname.getText()
							.toString();
					String strPassword = etRegisterPassword.getText()
							.toString();
					String strPasswordComfirm = etRegisterPasswordConfirm
							.getText().toString();
					if (registerParamCheck(strAccount, strNickName,
							strPassword, strPasswordComfirm))
						register(strAccount, strNickName, strPassword);
				}
			});
		}

		private boolean registerParamCheck(String strAccount,
				String strNickName, String strPassword,
				String strPasswordComfirm) {
			if (TextUtils.isEmpty(strAccount)) {
				Utils.showToast(LoginActivity.this, "email empty");
				return false;
			} else if (!StringUtil.isEmail(strAccount)
					&& !StringUtil.isPhoneNum(strAccount)) {
				Utils.showToast(LoginActivity.this,
						"please input email or phone number");
				return false;
			} else if (TextUtils.isEmpty(strPassword)) {
				Utils.showToast(LoginActivity.this, "password empty");
				return false;
			} else if (!strPassword.equals(strPasswordComfirm)) {
				Utils.showToast(LoginActivity.this,
						"passwordconfirm must equal");
				return false;
			} else if (strPassword.length() < 8 || strPassword.length() > 16) {
				Utils.showToast(LoginActivity.this,
						"password must be 8~16 word");
				return false;
			} else if (strNickName.isEmpty()) {
				Utils.showToast(LoginActivity.this, "nickname empty");
				return false;
			}
			return true;
		}

		private void register(String account, String password, String nickname) {
			showWaitDialog("register...");
			accountTask.register(account, password, nickname,
					new AsyncCallBacks.ZeroTwo<Integer, String>() {

						@Override
						public void onSuccess() {
							dismissWaitDialog();
							Intent intent = new Intent(LoginActivity.this,
									MainActivity.class);

							startActivity(intent);
							finish();
						}

						@Override
						public void onError(Integer status, String msg) {
							dismissWaitDialog();

							if (status == RegisterResponse.ACCOUNT_EXIST) {
								Utils.showToast(LoginActivity.this,
										"already registered");
							} else if (status == RegisterResponse.NICKNAME_EXIST) {
								Utils.showToast(LoginActivity.this,
										"please write another nickname");
							}

							Utils.showToast(LoginActivity.this, msg);
						}
					});
		}

	}

	private void showAccountLoginDialog() {
		loginDialog = new AccountLoginDialog(LoginActivity.this,
				R.style.new_setting_dialog);
		loginDialog.show();
	}

	public class AccountLoginDialog extends Dialog {

		private EditText etLoginAccount, etLoginPassword;
		private RelativeLayout submit;

		public AccountLoginDialog(Context context, int theme) {
			super(context, theme);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.dialog_account_login);

			etLoginAccount = (EditText) findViewById(R.id.et_login_account);
			etLoginPassword = (EditText) findViewById(R.id.et_login_password);
			submit = (RelativeLayout) findViewById(R.id.rl_submit_login);
			submit.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {

					String strAccount = etLoginAccount.getText().toString();
					String strPassword = etLoginPassword.getText().toString();
					if (loginParamCheck(strAccount, strPassword))
						login(strAccount, strPassword);
				}
			});
		}

		private boolean loginParamCheck(String strAccount, String strPassword) {
			if (TextUtils.isEmpty(strAccount)) {
				Utils.showToast(LoginActivity.this, "email empty");
				return false;
			} else if (!StringUtil.isEmail(strAccount)
					&& !StringUtil.isPhoneNum(strAccount)) {
				Utils.showToast(LoginActivity.this,
						"please input email or phone number");
				return false;
			} else if (TextUtils.isEmpty(strPassword)) {
				Utils.showToast(LoginActivity.this, "password empty");
				return false;
			} else if (strPassword.length() < 8 || strPassword.length() > 16) {
				Utils.showToast(LoginActivity.this,
						"password must be 8~16 word");
				return false;
			}
			return true;
		}

		private void login(String account, String password) {
			showWaitDialog("login...");
			accountTask.login(account, password,
					new AsyncCallBacks.TwoOne<Integer, String, String>() {

						@Override
						public void onSuccess(Integer state, String msg) {
							dismissWaitDialog();
							Intent intent = new Intent(LoginActivity.this,
									MainActivity.class);

							startActivity(intent);
							finish();
						}

						@Override
						public void onError(String errorMsg) {
							dismissWaitDialog();
							Utils.showToast(LoginActivity.this, errorMsg);
						}
					});
		}

	}

	private void showFindPassDialog() {
		findPassDialog = new FindPassDialog(LoginActivity.this,
				R.style.new_setting_dialog);
		findPassDialog.show();
	}

	public class FindPassDialog extends Dialog {

		private EditText etFindPassAccount;
		private RelativeLayout submit;

		public FindPassDialog(Context context, int theme) {
			super(context, theme);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.dialog_find_pass);

			etFindPassAccount = (EditText) findViewById(R.id.et_find_pass_account);
			submit = (RelativeLayout) findViewById(R.id.rl_submit_find_pass);
			submit.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					String strAccount = etFindPassAccount.getText().toString();
					if (findPasswordParamCheck(strAccount)) {
						findPassword(strAccount);
					}

				}
			});
		}

		private boolean findPasswordParamCheck(String strAccount) {
			if (TextUtils.isEmpty(strAccount)) {
				Utils.showToast(LoginActivity.this, "email empty");
				return false;
			} else if (!StringUtil.isEmail(strAccount)
					&& !StringUtil.isPhoneNum(strAccount)) {
				Utils.showToast(LoginActivity.this,
						"please input email or phone number");
				return false;
			}
			return true;
		}

		private void findPassword(String strAccount) {
			showWaitDialog("findPassword...");
			accountTask.findPassword(strAccount,
					new AsyncCallBacks.ZeroOne<String>() {

						@Override
						public void onSuccess() {
							dismissWaitDialog();
							Utils.showToast(LoginActivity.this,
									"we send success to your email, check your new pass on email");
						}

						@Override
						public void onError(String errorMsg) {
							dismissWaitDialog();
							Utils.showToast(LoginActivity.this, errorMsg);
						}
					});
		}

	}

	// // call 시점 잘 생각해보기
	// private void checkAccount(String account) {
	// accountTask.checkAccount(account,
	// new AsyncCallBacks.TwoOne<Integer, String, String>() {
	//
	// @Override
	// public void onSuccess(Integer state, String msg) {
	// tvCheckAccount.setText("you can use this account");
	// tvCheckAccount.setTextColor(Color.GREEN);
	// }
	//
	// @Override
	// public void onError(String errorMsg) {
	// tvCheckAccount
	// .setText("already existed account, plz set another account");
	// tvCheckAccount.setTextColor(Color.RED);
	// }
	// });
	// }
	//
	// // call 시점 잘 생각해보기
	// private void checkNickname(String nickName) {
	// accountTask.checkAccount(nickName,
	// new AsyncCallBacks.TwoOne<Integer, String, String>() {
	//
	// @Override
	// public void onSuccess(Integer state, String msg) {
	// tvCheckNickname.setText("you can use this nickname");
	// tvCheckNickname.setTextColor(Color.GREEN);
	// }
	//
	// @Override
	// public void onError(String errorMsg) {
	// tvCheckNickname
	// .setText("already existed nickname, plz set another nickname");
	// tvCheckNickname.setTextColor(Color.RED);
	// }
	// });
	// }

	/**
	 * show wait dialog
	 */

	private Dialog waitDialog;

	public void showWaitDialog(String message) {
		if (this.isFinishing()) {
			return;
		}
		if (waitDialog != null && waitDialog.isShowing()) {
			waitDialog.dismiss();
			waitDialog = null;
		}
		waitDialog = new Dialog(this, R.style.dialogProgress);
		waitDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		waitDialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);
		waitDialog.setContentView(R.layout.dialog_progress);
		((TextView) waitDialog.findViewById(R.id.message)).setText(message);
		waitDialog.setCancelable(false);
		waitDialog.show();

	}

	/**
	 * show dismiss dialog
	 */
	public void dismissWaitDialog() {
		if (waitDialog != null && waitDialog.isShowing() && !this.isFinishing()) {
			waitDialog.dismiss();
		}
		waitDialog = null;
	}

	public void LoginQQ() {
		mAppid = AppConstant.APP_ID;
		mTencent = Tencent.createInstance(mAppid, getApplicationContext());
		mTencent.login(LoginActivity.this, "all", new BaseUiListener());
	}

	/**
	 * 当自定义的监听器实现IUiListener接口后，必须要实现接口的三个方法， onComplete onCancel onError
	 * 分别表示第三方登录成功，取消 ，错误。
	 */
	private class BaseUiListener implements IUiListener {

		public void onCancel() {
		}

		public void onComplete(Object response) {
			Toast.makeText(getApplicationContext(), "登录成功", 0).show();
			try {
				Log.e(TAG, "-------------" + response.toString());
				openidString = ((JSONObject) response).getString("openid");
				Log.e(TAG, "-------------" + openidString);
				// access_token= ((JSONObject)
				// response).getString("access_token"); //expires_in =
				// ((JSONObject) response).getString("expires_in");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			UserInfo info = new UserInfo(getApplicationContext(),
					mTencent.getQQToken());

			info.getUserInfo(new IUiListener() {

				public void onComplete(final Object response) {
					Log.e(TAG, "---------------111111");
					Log.e(TAG, "-----111---" + response.toString());
					JSONObject json = (JSONObject) response;
					String nickName = null;
					Boolean gender = null;
					String figureurl = null;
					try {
						if (json.has("nickname")) {
							nickName = json.getString("nickname");
						}

						if (json.has("figureurl")) {
							figureurl = json.getString("figureurl");
						}

						if (json.has("gender")) {
							gender = "男".equals(json.getString("gender")) ? true
									: false;
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

					accountTask
							.qqLogin(
									new AsyncCallBacks.TwoTwo<Integer, String, Integer, String>() {
										@Override
										public void onSuccess(Integer status,
												String msg) {
											Intent intent = new Intent(
													LoginActivity.this,
													MainActivity.class);
											startActivity(intent);
											finish();
										}

										@Override
										public void onError(Integer status,
												String errorMsg) {
											dismissWaitDialog();
											Utils.showToast(LoginActivity.this,
													errorMsg);
										}
									}, nickName, figureurl, gender,
									openidString);
				}

				public void onCancel() {
				}

				public void onError(UiError arg0) {
				}

			});

		}

		public void onError(UiError arg0) {
		}

	}

}
