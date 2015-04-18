package com.zifei.corebeau.common.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zifei.corebeau.R;
import com.zifei.corebeau.account.task.AccountTask;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.CorebeauApp;
import com.zifei.corebeau.common.PreferenceManager;
import com.zifei.corebeau.utils.Utils;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by im14s_000 on 2015/3/26.
 */
public class SplashActivity extends CommonFragmentActvity implements View.OnClickListener {

    private final int DELAY_MILLIS = 1000;
    private PreferenceManager preferenceManager;
    private CorebeauApp app;
    private LinearLayout accountForm;
    private EditText email, password, nickname;
    private TextView typeChange, submit,findpassType;
//    private boolean isLoginMode = true;
    private AccountTask accountTask;
    private ProgressBar progressBar;
    private TaskType taskType;
    private TextView logo;
    private enum TaskType {
        LOGIN, REGISTER, FINDPASS
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        app = (CorebeauApp) getApplication();
        preferenceManager = PreferenceManager.getInstance(this);
        accountTask = new AccountTask(this);
        logo = (TextView)findViewById(R.id.logo);
        logo.setText("zfi");
        logo.setTextColor(Color.WHITE);
        logo.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
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


                String userId = PreferenceManager.getInstance(SplashActivity.this).getPreferencesString("userId");

                if (userId != null && userId != "") {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
                    MobclickAgent.onEvent(SplashActivity.this, "launcher", map);
//                }


            finish();

                } else {

                    // layout animation 넣지말고 edit text 따로해서 애니메이션 넣기

                    accountForm = (LinearLayout)findViewById(R.id.ll_login_form);
                    email = (EditText)findViewById(R.id.et_login_email);
                    password = (EditText)findViewById(R.id.et_login_pass);
                    nickname = (EditText)findViewById(R.id.et_login_nickname);
                    findpassType = (TextView)findViewById(R.id.tv_change_findpass);
                    typeChange = (TextView)findViewById(R.id.tv_type_change);
                    submit = (TextView)findViewById(R.id.tv_submit);
                    progressBar = (ProgressBar) findViewById(R.id.pb_splash);

                    findpassType.setOnClickListener(SplashActivity.this);
                    typeChange.setOnClickListener(SplashActivity.this);
                    submit.setOnClickListener(SplashActivity.this);

                    Animation appear1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.ani_login_form);
                    email.setVisibility(View.VISIBLE);
                    email.setAnimation(appear1);
                    password.setVisibility(View.VISIBLE);
                    password.setAnimation(appear1);
                    nickname.setVisibility(View.VISIBLE);
                    nickname.setAnimation(appear1);

                    Animation appear3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.ani_logo);
                    logo.setAnimation(appear3);
                    logo.setVisibility(View.GONE);
                    Animation appear2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.ani_login_register);


                    typeChange.setVisibility(View.VISIBLE);
                    typeChange.setAnimation(appear2);

                    submit.setVisibility(View.VISIBLE);
                    submit.setAnimation(appear2);

                    taskType = TaskType.REGISTER;
                    setTextByType();
                }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit :
                paramCheck();
                break;
            case R.id.tv_type_change :
                if(taskType == TaskType.REGISTER){
                    this.taskType = TaskType.LOGIN;
                    setTextByType();
                } else if(taskType == TaskType.LOGIN){
                    this.taskType = TaskType.REGISTER;
                    setTextByType();
                } else {
                    this.taskType = TaskType.LOGIN;
                    setTextByType();
                }
                break;
            case R.id.tv_change_findpass :
                this.taskType = TaskType.FINDPASS;
                setTextByType();
                break;
        }
    }

    private void setTextByType(){
        switch (taskType) {
            case LOGIN:
                findpassType.setVisibility(View.VISIBLE);
                findpassType.setText("are you forget password?");
                typeChange.setText("register");
                submit.setText("login");

//                email.setVisibility(View.VISIBLE);
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


    private void paramCheck(){

        // toast들  text animation으로 대체
        // toast 색갈 변경.... custom toast

        trim_email = email.getText().toString().trim();
        trim_password = password.getText().toString().trim();
        trim_nickname = password.getText().toString().trim();

        switch (taskType) {
            case LOGIN:
                if(loginParamCheck()){
                    loginTask(trim_email,trim_password);
                };
                break;

            case REGISTER:
                if(registerParamCheck()){
                    registerTask(trim_email, trim_password, trim_nickname);
                }
                break;

            case FINDPASS:
                if(findPasswordParamCheck()) {
                    findPasswordTask(trim_email);
                }
                break;
        }
    }

    private boolean loginParamCheck(){
        if (TextUtils.isEmpty(trim_email)) {
            Utils.showToast(SplashActivity.this, "email empty");
            return false;
//        } else if (trim_email) {  // check email form
        } else if (TextUtils.isEmpty(trim_password)) {
            Utils.showToast(SplashActivity.this, "password empty");
            return false;
        } else if(trim_password.length()< 8 || trim_password.length()> 16){
            Utils.showToast(SplashActivity.this, "password must be 8~16 word");
            return false;
        }
        return true;
    }

    private boolean registerParamCheck(){
        if (TextUtils.isEmpty(trim_email)) {
            Utils.showToast(SplashActivity.this, "email empty");
            return false;
//        } else if (trim_email) {  // check email form

        } else if (TextUtils.isEmpty(trim_password)) {
            Utils.showToast(SplashActivity.this, "password empty");
            return false;
        } else if(trim_password.length()< 8 || trim_password.length()> 16){
            Utils.showToast(SplashActivity.this, "password must be 8~16 word");
            return false;
        }else if(taskType == TaskType.REGISTER & trim_nickname.isEmpty()){
            Utils.showToast(SplashActivity.this, "nickname empty");
            return false;
//        }else if(isRegister == true & trim_nickname.isEmpty()){
//            Utils.showToast(SplashActivity.this, "nickname empty");
            //nick name   %^&*( impossible and 7 ~ 1? word
        }
        return true;
    }

    private boolean findPasswordParamCheck(){
        if (TextUtils.isEmpty(trim_email)) {
            Utils.showToast(SplashActivity.this, "email empty");
            return false;
//        } else if (trim_email) {  // check email form
        }
        return true;
    }

    private void loginTask(String email, String password){
        progressBar.setVisibility(View.VISIBLE);
        accountTask.login(email, password, new AsyncCallBacks.TwoOne<Integer, String, String>() {

            @Override
            public void onSuccess(Integer state, String msg) {
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);

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


    private void registerTask(String email, String password, String nickname){
        progressBar.setVisibility(View.VISIBLE);
        accountTask.register(email, password, nickname, new AsyncCallBacks.ZeroOne<String>() {

            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);

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


    private void findPasswordTask(String email){
        progressBar.setVisibility(View.VISIBLE);
//        accountTask.findPassword(email, password, nickname, new AsyncCallBacks.ZeroOne<String>() {
//
//            @Override
//            public void onSuccess() {
//                progressBar.setVisibility(View.GONE);
//                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//
//                startActivity(intent);
//                finish();
//            }
//
//            @Override
//            public void onError(String errorMsg) {
//                progressBar.setVisibility(View.GONE);
//                Utils.showToast(SplashActivity.this, errorMsg);
//            }
//        });
    }

}
