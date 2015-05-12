package com.zifei.corebeau.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.zifei.corebeau.utils.Utils;

public class LoginActivity extends Activity implements OnClickListener {

	private AccountLoginDialog loginDialog;
	private RegisterDialog registerDialog;
	private FindPassDialog findPassDialog;
//	private QQLoginDialog qQLoginDialog;
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
//		qQLoginDialog = new QQLoginDialog(LoginActivity.this,
//				R.style.new_setting_dialog);
//		qQLoginDialog.show();
		Intent intent = new Intent(LoginActivity.this,
				QQLoginActivity.class);

		startActivity(intent);
	}

//	public class QQLoginDialog extends Dialog {
//		
//		TextView openidTextView;
//		TextView nicknameTextView;
//		Button loginButton;
//		ImageView userlogo;
//		private Tencent mTencent;
//		public  QQAuth mQQAuth;
//		public  String mAppid;
//		public  String openidString;
//		public  String nicknameString;
//		public  String TAG = "MainActivity";
//		Bitmap bitmap = null;
//
//		public QQLoginDialog(Context context, int theme) {
//			super(context, theme);
//		}
//
//		@Override
//		protected void onCreate(Bundle savedInstanceState) {
//			super.onCreate(savedInstanceState);
//			setContentView(R.layout.activity_qqlogin);
//			
//			loginButton = (Button) findViewById(R.id.login);
//			// 用来显示OpenID的textView
//			openidTextView = (TextView) findViewById(R.id.user_openid);
//			// 用来显示昵称的textview
//			nicknameTextView = (TextView) findViewById(R.id.user_nickname);
//			// 用来显示头像的Imageview
//			userlogo = (ImageView) findViewById(R.id.user_logo);
//
//
//			loginButton.setOnClickListener(new View.OnClickListener() {
//				public void onClick(View v) {
//
////					String boundAccount = etBoundAccount.getText().toString();
////					String boundPassword = etBoundPassword.getText().toString();
////					if (paramCheck(boundAccount, boundPassword))
//					LoginQQ();
//				}
//			});
//		}
//		
//		// 这里是调用QQ登录的关键代码
//		public void LoginQQ() {
//			// 这里的APP_ID请换成你应用申请的APP_ID，我这里使用的是DEMO中官方提供的测试APP_ID 222222
//			mAppid = AppConstant.APP_ID;
//			// 第一个参数就是上面所说的申请的APPID，第二个是全局的Context上下文，这句话实现了调用QQ登录
//			mTencent = Tencent.createInstance(mAppid, getApplicationContext());
//			/**
//			 * 通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO
//			 * 是一个String类型的字符串，表示一些权限 官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE =
//			 * “get_user_info,add_t”；所有权限用“all”
//			 * 第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类
//			 */
//			mTencent.login(LoginActivity.this, "all", new BaseUiListener());
//
//		}
//
//		/**
//		 * 当自定义的监听器实现IUiListener接口后，必须要实现接口的三个方法， onComplete onCancel onError
//		 * 分别表示第三方登录成功，取消 ，错误。
//		 */
//		private class BaseUiListener implements IUiListener {
//
//			public void onCancel() {
//				// TODO Auto-generated method stub
//
//			}
//
//			public void onComplete(Object response) {
//				// TODO Auto-generated method stub
//				Toast.makeText(getApplicationContext(), "登录成功", 0).show();
//				try {
//					//获得的数据是JSON格式的，获得你想获得的内容
//					//如果你不知道你能获得什么，看一下下面的LOG
//					Log.e(TAG, "-------------"+response.toString());
//					openidString = ((JSONObject) response).getString("openid");
//					openidTextView.setText(openidString);
//					Log.e(TAG, "-------------"+openidString);
//					//access_token= ((JSONObject) response).getString("access_token");				//expires_in = ((JSONObject) response).getString("expires_in");
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				/**到此已经获得OpneID以及其他你想获得的内容了
//				QQ登录成功了，我们还想获取一些QQ的基本信息，比如昵称，头像什么的，这个时候怎么办？ 
//				sdk给我们提供了一个类UserInfo，这个类中封装了QQ用户的一些信息，我么可以通过这个类拿到这些信息 
//				如何得到这个UserInfo类呢？  */
//				//Q<a href="http://www.it165.net/pro/pkqt/" target="_blank" class="keylink">QT</a>oken qqToken = mTencent.getQ<a href="http://www.it165.net/pro/pkqt/" target="_blank" class="keylink">QT</a>oken();
//				UserInfo info = new UserInfo(getApplicationContext(), mQQAuth.getQQToken());
//				//这样我们就拿到这个类了，之后的操作就跟上面的一样了，同样是解析JSON			
//				
//				info.getUserInfo(new IUiListener() {
//
//					public void onComplete(final Object response) {
//						// TODO Auto-generated method stub
//						Log.e(TAG, "---------------111111");
//						Message msg = new Message();
//						msg.obj = response;
//						msg.what = 0;
//						mHandler.sendMessage(msg);
//						Log.e(TAG, "-----111---"+response.toString());
//						/**由于图片需要下载所以这里使用了线程，如果是想获得其他文字信息直接
//						 * 在mHandler里进行操作
//						 * 
//						 */
//						new Thread(){
//
//							@Override
//							public void run() {
//								// TODO Auto-generated method stub
//								JSONObject json = (JSONObject)response;
//								try {
//									bitmap = Utils.getbitmap(json.getString("figureurl"));
//								} catch (JSONException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//								Message msg = new Message();
//								msg.obj = bitmap;
//								msg.what = 1;
//								mHandler.sendMessage(msg);
//							}						
//						}.start();
//					}				
//					public void onCancel() {
//						Log.e(TAG, "--------------111112");
//						// TODO Auto-generated method stub					
//					}
//					public void onError(UiError arg0) {
//						// TODO Auto-generated method stub
//						Log.e(TAG, "-111113"+":"+arg0);
//					}
//					
//				});
//				
//			}
//
//			public void onError(UiError arg0) {
//				// TODO Auto-generated method stub
//
//			}
//
//		}
//
//		Handler mHandler = new Handler() {
//
//			@Override
//			public void handleMessage(Message msg) {
//				if (msg.what == 0) {
//					JSONObject response = (JSONObject) msg.obj;
//					if (response.has("nickname")) {
//						try {
//							nicknameString = response.getString("nickname");
//
//							nicknameTextView.setText(nicknameString);
//							Log.e(TAG, "--" + nicknameString);
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				} else if (msg.what == 1) {
//					Bitmap bitmap = (Bitmap) msg.obj;
//					userlogo.setImageBitmap(bitmap);
//
//				}
//			}
//
//		};
//	}

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
