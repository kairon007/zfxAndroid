package com.zifei.corebeau.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zifei.corebeau.R;
import com.zifei.corebeau.extra.indicator.CirclePageIndicator;
import com.zifei.corebeau.task.UserInfoService;
import com.zifei.corebeau.utils.StringUtil;

public class IntroActivity extends Activity implements OnClickListener ,OnPageChangeListener{
	
	private static final int MAX_PAGES = 4;
	private CirclePageIndicator mCirclePageIndicator;
	public LinearLayout mShowButtonLinearlayout;
	private Button loginBtn;
	private boolean from_setting = false;
	private boolean accountCreated = false;
	private UserInfoService userInfoService;

	private int[] logoTextBank = new int[]{
//			R.drawable.navi_one,R.drawable.navi_two, R.drawable.navi_three,
//			R.drawable.navi_four
	};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_intro);
//		Intent intent = getIntent();
//		accountCreated = intent.getBooleanExtra("accountCreated", false);
//		from_setting = getIntent().getBooleanExtra("from_setting", false);
//		userInfoService = new UserInfoService(this);
//		loginBtn = ((Button) findViewById(R.id.btn_login));
//		loginBtn.setOnClickListener(this);
//        final ViewPagerParallax pager = (ViewPagerParallax) findViewById(R.id.pagerparallax);
//        pager.set_max_pages(MAX_PAGES);
//        pager.setBackgroundAsset(R.raw.navigation_page);
//        pager.setAdapter(new my_adapter());
//
//        if (savedInstanceState!=null) {
//            pager.setCurrentItem(savedInstanceState.getInt("current_page"), false);
//        }
//        
//		mCirclePageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
//		mCirclePageIndicator.setViewPager(pager);
//		mCirclePageIndicator.setOnPageChangeListener(this);
//		mShowButtonLinearlayout = (LinearLayout)findViewById(R.id.show_button_linearlayout);
        
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putInt("num_pages", 4);
//        final ViewPagerParallax pager = (ViewPagerParallax) findViewById(R.id.pagerparallax);
//        outState.putInt("current_page", pager.getCurrentItem());
    }

    private class my_adapter extends PagerAdapter {

    	@Override
    	public int getCount() {
    		return 4;
    	}

    	@Override
    	public boolean isViewFromObject(View view, Object o) {
    		return view == o;
    	}

    	@Override
    	public void destroyItem(ViewGroup container, int position, Object object) {
    		container.removeView((View)object);
    	}

    	@Override
    	public Object instantiateItem(ViewGroup container, int position) {
//    		View new_view=null;
//    		LayoutInflater inflater = getLayoutInflater();
//			new_view = inflater.inflate(R.layout.navigation_detail_pageview, null);
//    		ImageView IvGogoText = (ImageView)new_view.findViewById(R.id.logo_text);
//    		try {
//    			IvGogoText.setImageResource(logoTextBank[position]);
//			} catch (OutOfMemoryError e) {
//				throw e;
//			}
//
//    		container.addView(new_view);

//    		return new_view;
    		return null;
    	}

    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//		case R.id.btn_login:
//			Intent intent;
//			if (from_setting) {
//				finish();
//				return;
//			}
//			PreferenceManager.getInstance().savePreferencesBoolean("first_enter", false);
//			if (!StringUtil.isEmpty(userInfoService.getLoginId())) {
//				intent = new Intent(IntroActivity.this, MainActivity.class);
//				intent.putExtra("accountCreated", accountCreated);
//			} else {
//				intent = new Intent(IntroActivity.this, LoginActivity.class);
			}
//			startActivity(intent);
//			finish();
//			break;
	}
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int position) {

		if(position !=MAX_PAGES-1) {
        	mShowButtonLinearlayout.setVisibility(View.GONE);
        	loginBtn.setVisibility(View.GONE);
        }else {
        	mShowButtonLinearlayout.setVisibility(View.VISIBLE);
        	loginBtn.setVisibility(View.VISIBLE);
        }
	}
    
}
