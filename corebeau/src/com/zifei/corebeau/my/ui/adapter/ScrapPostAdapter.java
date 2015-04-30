package com.zifei.corebeau.my.ui.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.common.ui.view.CircularImageView;
import com.zifei.corebeau.post.ui.PostDetailActivity;
import com.zifei.corebeau.utils.StringUtil;

public class ScrapPostAdapter  extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<ItemInfo> data = null;
	private DisplayImageOptions imageOptions;
	private ImageLoader imageLoader;
	private ImageLoaderConfiguration config;

	public ScrapPostAdapter(Context context, ListView listView) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		imageLoader = ImageLoader.getInstance();
		config = new ImageLoaderConfiguration.Builder(context)
				.threadPoolSize(3).build();
		imageLoader.init(config);

		imageOptions = new DisplayImageOptions.Builder() //
				.delayBeforeLoading(200) // 载入之前的延迟时间
				.cacheInMemory(false).cacheOnDisk(true)
				// .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				// .displayer(new FadeInBitmapDisplayer(500))
				.build();
	}

	public void addData(List<ItemInfo> data, boolean append) {
		if (append) {
			this.data.addAll(data);
		} else {
			this.data = data;
		}
		notifyDataSetChanged();
	}

	public List<ItemInfo> getData() {
		return this.data;
	}

	public void startLoading() {
		notifyDataSetChanged();
	}

	public void endLoading() {
	}

	@Override
	public int getCount() {
		if (data == null) {
			return 0;
		}
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		if (data == null) {
			return null;
		}
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_scrap, parent, false);
		}

		ViewHolder holder = new ViewHolder();
		holder.usericon = (CircularImageView) convertView
				.findViewById(R.id.scrap_user_thumb);
		holder.usericon.setBorderWidth(1);
		holder.message = (TextView) convertView
				.findViewById(R.id.tv_scrap_message);
		holder.image = (ImageView) convertView.findViewById(R.id.scrap_image);
		holder.nickName = (TextView)convertView.findViewById(R.id.scrap_user_nickname);
		holder.goPostDetail = (TextView) convertView
				.findViewById(R.id.tv_go_detail);

		final ItemInfo p = data.get(position);

		holder.message.setText(p.getTitle());
		holder.nickName.setText(p.getNickName());
		
		String url = p.getShowUrl();
		if (!StringUtil.isEmpty(url)) {
			imageLoader.displayImage(url+"", holder.image, imageOptions);
		} else {
		}
		
		String iconUrl = p.getUserImageUrl();
		if (!StringUtil.isEmpty(iconUrl)) {
			imageLoader.displayImage(iconUrl, holder.image, imageOptions);
		} else {
		}
		
		holder.goPostDetail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goPostPage(p.getItemId());
			}
		});
		convertView.setTag(position);
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
		ImageView image;
		TextView goPostDetail;
	}

}