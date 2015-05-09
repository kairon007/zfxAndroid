package com.zifei.corebeau.search.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.bean.PageBean;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.ui.view.HorizontalListView;
import com.zifei.corebeau.post.ui.PostDetailActivity;
import com.zifei.corebeau.search.bean.Response.RecommendPostResponse;
import com.zifei.corebeau.search.bean.Response.RecommendUserResponse;
import com.zifei.corebeau.search.task.SearchTask;
import com.zifei.corebeau.search.ui.adapter.RecommedUserAdapter;
import com.zifei.corebeau.search.ui.adapter.SearchPostAdapter;
import com.zifei.corebeau.search.ui.view.pla.XListView;
import com.zifei.corebeau.search.ui.view.pla.XListView.IXListViewListener;
import com.zifei.corebeau.search.ui.view.pla.internal.PLA_AbsListView;
import com.zifei.corebeau.search.ui.view.pla.internal.PLA_AbsListView.OnScrollListener;
import com.zifei.corebeau.search.ui.view.pla.internal.PLA_AdapterView;
import com.zifei.corebeau.search.ui.view.pla.internal.PLA_AdapterView.OnItemClickListener;
import com.zifei.corebeau.utils.Utils;

public class SearchFragment extends Fragment implements
		OnScrollListener, OnItemClickListener, View.OnClickListener, IXListViewListener {

	// private SearchView searchView;
	private HorizontalListView horizontalListView;
	private XListView mAdapterView;
	private RecommedUserAdapter recommedUserAdapter;
	private SearchTask searchTask;
	private ProgressBar progressBar;
	private SearchPostAdapter mAdapter;
	public static final String SAVED_DATA_KEY = "SAVED_DATA";
	private ImageView refreshBtn;
	private boolean isLast = false;
	private int requestCurrentPage;
	private boolean isRequestPost = false;

	public static SearchFragment newInstance(String param1, String param2) {
		SearchFragment fragment = new SearchFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public SearchFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();

	}

	private void init() {
		searchTask = new SearchTask(getActivity());

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_search, container, false);
		progressBar = (ProgressBar) view.findViewById(R.id.pb_search);
		mAdapterView = (XListView) view.findViewById(R.id.sgv_search);

		LayoutInflater layoutInflater = getActivity().getLayoutInflater();
		View header = layoutInflater.inflate(R.layout.layout_search_header,
				null);
		horizontalListView = (HorizontalListView) header
				.findViewById(R.id.hlv_search);
		mAdapterView.addHeaderView(header);
		recommedUserAdapter = new RecommedUserAdapter(getActivity(),
				horizontalListView);
		mAdapter = new SearchPostAdapter(getActivity(), mAdapterView);
		mAdapterView.setAdapter(mAdapter);
		horizontalListView.setAdapter(recommedUserAdapter);
		refreshBtn = (ImageView) view.findViewById(R.id.refresh_search);
		refreshBtn.setOnClickListener(this);
		mAdapterView.setOnItemClickListener(this);
		mAdapterView.setOnScrollListener(this);
		mAdapterView.setXListViewListener(this);
		getRecommendPostList();
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	private void getRecommendUserList() {
		// progressBar.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.GONE);
		searchTask
				.getRecommendUserList(new AsyncCallBacks.OneOne<RecommendUserResponse, String>() {
					@Override
					public void onSuccess(RecommendUserResponse response) {
						progressBar.setVisibility(View.GONE);
						// List<RecommendUserList> spotList =
						// response.getRecommendUserList();
						// if (spotList.size() <= 0) {
						// } else {
						// }
					}

					@Override
					public void onError(String msg) {
						progressBar.setVisibility(View.GONE);
						// recommedUserAdapter.addData(TestData.getRecommendUserList(),
						// false);
						// recommedUserAdapter.notifyDataSetChanged();
						Utils.showToast(getActivity(), msg);
					}
				});
	}

	private void getRecommendPostList() {
		if(isLast){
			return;
		}
		
		isRequestPost = true;
		progressBar.setVisibility(View.VISIBLE);
		searchTask
				.getRecommendPostList(requestCurrentPage, new AsyncCallBacks.OneOne<RecommendPostResponse, String>() {
					@Override
					public void onSuccess(RecommendPostResponse response) {
						progressBar.setVisibility(View.GONE);
						PageBean<ItemInfo> pageBean = (PageBean<ItemInfo>) response
								.getPageBean();
						List<ItemInfo> list = pageBean.getList();
						requestCurrentPage = pageBean.getCurrentPage();
							mAdapter.setColumnWidth(mAdapterView
									.getColumnWidth(0));
							
						
						if(list.size() >= 30){
							isLast = false;
							if(requestCurrentPage==1){
								mAdapter.addItemTop(list);
								mAdapter.notifyDataSetChanged();
								
							}else{
				                mAdapter.addItemLast(list);
				                mAdapter.notifyDataSetChanged();
							}
							requestCurrentPage = requestCurrentPage+1;
						}else if(list.size() < 30){
							isLast = true;
							if(requestCurrentPage==1){
								mAdapter.addItemTop(list);
								mAdapter.notifyDataSetChanged();
							}else{
				                mAdapter.addItemLast(list);
				                mAdapter.notifyDataSetChanged();
							}
						}else{
							isLast = true;
						}
						
						
						isRequestPost = false;
					}

					@Override
					public void onError(String msg) {
						progressBar.setVisibility(View.GONE);
						Utils.showToast(getActivity(), msg);
						isRequestPost = false;
						mAdapterView.stopRefresh();
					}
				});
	}
	
	private void getRecommendPostListRefresh() {
		requestCurrentPage = 0;
		searchTask
				.getRecommendPostList(requestCurrentPage, new AsyncCallBacks.OneOne<RecommendPostResponse, String>() {
					@Override
					public void onSuccess(RecommendPostResponse response) {
						PageBean<ItemInfo> pageBean = (PageBean<ItemInfo>) response
								.getPageBean();
						List<ItemInfo> list = pageBean.getList();
						requestCurrentPage = pageBean.getCurrentPage();
							mAdapter.setColumnWidth(mAdapterView
									.getColumnWidth(0));
							mAdapter.clearAdapter();
						if(list.size() >= 30){
							isLast = false;
							if(requestCurrentPage==1){
								mAdapter.addItemTop(list);
								mAdapter.notifyDataSetChanged();
								mAdapterView.stopRefresh();
							}else{
				                mAdapter.addItemLast(list);
				                mAdapter.notifyDataSetChanged();
							}
							requestCurrentPage = requestCurrentPage+1;
						}else if(list.size() < 30){
							isLast = true;
							if(requestCurrentPage==1){
								mAdapter.addItemTop(list);
								mAdapter.notifyDataSetChanged();
								mAdapterView.stopRefresh();
							}else{
				                mAdapter.addItemLast(list);
				                mAdapter.notifyDataSetChanged();
							}
						}else{
							isLast = true;
						}
					}

					@Override
					public void onError(String msg) {
						Utils.showToast(getActivity(), msg);
					}
				});
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		default:
			break;
		}
	}


	@Override
	public void onItemClick(PLA_AdapterView<?> parent, View view, int position,
			long id) {
		ItemInfo itemInfo = mAdapter.getItem(position-2);
		Intent intent = new Intent(getActivity(), PostDetailActivity.class);
		intent.putExtra("itemInfo", itemInfo);
		startActivity(intent);
	}

	@Override
	public void onScrollStateChanged(PLA_AbsListView view, int scrollState) {
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			if (view.getLastVisiblePosition() == (view.getCount()-1)) {
				if(!isRequestPost ){
						getRecommendPostList();
				}
			}
		}
	}

	@Override
	public void onScroll(PLA_AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
	}

	@Override
	public void onRefresh() {
		getRecommendPostListRefresh();
	}
}
