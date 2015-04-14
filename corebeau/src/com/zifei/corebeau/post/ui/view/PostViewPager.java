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

//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		
//		final int width = MeasureSpec.getSize(widthMeasureSpec);
//		int maxheight = 0;
//		final int count = getChildCount();
//		for (int i = 0; i < count; i++) {
//			View child = getChildAt(i);
//			child.measure(widthMeasureSpec, heightMeasureSpec);
//			int childheight = child.getMeasuredHeight();
//			if (childheight > maxheight)
//				maxheight = childheight;
//		}
//		super.onMeasure(
//				MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
//				MeasureSpec.makeMeasureSpec(maxheight, MeasureSpec.EXACTLY));
//	}
	
//	@Override
//    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        boolean wrapHeight = MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST;
//
//        final View tab = getChildAt(0);
//        int width = getMeasuredWidth();
//        int tabHeight = tab.getMeasuredHeight();
//        
////        tabHeight = 0;
//
//        if (wrapHeight) {
//            // Keep the current measured width.
//            widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
//        }
//
//        int fragmentHeight = measureFragment(((Fragment) getAdapter().instantiateItem(this, getCurrentItem())).getView());
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(tabHeight + fragmentHeight + (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()), MeasureSpec.AT_MOST);
//
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }
//
//    public int measureFragment(View view) {
//        if (view == null)
//            return 0;
//
//        view.measure(0, 0);
//        return view.getMeasuredHeight();
//    }
	
	int getMeasureExactly(View child, int widthMeasureSpec) {
	    child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
	    int height = child.getMeasuredHeight();
	    return MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    boolean wrapHeight = MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST;

	    final View tab = getChildAt(0);
	    if (tab == null) {
	        return;
	    }

	    int width = getMeasuredWidth();
	    if (wrapHeight) {
	        // Keep the current measured width.
	        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
	    }
	    Fragment fragment = ((Fragment) getAdapter().instantiateItem(this, getCurrentItem()));
	    heightMeasureSpec = getMeasureExactly(fragment.getView(), widthMeasureSpec);

	    //Log.i(Constants.TAG, "item :" + getCurrentItem() + "|height" + heightMeasureSpec);
	    // super has to be called again so the new specs are treated as
	    // exact measurements.
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
