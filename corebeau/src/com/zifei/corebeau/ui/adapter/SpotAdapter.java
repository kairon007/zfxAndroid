package com.zifei.corebeau.ui.adapter;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.common.CorebeauApp;
import com.zifei.corebeau.extra.CircularImageView;
import com.zifei.corebeau.extra.ScaleImageView;
import com.zifei.corebeau.extra.pla.PullSingleListView;
import com.zifei.corebeau.ui.activity.OtherUserActivity;
import com.zifei.corebeau.ui.activity.PostDetailActivity;
import com.zifei.corebeau.utils.StringUtil;
import com.zifei.corebeau.utils.Utils;

public class SpotAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private LinkedList<ItemInfo> data;
	private DisplayImageOptions imageOptions;
	private DisplayImageOptions iconImageOptions;
	private ImageLoader imageLoader;
	private String bigImageConfig;

	public SpotAdapter(Context context, PullSingleListView listView) {
		this.context = context;
		data = new LinkedList<ItemInfo>();
		this.inflater = LayoutInflater.from(context);
		
		getConfig();
		imageLoader = ImageLoader.getInstance();

		imageOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).resetViewBeforeLoading(true).build();

		iconImageOptions = new DisplayImageOptions.Builder()
				//
				.delayBeforeLoading(200)
				.cacheInMemory(true)
				.showImageOnFail(R.drawable.user_icon_default)
				.showImageForEmptyUri(R.drawable.user_icon_default)
				.showImageOnLoading(R.drawable.user_icon_default)
				.build();
	}

	public void clearAdapter() {
		if (this.data != null) {
			this.data.clear();
		}
	}

	private void getConfig() {
		bigImageConfig = CorebeauApp.getBigImageConfig();
	}

	
	public void addItemLast(List<ItemInfo> datas) {
		data.addAll(datas);
	}

	public void addItemTop(List<ItemInfo> datas) {
		for (ItemInfo info : datas) {
			data.addLast(info);
		}
	}

	public LinkedList<ItemInfo> getData() {
		return data;
	}

	@Override
	public ItemInfo getItem(int position) {
		return data.get(position);
	}

	@Override
	public int getCount() {
		if (data == null) {
			return 0;
		}
		return data.size();
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final ItemInfo p = getItem(position);

		int bigImgHeight = p.getBheight();
		int bigIwidth = p.getBwidth();
		int screenWidth = Utils.getScreenWidth(context);
		
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_spot, parent, false);
		
		holder = new ViewHolder();
		holder.image = (ScaleImageView) convertView.findViewById(R.id.spot_image);
		
		holder.usericon = (CircularImageView) convertView
				.findViewById(R.id.spot_user_thumb);
		holder.usericon.setBorderWidth(0);
		holder.nickName = (TextView) convertView
				.findViewById(R.id.spot_user_nickname);
		holder.message = (TextView) convertView
				.findViewById(R.id.tv_spot_message);
		holder.goPostDetail = (TextView) convertView
				.findViewById(R.id.tv_spot_go_detail);
		holder.commentCnt = (TextView) convertView
				.findViewById(R.id.tv_spot_comment_cnt);
		holder.likeCnt = (TextView) convertView
				.findViewById(R.id.tv_spot_like_cnt);
		convertView.setTag(holder);
		}
		
		holder = (ViewHolder) convertView.getTag();
		
		holder.image.setImageWidth(screenWidth);
		holder.image.setImageHeight((int) (((double) screenWidth) * ((double) bigImgHeight / (double) bigIwidth)));
		
		ImageAware imageAwareIcon = new ImageViewAware(holder.usericon, false);
		imageLoader.displayImage("drawable://" + R.drawable.my_default,
				imageAwareIcon, iconImageOptions);
		String urlThumb = p.getUserImageUrl();
		if (!StringUtil.isEmpty(urlThumb)) {
			imageLoader.displayImage(urlThumb, imageAwareIcon,
					iconImageOptions);
		} else {
		}

		holder.nickName.setText(p.getNickName());

		String title = p.getTitle();
		if (title.length() > 50) {
			holder.message.setText(title.substring(0, 50) + "...");
		} else {
//			holder.message.setText(title);
			holder.message.setText(String.valueOf(position));
			holder.message.setTextColor(Color.RED);
		}

		holder.commentCnt.setText(String.valueOf(p.getCommentCnt()));
		holder.likeCnt.setText(String.valueOf(p.getLikeCnt()));
		String url = p.getShowUrl();

		ImageAware imageAware = new ImageViewAware(holder.image, false);
		if (!StringUtil.isEmpty(url)) {
			if (bigImageConfig != null && !StringUtil.isEmpty(bigImageConfig)) {
				imageLoader.displayImage(url + bigImageConfig, imageAware,
						imageOptions);
			} else {
				imageLoader.displayImage(url, imageAware, imageOptions);
			}
		} else {
		}
		holder.image.setTag(p.getShowUrl());

		
		
		holder.usericon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goUserPage(p);
			}
		});

		holder.goPostDetail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goPostPage(p);
			}
		});

		
		return convertView;
	}

	private void goPostPage(ItemInfo itemInfo) {
		context.startActivity(new Intent(context, PostDetailActivity.class)
				.putExtra("itemInfo", itemInfo));
	}

	private void goUserPage(ItemInfo itemInfo) {
		context.startActivity(new Intent(context, OtherUserActivity.class)
				.putExtra("itemInfo", itemInfo));
	}

	private class ViewHolder {
		TextView message;
		TextView nickName;
		CircularImageView usericon;
		ScaleImageView image;
		TextView goPostDetail;
		TextView commentCnt;
		TextView likeCnt;
	}

}
