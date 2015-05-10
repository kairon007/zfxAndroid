package com.zifei.corebeau.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.ItemComment;
import com.zifei.corebeau.extra.CircularImageView;
import com.zifei.corebeau.utils.StringUtil;

/**
 * Created by im14s_000 on 2015/3/28.
 */
public class CommentAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<ItemComment> data = null;
	private DisplayImageOptions iconImageOptions;
	private ImageLoader imageLoader;
	private ImageLoaderConfiguration config;

	public CommentAdapter(Context context, ListView listview) {
		inflater = LayoutInflater.from(context);
		imageLoader = ImageLoader.getInstance();
		config = new ImageLoaderConfiguration.Builder(context)
				.threadPoolSize(3).build();
		imageLoader.init(config);

		iconImageOptions = new DisplayImageOptions.Builder()
		//
		.delayBeforeLoading(200)
		.cacheInMemory(true)
		.showImageOnFail(R.drawable.user_icon_default)
		.showImageForEmptyUri(R.drawable.user_icon_default)
		.showImageOnLoading(R.drawable.user_icon_default)
		.build();
	}

	public void addData(List<ItemComment> data, boolean append) {
		if (append) {
			this.data.addAll(data);
		} else {
			this.data = data;
		}
		notifyDataSetChanged();
	}

	public List<ItemComment> getData() {
		return this.data;
	}
	
	public void clearAdapter() {
		data.clear();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater
					.inflate(R.layout.item_comment, parent, false);
		}
		ViewHolder holder = new ViewHolder();
		holder.image = (CircularImageView) convertView
				.findViewById(R.id.iv_comment_icon);
		holder.image.setBorderWidth(1);
		holder.message = (TextView) convertView
				.findViewById(R.id.tv_comment_message);
		holder.nickName = (TextView) convertView
				.findViewById(R.id.tv_comment_nickname);

		ItemComment p = data.get(position);

		
		holder.message.setText(p.getContent());
		
		String nick = p.getUserNickName();
		if(nick!=null){
			holder.nickName.setText(nick);
		}else{
			
		}

		String url = p.getUserImageUrl();
		if (!StringUtil.isEmpty(url)) {
			imageLoader.displayImage(url, holder.image, iconImageOptions);
		} else {
			imageLoader.displayImage("drawable://" + R.drawable.user_icon_default, holder.image, iconImageOptions);
		}

		convertView.setTag(position);
		return convertView;
	}

	private class ViewHolder {
		CircularImageView image;
		TextView message;
		TextView nickName;
	}
}
