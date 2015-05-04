package com.zifei.corebeau.my.ui.fragment;

import android.support.v4.app.Fragment;
import android.widget.AbsListView;

import com.zifei.corebeau.my.ui.parallaxheader.ScrollTabHolder;

public abstract class ScrollTabHolderFragment extends Fragment implements ScrollTabHolder {

	protected ScrollTabHolder mScrollTabHolder;

	public void setScrollTabHolder(ScrollTabHolder scrollTabHolder) {
		mScrollTabHolder = scrollTabHolder;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {
		// nothing
	}

}