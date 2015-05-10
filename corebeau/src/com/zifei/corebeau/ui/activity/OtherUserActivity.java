package com.zifei.corebeau.ui.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.bean.PageBean;
import com.zifei.corebeau.bean.UserInfo;
import com.zifei.corebeau.bean.UserShowInfo;
import com.zifei.corebeau.bean.response.OtherUserInfoResponse;
import com.zifei.corebeau.bean.response.OtherUserItemListResponse;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.extra.CircularImageView;
import com.zifei.corebeau.task.FollowTask;
import com.zifei.corebeau.task.OtherUserTask;
import com.zifei.corebeau.task.UserInfoService;
import com.zifei.corebeau.ui.adapter.OtherUserPostAdapter;
import com.zifei.corebeau.ui.adapter.OtherUserPostAdapter.OnUserDetailStartClickListener;
import com.zifei.corebeau.utils.Utils;

/**
 * Created by im14s_000 on 2015/3/28.
 */
public class OtherUserActivity extends SherlockActivity implements
		OnUserDetailStartClickListener {

	private ListView postList;
	private CircularImageView circularImageView;
	private OtherUserTask otherUserTask;
	private FollowTask folowTask;
	private OtherUserPostAdapter otherUserPostAdapter;
	private DisplayImageOptions imageOptions, iconImageOptions;
	private ImageLoader imageLoader;
	private ImageLoaderConfiguration config;
	private ImageView backgroundImageView;
	private TextView nicknameTextView, followCnt, likeCnt, itemCnt;
	private ProgressBar progressBar;
	private String targetUserId;
	private String nickName;
	private String userImageUrl;
	private int currentPage;
	private UserInfoService userInfoService;
	private String myUserId;
	private UserShowInfo userShowInfo;
	private boolean isFollowed = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_another_user);

		Intent intent = getIntent();
		ItemInfo itemInfo = (ItemInfo) intent.getSerializableExtra("itemInfo");
		targetUserId = itemInfo.getUserId();
		nickName = itemInfo.getNickName();
		userImageUrl = itemInfo.getUserImageUrl();

		userInfoService = new UserInfoService(this);
		otherUserTask = new OtherUserTask(this);
		folowTask = new FollowTask(this);
		UserInfo myUserInfo = userInfoService.getCurentUserInfo();
		myUserId = myUserInfo.getUserId();

		initActionBar();
		initLoader();
		init();

	}

	private void initActionBar() {
		getSupportActionBar().setTitle("other user");
		getSupportActionBar().setSubtitle(" " + nickName);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!myUserId.equals(targetUserId)){
			menu.add("follow").setShowAsAction(
					MenuItem.SHOW_AS_ACTION_IF_ROOM
							| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().equals("other user")) {
			finish();
		} else if (item.getTitle().equals("follow")) {
			follow();
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

		iconImageOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.showImageOnFail(R.drawable.user_icon_default)
				.showImageForEmptyUri(R.drawable.user_icon_default)
				.showImageOnLoading(R.drawable.user_icon_default).build();
	}

	private void init() {
		otherUserTask = new OtherUserTask(this);
		folowTask = new FollowTask(this);
		postList = (ListView) findViewById(R.id.lv_another_user_post);
		progressBar = (ProgressBar) findViewById(R.id.pb_another_user_post);
		LayoutInflater layoutInflater = getLayoutInflater();

		View header = layoutInflater.inflate(R.layout.layout_userpage_header,
				postList, false);
		postList.addHeaderView(header, null, false);
		otherUserPostAdapter = new OtherUserPostAdapter(this, postList);
		postList.setAdapter(otherUserPostAdapter);
		circularImageView = (CircularImageView) findViewById(R.id.civ_userpage_icon);
		backgroundImageView = (ImageView) findViewById(R.id.iv_userpage_background);
		nicknameTextView = (TextView) findViewById(R.id.tv_userpage_nickname);
		followCnt = (TextView) findViewById(R.id.tv_otheruser_follow_cnt);
		likeCnt = (TextView) findViewById(R.id.tv_otheruser_like_cnt);
		itemCnt = (TextView) findViewById(R.id.tv_otheruser_item_cnt);

		otherUserPostAdapter.setOnUserDetailStartClickListener(this);
		setUserBaseInfo();
		getOtherUserInfo();
		postListTask();
	}

	private void setUserBaseInfo() {
		if (userImageUrl == null) {
			imageLoader.displayImage("drawable://"
					+ R.drawable.user_icon_default, circularImageView,
					iconImageOptions);
		} else {
			imageLoader.displayImage(userImageUrl, circularImageView,
					iconImageOptions);
		}

		if (nickName != null) {
			nicknameTextView.setText(nickName);
		}
	}

	private void getOtherUserInfo() {
		otherUserTask.getOtherUserInfo(targetUserId, new AsyncCallBacks.TwoOne<Integer, OtherUserInfoResponse, String>() {

			@Override
			public void onSuccess(Integer status, OtherUserInfoResponse response) {
				userShowInfo = response.getUserShowInfo();
				;
				
				followCnt.setText(String.valueOf(userShowInfo.getFollowedCount()));
//				likeCnt.setText(String.valueOf(userShowInfo.l));
				itemCnt.setText(String.valueOf(userShowInfo.getItemCount()));
				
				isFollowed = response.getIsFollowed();
			}

			@Override
			public void onError(String msg) {
				Utils.showToast(OtherUserActivity.this, msg);
			}

		});
	}

	private void postListTask() {
		progressBar.setVisibility(View.VISIBLE);
		otherUserTask.getOtherUserItem(targetUserId, currentPage,
				new AsyncCallBacks.OneOne<OtherUserItemListResponse, String>() {

					@Override
					public void onSuccess(OtherUserItemListResponse response) {
						progressBar.setVisibility(View.GONE);

						PageBean<ItemInfo> pageBean = (PageBean<ItemInfo>) response
								.getPageBean();
						List<ItemInfo> list = pageBean.getList();
						if (response.getPageBean().getList().size() <= 0) {

						} else {
							otherUserPostAdapter.addData(list, false);
						}
					}

					@Override
					public void onError(String msg) {
						progressBar.setVisibility(View.GONE);
						Utils.showToast(OtherUserActivity.this, msg);
					}

				});
	}

	@Override
	public void onUserDetailStartClicked(View view, int position) {
		ItemInfo itemInfo = otherUserPostAdapter.getData().get(position);
		Intent intent = new Intent(this, PostDetailActivity.class);
		intent.putExtra("itemInfo", itemInfo);
		startActivity(intent);
	}

	private void follow() {
		if(isFollowed){
			Utils.showToast(this, "already followed");
			return;
		}
		folowTask.addFollow(targetUserId,
				new AsyncCallBacks.OneOne<Response, String>() {

					@Override
					public void onSuccess(Response response) {
						Utils.showToast(OtherUserActivity.this,
								"follow success");
						isFollowed = true;
					}

					@Override
					public void onError(String msg) {
						Utils.showToast(OtherUserActivity.this, msg);
					}

				});
	}
}
