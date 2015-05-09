package com.zifei.corebeau.extra.parallaxheader;

import com.zifei.corebeau.extra.pla.PullSingleListView;
import com.zifei.corebeau.extra.pla.internal.PLA_AbsListView;

import android.widget.AbsListView;

public interface PLAScrollTabHolder {

	void adjustScroll(int scrollHeight);

	void onScroll(PLA_AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition);
}
