package com.zifei.corebeau.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.zifei.corebeau.R;
import com.zifei.corebeau.R.color;
import com.zifei.corebeau.bean.RecommendUserList;
import com.zifei.corebeau.bean.UserInfo;
import com.zifei.corebeau.extra.CircularImageView;
import com.zifei.corebeau.extra.HorizontalListView;
import com.zifei.corebeau.utils.StringUtil;

/**
 * Created by im14s_000 on 2015/4/2.
 */
public class RecommedUserAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<UserInfo> data = null;
	private DisplayImageOptions iconImageOptions;
	private ImageLoader imageLoader;

	public RecommedUserAdapter(Context context, HorizontalListView listView) {
		inflater = LayoutInflater.from(context);
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

	public void addData(List<UserInfo> data, boolean append) {
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
	public UserInfo getItem(int position) {
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
		holder.usericon.setBorderWidth(1);
		holder.usericon.setBorderColor(color.spot_top_divider);
		holder.nickName = (TextView) convertView
				.findViewById(R.id.tv_search_user_nick);

		final UserInfo p = data.get(position);

		String urlThumb = p.getUrl();
		String nick = p.getNickName();
		
		if (!StringUtil.isEmpty(urlThumb)) {
			imageLoader.displayImage(urlThumb, holder.usericon, iconImageOptions);
		} else {
			imageLoader.displayImage("drawable://" + R.drawable.user_icon_default, holder.usericon, iconImageOptions);
		}
		
		if(nick!=null){
		 holder.nickName.setText(p.getNickName());
		}else{
			holder.nickName.setText("guest");
		}
		
		convertView.setTag(position);
		return convertView;
	}

	private class ViewHolder {
		TextView nickName;
		CircularImageView usericon;
	}

}
