package com.zifei.corebeau.user.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.ui.view.CircularImageView;
import com.zifei.corebeau.common.ui.widget.NavigationBar;
import com.zifei.corebeau.post.ui.PostDetailActivity;
import com.zifei.corebeau.user.bean.OtherUserInfo;
import com.zifei.corebeau.user.task.OtherUserTask;
import com.zifei.corebeau.user.ui.adapter.OtherUserPostAdapter;
import com.zifei.corebeau.user.ui.adapter.OtherUserPostAdapter.OnUserDetailStartClickListener;
import com.zifei.corebeau.utils.Utils;

/**
 * Created by im14s_000 on 2015/3/28.
 */
public class OtherUserActivity extends ActionBarActivity implements OnUserDetailStartClickListener{

	private ListView postList;
	private CircularImageView circularImageView;
	private OtherUserTask otherUserTask;
	private OtherUserPostAdapter otherUserPostAdapter;
	private DisplayImageOptions imageOptions;
	private ImageLoader imageLoader;
	private ImageLoaderConfiguration config;
	private ImageView backgroundImageView;
	private ProgressBar progressBar;
	private String userId;
	private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_user);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        getSupportActionBar().setTitle("nickname..");
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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.bar_other_user, menu);
      return true;
    } 
    
    @SuppressLint("NewApi")
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      case R.id.other_user:
    	  menuItem=item;
    	  menuItem.expandActionView();
        break;
      default:
        break;
      }

      return true;
    } 



	private void init() {
		otherUserTask = new OtherUserTask(this);
		postList = (ListView) findViewById(R.id.lv_my_post);
		progressBar = (ProgressBar) findViewById(R.id.pb_my_post);
		LayoutInflater layoutInflater = getLayoutInflater();
		
		View header = layoutInflater.inflate(R.layout.layout_userpage_header,
				postList, false);
		postList.addHeaderView(header, null, false);
		otherUserPostAdapter = new OtherUserPostAdapter(this, postList);
		postList.setAdapter(otherUserPostAdapter);
		circularImageView = (CircularImageView) findViewById(R.id.civ_userpage_icon);
		backgroundImageView = (ImageView) findViewById(R.id.iv_userpage_background);
		
		
		

		setDefault();
		otherUserPostAdapter.setOnUserDetailStartClickListener(this);
		
		getOtherUserInfo();
		postListTask();
	}

	private void setDefault() {
		imageLoader.displayImage("drawable://" + R.drawable.my_default,
				circularImageView, imageOptions);
	}

	private void getOtherUserInfo() {
		otherUserTask.getOtherUserInfo(userId, new AsyncCallBacks.TwoOne<Integer, OtherUserInfo, String>() {

			@Override
			public void onSuccess(Integer status,OtherUserInfo otherUserInfo) {
				progressBar.setVisibility(View.GONE);

			}

			@Override
			public void onError(String msg) {
				progressBar.setVisibility(View.GONE);
				Utils.showToast(OtherUserActivity.this, msg);
			}

		});
	}
	
	private void postListTask() {
		progressBar.setVisibility(View.VISIBLE);
//		otherUserTask.getMyItemList(new AsyncCallBacks.OneOne<MyPostListResponse, String>() {
//
//			@Override
//			public void onSuccess(MyPostListResponse response) {
//				progressBar.setVisibility(View.GONE);
//
//				PageBean<ItemInfo> pageBean = (PageBean<ItemInfo>) response
//						.getPageBean();
//				List<ItemInfo> list = pageBean.getList();
//				if (response.getPageBean().getList().size() <= 0) {
//
//				} else {
//					otherUserPostAdapter.addData(list, false);
//				}
//			}
//
//			@Override
//			public void onError(String msg) {
//				progressBar.setVisibility(View.GONE);
//				Utils.showToast(OtherUserActivity.this, msg);
//			}
//
//		});
	}


	@Override
	public void onUserDetailStartClicked(View view, int position) {
		ItemInfo itemInfo = otherUserPostAdapter.getData().get(position);
		Intent intent = new Intent(this, PostDetailActivity.class);
		intent.putExtra("itemInfo", itemInfo);
		startActivity(intent);
	}
}
