package com.zifei.corebeau.my.ui;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.qiniu.android.common.Config;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.zifei.corebeau.R;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.ui.BarActivity;
import com.zifei.corebeau.my.bean.MyPostListResponse;
import com.zifei.corebeau.my.bean.QiniuResponse;
import com.zifei.corebeau.my.task.MyTask;
import com.zifei.corebeau.test.TestData;
import com.zifei.corebeau.utils.StringUtil;

public class UploadActivity extends BarActivity implements OnClickListener {

	private static final int REQUEST_IMAGE = 2;
	private ArrayList<String> mSelectPath;
	private DisplayImageOptions imageOptions;
	private ImageLoader imageLoader;
	private ImageLoaderConfiguration config;
	private GridView gridView;
	private MyTask myTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload);
		startSelectActivity();
		gridView = (GridView)findViewById(R.id.gv_upload_image);
		initLoader();
		setActivityStatus();
		myTask = new MyTask(this);
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

		// whether show camera
		intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);

		// max select image amount
		intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);

		// select mode (MultiImageSelectorActivity.MODE_SINGLE OR
		// MultiImageSelectorActivity.MODE_MULTI)
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
				// StringBuilder sb = new StringBuilder();
				ArrayList<String> imageList = new ArrayList<String>();
				for (String p : mSelectPath) {
					// sb.append(p);
					// sb.append("\n");
					imageList.add(p);
				}
				// mResultText.setText(sb.toString());
				gridView.setAdapter(new ImageAdapter(this,imageList));
			}
		}
	}
	
	private void submit(final ArrayList<String> imageList){
		new Thread(new Runnable(){
            @Override
            public void run() {
            	for (String dataUri : imageList) {
            		uploadImage(dataUri);
				}
                
            }
        }).start();
	}
	
	private void uploadImage(String dataUri) {
		// data = <File对象、或 文件路径、或 字节数组>
		// String data =
		String key = "fix where? get? random?";
		String token = "getToken()  value";

		if (token != null) {
			UploadManager uploadManager = new UploadManager();
			uploadManager.put(dataUri, key, token, new UpCompletionHandler() {
				@Override
				public void complete(String key, ResponseInfo info,
						JSONObject response) {
				}
			}, null);
		}
	}
	
	private void getToken() {
		myTask.getQiniuToken(new AsyncCallBacks.OneOne<QiniuResponse, String>() {

			@Override
			public void onSuccess(QiniuResponse msg) {
			}

			@Override
			public void onError(String msg) {
			}
		});
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
				imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(8, 8, 8, 8);
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
	}

}
