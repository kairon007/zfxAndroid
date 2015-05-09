package com.zifei.corebeau.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.response.ScrapItemListResponse;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.task.ScrapTask;
import com.zifei.corebeau.ui.adapter.ScrapPostAdapter;
import com.zifei.corebeau.utils.Utils;

public class ScrapItemFragment extends ScrollTabHolderFragment implements OnScrollListener {
	
	private static final String ARG_POSITION = "position";
	private int mPosition;
	private ScrapPostAdapter scrapPostAdapter;
	private ScrapTask scrapTask;
	private ListView listView;
	
	public static ScrapItemFragment newInstance(int position) {
		ScrapItemFragment fragment = new ScrapItemFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_POSITION, position);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPosition = 1;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_scrap, container, false);
		
		scrapTask = new ScrapTask(getActivity());
		listView = (ListView) view.findViewById(R.id.lv_scrap);
		
		View placeHolderView = inflater.inflate(R.layout.view_header_placeholder, listView, false);
		listView.addHeaderView(placeHolderView);
		listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
		scrapPostAdapter = new ScrapPostAdapter(getActivity(), listView);
		listView.setAdapter(scrapPostAdapter);
		getScrapList();
		return view;
	}
	
	private void getScrapList() {
		// progressBar.setVisibility(View.VISIBLE);
		scrapTask
				.getScrapList(new AsyncCallBacks.OneOne<ScrapItemListResponse, String>() {

					@Override
					public void onSuccess(ScrapItemListResponse response) {
						// progressBar.setVisibility(View.INVISIBLE);
						scrapPostAdapter.addData(response.getPageBean()
								.getList(), false);
					}

					@Override
					public void onError(String msg) {
						// progressBar.setVisibility(View.INVISIBLE);
						Utils.showToast(getActivity(), msg);
					}
				});
	}
	
	@Override
	public void adjustScroll(int scrollHeight) {
		if (scrollHeight == 0 && listView.getFirstVisiblePosition() >= 1) {
			return;
		}

		listView.setSelectionFromTop(1, scrollHeight);

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (mScrollTabHolder != null)
			mScrollTabHolder.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, mPosition);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// nothing
	}


}
