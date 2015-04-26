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
import com.zifei.corebeau.account.task.UserInfoService;
import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.common.ui.view.CircularImageView;
import com.zifei.corebeau.post.task.PostTask;
import com.zifei.corebeau.post.ui.CommentActivity;
import com.zifei.corebeau.utils.StringUtil;
import com.zifei.corebeau.utils.Utils;

public class DetailBottomBar extends RelativeLayout implements OnClickListener {

	private Context context;
	private CircularImageView userIcon;
	private ImageView ivLike, ivComment, ivScrap;
	private TextView tvNickname, tvLikeCnt, tvCommentCnt;
	private boolean isScrap = false;
	private boolean isLike = false;
	private ItemInfo itemInfo;
	private String itemId;
	private DisplayImageOptions iconImageOptions;
	private ImageLoader imageLoader;
	private ImageLoaderConfiguration config;
	private PostTask postTask;

	public DetailBottomBar(Context context) {
		super(context);
		init(context);

	}

	public DetailBottomBar(Context context, AttributeSet attrs) {
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
		postTask = new PostTask(context);
		initLoader();
		setDefault();
	}

	private void initLoader() {

		imageLoader = ImageLoader.getInstance();
		config = new ImageLoaderConfiguration.Builder(context)
				.threadPoolSize(3).build();
		imageLoader.init(config);
		iconImageOptions = new DisplayImageOptions.Builder() //
				.cacheInMemory(false).cacheOnDisk(true).build();
	}

	private void setDefault() {
		imageLoader.displayImage("drawable://" + R.drawable.my_default,
				userIcon, iconImageOptions);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_post_like:
			if (isLike) {
				deleteLike();
			} else {
				insertLike();
			}
			break;
		case R.id.iv_post_comment:
			Intent intent = new Intent(context, CommentActivity.class);
			intent.putExtra("itemId", itemId);
			context.startActivity(intent);
			break;
		case R.id.iv_post_scrap:
			if (isScrap) {
				cancelScrap();
			} else {
				addScrap();
			}
		}
	}

	public void setCurrentItem(ItemInfo itemInfo) {
		this.itemInfo = itemInfo;
		this.itemId = itemInfo.getItemId();
		setWigetImageView();
		setIconImage();
	}

	public void setLikeScrapStatus(boolean isLike,boolean isScrap) {
		this.isLike = isLike;
		this.isScrap = isScrap;
		likeScrapStatus(isLike,isScrap);
	}

	private void setWigetImageView() {
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

	private void setIconImage() {
		String iconUrl = itemInfo.getUserImageUrl();

		if (!StringUtil.isEmpty(iconUrl)) {
			imageLoader.displayImage(iconUrl, userIcon, iconImageOptions);
		} else {
		}
	}

	private void addScrap() {
		ivScrap.setClickable(false);
		postTask.addScrap(itemId,
				new AsyncCallBacks.OneOne<Response, String>() {

					@Override
					public void onSuccess(Response response) {
						if (isScrap == false) {
							isScrap = true;
							ivScrap.setBackgroundResource(R.drawable.bottom_scrap_on);
						}
						// DB set data
						// delete add data
						ivScrap.setClickable(true);
						Utils.showToast(context, "scrap success !");
					}

					@Override
					public void onError(String msg) {
						// remain add data
						if (isScrap == true) {
							isScrap = false;
							ivScrap.setBackgroundResource(R.drawable.bottom_scrap_off);
						}
						ivScrap.setClickable(true);
						Utils.showToast(context, "scrap fail...");
					}
				});
	}

	private void cancelScrap() {
		ivScrap.setClickable(false);
		postTask.cancelScrap(itemId,
				new AsyncCallBacks.OneOne<Response, String>() {

					@Override
					public void onSuccess(Response response) {

						// DB set data
						// delete add data
						if (isScrap == true) {
							isScrap = false;
							ivScrap.setBackgroundResource(R.drawable.bottom_scrap_off);
						}
						ivScrap.setClickable(true);
						Utils.showToast(context, "scrap cancel success !");
					}

					@Override
					public void onError(String msg) {

						// remain add data
						if (isScrap == false) {
							isScrap = true;
							ivScrap.setBackgroundResource(R.drawable.bottom_scrap_on);
						}
						ivScrap.setClickable(true);
						Utils.showToast(context, "scrap cancel fail");
					}
				});
	}

	// 해놓고 백그라운드에서 돌린다
	private void insertLike() {
		ivLike.setClickable(false);
		postTask.insertLike(itemId,
				new AsyncCallBacks.OneOne<Response, String>() {

					@Override
					public void onSuccess(Response response) {
						if (isLike == false) {
							isLike = true;
							ivLike.setBackgroundResource(R.drawable.bottom_like_pressed);
						}
						ivLike.setClickable(true);
						Utils.showToast(context, "like success!");
					}

					@Override
					public void onError(String msg) {
						if (isLike == true) {
							isLike = false;
							ivLike.setBackgroundResource(R.drawable.bottom_like_normal);
						}
						ivLike.setClickable(true);
						Utils.showToast(context, "like fail..");
					}
				});
	}

	private void likeScrapStatus(boolean isLike, boolean isScrap) {
		if (isLike == true) {
			ivLike.setBackgroundResource(R.drawable.bottom_like_pressed);
		} else {
			ivLike.setBackgroundResource(R.drawable.bottom_like_normal);
		}
		
		if (isScrap == true) {
			ivScrap.setBackgroundResource(R.drawable.bottom_scrap_on);
		} else {
			ivScrap.setBackgroundResource(R.drawable.bottom_scrap_off);
		}
	}

	private void deleteLike() {
		ivLike.setClickable(false);
		postTask.deleteLike(itemId,
				new AsyncCallBacks.OneOne<Response, String>() {

					@Override
					public void onSuccess(Response response) {
						if (isLike == true) {
							isLike = false;
							ivLike.setBackgroundResource(R.drawable.bottom_like_normal);
						}
						ivLike.setClickable(true);
						Utils.showToast(context, "like cancel success!");
					}

					@Override
					public void onError(String msg) {
						if (isLike == false) {
							isLike = true;
							ivLike.setBackgroundResource(R.drawable.bottom_like_pressed);
						}
						ivLike.setClickable(true);
						Utils.showToast(context, "like cancel fail...");
					}
				});
	}

}
