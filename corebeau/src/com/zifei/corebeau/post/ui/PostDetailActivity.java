package com.zifei.corebeau.post.ui;

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

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.common.ui.widget.indicator.CirclePageIndicator;
import com.zifei.corebeau.post.bean.response.PostResponse;
import com.zifei.corebeau.post.task.PostTask;
import com.zifei.corebeau.post.ui.adapter.ImageAdapter;
import com.zifei.corebeau.post.ui.view.BottomBar;
import com.zifei.corebeau.post.ui.view.PostViewPager;
import com.zifei.corebeau.utils.Utils;

/**
 * Created by im14s_000 on 2015/3/28.
 */
public class PostDetailActivity extends FragmentActivity implements OnClickListener {

    private PostViewPager mPager;
    private PostTask postTask;
    private ItemInfo itemInfo;
    private String itemId;
    private ImageLoader imageLoader;
    private ImageLoaderConfiguration config;
    private TextView tvMsg; 
    private CirclePageIndicator mIndicator;
    private BottomBar bottomBar;

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

        postTask = new PostTask(this);
        mPager = (PostViewPager) findViewById(R.id.vp_post_image);
        mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        mIndicator.setOnPageChangeListener(new PageChangeListener());
        mIndicator.setFillColor(R.color.spot_inner_divider);
        tvMsg = (TextView)findViewById(R.id.tv_post_msg);
        
        bottomBar = new BottomBar(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.BOTTOM;
		((FrameLayout) findViewById(android.R.id.content)).addView(bottomBar,
				lp);
		bottomBar.setCurrentItem(itemInfo);
        setPostData();
       
    }
    
//    @Override
//    protected void onResume() {
//    	super.onResume();
//    	
//    	 ViewTreeObserver viewTreeObserver = mPager.getViewTreeObserver();
//         viewTreeObserver
//                 .addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
//
//                     @Override
//                     public void onGlobalLayout() {
//
//                         LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                                 LinearLayout.LayoutParams.WRAP_CONTENT,
//                                 LinearLayout.LayoutParams.WRAP_CONTENT);
//
//                         int viewPagerWidth = mPager.getWidth();
//                         float viewPagerHeight = (float) (viewPagerWidth * FEATURED_IMAGE_RATIO);
//
//                         layoutParams.width = viewPagerWidth;
//                         layoutParams.height = (int) viewPagerHeight;
//
//                         mPager.setLayoutParams(layoutParams);
//                         mPager.getViewTreeObserver()
//                                 .removeGlobalOnLayoutListener(this);
//                     }
//                 });
//    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getPostTask();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getPostTask() {   // get isScrap, isLike
        postTask.getItem(itemId, new AsyncCallBacks.OneOne<PostResponse, String>() {
            @Override
            public void onSuccess(PostResponse response) {
            	List<String> urlList = response.getPictureUrls();
            	setPostImage(urlList);
            	
            }

            @Override
            public void onError(String result) {
                
            }
        });
    }
    
    private void setPostImage(List<String> urlList){
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
    
    private void addScrap(){
    	
    	postTask.addScrap(itemId, new AsyncCallBacks.OneOne<Response, String>() {

            @Override
            public void onSuccess(Response response) {

                
                // DB set data
                
                // delete add data
            }

            @Override
            public void onError(String msg) {
            	
            	
                
                // remain add data
            }
        });
    }

    // 해놓고 백그라운드에서 돌린다
    private void insertLikeTask() {
        postTask.insertLike(itemId, new AsyncCallBacks.OneOne<Response, String>() {

            @Override
            public void onSuccess(Response response) {

//                Utils.showToast(PostActivity.this, msg);
            }

            @Override
            public void onError(String msg) {
                Utils.showToast(PostDetailActivity.this, msg);
            }
        });
    }

    private void deleteLikeTask() {
        postTask.deleteLike(itemId, new AsyncCallBacks.OneOne<Response, String>() {

            @Override
            public void onSuccess(Response response) {

//                Utils.showToast(PostActivity.this, msg);
            }

            @Override
            public void onError(String msg) {
                Utils.showToast(PostDetailActivity.this, msg);
            }
        });
    }

	@Override
	public void onClick(View v) {
	       switch (v.getId()) {
         default:
             break;
     }		
	}
}
