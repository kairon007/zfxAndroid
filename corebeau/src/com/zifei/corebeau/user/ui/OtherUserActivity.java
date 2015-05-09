package com.zifei.corebeau.user.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
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
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.ui.view.CircularImageView;
import com.zifei.corebeau.post.ui.PostDetailActivity;
import com.zifei.corebeau.user.bean.OtherUserInfo;
import com.zifei.corebeau.user.task.OtherUserTask;
import com.zifei.corebeau.user.ui.adapter.OtherUserPostAdapter;
import com.zifei.corebeau.user.ui.adapter.OtherUserPostAdapter.OnUserDetailStartClickListener;
import com.zifei.corebeau.utils.Utils;

/**
 * Created by im14s_000 on 2015/3/28.
 */
public class OtherUserActivity extends SherlockActivity implements OnUserDetailStartClickListener{

	private ListView postList;
	private CircularImageView circularImageView;
	private OtherUserTask otherUserTask;
	private OtherUserPostAdapter otherUserPostAdapter;
	private DisplayImageOptions imageOptions;
	private ImageLoader imageLoader;
	private ImageLoaderConfiguration config;
	private ImageView backgroundImageView;
	private TextView nicknameTextView;
	private ProgressBar progressBar;
	private String userId;
	private String nickName;
	private String userImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_user);
        Intent intent = getIntent();
        ItemInfo itemInfo = (ItemInfo) intent.getSerializableExtra("itemInfo");
        userId = itemInfo.getUserId();
        nickName = itemInfo.getNickName();
        userImageUrl = itemInfo.getUserImageUrl();
        initActionBar();
        initLoader();
		init();
		
    }
    
	private void initActionBar(){
		getSupportActionBar().setTitle("other user");
		getSupportActionBar().setSubtitle(" "+nickName);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		 getSupportActionBar().setDisplayShowHomeEnabled(false);
	}
	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	      menu.add("follow")
       .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	    	if(item.getTitle().equals("other user")){
	    		finish();
	    	}else if(item.getTitle().equals("follow")){
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
		nicknameTextView = (TextView)findViewById(R.id.tv_userpage_nickname);

		otherUserPostAdapter.setOnUserDetailStartClickListener(this);
		setUserBaseInfo();
		getOtherUserInfo();
		postListTask();
	}

	
	private void setUserBaseInfo(){
		if(userImageUrl==null){
			imageLoader.displayImage("drawable://" + R.drawable.my_default,
					circularImageView, imageOptions);
		}else{
			imageLoader.displayImage(userImageUrl,
					circularImageView, imageOptions);
		}
		
		if(nickName!=null){
			nicknameTextView.setText(nickName);
		}
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
	
	private void follow(){
		
	}
}
