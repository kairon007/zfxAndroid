package com.zifei.corebeau.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.utils.StringUtil;
import com.zifei.corebeau.utils.Utils;

public class MyItemListAdapter extends BaseAdapter implements OnClickListener {

	private Context context;
	private LayoutInflater inflater;
	private List<ItemInfo> data = null;
	private DisplayImageOptions imageOptions;
	private ImageLoader imageLoader;

	public MyItemListAdapter(Context context, ListView listView) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		imageLoader = ImageLoader.getInstance();

		imageOptions = new DisplayImageOptions.Builder() //
				.delayBeforeLoading(200) // 载入之前的延迟时间
				.cacheInMemory(true)
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
	
	public void clearAdapter(){
		if(this.data!=null){
		this.data.clear();
		}
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
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater
					.inflate(R.layout.item_my_item, parent, false);
		}
		final ItemInfo p = data.get(position);
		int bigImgHeight = p.getBheight();
		int bigIwidth = p.getBwidth();
		int screenWidth = Utils.getScreenWidth(context);

		ViewHolder holder = new ViewHolder();
		holder.message = (TextView) convertView
				.findViewById(R.id.tv_my_item_message);
		holder.image = (ImageView) convertView
				.findViewById(R.id.iv_my_item_image);
		if (bigImgHeight != 0 && bigIwidth != 0 && screenWidth != 0) {
			holder.image.getLayoutParams().height = (int) (((double) screenWidth) * ((double) bigImgHeight / (double) bigIwidth));
		}
		holder.goPostDetail = (TextView) convertView
				.findViewById(R.id.tv_go_my_detail);
		holder.goPostDetail.setTag(position);
		holder.goPostDetail.setOnClickListener(this);
		holder.message.setText(p.getTitle());

		String url = p.getShowUrl();
		ImageAware imageAware = new ImageViewAware(holder.image, false);
		if (!StringUtil.isEmpty(url)) {
			imageLoader.displayImage(url, imageAware, imageOptions);
		} else {
			// holder.image.setBackgroundColor(color.blue);
		}
		holder.image.setTag(p.getShowUrl());

		convertView.setTag(position);
		return convertView;
	}

	private class ViewHolder {
		TextView message;
		ImageView image;
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
