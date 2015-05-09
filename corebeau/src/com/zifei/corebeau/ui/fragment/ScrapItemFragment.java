package com.zifei.corebeau.ui.fragment;

import java.util.List;

import android.R.color;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.bean.PageBean;
import com.zifei.corebeau.bean.response.ScrapItemListResponse;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.extra.pla.PullSingleListView;
import com.zifei.corebeau.extra.pla.PullSingleListView.SingleRefreshListener;
import com.zifei.corebeau.extra.pla.internal.PLA_AbsListView;
import com.zifei.corebeau.extra.pla.internal.PLA_AbsListView.OnScrollListener;
import com.zifei.corebeau.task.ScrapTask;
import com.zifei.corebeau.ui.adapter.ScrapPostAdapter;
import com.zifei.corebeau.utils.Utils;

public class ScrapItemFragment extends PLAScrollTabHolderFragment implements OnScrollListener,SingleRefreshListener {
	
	private static final String ARG_POSITION = "position";
	private int mPosition;
	private ScrapPostAdapter scrapPostAdapter;
	private ScrapTask scrapTask;
	private PullSingleListView listView;
	
	private boolean isLast = false;
	private int currentPage;
	private boolean isRequestPost = false;
	
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
		scrapTask = new ScrapTask(getActivity());
		mPosition = 1;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_scrap, container, false);
		
		
		listView = (PullSingleListView) view.findViewById(R.id.lv_scrap);
		listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
		
		View placeHolderView = inflater.inflate(R.layout.view_header_placeholder, listView, false);
		listView.addHeaderView(placeHolderView);
		
		scrapPostAdapter = new ScrapPostAdapter(getActivity(), listView);
		listView.setAdapter(scrapPostAdapter);
		
		listView.setOnScrollListener(this);
		listView.setSingleRefreshListener(this);
		listView.setSelector(color.transparent);
		getScrapList();
		return view;
	}
	
	private void getScrapList() {
		if(isLast){
			return;
		}
		isRequestPost = true;
		
		
		scrapTask
				.getScrapList(currentPage, new AsyncCallBacks.OneOne<ScrapItemListResponse, String>() {

					@Override
					public void onSuccess(ScrapItemListResponse response) {

						PageBean<ItemInfo> pageBean = (PageBean<ItemInfo>) response
								.getPageBean();
						List<ItemInfo> list = pageBean.getList();
						currentPage = pageBean.getCurrentPage();
						
						if(list.size() >= 30){
							isLast = false;
							if(currentPage==1){
								scrapPostAdapter.addItemTop(list);
								scrapPostAdapter.notifyDataSetChanged();
							}else{
								scrapPostAdapter.addItemLast(list);
								scrapPostAdapter.notifyDataSetChanged();
							}
							currentPage = currentPage+1;
						}else if(list.size() < 30){
							isLast = true;
							if(currentPage==1){
								scrapPostAdapter.addItemTop(list);
								scrapPostAdapter.notifyDataSetChanged();
							}else{
								scrapPostAdapter.addItemLast(list);
								scrapPostAdapter.notifyDataSetChanged();
							}
						}else{
							isLast = true;
						}
						isRequestPost = false;
					}

					@Override
					public void onError(String msg) {
						// progressBar.setVisibility(View.INVISIBLE);
						Utils.showToast(getActivity(), msg);
					}
				});
	}
	
	
	private void getScrapListRefresh() {
		currentPage = 0;
		scrapTask
				.getScrapList(currentPage, new AsyncCallBacks.OneOne<ScrapItemListResponse, String>() {

					@Override
					public void onSuccess(ScrapItemListResponse response) {

						PageBean<ItemInfo> pageBean = (PageBean<ItemInfo>) response
								.getPageBean();
						List<ItemInfo> list = pageBean.getList();
						currentPage = pageBean.getCurrentPage();
						scrapPostAdapter.clearAdapter();
						
						if(list.size() >= 30){
							isLast = false;
							if(currentPage==1){
								scrapPostAdapter.addItemTop(list);
								scrapPostAdapter.notifyDataSetChanged();
								listView.stopRefresh();
							}else{
								scrapPostAdapter.addItemLast(list);
								scrapPostAdapter.notifyDataSetChanged();
							}
							currentPage = currentPage+1;
						}else if(list.size() < 30){
							isLast = true;
							if(currentPage==1){
								scrapPostAdapter.addItemTop(list);
								scrapPostAdapter.notifyDataSetChanged();
								listView.stopRefresh();
							}else{
								scrapPostAdapter.addItemLast(list);
								scrapPostAdapter.notifyDataSetChanged();
							}
						}else{
							isLast = true;
						}
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
	public void onScroll(PLA_AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount, int pagePosition) {
		
	}

	@Override
	public void onRefresh() {
		getScrapListRefresh();		
	}

	@Override
	public void onScrollStateChanged(PLA_AbsListView view, int scrollState) {
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			if (view.getLastVisiblePosition() == (view.getCount()-1)) {
				if(!isRequestPost){
					getScrapList();
				}
			}
		}
	}

	@Override
	public void onScroll(PLA_AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (mScrollTabHolder != null)
			mScrollTabHolder.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, mPosition);
		
		
	}


}
