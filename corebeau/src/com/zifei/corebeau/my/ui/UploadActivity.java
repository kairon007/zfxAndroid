package com.zifei.corebeau.my.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.analytics.MobclickAgent;
import com.zifei.corebeau.R;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.ui.BarActivity;
import com.zifei.corebeau.common.ui.SplashActivity;

import com.zifei.corebeau.my.task.UploadTask;
import com.zifei.corebeau.my.task.UploadTask.OnUploadStatusListener;
import com.zifei.corebeau.my.ui.selector.MultiImageSelectorActivity;
import com.zifei.corebeau.utils.StringUtil;
import com.zifei.corebeau.utils.Utils;

public class UploadActivity extends BarActivity implements OnClickListener, OnUploadStatusListener {

	private static final int REQUEST_IMAGE = 2;
	private ArrayList<String> mSelectPath;
	private DisplayImageOptions imageOptions;
	private ImageLoader imageLoader;
	private ImageLoaderConfiguration config;
	private GridView gridView;
	private UploadTask uploadTask;
	private ProgressBar progressBar;
	private EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload);
		startSelectActivity();
		gridView = (GridView)findViewById(R.id.gv_upload_image);
		progressBar = (ProgressBar)findViewById(R.id.pb_upload);
		editText = (EditText)findViewById(R.id.et_upload_text);
		
		initLoader();
		setActivityStatus();
		uploadTask = new UploadTask(this);
		uploadTask.setonTouchUpCallBackListener(this);
		//TODO disable submit and loading....
	}
	
	private void setActivityStatus(){
		setNavTitle("upload");
		setNavRightText("submit");
		navi.setRightTextVisible(true);
		navi.rightText.setOnClickListener(this);
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

	private void startSelectActivity() {
		Intent intent = new Intent(this, MultiImageSelectorActivity.class);
		intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
		intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 5);
		intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE,
				MultiImageSelectorActivity.MODE_MULTI);
		startActivityForResult(intent, REQUEST_IMAGE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_IMAGE) {
			if (resultCode == RESULT_OK) {
				mSelectPath = data
						.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
				
				gridView.setAdapter(new ImageAdapter(UploadActivity.this, mSelectPath));
			}
		}
	}
	
	private void submit(){
		progressBar.setVisibility(View.VISIBLE);
		uploadTask.getToken(mSelectPath, new AsyncCallBacks.TwoTwo<Integer, String, Integer, String>() {

			@Override
			public void onSuccess(Integer state, String msg) {
				
			}

			@Override
			public void onError(Integer state, String msg) {
				progressBar.setVisibility(View.GONE);
				Utils.showToast(UploadActivity.this, msg);
			}
		});
	}

	
	final Handler handler = new Handler();
	
	private void upload(String message){
		uploadTask.upload(message);
		
		Runnable run = new Runnable() {
			@Override
			public void run() {
				Utils.showToast(UploadActivity.this, "submit success");
				finish();
				
			}
		};
		handler.postDelayed(run, 500);
		
	}
	
	public class ImageAdapter extends BaseAdapter {

		private Context mContext;
		private List<String> urls;

		public ImageAdapter(Context c, List<String> urls) {
			mContext = c;
			this.urls = urls;
		}

		@Override
		public int getCount() {
			return urls.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView == null) {
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(350, 350));
				imageView.setBackgroundResource(R.drawable.upload_preimage);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			} else {
				imageView = (ImageView) convertView;
			}

			imageLoader.displayImage("file:///"+urls.get(position), imageView,
					imageOptions);

			return imageView;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.navi_bar_right_text:
			submit();
			break;
		}
	}
	
	

	@Override
	public void uploadFinish(boolean status) {
		
		if(status){
			upload(editText.getText().toString());
		}else{
			if(progressBar != null){
				progressBar.setVisibility(View.GONE);
			}
			Utils.showToast(UploadActivity.this, "upload fail.... reuplod plz");
		}
	}

}
