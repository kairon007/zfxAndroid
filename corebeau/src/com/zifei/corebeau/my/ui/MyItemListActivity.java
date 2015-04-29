package com.zifei.corebeau.my.ui;

import java.io.File;
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
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zifei.corebeau.R;
import com.zifei.corebeau.account.task.UserInfoService;
import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.bean.PageBean;
import com.zifei.corebeau.bean.UserInfo;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.ui.view.CircularImageView;
import com.zifei.corebeau.my.bean.response.MyPostListResponse;
import com.zifei.corebeau.my.task.MyTask;
import com.zifei.corebeau.my.task.UploadTask;
import com.zifei.corebeau.my.ui.adapter.MyItemListAdapter;
import com.zifei.corebeau.my.ui.adapter.MyItemListAdapter.OnMyDetailStartClickListener;
import com.zifei.corebeau.my.ui.crop.Crop;
import com.zifei.corebeau.utils.StringUtil;
import com.zifei.corebeau.utils.Utils;

public class MyItemListActivity extends Activity implements OnClickListener,
		OnMyDetailStartClickListener {
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
	private UserInfoService userInfoService;
	private UserInfo userInfo;
	private TextView nickName;
	private UploadTask uploadTask;;

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
		uploadTask = new UploadTask(this);
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
		nickName = (TextView) findViewById(R.id.tv_my_post_nickname);

		postListTask();

		setUserInfo();
		myPostAdapter.setOnMyDetailStartClickListener(this);
		circularImageView.setOnClickListener(this);
		nickName.setOnClickListener(this);
	}

	private void setUserInfo() {
		userInfoService = new UserInfoService(this);
		userInfo = userInfoService.getCurentUserInfo();
		if (userInfo == null) {
			return;
		}

		String nick = userInfo.getNickName();
		if (nick != null) {
			nickName.setText(nick);
		}else{
			nickName.setText("guest"+userInfo.getUserId());
		}

		String iconUrl = userInfo.getPicThumbUrl();
		if (iconUrl == null || StringUtil.isEmpty(iconUrl)) {
			imageLoader.displayImage("drawable://" + R.drawable.my_default,
					circularImageView, imageOptions);
		} else {
			imageLoader.displayImage(iconUrl, circularImageView, imageOptions);
		}
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
		uploadTask.getProfileToken(uri.getPath(),
				new AsyncCallBacks.TwoTwo<Integer, String, Integer, String>() {

					@Override
					public void onSuccess(Integer state, String msg) {
						Utils.showToast(MyItemListActivity.this, msg);
					}

					@Override
					public void onError(Integer state, String msg) {
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
			Crop.pickImage(this);
			break;
		case R.id.tv_my_post_nickname:
			
		}
	}
	
	private void beginCrop(Uri source) {
		Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
		Crop.of(source, destination).asSquare().start(this);
	}

	private void handleCrop(int resultCode, Intent result) {
		if (resultCode == RESULT_OK) {
			submit(Crop.getOutput(result));
		} else if (resultCode == Crop.RESULT_ERROR) {
			Toast.makeText(this, Crop.getError(result).getMessage(),
					Toast.LENGTH_SHORT).show();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQ_CODE_PICK_GALLERY
				&& resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();
			submit(uri);
		} else if (requestCode == REQ_CODE_MY_DETAIL) {
			List<ItemInfo> list = (List<ItemInfo>) data
					.getSerializableExtra("itemList");
			myPostAdapter.clearAdapter();
			myPostAdapter.addData(list, false);
		} else if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
			beginCrop(data.getData());
		} else if (requestCode == Crop.REQUEST_CROP) {
			handleCrop(resultCode, data);
		}
	}

	@Override
	public void onMyDetailStartClicked(View view, int position) {
		ItemInfo itemInfo = myPostAdapter.getData().get(position);
		Intent intent = new Intent(this, MyItemDetailActivity.class);
		intent.putExtra("itemInfo", itemInfo);
		startActivityForResult(intent, REQ_CODE_MY_DETAIL);
	}

}
