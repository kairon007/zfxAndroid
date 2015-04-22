package com.zifei.corebeau.search.ui.adapter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.media.Image;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.zifei.corebeau.R;
import com.zifei.corebeau.common.ui.widget.staggered.util.DynamicHeightImageView;
import com.zifei.corebeau.search.bean.ItemInfo;

public class SampleAdapter extends ArrayAdapter<ItemInfo> {

	private DisplayImageOptions imageOptions;
	private ImageLoader imageLoader;
	private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
	private final Random mRandom;

	private final LayoutInflater mLayoutInflater;

	public SampleAdapter(final Context context, final int textViewResourceId, List<ItemInfo> objects) {
		super(context, textViewResourceId, objects);
		
		this.mLayoutInflater = LayoutInflater.from(context);
		this.mRandom = new Random();
		imageLoader = ImageLoader.getInstance();
		imageOptions = new DisplayImageOptions.Builder().cacheInMemory(false)
				.imageScaleType(ImageScaleType.EXACTLY)
				.cacheOnDisk(true).build();
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {

		String imgUrl = getItem(position).getShowUrl();
		 try {
			URL u = new URL(imgUrl);
			Image i= ImageIO.read(u);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		ViewHolder vh;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.item_search_post,
					parent, false);
			vh = new ViewHolder();
			vh.image = (DynamicHeightImageView) convertView
					.findViewById(R.id.iv_search_post);
			vh.message = (TextView)convertView.findViewById(R.id.tv_search_message);
			vh.likeCnt = (TextView)convertView.findViewById(R.id.tv_search_like);
			vh.commentCnt = (TextView)convertView.findViewById(R.id.tv_comment_cnt);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		imageLoader.displayImage(getItem(position).getShowUrl(), vh.image,imageOptions);
		vh.message.setText(getItem(position).getTitle());
		vh.likeCnt.setText(String.valueOf(getItem(position).getLikeCnt()));
		vh.commentCnt.setText(String.valueOf(getItem(position).getCommentCnt()));
		return convertView;
	}
	
	static class ViewHolder {
		DynamicHeightImageView image;
		TextView likeCnt;
		TextView commentCnt;
		TextView message;
	}
	
	private double getPositionRatio(final int position) {
		double ratio = sPositionHeightRatios.get(position, 0.0);
		// if not yet done generate and stash the columns height
		// in our real world scenario this will be determined by
		// some match based on the known height and width of the image
		// and maybe a helpful way to get the column height!
		if (ratio == 0) {
			ratio = getRandomHeightRatio();
			sPositionHeightRatios.append(position, ratio);
		}
		return ratio;
	}

	private double getRandomHeightRatio() {
		return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5
		// the width
	}

}