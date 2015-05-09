package com.zifei.corebeau.ui.fragment;

import java.util.List;

import android.R.color;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.bean.PageBean;
import com.zifei.corebeau.bean.response.SpotListResponse;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.extra.pla.PullSingleListView;
import com.zifei.corebeau.extra.pla.PullSingleListView.SingleRefreshListener;
import com.zifei.corebeau.extra.pla.internal.PLA_AbsListView;
import com.zifei.corebeau.extra.pla.internal.PLA_AdapterView;
import com.zifei.corebeau.extra.pla.internal.PLA_AbsListView.OnScrollListener;
import com.zifei.corebeau.extra.pla.internal.PLA_AdapterView.OnItemClickListener;
import com.zifei.corebeau.task.SpotTask;
import com.zifei.corebeau.ui.adapter.SpotAdapter;
import com.zifei.corebeau.utils.Utils;

public class SpotFragment extends Fragment implements
		OnItemClickListener, View.OnClickListener, OnScrollListener, SingleRefreshListener {

	private SpotAdapter spotAdapter;
	private PullSingleListView listview;
	private SpotTask spotTask;
	private ProgressBar progressBar;
	private boolean isLast = false;
	private int currentPage;
	private boolean isRequestPost = false;

	public SpotFragment() {

	}

	public static SpotFragment newInstance(String param1, String param2) {
		SpotFragment fragment = new SpotFragment();
		Bundle args = new Bundle();

		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		spotTask = new SpotTask(getActivity());
		spotAdapter = new SpotAdapter(getActivity(), listview);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_spot, container, false);
		progressBar = (ProgressBar) view.findViewById(R.id.pb_spot);
		listview = (PullSingleListView) view.findViewById(R.id.lv_spot);
		listview.setEmptyView(view.findViewById(android.R.id.empty));
		listview.setAdapter(spotAdapter);
		listview.setOnScrollListener(this);
		listview.setSingleRefreshListener(this);
		listview.setSelector(color.transparent);
		
		getSpotTask();
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	private void getSpotTask() {
		if(isLast){
			return;
		}
		isRequestPost = true;
		progressBar.setVisibility(View.VISIBLE);
		spotTask.getSpotList(currentPage,new AsyncCallBacks.OneOne<SpotListResponse, String>() {
			@Override
			public void onSuccess(SpotListResponse response) {
				progressBar.setVisibility(View.GONE);
				PageBean<ItemInfo> pageBean = (PageBean<ItemInfo>) response
						.getPageBean();
				List<ItemInfo> list = pageBean.getList();
				currentPage = pageBean.getCurrentPage();
				
					if(list.size() >= 30){
						isLast = false;
						if(currentPage==1){
							spotAdapter.addItemTop(list);
							spotAdapter.notifyDataSetChanged();
						}else{
							spotAdapter.addItemLast(list);
							spotAdapter.notifyDataSetChanged();
						}
						currentPage = currentPage+1;
					}else if(list.size() < 30){
						isLast = true;
						if(currentPage==1){
							spotAdapter.addItemTop(list);
							spotAdapter.notifyDataSetChanged();
						}else{
							spotAdapter.addItemLast(list);
							spotAdapter.notifyDataSetChanged();
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
			}
		});
	}
	
	
	private void getSpotTaskRefresh() {
		currentPage = 0;
		spotTask.getSpotList(currentPage,new AsyncCallBacks.OneOne<SpotListResponse, String>() {
			@Override
			public void onSuccess(SpotListResponse response) {
				PageBean<ItemInfo> pageBean = (PageBean<ItemInfo>) response
						.getPageBean();
				List<ItemInfo> list = pageBean.getList();
				currentPage = pageBean.getCurrentPage();
				spotAdapter.clearAdapter();
					if(list.size() >= 30){
						isLast = false;
						if(currentPage==1){
							spotAdapter.addItemTop(list);
							spotAdapter.notifyDataSetChanged();
							listview.stopRefresh();
						}else{
							spotAdapter.addItemLast(list);
							spotAdapter.notifyDataSetChanged();
						}
						currentPage = currentPage+1;
					}else if(list.size() < 30){
						isLast = true;
						if(currentPage==1){
							spotAdapter.addItemTop(list);
							spotAdapter.notifyDataSetChanged();
							listview.stopRefresh();
						}else{
							spotAdapter.addItemLast(list);
							spotAdapter.notifyDataSetChanged();
						}
					}else{
						isLast = true;
					}
				
			}

			@Override
			public void onError(String msg) {
				Utils.showToast(getActivity(), msg);
				listview.stopRefresh();
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
	public void onScrollStateChanged(PLA_AbsListView view, int scrollState) {
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			if (view.getLastVisiblePosition() == (view.getCount()-1)) {
				if(!isRequestPost){
					getSpotTask();
				}
			}
		}
	}

	@Override
	public void onScroll(PLA_AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
	}

	@Override
	public void onItemClick(PLA_AdapterView<?> parent, View view, int position,
			long id) {
	}

	@Override
	public void onRefresh() {
		getSpotTaskRefresh();
	}

}
