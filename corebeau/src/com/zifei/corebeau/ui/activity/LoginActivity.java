package com.zifei.corebeau.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.response.RegisterResponse;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.task.AccountTask;
import com.zifei.corebeau.utils.StringUtil;
import com.zifei.corebeau.utils.Utils;

public class LoginActivity extends Activity implements OnClickListener {

	private AccountLoginDialog loginDialog;
	private RegisterDialog registerDialog;
	private FindPassDialog findPassDialog;
	private QQLoginDialog qQLoginDialog;
	private TextView tvQQLogin;
	private TextView tvAccountLogin;
	private TextView tvRegister;
	private TextView tvFindPass;
	private TextView tvGuestLogin;
	private AccountTask accountTask;

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
			showQQLoginDialog();
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

	private void showQQLoginDialog() {
		qQLoginDialog = new QQLoginDialog(LoginActivity.this,
				R.style.new_setting_dialog);
		qQLoginDialog.show();
	}

	public class QQLoginDialog extends Dialog {

		public QQLoginDialog(Context context, int theme) {
			super(context, theme);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.dialog_qq_login);

//			submit.setOnClickListener(new View.OnClickListener() {
//				public void onClick(View v) {
//
//					String boundAccount = etBoundAccount.getText().toString();
//					String boundPassword = etBoundPassword.getText().toString();
//					if (paramCheck(boundAccount, boundPassword))
//				}
//			});
		}
	}

	private void guestLogin() {

	}

	private void showRegisterDialog() {
		registerDialog = new RegisterDialog(LoginActivity.this,
				R.style.new_setting_dialog);
		registerDialog.show();
	}

	public class RegisterDialog extends Dialog {

		private EditText etRegisterAccount, etRegisterNickname, etRegisterPassword, etRegisterPasswordConfirm;
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
					String strNickName = etRegisterNickname.getText().toString();
					String strPassword = etRegisterPassword.getText().toString();
					String strPasswordComfirm = etRegisterPasswordConfirm.getText().toString();
					if (registerParamCheck())
						register(strAccount, strNickName, strPassword);
				}
			});
		}

		private boolean registerParamCheck() {
//			if (TextUtils.isEmpty(trim_email)) {
//				Utils.showToast(LoginActivity.this, "email empty");
//				return false;
//			} else if (!StringUtil.isEmail(trim_email)
//					&& !StringUtil.isPhoneNum(trim_email)) {
//				Utils.showToast(LoginActivity.this,
//						"please input email or phone number");
//				return false;
//			} else if (TextUtils.isEmpty(trim_password)) {
//				Utils.showToast(LoginActivity.this, "password empty");
//				return false;
//			} else if (trim_password.length() < 8
//					|| trim_password.length() > 16) {
//				Utils.showToast(LoginActivity.this,
//						"password must be 8~16 word");
//				return false;
//			} else if (taskType == TaskType.REGISTER & trim_nickname.isEmpty()) {
//				Utils.showToast(LoginActivity.this, "nickname empty");
//				return false;
//			}
			return true;
		}

		private void register(String account, String password, String nickname) {
			showWaitDialog("wait...");
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
					if (loginParamCheck())
						login(strAccount, strPassword);
				}
			});
		}

		private boolean loginParamCheck() {
//			if (TextUtils.isEmpty(trim_email)) {
//				Utils.showToast(LoginActivity.this, "email empty");
//				return false;
//			} else if (!StringUtil.isEmail(trim_email)
//					&& !StringUtil.isPhoneNum(trim_email)) {
//				Utils.showToast(LoginActivity.this,
//						"please input email or phone number");
//				return false;
//			} else if (TextUtils.isEmpty(trim_password)) {
//				Utils.showToast(LoginActivity.this, "password empty");
//				return false;
//			} else if (trim_password.length() < 8
//					|| trim_password.length() > 16) {
//				Utils.showToast(LoginActivity.this,
//						"password must be 8~16 word");
//				return false;
//			}
			return true;
		}

		private void login(String account, String password) {
			showWaitDialog("wait...");
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
//			if (TextUtils.isEmpty(trim_email)) {
//				Utils.showToast(LoginActivity.this, "email empty");
//				return false;
//			} else if (!StringUtil.isEmail(trim_email)
//					&& !StringUtil.isPhoneNum(trim_email)) {
//				Utils.showToast(LoginActivity.this,
//						"please input email or phone number");
//				return false;
//			}
			return true;
		}

		private void findPassword(String strAccount) {
			showWaitDialog("wait...");
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

//	// call 시점 잘 생각해보기
//	private void checkAccount(String account) {
//		accountTask.checkAccount(account,
//				new AsyncCallBacks.TwoOne<Integer, String, String>() {
//
//					@Override
//					public void onSuccess(Integer state, String msg) {
//						tvCheckAccount.setText("you can use this account");
//						tvCheckAccount.setTextColor(Color.GREEN);
//					}
//
//					@Override
//					public void onError(String errorMsg) {
//						tvCheckAccount
//								.setText("already existed account, plz set another account");
//						tvCheckAccount.setTextColor(Color.RED);
//					}
//				});
//	}
//
//	// call 시점 잘 생각해보기
//	private void checkNickname(String nickName) {
//		accountTask.checkAccount(nickName,
//				new AsyncCallBacks.TwoOne<Integer, String, String>() {
//
//					@Override
//					public void onSuccess(Integer state, String msg) {
//						tvCheckNickname.setText("you can use this nickname");
//						tvCheckNickname.setTextColor(Color.GREEN);
//					}
//
//					@Override
//					public void onError(String errorMsg) {
//						tvCheckNickname
//								.setText("already existed nickname, plz set another nickname");
//						tvCheckNickname.setTextColor(Color.RED);
//					}
//				});
//	}

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
		if (waitDialog != null && waitDialog.isShowing()
				&& !this.isFinishing()) {
			waitDialog.dismiss();
		}
		waitDialog = null;
	}

}
