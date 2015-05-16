package com.zifei.corebeau.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.zifei.corebeau.R;
import com.zifei.corebeau.extra.parallaxheader.PagerSlidingTabStrip;
import com.zifei.corebeau.extra.parallaxheader.PagerSlidingTabStrip.IconTabProvider;
import com.zifei.corebeau.ui.fragment.MyFragment;
import com.zifei.corebeau.ui.fragment.SearchFragment;
import com.zifei.corebeau.ui.fragment.SpotFragment;

public class MainActivity extends SherlockFragmentActivity implements  ViewPager.OnPageChangeListener{

	private ViewPager mViewPager;
	private PagerAdapter mPagerAdapter;
	private PagerSlidingTabStrip mPagerSlidingTabStrip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initActionBar();
		
		mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		mViewPager = (ViewPager) findViewById(R.id.main_pager);
		mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);
		mPagerSlidingTabStrip.setViewPager(mViewPager);
		mPagerSlidingTabStrip.setOnPageChangeListener(this);
	
	}

	private void initActionBar() {
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
	}
	
	public class PagerAdapter extends FragmentPagerAdapter implements IconTabProvider  {

		private final int PAGE_COUNT = 3;
		private final int[] ICONS = {R.drawable.tab_spot,R.drawable.tab_search,R.drawable.tab_my};

		public PagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment f = null;

			switch (position) {
			case 0:
				f =  Fragment.instantiate(
						MainActivity.this,
						SpotFragment.class.getName(), null);
				break;
			case 1:
				f = Fragment.instantiate(
						MainActivity.this,
						SearchFragment.class.getName(), null);
				break;
			case 2:
				f = Fragment.instantiate(
						MainActivity.this,
						MyFragment.class.getName(), null);
				break;
			default:
				f = Fragment.instantiate(
						MainActivity.this,
						SpotFragment.class.getName(), null);
				break;
			}

			return f;
		}

		@Override
		public int getCount() {
			return PAGE_COUNT;
		}


		@Override
		public int getPageIconResId(int position) {
			 return ICONS[position];
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
	}


}
