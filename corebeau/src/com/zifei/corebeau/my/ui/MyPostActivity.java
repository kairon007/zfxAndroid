package com.zifei.corebeau.my.ui;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
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
import com.zifei.corebeau.my.ui.adapter.MyPostAdapter;
import com.zifei.corebeau.search.ui.adapter.SampleAdapter;
import com.zifei.corebeau.utils.StringUtil;

public class MyPostActivity extends Activity implements OnClickListener {
	private ListView postList;
	private CircularImageView circularImageView;
	private MyTask myTask;
	private MyPostAdapter myPostAdapter;
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
		init();
		initLoader();
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
		myPostAdapter = new MyPostAdapter(this, postList);
		postList.setAdapter(myPostAdapter);
		circularImageView = (CircularImageView) findViewById(R.id.civ_my_post_icon);
		backgroundImageView = (ImageView) findViewById(R.id.iv_my_post_background);
		postListTask();
	}

	private void postListTask() {
		progressBar.setVisibility(View.VISIBLE);
		myTask.getMyPostList(new AsyncCallBacks.OneOne<MyPostListResponse, String>() {

			@Override
			public void onSuccess(MyPostListResponse response) {
				progressBar.setVisibility(View.GONE);

				// String urlThumb = response.;
				// if (!StringUtil.isEmpty(urlThumb)) {
				// imageLoader.displayImage(urlThumb, circularImageView,
				// imageOptions);
				// } else {
				// }

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
				// } else {
			}

		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_write:
			Intent intent = new Intent(this, UploadActivity.class);
			startActivity(intent);
			break;
		}
	}
}
