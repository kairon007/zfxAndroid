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
import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.FollowUserInfo;
import com.zifei.corebeau.extra.CircularImageView;
import com.zifei.corebeau.utils.StringUtil;

public class FollowAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<FollowUserInfo> data = null;
	private DisplayImageOptions iconImageOptions;
	private ImageLoader imageLoader;

	public FollowAdapter(Context context, ListView listView) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		imageLoader = ImageLoader.getInstance();

		iconImageOptions = new DisplayImageOptions.Builder()
		//
		.delayBeforeLoading(200)
		.cacheInMemory(true)
		.showImageOnFail(R.drawable.user_icon_default)
		.showImageForEmptyUri(R.drawable.user_icon_default)
		.showImageOnLoading(R.drawable.user_icon_default)
		.build();
	}

	public void addData(List<FollowUserInfo> data, boolean append) {
		if (append) {
			this.data.addAll(data);
		} else {
			this.data = data;
		}
		notifyDataSetChanged();
	}

	public List<FollowUserInfo> getData() {
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
			convertView = inflater.inflate(R.layout.item_follow, parent, false);
		}

		ViewHolder holder = new ViewHolder();
		holder.usericon = (CircularImageView) convertView
				.findViewById(R.id.civ_follow_icon);
		holder.usericon.setBorderWidth(0);
		holder.nickName = (TextView) convertView
				.findViewById(R.id.tv_follow_nickname);

		final FollowUserInfo p = data.get(position);

		String urlThumb = p.getUrl();
		if (!StringUtil.isEmpty(urlThumb)) {
			imageLoader.displayImage(urlThumb, holder.usericon, iconImageOptions);
		} else {
			imageLoader.displayImage("drawable://" + R.drawable.user_icon_default,
					holder.usericon, iconImageOptions);
		}

		holder.nickName.setText(p.getNickName());

		convertView.setTag(position);
		return convertView;
	}

	private class ViewHolder {
		TextView nickName;
		CircularImageView usericon;
	}

}
