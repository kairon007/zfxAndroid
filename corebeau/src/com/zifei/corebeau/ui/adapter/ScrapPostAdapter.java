package com.zifei.corebeau.ui.adapter;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.common.CorebeauApp;
import com.zifei.corebeau.extra.CircularImageView;
import com.zifei.corebeau.extra.ScaleImageView;
import com.zifei.corebeau.extra.pla.PullSingleListView;
import com.zifei.corebeau.ui.activity.PostDetailActivity;
import com.zifei.corebeau.utils.StringUtil;
import com.zifei.corebeau.utils.Utils;

public class ScrapPostAdapter  extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private LinkedList<ItemInfo> data;
	private DisplayImageOptions imageOptions;
	private ImageLoader imageLoader;
	private String bigImageConfig;

	public ScrapPostAdapter(Context context, PullSingleListView listView) {
		this.context = context;
		data = new LinkedList<ItemInfo>();
		this.inflater = LayoutInflater.from(context);
		
		getConfig();
		imageLoader = ImageLoader.getInstance();

		imageOptions = new DisplayImageOptions.Builder() //
				.delayBeforeLoading(200) // 载入之前的延迟时间
				.cacheInMemory(false).cacheOnDisk(true)
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
			data.addFirst(info);
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
		

		final ItemInfo p = data.get(position);

		int bigImgHeight = p.getBheight();
		int bigIwidth = p.getBwidth();
		int screenWidth = Utils.getScreenWidth(context);
		
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_scrap, parent, false);
			
			holder = new ViewHolder();
			
			holder.usericon = (CircularImageView) convertView
					.findViewById(R.id.scrap_user_thumb);
			holder.usericon.setBorderWidth(1);
			holder.message = (TextView) convertView
					.findViewById(R.id.tv_scrap_message);
			holder.image = (ScaleImageView) convertView.findViewById(R.id.scrap_image);
			holder.nickName = (TextView)convertView.findViewById(R.id.scrap_user_nickname);
			holder.goPostDetail = (TextView) convertView
					.findViewById(R.id.tv_go_detail);
			
			convertView.setTag(holder);
		}

		
		holder = (ViewHolder) convertView.getTag();
		
		holder.image.setImageWidth(screenWidth);
		holder.image
				.setImageHeight((int) (((double) screenWidth) * ((double) bigImgHeight / (double) bigIwidth)));

		
		holder.message.setText(p.getTitle());
		holder.nickName.setText(p.getNickName());
		
		ImageAware imageAware = new ImageViewAware(holder.image, false);
		String url = p.getShowUrl();
		if (!StringUtil.isEmpty(url)) {
			imageLoader.displayImage(url+"", imageAware, imageOptions);
		} else {
		}
		
		String iconUrl = p.getUserImageUrl();
		if (!StringUtil.isEmpty(iconUrl)) {
			imageLoader.displayImage(iconUrl, holder.image, imageOptions);
		} else {
		}
		holder.image.setTag(p.getShowUrl());
		
		holder.goPostDetail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goPostPage(p.getItemId());
			}
		});
		return convertView;
	}

	private void goPostPage(String itemId) {
		context.startActivity(new Intent(context, PostDetailActivity.class).putExtra(
				"itemId", itemId));
	}

	private class ViewHolder {
		TextView message;
		TextView nickName;
		CircularImageView usericon;
		ScaleImageView image;
		TextView goPostDetail;
	}

}
