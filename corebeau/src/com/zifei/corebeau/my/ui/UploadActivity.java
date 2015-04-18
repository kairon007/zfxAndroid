package com.zifei.corebeau.my.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.zifei.corebeau.R;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.ui.BarActivity;
import com.zifei.corebeau.my.bean.QiniuResponse;
import com.zifei.corebeau.my.qiniu.QiniuTask;
import com.zifei.corebeau.my.qiniu.up.UpParam;
import com.zifei.corebeau.my.qiniu.up.UploadHandler;
import com.zifei.corebeau.my.qiniu.up.rs.UploadResultCallRet;
import com.zifei.corebeau.my.qiniu.up.slice.Block;
import com.zifei.corebeau.my.task.MyTask;
import com.zifei.corebeau.my.ui.selector.MultiImageSelectorActivity;
import com.zifei.corebeau.utils.Utils;

public class UploadActivity extends BarActivity implements OnClickListener {

	private static final int REQUEST_IMAGE = 2;
	private ArrayList<String> mSelectPath;
	private DisplayImageOptions imageOptions;
	private ImageLoader imageLoader;
	private ImageLoaderConfiguration config;
	private GridView gridView;
	private MyTask myTask;
	private QiniuTask qiniuTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload);
		startSelectActivity();
		gridView = (GridView)findViewById(R.id.gv_upload_image);
		initLoader();
		setActivityStatus();
		myTask = new MyTask(this);
		qiniuTask = new QiniuTask(this);
		
		qiniuTask.initBuildToken();
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
				ArrayList<String> imageList = new ArrayList<String>();
				for (String p : mSelectPath) {
					imageList.add(p);
					qiniuTask.preUpload(Uri.fromFile(new File(p)), uploadHandler);
				}
				gridView.setAdapter(new ImageAdapter(this,imageList));
			}
		}
	}
	
	private void submit(){
		qiniuTask.doUpload();
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
	
	
	public UploadHandler uploadHandler = new UploadHandler() {
		@Override
		protected void onProcess(long contentLength, long currentUploadLength, long lastUploadLength, UpParam p, Object passParam) {
		}

		@Override
		protected void onSuccess(UploadResultCallRet ret, UpParam p, Object passParam) {
			Utils.showToast(UploadActivity.this, "success!!");
			try {
				String sourceId = qiniuTask.generateSourceId(p, passParam);
				qiniuTask.clean(sourceId);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		protected void onFailure(UploadResultCallRet ret, UpParam p, Object passParam) {
			Utils.showToast(UploadActivity.this, "fail!");
			if (ret.getException() != null) {
				ret.getException().printStackTrace();
			}
		}

		@Override
		protected void onBlockSuccess(List<Block> uploadedBlocks, Block block, UpParam p, Object passParam) {
			Utils.showToast(UploadActivity.this, "block success!!");
			try {
				String sourceId = qiniuTask.generateSourceId(p, passParam);
				qiniuTask.addBlock(sourceId, block);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	};
	
}
