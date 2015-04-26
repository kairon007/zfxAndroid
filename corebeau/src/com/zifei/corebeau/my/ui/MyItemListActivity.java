package com.zifei.corebeau.my.ui;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.bean.PageBean;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.ui.view.CircularImageView;
import com.zifei.corebeau.my.bean.response.MyPostListResponse;
import com.zifei.corebeau.my.task.MyTask;
import com.zifei.corebeau.my.ui.adapter.MyItemListAdapter;
import com.zifei.corebeau.my.ui.adapter.MyItemListAdapter.OnMyDetailStartClickListener;
import com.zifei.corebeau.utils.Utils;

public class MyItemListActivity extends Activity implements OnClickListener,
		OnMyDetailStartClickListener{
	private ListView postList;
	private CircularImageView circularImageView;
	private MyTask myTask;
	private MyItemListAdapter myPostAdapter;
	private DisplayImageOptions imageOptions;
	private ImageLoader imageLoader;
	private ImageLoaderConfiguration config;
	private ImageView backgroundImageView;
	private ImageView write;
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_post);
		initLoader();
		init();
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

	private void init() {
		myTask = new MyTask(this);
		postList = (ListView) findViewById(R.id.lv_my_post);
		write = (ImageView) findViewById(R.id.iv_write);
		progressBar = (ProgressBar) findViewById(R.id.pb_my_post);
		write.setOnClickListener(this);
		LayoutInflater layoutInflater = getLayoutInflater();
		View header = layoutInflater.inflate(R.layout.layout_my_post_header,
				postList, false);
		postList.addHeaderView(header, null, false);
		myPostAdapter = new MyItemListAdapter(this, postList);
		postList.setAdapter(myPostAdapter);
		circularImageView = (CircularImageView) findViewById(R.id.civ_my_post_icon);
		backgroundImageView = (ImageView) findViewById(R.id.iv_my_post_background);
		
		postListTask();

		setDefault();
		myPostAdapter.setOnMyDetailStartClickListener(this);
		backgroundImageView.setOnClickListener(this);
	}

	private void setDefault() {
		imageLoader.displayImage("drawable://" + R.drawable.my_default,
				circularImageView, imageOptions);
	}

	private void getUserInfo() {
		// myTask.get

		// String urlThumb = response.;
		// if (!StringUtil.isEmpty(urlThumb)) {
		// imageLoader.displayImage(urlThumb, circularImageView,
		// imageOptions);
		// } else {
		// }
		// // if(data.size() > 0){
		// myPostAdapter.addData(TestData.getSpotList(), false);
		// myPostAdapter.notifyDataSetChanged();
		// // }else{
		// postList.setEmptyView(findViewById(android.R.id.empty));
		//
		// String urlThumb =
		// "http://e0.vingle.net/t_us_m/opdr3ab94hxosfpwyl2x";
		// if (!StringUtil.isEmpty(urlThumb)) {
		// imageLoader.displayImage(urlThumb, circularImageView,
		// imageOptions);
		// } else {
		// }
		//
		// String urlBackground =
		// "http://e1.vingle.net/t_ca_xl/h3t1sdj903oevpovb1q6.jpg";
		// if (!StringUtil.isEmpty(urlBackground)) {
		// imageLoader.displayImage(urlBackground,
		// backgroundImageView, imageOptions);
		// } else {}
	}

	private void postListTask() {
		progressBar.setVisibility(View.VISIBLE);
		myTask.getMyItemList(new AsyncCallBacks.OneOne<MyPostListResponse, String>() {

			@Override
			public void onSuccess(MyPostListResponse response) {
				progressBar.setVisibility(View.GONE);

				PageBean<ItemInfo> pageBean = (PageBean<ItemInfo>) response
						.getPageBean();
				List<ItemInfo> list = pageBean.getList();
				if (response.getPageBean().getList().size() <= 0) {

				} else {
					myPostAdapter.addData(list, false);
				}
			}

			@Override
			public void onError(String msg) {
				progressBar.setVisibility(View.GONE);
				Utils.showToast(MyItemListActivity.this, msg);
			}

		});
	}

	private void submit(Uri uri) {
		myTask.getToken(uri.getPath(),
				new AsyncCallBacks.TwoTwo<Integer, String, Integer, String>() {

					@Override
					public void onSuccess(Integer state, String msg) {

					}

					@Override
					public void onError(Integer state, String msg) {
						progressBar.setVisibility(View.GONE);
						Utils.showToast(MyItemListActivity.this, msg);
					}
				});
	}

	private final int REQ_CODE_PICK_GALLERY = 900001;
	private final int REQ_CODE_MY_DETAIL = 900002;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_write:
			Intent intent = new Intent(this, UploadActivity.class);
			startActivity(intent);
			break;
		case R.id.civ_my_post_icon:
			Intent i = new Intent(Intent.ACTION_PICK);
			i.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
			i.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(i, REQ_CODE_PICK_GALLERY);
			break;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQ_CODE_PICK_GALLERY
				&& resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();
			submit(uri);
		}else if(requestCode ==REQ_CODE_MY_DETAIL){
			List<ItemInfo> list =  (List<ItemInfo>) data.getSerializableExtra("itemList");
			myPostAdapter.clearAdapter();
			myPostAdapter.addData(list, false);
		}
	}

	@Override
	public void onMyDetailStartClicked(View view, int position) {
		ItemInfo itemInfo = myPostAdapter.getData().get(position);
		Intent intent = new Intent(this, MyItemDetailActivity.class);
		intent.putExtra("itemInfo", itemInfo);
		startActivityForResult(intent,REQ_CODE_MY_DETAIL);
	}

}
