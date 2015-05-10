package com.zifei.corebeau.ui.widget;

import java.util.List;

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
import com.zifei.corebeau.bean.response.MyPostListResponse;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.extra.CircularImageView;
import com.zifei.corebeau.task.MyTask;
import com.zifei.corebeau.task.PostTask;
import com.zifei.corebeau.task.UserInfoService;
import com.zifei.corebeau.ui.activity.CommentActivity;
import com.zifei.corebeau.utils.StringUtil;
import com.zifei.corebeau.utils.Utils;

public class MyItemDetailBottomBar extends RelativeLayout implements
		OnClickListener {

	private Context context;
	private CircularImageView userIcon;
	private ImageView ivLike, ivComment, ivScrap, ivDelete;
	private TextView tvNickname, tvLikeCnt, tvCommentCnt;
	private boolean isScrap = false;
	private boolean isLike = false;
	private ItemInfo itemInfo;
	private String itemId;
	private DisplayImageOptions iconImageOptions;
	private ImageLoader imageLoader;
	private ImageLoaderConfiguration config;
	private PostTask postTask;
	private MyTask myTask;
	private OnMyItemDeleteListener onMyItemDeleteListener;
	private int commentCnt ;

	public MyItemDetailBottomBar(Context context) {
		super(context);
		init(context);

	}

	public MyItemDetailBottomBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	@SuppressLint("ResourceAsColor")
	private void init(Context context) {
		LayoutInflater.from(context).inflate(
				R.layout.layout_detail_bottom_bar_my, this);
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
		ivDelete = (ImageView) findViewById(R.id.iv_item_delete);

		ivLike.setOnClickListener(this);
		ivComment.setOnClickListener(this);
		ivScrap.setOnClickListener(this);
		postTask = new PostTask(context);
		myTask = new MyTask(context);
		initLoader();
		setDefault();
	}

	private void initLoader() {

		imageLoader = ImageLoader.getInstance();
		config = new ImageLoaderConfiguration.Builder(context)
				.threadPoolSize(3).build();
		imageLoader.init(config);
		iconImageOptions = new DisplayImageOptions.Builder()
		.cacheInMemory(true)
		.showImageOnFail(R.drawable.user_icon_default)
		.showImageForEmptyUri(R.drawable.user_icon_default)
		.showImageOnLoading(R.drawable.user_icon_default)
		.build();
	}

	private void setDefault() {
		imageLoader.displayImage("drawable://" + R.drawable.my_default,
				userIcon, iconImageOptions);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_post_like:
			break;
		case R.id.iv_post_comment:
			Intent intent = new Intent(context, CommentActivity.class);
			intent.putExtra("itemId", itemId);
			intent.putExtra("commentCnt", commentCnt);
			context.startActivity(intent);
			break;
		case R.id.iv_post_scrap:
			if (isScrap) {
				cancelScrap();
			} else {
				addScrap();
			}
		case R.id.iv_item_delete:
			deleteItem();
			break;
		}
	}

	public void setCurrentItem(ItemInfo itemInfo) {
		this.itemInfo = itemInfo;
		this.itemId = itemInfo.getItemId();
		this.commentCnt = itemInfo.getCommentCnt();
		setWigetImageView();
		setIconImage();
	}

	private void setWigetImageView() {
		// ivScrap = response.get...;
		if (itemInfo.getUserId().equals(UserInfoService.getUserId())) {
			ivDelete.setBackgroundResource(R.drawable.bottom_delete_normal);
			ivDelete.setVisibility(View.VISIBLE);
			ivDelete.setOnClickListener(this);
		}

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
						Utils.showToast(context, response.getMsg());
					}

					@Override
					public void onError(String msg) {
						// remain add data
						if (isScrap == true) {
							isScrap = false;
							ivScrap.setBackgroundResource(R.drawable.bottom_scrap_off);
						}
						Utils.showToast(context, msg);
					}
				});
	}

	private void cancelScrap() {

		postTask.addScrap(itemId,
				new AsyncCallBacks.OneOne<Response, String>() {

					@Override
					public void onSuccess(Response response) {

						// DB set data
						// delete add data
						if (isScrap == true) {
							isScrap = false;
							ivScrap.setBackgroundResource(R.drawable.bottom_scrap_off);
						}
						Utils.showToast(context, response.getMsg());
					}

					@Override
					public void onError(String msg) {

						// remain add data
						if (isScrap == false) {
							isScrap = true;
							ivScrap.setBackgroundResource(R.drawable.bottom_scrap_on);
						}
						Utils.showToast(context, msg);
					}
				});
	}

	


	private void deleteItem() {
		myTask.deleteItem(itemId,
				new AsyncCallBacks.OneOne<MyPostListResponse, String>() {

					@Override
					public void onSuccess(MyPostListResponse response) {
						Utils.showToast(context, response.getMsg());
						List<ItemInfo> list = response.getPageBean().getList();
						onMyItemDeleteListener.onMyListDataChanged(list);
					}

					@Override
					public void onError(String msg) {
						Utils.showToast(context, msg);
					}
				});
	}

	public interface OnMyItemDeleteListener {
		public void onMyListDataChanged(List<ItemInfo> list);
	}

	public void registerOnMyItemDeleteListener(
			OnMyItemDeleteListener onMyItemDeleteListener) {
		this.onMyItemDeleteListener = onMyItemDeleteListener;
	}
}
