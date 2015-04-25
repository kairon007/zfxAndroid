package com.zifei.corebeau.post.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.common.ui.view.CircularImageView;
import com.zifei.corebeau.post.task.PostTask;
import com.zifei.corebeau.post.ui.CommentActivity;
import com.zifei.corebeau.utils.StringUtil;

public class BottomBar extends RelativeLayout implements OnClickListener {

	private Context context;
	private CircularImageView userIcon;
	private ImageView ivLike, ivComment, ivScrap;
	private TextView tvNickname, tvLikeCnt, tvCommentCnt;
    private boolean isScrap = false;
    private boolean isLike = false;
    private ItemInfo itemInfo;
    private DisplayImageOptions iconImageOptions;
	private ImageLoader imageLoader;
	private ImageLoaderConfiguration config;
	private PostTask postTask;

	public BottomBar(Context context) {
		super(context);
		init(context);
		
	}

	public BottomBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	@SuppressLint("ResourceAsColor")
	private void init(Context context) {
		LayoutInflater.from(context).inflate(R.layout.layout_detail_bottom_bar,
				this);
		this.context = context;
		userIcon = (CircularImageView) findViewById(R.id.iv_post_icon);
		userIcon.setBorderWidth(1);
		userIcon.setBorderColor(R.color.spot_inner_divider);
		ivLike = (ImageView) findViewById(R.id.iv_post_like);
		ivComment = (ImageView) findViewById(R.id.iv_post_comment);
		tvNickname = (TextView) findViewById(R.id.tv_post_nickname);
		tvLikeCnt = (TextView) findViewById(R.id.tv_post_like);
		tvCommentCnt = (TextView) findViewById(R.id.tv_post_comment);
		ivScrap = (ImageView) findViewById(R.id.iv_post_scrap);

		ivLike.setOnClickListener(this);
		ivComment.setOnClickListener(this);
		ivScrap.setOnClickListener(this);
		initLoader();
		setDefault();
	}
	
	private void initLoader(){
		
		imageLoader = ImageLoader.getInstance();
		config = new ImageLoaderConfiguration.Builder(context)
				.threadPoolSize(3).build();
		imageLoader.init(config);
		iconImageOptions = new DisplayImageOptions.Builder() //
		.cacheInMemory(false).cacheOnDisk(true)
		.build();
	}
	
	private void setDefault(){
		imageLoader.displayImage("drawable://" + R.drawable.my_default, userIcon, iconImageOptions);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_post_like:
			break;
		case R.id.iv_post_comment:
			Intent intent = new Intent(context,
					CommentActivity.class);
			context.startActivity(intent);
			break;
		case R.id.iv_post_scrap:
			
			break;
		}
	}

	public void setCurrentItem(ItemInfo itemInfo) {
		this.itemInfo = itemInfo;
		setWigetImageView();
		setIconImage();
	}
	
	private void setWigetImageView(){
		// ivScrap = response.get...;
		if (isScrap) {
			ivScrap.setBackgroundResource(R.drawable.bottom_scrap_on);
		} else {
			ivScrap.setBackgroundResource(R.drawable.bottom_scrap_off);
		}

		// ivlike = response.get...;
		if (isLike) {
			ivLike.setBackgroundResource(R.drawable.bottom_like_pressed);
		} else {
			ivLike.setBackgroundResource(R.drawable.bottom_like_normal);
		}
		
		ivComment.setBackgroundResource(R.drawable.bottom_comment_normal);
		
		tvNickname.setText(itemInfo.getNickName());
		tvLikeCnt.setText(String.valueOf(itemInfo.getLikeCnt()));
		tvCommentCnt.setText(String.valueOf(itemInfo.getCommentCnt()));
	}
	
	private void setIconImage(){
		String iconUrl = itemInfo.getUserImageUrl();
		
		if (!StringUtil.isEmpty(iconUrl)) {
			imageLoader.displayImage(iconUrl, userIcon, iconImageOptions);
		} else {
		}
	}

}
