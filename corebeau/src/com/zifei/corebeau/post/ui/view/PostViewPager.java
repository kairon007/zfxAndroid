package com.zifei.corebeau.post.ui.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class PostViewPager extends ViewPager {

	public PostViewPager(Context context) {
		super(context);
	}

	public PostViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		final int width = MeasureSpec.getSize(widthMeasureSpec);
		int maxheight = 0;
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			child.measure(widthMeasureSpec, heightMeasureSpec);
			int childheight = child.getMeasuredHeight();
			if (childheight > maxheight)
				maxheight = childheight;
		}
		super.onMeasure(
				MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
				MeasureSpec.makeMeasureSpec(maxheight, MeasureSpec.EXACTLY));
	}
	
}
