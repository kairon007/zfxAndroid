package com.zifei.corebeau.ui.fragment;

import com.zifei.corebeau.extra.parallaxheader.PLAScrollTabHolder;

import android.support.v4.app.Fragment;

public abstract class PLAScrollTabHolderFragment extends Fragment implements PLAScrollTabHolder {

	protected PLAScrollTabHolder mScrollTabHolder;

	public void setScrollTabHolder(PLAScrollTabHolder scrollTabHolder) {
		mScrollTabHolder = scrollTabHolder;
	}


}