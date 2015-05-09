package com.zifei.corebeau.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.nineoldandroids.view.ViewHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.UserInfo;
import com.zifei.corebeau.extra.CircularImageView;
import com.zifei.corebeau.extra.parallaxheader.AlphaForegroundColorSpan;
import com.zifei.corebeau.extra.parallaxheader.PagerSlidingTabStrip;
import com.zifei.corebeau.extra.parallaxheader.ScrollTabHolder;
import com.zifei.corebeau.task.UserInfoService;
import com.zifei.corebeau.ui.fragment.MyItemFragment;
import com.zifei.corebeau.ui.fragment.ScrapItemFragment;
import com.zifei.corebeau.ui.fragment.ScrollTabHolderFragment;
import com.zifei.corebeau.utils.StringUtil;

public class MyPageActivity extends SherlockFragmentActivity implements
		OnClickListener, ScrollTabHolder, ViewPager.OnPageChangeListener {

	private CircularImageView circularImageView;
	private DisplayImageOptions imageOptions,iconImageOptions;
	private ImageLoader imageLoader;
	private ImageLoaderConfiguration config;
	private ImageView backgroundImageView;
	private ImageView write;
	private UserInfoService userInfoService;
	private UserInfo userInfo;
	private TextView nickName;
	private View mHeader;
	private PagerSlidingTabStrip mPagerSlidingTabStrip;
	private ViewPager mViewPager;
	private PagerAdapter mPagerAdapter;
	private int mActionBarHeight;
	private int mMinHeaderHeight;
	private int mHeaderHeight;
	private int mMinHeaderTranslation;
	private TypedValue mTypedValue = new TypedValue();
	private SpannableString mSpannableString;
	private AlphaForegroundColorSpan mAlphaForegroundColorSpan;

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
		iconImageOptions = new DisplayImageOptions.Builder()
		.cacheInMemory(true)
		.showImageOnFail(R.drawable.user_icon_default)
		.showImageForEmptyUri(R.drawable.user_icon_default)
		.showImageOnLoading(R.drawable.user_icon_default)
		.build();
	}

	private void init() {

		mMinHeaderHeight = getResources().getDimensionPixelSize(
				R.dimen.min_header_height);
		mHeaderHeight = getResources().getDimensionPixelSize(
				R.dimen.header_height);
		mMinHeaderTranslation = -mMinHeaderHeight + getActionBarHeight();

		backgroundImageView = (ImageView) findViewById(R.id.header_picture);

		mHeader = findViewById(R.id.fl_my_header);
		mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		mViewPager = (ViewPager) findViewById(R.id.pager_mypage);
		mViewPager.setOffscreenPageLimit(4);

		mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
		mPagerAdapter.setTabHolderScrollingContent(this);

		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
		mPagerSlidingTabStrip.setViewPager(mViewPager);
		mPagerSlidingTabStrip.setOnPageChangeListener(this);
		mSpannableString = new SpannableString("aa");
		mAlphaForegroundColorSpan = new AlphaForegroundColorSpan(0xffffffff);

		ViewHelper.setAlpha(getActionBarIconView(), 0f);

		getActionBar().setBackgroundDrawable(null);

		write = (ImageView) findViewById(R.id.iv_write);
		write.setOnClickListener(this);
		circularImageView = (CircularImageView) findViewById(R.id.civ_my_post_icon);
		nickName = (TextView) findViewById(R.id.tv_my_post_nickname);

		setUserInfo();
		circularImageView.setOnClickListener(this);
		nickName.setOnClickListener(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().equals("aa")) {
			finish();
		}
		return true;
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
		} else {
			nickName.setText("guest" + userInfo.getUserId());
		}

		String iconUrl = userInfo.getUrl();
		if (iconUrl != null && !StringUtil.isEmpty(iconUrl)) {
			imageLoader.displayImage(iconUrl, circularImageView, iconImageOptions);
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

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int position) {
		SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mPagerAdapter
				.getScrollTabHolders();
		ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);

		currentHolder.adjustScroll((int) (mHeader.getHeight() + ViewHelper
				.getTranslationY(mHeader)));
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount, int pagePosition) {
		if (mViewPager.getCurrentItem() == pagePosition) {
			int scrollY = getScrollY(view);
			ViewHelper.setTranslationY(mHeader,
					Math.max(-scrollY, mMinHeaderTranslation));
			float ratio = clamp(ViewHelper.getTranslationY(mHeader)
					/ mMinHeaderTranslation, 0.0f, 1.0f);
			setTitleAlpha(clamp(5.0F * ratio - 4.0F, 0.0F, 1.0F));
		}
	}

	@Override
	public void adjustScroll(int scrollHeight) {
		// nothing
	}

	public int getScrollY(AbsListView view) {
		View c = view.getChildAt(0);
		if (c == null) {
			return 0;
		}

		int firstVisiblePosition = view.getFirstVisiblePosition();
		int top = c.getTop();

		int headerHeight = 0;
		if (firstVisiblePosition >= 1) {
			headerHeight = mHeaderHeight;
		}

		return -top + firstVisiblePosition * c.getHeight() + headerHeight;
	}

	public static float clamp(float value, float max, float min) {
		return Math.max(Math.min(value, min), max);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public int getActionBarHeight() {
		if (mActionBarHeight != 0) {
			return mActionBarHeight;
		}

		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
			getTheme().resolveAttribute(android.R.attr.actionBarSize,
					mTypedValue, true);
		}

		// else{
		// getTheme().resolveAttribute(R.attr.actionBarSize, mTypedValue, true);
		// }

		mActionBarHeight = TypedValue.complexToDimensionPixelSize(
				mTypedValue.data, getResources().getDisplayMetrics());

		return mActionBarHeight;
	}

	private void setTitleAlpha(float alpha) {
		mAlphaForegroundColorSpan.setAlpha(alpha);
		mSpannableString.setSpan(mAlphaForegroundColorSpan, 0,
				mSpannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		getActionBar().setTitle(mSpannableString);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private ImageView getActionBarIconView() {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			return (ImageView) findViewById(android.R.id.home);
		}

		return (ImageView) findViewById(R.drawable.asv);
	}

	public class PagerAdapter extends FragmentPagerAdapter {

		private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
		private final String[] TITLES = { "my post", "my scrap" };
		private ScrollTabHolder mListener;

		public PagerAdapter(FragmentManager fm) {
			super(fm);
			mScrollTabHolders = new SparseArrayCompat<ScrollTabHolder>();
		}

		public void setTabHolderScrollingContent(ScrollTabHolder listener) {
			mListener = listener;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public Fragment getItem(int position) {
			ScrollTabHolderFragment f;
			if (position == 0) {
				f = (ScrollTabHolderFragment) MyItemFragment
						.newInstance(position);
			} else {
				f = (ScrollTabHolderFragment) ScrapItemFragment
						.newInstance(position);
			}

			switch (position) {
			case 0:
				f = (ScrollTabHolderFragment) Fragment.instantiate(
						MyPageActivity.this,
						MyItemFragment.class.getName(), null);
				break;
			case 1:
				f = (ScrollTabHolderFragment) Fragment.instantiate(
						MyPageActivity.this,
						ScrapItemFragment.class.getName(), null);
				break;
			default:
				f = (ScrollTabHolderFragment) Fragment.instantiate(
						MyPageActivity.this,
						MyItemFragment.class.getName(), null);
				break;
			}

			mScrollTabHolders.put(position, f);
			if (mListener != null) {
				f.setScrollTabHolder(mListener);
			}

			return f;
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
			return mScrollTabHolders;
		}

	}
}
