package com.zifei.corebeau.my.ui;

import java.io.Serializable;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.ui.widget.indicator.CirclePageIndicator;
import com.zifei.corebeau.my.task.MyTask;
import com.zifei.corebeau.my.ui.widget.MyItemDetailBottomBar;
import com.zifei.corebeau.my.ui.widget.MyItemDetailBottomBar.OnMyItemDeleteListener;
import com.zifei.corebeau.post.bean.UserUploadPicture;
import com.zifei.corebeau.post.bean.response.ItemDetailResponse;
import com.zifei.corebeau.post.task.PostTask;
import com.zifei.corebeau.post.ui.adapter.ImageAdapter;
import com.zifei.corebeau.post.ui.view.PostViewPager;

public class MyItemDetailActivity extends FragmentActivity implements
		OnClickListener, OnMyItemDeleteListener{

	private PostViewPager mPager;
	private PostTask postTask;
	private ItemInfo itemInfo;
	private String itemId;
	private TextView tvMsg;
	private CirclePageIndicator mIndicator;
	private MyItemDetailBottomBar bottomBar;
	private MyTask myTask;
//	private OnMyItemDeleteListener onMyItemDeleteListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		Intent intent = getIntent();
		itemInfo = (ItemInfo) intent.getSerializableExtra("itemInfo");
		itemId = itemInfo.getItemId();
		init();

	}

	@SuppressLint("ResourceAsColor")
	private void init() {
		myTask = new MyTask(this);
		postTask = new PostTask(this);
		mPager = (PostViewPager) findViewById(R.id.vp_post_image);
		mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
		mIndicator.setOnPageChangeListener(new PageChangeListener());
		mIndicator.setFillColor(R.color.spot_inner_divider);
		tvMsg = (TextView) findViewById(R.id.tv_post_msg);

		bottomBar = new MyItemDetailBottomBar(this);
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.BOTTOM;
		((FrameLayout) findViewById(android.R.id.content)).addView(bottomBar,
				lp);
		bottomBar.setCurrentItem(itemInfo);
		bottomBar.registerOnMyItemDeleteListener(this);
		
		setPostData();

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		getItemDetail();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	private void getItemDetail() { // get isScrap, isLike
		postTask.getItemDetail(itemId,
				new AsyncCallBacks.OneOne<ItemDetailResponse, String>() {
					@Override
					public void onSuccess(ItemDetailResponse response) {
						List<UserUploadPicture> picList = response
								.getPictureUrls();
						setPostImage(picList);

					}

					@Override
					public void onError(String result) {

					}
				});
	}

	private void setPostImage(List<UserUploadPicture> urlList) {
		mPager.setAdapter(new ImageAdapter(this, urlList));
		mIndicator.setViewPager(mPager);
	}

	private void setPostData() {

		tvMsg.setText(itemInfo.getTitle());

	}

	private class PageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int state) {
			if (state == PostViewPager.SCROLL_STATE_IDLE) {
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		default:
			break;
		}
	}

	@Override
	public void onMyListDataChanged(List<ItemInfo> list) {
		
		Intent intent = new Intent();
		intent.putExtra("itemList",(Serializable) list);
		this.setResult(RESULT_OK, intent);
		this.finish();

	}
	
}