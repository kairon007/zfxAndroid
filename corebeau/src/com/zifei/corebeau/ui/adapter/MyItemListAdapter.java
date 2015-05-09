package com.zifei.corebeau.ui.adapter;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.zifei.corebeau.extra.pla.PullSingleListView;
import com.zifei.corebeau.utils.StringUtil;
import com.zifei.corebeau.utils.Utils;

public class MyItemListAdapter extends BaseAdapter implements OnClickListener {

	private Context context;
	private LayoutInflater inflater;
	private LinkedList<ItemInfo> data;
	private DisplayImageOptions imageOptions;
	private ImageLoader imageLoader;
	private String bigImageConfig;

	public MyItemListAdapter(Context context, PullSingleListView listView) {
		this.context = context;
		data = new LinkedList<ItemInfo>();
		this.inflater = LayoutInflater.from(context);

		getConfig();
		imageLoader = ImageLoader.getInstance();

		imageOptions = new DisplayImageOptions.Builder() //
				.delayBeforeLoading(200) // 载入之前的延迟时间
				.cacheInMemory(true).build();
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
			convertView = inflater
					.inflate(R.layout.item_my_item, parent, false);

			holder = new ViewHolder();

			holder.message = (TextView) convertView
					.findViewById(R.id.tv_my_item_message);
			holder.image = (ScaleImageView) convertView
					.findViewById(R.id.iv_my_item_image);
			holder.goPostDetail = (TextView) convertView
					.findViewById(R.id.tv_go_my_detail);
			convertView.setTag(holder);
		}

		holder = (ViewHolder) convertView.getTag();

		holder.image.setImageWidth(screenWidth);
		holder.image
				.setImageHeight((int) (((double) screenWidth) * ((double) bigImgHeight / (double) bigIwidth)));

		holder.goPostDetail.setTag(position);
		holder.goPostDetail.setOnClickListener(this);
		holder.message.setText(p.getTitle());

		ImageAware imageAware = new ImageViewAware(holder.image, false);
		String url = p.getShowUrl();
		if (!StringUtil.isEmpty(url)) {
			if (bigImageConfig != null && !StringUtil.isEmpty(bigImageConfig)) {
				imageLoader.displayImage(url + bigImageConfig, imageAware,
						imageOptions);
			} else {
				imageLoader.displayImage(url, imageAware, imageOptions);
			}
		} else {
			// holder.image.setBackgroundColor(color.blue);
		}

		holder.image.setTag(p.getShowUrl());
		return convertView;
	}

	private class ViewHolder {
		TextView message;
		ScaleImageView image;
		TextView goPostDetail;
	}

	private OnMyDetailStartClickListener onMyDetailStartClickListener;

	public interface OnMyDetailStartClickListener {
		public void onMyDetailStartClicked(View view, int position);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.tv_go_my_detail) {
			Integer position = (Integer) v.getTag();
			if (onMyDetailStartClickListener != null) {
				onMyDetailStartClickListener
						.onMyDetailStartClicked(v, position);
			}
		}
	}

	public void setOnMyDetailStartClickListener(
			OnMyDetailStartClickListener onMyDetailStartClickListener) {
		this.onMyDetailStartClickListener = onMyDetailStartClickListener;
	}

}
