package com.zifei.corebeau.ui.adapter;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
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
import com.zifei.corebeau.extra.ScaleImageView;
import com.zifei.corebeau.extra.pla.XListView;
import com.zifei.corebeau.utils.StringUtil;

public class SearchPostAdapter extends BaseAdapter {

	private DisplayImageOptions imageOptions;
	private ImageLoader imageLoader;
	private final LayoutInflater mLayoutInflater;
	private String smallImageConfig;
	private int columnWidth;
	private LinkedList<ItemInfo> data;

	public SearchPostAdapter(Context context, XListView xListView) {
		data = new LinkedList<ItemInfo>();

		getSmallImageConfig();
		this.mLayoutInflater = LayoutInflater.from(context);
		imageLoader = ImageLoader.getInstance();
		imageOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).resetViewBeforeLoading(true).build();
	}
	
	public void clearAdapter() {
		if (this.data != null) {
			this.data.clear();
		}
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

	public void setColumnWidth(int columnWidth) {
		this.columnWidth = columnWidth;
	}

	private void getSmallImageConfig() {
		this.smallImageConfig = CorebeauApp.getSmallImageConfig();
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {

		ItemInfo itemInfo = getItem(position);
		int smallImgHeight = itemInfo.getSheight();
		int smallImgWidth = itemInfo.getSwidth();

		ViewHolder vh;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.item_search_post,
					parent, false);
			vh = new ViewHolder();
			vh.image = (ScaleImageView) convertView
					.findViewById(R.id.iv_search_post);
			vh.message = (TextView) convertView
					.findViewById(R.id.tv_search_message);
			vh.likeCnt = (TextView) convertView
					.findViewById(R.id.tv_search_like);
			vh.commentCnt = (TextView) convertView
					.findViewById(R.id.tv_comment_cnt);
			vh.nickName = (TextView) convertView
					.findViewById(R.id.tv_search_nickname);
			convertView.setTag(vh);
		}

		vh = (ViewHolder) convertView.getTag();
		vh.image.setImageWidth(200);
		vh.image.setImageHeight((int) (((double) 200) * ((double) smallImgHeight / (double) smallImgWidth)));

		// vh.message.setText(itemInfo.getTitle());

		vh.message
				.setText(String
						.valueOf("hei : "
								+ (int) (((double) 200) * ((double) smallImgHeight / (double) smallImgWidth))
								+ "  idx : " + position));
		vh.message.setTextColor(Color.RED);

		if (itemInfo.getNickName() != null) {
			vh.nickName.setText(String.valueOf("by " + itemInfo.getNickName()));
		}
		
		vh.likeCnt.setText(String.valueOf(itemInfo.getLikeCnt()));
		vh.commentCnt.setText(String.valueOf(itemInfo.getCommentCnt()));

		ImageAware imageAware = new ImageViewAware(vh.image, false);

		if (smallImageConfig != null && !StringUtil.isEmpty(smallImageConfig)) {
			imageLoader.displayImage(itemInfo.getShowUrl() + smallImageConfig,
					imageAware, imageOptions);
		} else {
			imageLoader.displayImage(itemInfo.getShowUrl(), imageAware,
					imageOptions);
		}
		vh.image.setTag(itemInfo.getShowUrl());
		return convertView;
	}

	static class ViewHolder {
		ScaleImageView image;
		TextView likeCnt;
		TextView commentCnt;
		TextView message;
		TextView nickName;
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

}
