package com.zifei.corebeau.search.ui.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.common.CorebeauApp;
import com.zifei.corebeau.utils.StringUtil;

public class SampleAdapter extends ArrayAdapter<ItemInfo> {

	private DisplayImageOptions imageOptions;
	private ImageLoader imageLoader;
	private final LayoutInflater mLayoutInflater;
	private String smallImageConfig;
	private int columnWidth;

	public SampleAdapter(final Context context, final int textViewResourceId,
			List<ItemInfo> objects) {
		super(context, textViewResourceId, objects);
		getSmallImageConfig();
		this.mLayoutInflater = LayoutInflater.from(context);
		imageLoader = ImageLoader.getInstance();
		imageOptions = new DisplayImageOptions.Builder().cacheInMemory(false)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED).cacheOnDisk(true)
				.build();
	}
	
	public void setColumnWidth(int columnWidth){
		this.columnWidth = columnWidth;
	}

	private void getSmallImageConfig() {
		this.smallImageConfig = CorebeauApp.getSmallImageConfig();
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {

		ItemInfo itemInfo = getItem(position);
		int smallImgHeight= itemInfo.getSheight();
		int smallImgWidth= itemInfo.getSwidth();
		
		ViewHolder vh;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.item_search_post,
					parent, false);
			vh = new ViewHolder();
			vh.image = (ImageView) convertView
					.findViewById(R.id.iv_search_post);
			
			if(smallImgHeight!=0 && smallImgWidth!=0 && columnWidth!=0){
			vh.image.getLayoutParams().height = (int) (((double)columnWidth)*((double)smallImgHeight/(double)smallImgWidth));
			}
			vh.message = (TextView) convertView
					.findViewById(R.id.tv_search_message);
			vh.likeCnt = (TextView) convertView
					.findViewById(R.id.tv_search_like);
			vh.commentCnt = (TextView) convertView
					.findViewById(R.id.tv_comment_cnt);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		if (smallImageConfig != null && !StringUtil.isEmpty(smallImageConfig)) {
			imageLoader.displayImage(itemInfo.getShowUrl()
					+ smallImageConfig, vh.image, imageOptions);
			Log.i("","test use small ");
		} else {
			imageLoader.displayImage(itemInfo.getShowUrl(), vh.image,
					imageOptions);
		}
		vh.message.setText(itemInfo.getTitle());
		vh.likeCnt.setText(String.valueOf(itemInfo.getLikeCnt()));
		vh.commentCnt
				.setText(String.valueOf(itemInfo.getCommentCnt()));
		
		return convertView;
	}

	static class ViewHolder {
		ImageView image;
		TextView likeCnt;
		TextView commentCnt;
		TextView message;
	}

}
