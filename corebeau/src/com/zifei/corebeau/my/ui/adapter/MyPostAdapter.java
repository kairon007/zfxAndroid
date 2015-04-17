package com.zifei.corebeau.my.ui.adapter;

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
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.zifei.corebeau.R;
import com.zifei.corebeau.R.color;
import com.zifei.corebeau.User.ui.OtherUserActivity;
import com.zifei.corebeau.common.ui.view.CircularImageView;
import com.zifei.corebeau.post.ui.PostActivity;
import com.zifei.corebeau.spot.bean.SpotList;
import com.zifei.corebeau.utils.StringUtil;

import java.util.List;

/**
 * Created by im14s_000 on 2015/3/25.
 */
public class MyPostAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<SpotList> data = null;
	private DisplayImageOptions imageOptions;
	private ImageLoader imageLoader;
	private ImageLoaderConfiguration config;

	public MyPostAdapter(Context context, ListView listView) {
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

	public void addData(List<SpotList> data, boolean append) {
		if (append) {
			this.data.addAll(data);
		} else {
			this.data = data;
		}
		notifyDataSetChanged();
	}

	public List<SpotList> getData() {
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
//		holder.usericon.setBorderWidth(5);
		holder.nickName = (TextView) convertView
				.findViewById(R.id.spot_user_nickname);
		holder.message = (TextView) convertView
				.findViewById(R.id.tv_spot_message);
		// holder.date = (TextView) convertView.findViewById(R.id.spot_image);
		holder.image = (ImageView) convertView.findViewById(R.id.spot_image);
		holder.goPostDetail = (TextView) convertView
				.findViewById(R.id.tv_go_detail);

		final SpotList p = data.get(position);

		String urlThumb = p.getUserIcon();
		if (!StringUtil.isEmpty(urlThumb)) {
			imageLoader.displayImage(urlThumb, holder.usericon, imageOptions);
		} else {
		}

		holder.nickName.setText(p.getUserNickname());
		holder.message.setText(p.getMessage());
		// holder.date.setText(p.getMessage());

		String url = p.getPic();
		if (!StringUtil.isEmpty(url)) {
			imageLoader.displayImage(url, holder.image, imageOptions);
		} else {
//			holder.image.setBackgroundColor(color.blue);
		}
		holder.nickName.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				notifyDataSetChanged();
				Toast.makeText(context, "You have deleted row No. " + position,
						Toast.LENGTH_SHORT).show();
			}
		});

		holder.goPostDetail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goPostPage(p.getPostId());
			}
		});

		convertView.setTag(position);
		return convertView;
	}

	private void goPostPage(Integer postId) {
		context.startActivity(new Intent(context, PostActivity.class).putExtra(
				"postId", postId));
	}

	private void goUserPage(Integer userId) {
		context.startActivity(new Intent(context, OtherUserActivity.class)
				.putExtra("userId", userId));
	}

	private class ViewHolder {
		TextView message;
		TextView nickName;
		TextView date;
		CircularImageView usericon;
		ImageView image;
		TextView goPostDetail;
	}

}
