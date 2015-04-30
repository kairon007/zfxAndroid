package com.zifei.corebeau.my.ui;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zifei.corebeau.R;
import com.zifei.corebeau.account.task.UserInfoService;
import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.bean.PageBean;
import com.zifei.corebeau.bean.UserInfo;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.ui.MainActivity;
import com.zifei.corebeau.common.ui.MainActivity.SectionsPagerAdapter;
import com.zifei.corebeau.common.ui.view.CircularImageView;
import com.zifei.corebeau.my.bean.response.MyPostListResponse;
import com.zifei.corebeau.my.task.MyTask;
import com.zifei.corebeau.my.ui.adapter.MyItemListAdapter;
import com.zifei.corebeau.my.ui.adapter.MyItemListAdapter.OnMyDetailStartClickListener;
import com.zifei.corebeau.my.ui.fragment.MyFragment;
import com.zifei.corebeau.my.ui.fragment.MyItemFragment;
import com.zifei.corebeau.my.ui.fragment.ScrapPostFragment;
import com.zifei.corebeau.search.ui.fragment.SearchFragment;
import com.zifei.corebeau.spot.ui.fragment.SpotFragment;
import com.zifei.corebeau.utils.StringUtil;
import com.zifei.corebeau.utils.Utils;

public class MyItemListActivity extends FragmentActivity implements OnClickListener{
	
	private CircularImageView circularImageView;
	private DisplayImageOptions imageOptions;
	private ImageLoader imageLoader;
	private ImageLoaderConfiguration config;
	private ImageView backgroundImageView;
	private ImageView write;
	private ProgressBar progressBar;
	private UserInfoService userInfoService;
	private UserInfo userInfo;
	private TextView nickName, tabMyPost, tabMyScrap;
	private ViewPager mViewPager;
	private SectionsPagerAdapter mSectionsPagerAdapter;
	private static final int PAGE_COUNT = 2;
	 private static int selectedPage = 0;
	    private static int preSelectedPage = 0;
	    private boolean isClick = false;
	    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_post);
		initLoader();
		init();
		initTabListener();
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
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		tabMyPost = (TextView) findViewById(R.id.tv_my_post_tab);
		tabMyPost.setText("my post");
		tabMyScrap = (TextView) findViewById(R.id.tv_my_scrap_tab);
		tabMyScrap.setText("my scrap");
		mViewPager = (ViewPager) findViewById(R.id.pager_mypage);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(listener);
		write = (ImageView) findViewById(R.id.iv_write);
		progressBar = (ProgressBar) findViewById(R.id.pb_my_post);
		write.setOnClickListener(this);
		circularImageView = (CircularImageView) findViewById(R.id.civ_my_post_icon);
		backgroundImageView = (ImageView) findViewById(R.id.iv_my_post_background);
		nickName = (TextView) findViewById(R.id.tv_my_post_nickname);

		setUserInfo();
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



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_write:
			Intent intent = new Intent(this, UploadActivity.class);
			startActivity(intent);
			break;
		case R.id.civ_my_post_icon:
			
			break;
		case R.id.tv_my_post_nickname:
			
		}
	}
	
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment f = null;
			switch (position) {
			case 0:
				f = Fragment.instantiate(MyItemListActivity.this,
						MyItemFragment.class.getName(), null);
				break;
			case 1:
				f = Fragment.instantiate(MyItemListActivity.this,
						ScrapPostFragment.class.getName(), null);
				break;
			default:
				f = Fragment.instantiate(MyItemListActivity.this,
						MyItemFragment.class.getName(), null);
				break;
			}
			return f;
		}

		@Override
		public int getCount() {
			return PAGE_COUNT;
		}
	}
	
	private ViewPager.SimpleOnPageChangeListener listener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            preSelectedPage = selectedPage;
            selectedPage = position;
            if(!isClick){
                switch(position) {
                case 0:
                    updateSelectedTab(0);
                    break;
                case 1:
                    updateSelectedTab(1);
                    break;
                default:
                    updateSelectedTab(0);
                    break;
                }
            }
        }
        
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
    
    private void initTabListener() {
        View view;
        view = findViewById(R.id.rl_my_post_tab);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isClick = true;
                if (preSelectedPage == 1) {
                    mViewPager.setCurrentItem(0, true);
                } else {
                    isClick = false;
                    mViewPager.setCurrentItem(0, false);
                    updateSelectedTab(0);
                }
            }
        });
        
        view = findViewById(R.id.rl_my_scrap_tab);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isClick = true;
                if (preSelectedPage == 0) {
                    mViewPager.setCurrentItem(1, true);
                } else {
                    isClick = false;
                    mViewPager.setCurrentItem(1, false);
                    updateSelectedTab(1);
                }
            }
        });
    }
    
	private void updateSelectedTab(int nSelected) {
		if (nSelected == 0) {
			tabMyPost.setTextColor(Color.BLACK);
			tabMyScrap.setTextColor(Color.GRAY);
			
		}else if (nSelected == 1) {
			tabMyPost.setTextColor(Color.BLACK);
			tabMyScrap.setTextColor(Color.GRAY);
		}
	}
}
