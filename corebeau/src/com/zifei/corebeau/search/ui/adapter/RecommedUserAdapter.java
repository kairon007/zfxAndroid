package com.zifei.corebeau.search.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.meetme.android.horizontallistview.HorizontalListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.zifei.corebeau.R;
import com.zifei.corebeau.common.ui.view.CircularImageView;
import com.zifei.corebeau.search.bean.RecommendUserList;
import com.zifei.corebeau.utils.StringUtil;

import java.util.List;

/**
 * Created by im14s_000 on 2015/4/2.
 */
public class RecommedUserAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<RecommendUserList> data = null;
	private DisplayImageOptions imageOptions;
	private ImageLoader imageLoader;
	private ImageLoaderConfiguration config;

	public RecommedUserAdapter(Context context, HorizontalListView listView) {
		inflater = LayoutInflater.from(context);
		imageLoader = ImageLoader.getInstance();
		config = new ImageLoaderConfiguration.Builder(context)
				.threadPoolSize(3).build();
		imageLoader.init(config);

		imageOptions = new DisplayImageOptions.Builder() //
				.imageScaleType(ImageScaleType.EXACTLY).build();
	}

	public void addData(List<RecommendUserList> data, boolean append) {
		if (append) {
			this.data.addAll(data);
		} else {
			this.data = data;
		}
		notifyDataSetChanged();
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
			convertView = inflater.inflate(R.layout.item_search_user, parent,
					false);
		}
		ViewHolder holder = new ViewHolder();
		holder.usericon = (CircularImageView) convertView
				.findViewById(R.id.civ_search_user_recommend);
		holder.usericon.setBorderWidth(0);
		// holder.nickName = (TextView)
		// convertView.findViewById(R.id.spot_user_nickname);

		final RecommendUserList p = data.get(position);

		String urlThumb = p.getPicThumbUrl();
		if (!StringUtil.isEmpty(urlThumb)) {
			imageLoader.displayImage(urlThumb, holder.usericon, imageOptions);
			// imageLoader.displayImage("drawable://" + R.drawable.a1,
			// holder.usericon, imageOptions);
		} else {
		}
		// holder.nickName.setText(p.getNickName());
		convertView.setTag(position);
		return convertView;
	}

	private class ViewHolder {
		TextView nickName;
		CircularImageView usericon;
	}

}
