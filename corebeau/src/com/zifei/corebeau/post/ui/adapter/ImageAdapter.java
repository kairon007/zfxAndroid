package com.zifei.corebeau.post.ui.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.zifei.corebeau.R;

/**
 * Created by im14s_000 on 2015/3/25.
 */
public class ImageAdapter extends PagerAdapter {

	private List<String> data = null;
	private DisplayImageOptions imageOptions;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageLoadingListener imageListener;
	private Activity activity;

	public ImageAdapter(Activity activity, List<String> data) {
		this.activity = activity;
		this.data = data;
		imageOptions = new DisplayImageOptions.Builder() //
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.build();
		imageListener = new ImageDisplayListener();
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public View instantiateItem(ViewGroup container, final int position) {
		LayoutInflater inflater = (LayoutInflater) activity
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.item_post_image, container, false);

		ImageView mImageView = (ImageView) view
				.findViewById(R.id.image_display);
		
		imageLoader.displayImage(
				data.get(position), mImageView,
				imageOptions, imageListener);
		container.addView(view);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	private static class ImageDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
