package com.zifei.corebeau.my.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zifei.corebeau.R;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.my.task.UploadTask;
import com.zifei.corebeau.my.ui.selector.MultiImageSelectorActivity;
import com.zifei.corebeau.utils.Utils;

public class UploadActivity extends SherlockActivity {

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
		initActionBar();
		startSelectActivity();
		gridView = (GridView) findViewById(R.id.gv_upload_image);
		progressBar = (ProgressBar) findViewById(R.id.pb_upload);
		editText = (EditText) findViewById(R.id.et_upload_text);

		initLoader();
		uploadTask = new UploadTask(this);
	}

	private void initActionBar() {
		getSupportActionBar().setTitle(" upload");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
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
			if (mSelectPath.isEmpty()) {
				
			} else {
				submit();
			}
		} else if (item.getTitle().equals(" upload")) {
			finish();
		}
		return true;
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
				gridView.setAdapter(new ImageAdapter(UploadActivity.this,
						mSelectPath));
			}
		}
	}

	private void submit() {
		final String message = editText.getText().toString();
		progressBar.setVisibility(View.VISIBLE);
		// navi.rightTextClicker.setClickable(false);
		uploadTask.getToken(mSelectPath, message,
				new AsyncCallBacks.TwoTwo<Integer, String, Integer, String>() {

					@Override
					public void onSuccess(Integer state, String msg) {
						Utils.showToast(UploadActivity.this, "soon upload");
						finish();
					}

					@Override
					public void onError(Integer state, String msg) {
						progressBar.setVisibility(View.GONE);
						// navi.rightTextClicker.setClickable(true);
						Utils.showToast(UploadActivity.this, msg);
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

			imageLoader.displayImage("file:///" + urls.get(position),
					imageView, imageOptions);

			return imageView;
		}
	}

}
