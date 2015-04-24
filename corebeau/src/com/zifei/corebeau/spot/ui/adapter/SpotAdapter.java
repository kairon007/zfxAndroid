package com.zifei.corebeau.spot.ui.adapter;

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
import com.zifei.corebeau.User.ui.OtherUserActivity;
import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.common.ui.view.CircularImageView;
import com.zifei.corebeau.post.ui.PostDetailActivity;
import com.zifei.corebeau.utils.StringUtil;

/**
 * Created by im14s_000 on 2015/3/25.
 */
public class SpotAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<ItemInfo> data = null;
	private DisplayImageOptions imageOptions;
	private DisplayImageOptions iconImageOptions;
	private ImageLoader imageLoader;
	private ImageLoaderConfiguration config;

	public SpotAdapter(Context context, ListView listView) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		imageLoader = ImageLoader.getInstance();
		config = new ImageLoaderConfiguration.Builder(context)
				.threadPoolSize(3).build();
		imageLoader.init(config);

		imageOptions = new DisplayImageOptions.Builder() //
				.delayBeforeLoading(200) // 载入之前的延迟时间
				.cacheInMemory(false).cacheOnDisk(true)
				.build();
		
		iconImageOptions = new DisplayImageOptions.Builder() //
		.delayBeforeLoading(200) // 载入之前的延迟时间
		.showImageForEmptyUri(R.drawable.my_default)
		.showImageOnFail(R.drawable.my_default)
		.showImageOnLoading(R.drawable.my_default)
		.cacheInMemory(false).cacheOnDisk(true)
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
			convertView = inflater.inflate(R.layout.item_spot, parent, false);
		}

		ViewHolder holder = new ViewHolder();
		holder.usericon = (CircularImageView) convertView
				.findViewById(R.id.spot_user_thumb);
		holder.usericon.setBorderWidth(0);
		holder.nickName = (TextView) convertView
				.findViewById(R.id.spot_user_nickname);
		holder.message = (TextView) convertView
				.findViewById(R.id.tv_spot_message);
		holder.image = (ImageView) convertView.findViewById(R.id.spot_image);
		holder.goPostDetail = (TextView) convertView
				.findViewById(R.id.tv_go_detail);
		holder.commentCnt = (TextView)convertView.findViewById(R.id.tv_spot_comment_cnt);
		

		final ItemInfo p = data.get(position);
		imageLoader.displayImage("drawable://" + R.drawable.my_default, holder.usericon, iconImageOptions);
		String urlThumb = p.getUserImageUrl();
		if (!StringUtil.isEmpty(urlThumb)) {
			imageLoader.displayImage(urlThumb, holder.usericon, iconImageOptions);
		} else {
		}

		holder.nickName.setText(p.getNickName());
		holder.message.setText(p.getTitle());
		holder.commentCnt.setText(String.valueOf(p.getCommentCnt()));
		String url = p.getShowUrl();
		if (!StringUtil.isEmpty(url)) {
			imageLoader.displayImage(url, holder.image, imageOptions);
		} else {

		}
		
		holder.usericon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goUserPage(p.getUserId());
			}
		});

		holder.goPostDetail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goPostPage(p);
			}
		});

		convertView.setTag(position);
		return convertView;
	}

	private void goPostPage(ItemInfo itemInfo) {
		context.startActivity(new Intent(context, PostDetailActivity.class).putExtra(
				"itemInfo", itemInfo));
	}

	private void goUserPage(String userId) {
		context.startActivity(new Intent(context, OtherUserActivity.class)
				.putExtra("userId", userId));
	}

	private class ViewHolder {
		TextView message;
		TextView nickName;
		CircularImageView usericon;
		ImageView image;
		TextView goPostDetail;
		TextView commentCnt;
	}

}
