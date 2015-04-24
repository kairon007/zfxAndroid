package com.zifei.corebeau.post.ui;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.common.ui.view.CircularImageView;
import com.zifei.corebeau.common.ui.widget.indicator.CirclePageIndicator;
import com.zifei.corebeau.common.ui.widget.indicator.PageIndicator;
import com.zifei.corebeau.post.bean.Post;
import com.zifei.corebeau.post.bean.response.PostResponse;
import com.zifei.corebeau.post.task.PostTask;
import com.zifei.corebeau.post.ui.adapter.ImageAdapter;
import com.zifei.corebeau.post.ui.view.PostViewPager;
import com.zifei.corebeau.utils.StringUtil;
import com.zifei.corebeau.utils.Utils;

/**
 * Created by im14s_000 on 2015/3/28.
 */
public class PostDetailActivity extends FragmentActivity implements OnClickListener {

    private PostViewPager mPager;
    private PostTask postTask;
    private ItemInfo itemInfo;
    private String itemId;
    private DisplayImageOptions imageOptions;
    private ImageLoader imageLoader;
    private ImageLoaderConfiguration config;
    private CircularImageView userIcon;
    private TextView tvNickname, tvLikeCnt, tvCommentCnt, tvMsg; 
    private PageIndicator mIndicator;
    private ImageView ivLike, ivComment, ivScrap;
    private boolean isScrap = false;
    private boolean isLike = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Intent intent = getIntent();
        itemInfo = (ItemInfo) intent.getSerializableExtra("itemInfo");
        init();
    }

    private void init() {

        postTask = new PostTask(this);
        mPager = (PostViewPager) findViewById(R.id.vp_post_image);
        mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        mIndicator.setOnPageChangeListener(new PageChangeListener());
        userIcon = (CircularImageView)findViewById(R.id.iv_post_icon);
        userIcon.setBorderWidth(0);
        ivLike = (ImageView)findViewById(R.id.iv_post_like);
        ivComment = (ImageView)findViewById(R.id.iv_post_comment);
        tvNickname = (TextView)findViewById(R.id.tv_post_nickname);
        tvLikeCnt = (TextView)findViewById(R.id.tv_post_like);
        tvCommentCnt = (TextView)findViewById(R.id.tv_post_comment);
        tvMsg = (TextView)findViewById(R.id.tv_post_msg);
        ivScrap = (ImageView)findViewById(R.id.iv_post_scrap);
        
        ivLike.setOnClickListener(this);
        ivComment.setOnClickListener(this);
        
        initImageLoader();
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
    
    private void initImageLoader(){
    	imageLoader = ImageLoader.getInstance();
        config = new ImageLoaderConfiguration.Builder(this).threadPoolSize(3).build();
        imageLoader.init(config);
        imageOptions = new DisplayImageOptions.Builder()
                .delayBeforeLoading(200) 
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .build();
    }

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
            	
//            	ivScrap = response.get...;
            	if(isScrap){
            		ivScrap.setBackgroundResource(R.drawable.scrap_on);
            	}else{
            		ivScrap.setBackgroundResource(R.drawable.scrap_off);
            	}
            	
//            	ivlike = response.get...;
            	if(isLike){
            		ivLike.setBackgroundResource(R.drawable.dashboard_post_control_like_selected);
            	}else{
            		ivLike.setBackgroundResource(R.drawable.dashboard_post_control_like);
            	}
            }

            @Override
            public void onError(String result) {
                
            }
        });
    }
    
    private void setPostImage(List<String> urlList){
    	mPager.setAdapter(new ImageAdapter(this, urlList));
//    	userIcon.set
    	String iconUrl = itemInfo.getUserImageUrl();
    	if (!StringUtil.isEmpty(iconUrl)) {
            imageLoader.displayImage(iconUrl, userIcon, imageOptions);
        } else {
        	// default image
        }
    	mIndicator.setViewPager(mPager);
    }

    private void setPostData() {
    	
    	tvNickname.setText(itemInfo.getNickName());
        tvLikeCnt.setText(String.valueOf(itemInfo.getLikeCnt()));
        tvCommentCnt.setText(String.valueOf(itemInfo.getCommentCnt()));
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
         case R.id.iv_post_like:
             break;
         case R.id.iv_post_comment:
        	 Intent intent = new Intent(PostDetailActivity.this, CommentActivity.class);
             startActivity(intent);
        	 break;
         default:
             break;
     }		
	}
}