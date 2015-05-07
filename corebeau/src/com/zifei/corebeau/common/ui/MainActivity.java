package com.zifei.corebeau.common.ui;

import android.graphics.PorterDuff.Mode;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.zifei.corebeau.R;
import com.zifei.corebeau.my.ui.fragment.MyFragment;
import com.zifei.corebeau.search.ui.fragment.SearchFragment;
import com.zifei.corebeau.spot.ui.fragment.SpotFragment;

public class MainActivity extends SherlockFragmentActivity implements
		OnFragmentInteractionListener {

	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;
	private ImageView spotTab, searchTab, myTab;
	 private static int selectedPage = 0;
	    private static int preSelectedPage = 0;
	    private boolean isClick = false;
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initActionBar();
		initView();
		initTabListener();
	}

	
	private void initActionBar() {
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
	}

	private void initView() {
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(listener);
		spotTab = (ImageView) findViewById(R.id.iv_rl_spot_tab);
		searchTab = (ImageView) findViewById(R.id.iv_rl_search_tab);
		myTab = (ImageView) findViewById(R.id.iv_rl_my_tab);
		spotTab.setColorFilter(0x8893e2ff, Mode.SRC_OVER);
		updateSelectedTab(0);
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
				f = Fragment.instantiate(MainActivity.this,
						SpotFragment.class.getName(), null);
				break;
			case 1:
				f = Fragment.instantiate(MainActivity.this,
						SearchFragment.class.getName(), null);
				break;
			case 2:
				f = Fragment.instantiate(MainActivity.this,
						MyFragment.class.getName(), null);
				break;
			default:
				f = Fragment.instantiate(MainActivity.this,
						SpotFragment.class.getName(), null);
				break;
			}
			return f;
		}

		@Override
		public int getCount() {
			return 3;
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
                case 2:
                    updateSelectedTab(2);
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
        view = findViewById(R.id.rl_spot_tab);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isClick = true;
                if (preSelectedPage == 1) {
                    mViewPager.setCurrentItem(0, true);
                } else if (preSelectedPage == 2) {
                    mViewPager.setCurrentItem(0, true);
                } else {
                    isClick = false;
                    mViewPager.setCurrentItem(0, false);
                    updateSelectedTab(0);
                }
            }
        });
        
        view = findViewById(R.id.rl_search_tab);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isClick = true;
                if (preSelectedPage == 0) {
                    mViewPager.setCurrentItem(1, true);
                } else if(preSelectedPage == 2) {
                    mViewPager.setCurrentItem(1, true);
                } else {
                    isClick = false;
                    mViewPager.setCurrentItem(1, false);
                    updateSelectedTab(1);
                }
            }
        });
        
        view = findViewById(R.id.rl_my_tab);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isClick = true;
                if (preSelectedPage == 0) {
                    mViewPager.setCurrentItem(2, true);
                } else if(preSelectedPage == 1) {
                    mViewPager.setCurrentItem(2, true);
                } else {
                    isClick = false;
                    mViewPager.setCurrentItem(2, false);
                    updateSelectedTab(2);
                }
            }
        });
    }

	private void updateSelectedTab(int nSelected) {
		if (nSelected == 0) {
			spotTab.setBackgroundResource(R.drawable.topnav_spot_on);
			searchTab.setBackgroundResource(R.drawable.topnav_search_off);
			myTab.setBackgroundResource(R.drawable.topnav_my_off);
		}else if (nSelected == 1) {
			spotTab.setBackgroundResource(R.drawable.topnav_spot_off);
			searchTab.setBackgroundResource(R.drawable.topnav_search_on);
			myTab.setBackgroundResource(R.drawable.topnav_my_off);
		} else if (nSelected == 2) {
			spotTab.setBackgroundResource(R.drawable.topnav_spot_off);
			searchTab.setBackgroundResource(R.drawable.topnav_search_off);
			myTab.setBackgroundResource(R.drawable.topnav_my_on);
		} 
	}

	@Override
	public void onFragmentInteraction(String id) {

	}

	@Override
	public void onFragmentInteraction(Uri uri) {

	}
}
