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
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zifei.corebeau.R;
import com.zifei.corebeau.User.ui.OtherUserActivity;
import com.zifei.corebeau.common.ui.view.CircularImageView;
import com.zifei.corebeau.my.bean.FollowUser;
import com.zifei.corebeau.post.ui.PostActivity;
import com.zifei.corebeau.spot.bean.SpotList;
import com.zifei.corebeau.utils.StringUtil;

public class FollowAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<FollowUser> data = null;
	private DisplayImageOptions imageOptions;
	private ImageLoader imageLoader;
	private ImageLoaderConfiguration config;

	public FollowAdapter(Context context, ListView listView) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		imageLoader = ImageLoader.getInstance();
		config = new ImageLoaderConfiguration.Builder(context)
				.threadPoolSize(3).build();
		imageLoader.init(config);

		imageOptions = new DisplayImageOptions.Builder() //
				// .showImageOnLoading(R.drawable.mall_item_bg) // 载入时图片设置为黑色
				// .showImageOnFail(R.drawable.mall_item_bg) // 加载失败时显示的图片
				.delayBeforeLoading(200) // 载入之前的延迟时间
				.cacheInMemory(false).cacheOnDisk(true)
				// .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				// .displayer(new FadeInBitmapDisplayer(500))
				.build();
	}

	public void addData(List<FollowUser> data, boolean append) {
		if (append) {
			this.data.addAll(data);
		} else {
			this.data = data;
		}
		notifyDataSetChanged();
	}

	public List<FollowUser> getData() {
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

		final FollowUser p = data.get(position);

		String urlThumb = p.getUserIcon();
		if (!StringUtil.isEmpty(urlThumb)) {
			imageLoader.displayImage(urlThumb, holder.usericon, imageOptions);
		} else {
		}

		holder.nickName.setText(p.getNickname());

		convertView.setTag(position);
		return convertView;
	}

	private class ViewHolder {
		TextView nickName;
		CircularImageView usericon;
	}

}
