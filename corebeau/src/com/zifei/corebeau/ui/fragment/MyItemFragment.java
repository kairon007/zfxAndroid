package com.zifei.corebeau.ui.fragment;

import java.util.List;

import android.R.color;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.bean.PageBean;
import com.zifei.corebeau.bean.response.MyPostListResponse;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.extra.pla.PullSingleListView;
import com.zifei.corebeau.extra.pla.PullSingleListView.SingleRefreshListener;
import com.zifei.corebeau.extra.pla.internal.PLA_AbsListView;
import com.zifei.corebeau.extra.pla.internal.PLA_AbsListView.OnScrollListener;
import com.zifei.corebeau.task.MyTask;
import com.zifei.corebeau.ui.activity.MyItemDetailActivity;
import com.zifei.corebeau.ui.adapter.MyItemListAdapter;
import com.zifei.corebeau.ui.adapter.MyItemListAdapter.OnMyDetailStartClickListener;
import com.zifei.corebeau.utils.Utils;

public class MyItemFragment extends PLAScrollTabHolderFragment implements OnMyDetailStartClickListener,OnScrollListener,SingleRefreshListener  {
	
	private static final String ARG_POSITION = "position";
	private PullSingleListView postList;
	private int mPosition;
	private MyTask myTask;
	private MyItemListAdapter myPostAdapter;
	private final int REQ_CODE_MY_DETAIL = 111;
	
	private boolean isLast = false;
	private int currentPage;
	private boolean isRequestPost = false;
	
	public static Fragment newInstance(int position) {
		MyItemFragment fragment = new MyItemFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_POSITION, position);
		fragment.setArguments(args);
		return fragment;
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myTask = new MyTask(getActivity());
		mPosition = 0;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my_post, container, false);
		
		
		postList = (PullSingleListView) view.findViewById(R.id.lv_my_post);
		postList.setOverScrollMode(View.OVER_SCROLL_NEVER);

		View placeHolderView = inflater.inflate(R.layout.view_header_placeholder, postList, false);
		postList.addHeaderView(placeHolderView);
		
		myPostAdapter = new MyItemListAdapter(getActivity(), postList);
		postList.setAdapter(myPostAdapter);
		
		myPostAdapter.setOnMyDetailStartClickListener(this);
		postList.setOnScrollListener(this);
		postList.setSingleRefreshListener(this);
		postList.setSelector(color.transparent);
		
		postListTask();
		return view;
	}
	
	private void postListTask() {
		if(isLast){
			return;
		}
		isRequestPost = true;
		
		myTask.getMyItemList(currentPage, new AsyncCallBacks.OneOne<MyPostListResponse, String>() {

			@Override
			public void onSuccess(MyPostListResponse response) {

				PageBean<ItemInfo> pageBean = (PageBean<ItemInfo>) response
						.getPageBean();
				List<ItemInfo> list = pageBean.getList();
				currentPage = pageBean.getCurrentPage();
				
				if(list.size() >= 30){
					isLast = false;
					if(currentPage==1){
						myPostAdapter.addItemTop(list);
						myPostAdapter.notifyDataSetChanged();
					}else{
						myPostAdapter.addItemLast(list);
						myPostAdapter.notifyDataSetChanged();
					}
					currentPage = currentPage+1;
				}else if(list.size() < 30){
					isLast = true;
					if(currentPage==1){
						myPostAdapter.addItemTop(list);
						myPostAdapter.notifyDataSetChanged();
					}else{
						myPostAdapter.addItemLast(list);
						myPostAdapter.notifyDataSetChanged();
					}
				}else{
					isLast = true;
				}
				isRequestPost = false;
			}

			@Override
			public void onError(String msg) {
				Utils.showToast(getActivity(), msg);
			}
		});
	}
	
	private void postListTaskRefresh() {
		currentPage = 0;
		myTask.getMyItemList(currentPage, new AsyncCallBacks.OneOne<MyPostListResponse, String>() {

			@Override
			public void onSuccess(MyPostListResponse response) {

				PageBean<ItemInfo> pageBean = (PageBean<ItemInfo>) response
						.getPageBean();
				List<ItemInfo> list = pageBean.getList();
				currentPage = pageBean.getCurrentPage();
				myPostAdapter.clearAdapter();
				
				if(list.size() >= 30){
					isLast = false;
					if(currentPage==1){
						myPostAdapter.addItemTop(list);
						myPostAdapter.notifyDataSetChanged();
						postList.stopRefresh();
					}else{
						myPostAdapter.addItemLast(list);
						myPostAdapter.notifyDataSetChanged();
					}
					currentPage = currentPage+1;
				}else if(list.size() < 30){
					isLast = true;
					if(currentPage==1){
						myPostAdapter.addItemTop(list);
						myPostAdapter.notifyDataSetChanged();
						postList.stopRefresh();
					}else{
						myPostAdapter.addItemLast(list);
						myPostAdapter.notifyDataSetChanged();
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
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQ_CODE_MY_DETAIL) {
			List<ItemInfo> list = (List<ItemInfo>) data
					.getSerializableExtra("itemList");
			myPostAdapter.clearAdapter();
			myPostAdapter.addItemLast(list);
		}
	}

	@Override
	public void onMyDetailStartClicked(View view, int position) {
		ItemInfo itemInfo = myPostAdapter.getData().get(position);
		Intent intent = new Intent(getActivity(), MyItemDetailActivity.class);
		intent.putExtra("itemInfo", itemInfo);
		startActivityForResult(intent, REQ_CODE_MY_DETAIL);
	}

	@Override
	public void adjustScroll(int scrollHeight) {
		if (scrollHeight == 0 && postList.getFirstVisiblePosition() >= 1) {
			return;
		}

		postList.setSelectionFromTop(1, scrollHeight);

	}

	@Override
	public void onScrollStateChanged(PLA_AbsListView view, int scrollState) {
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			if (view.getLastVisiblePosition() == (view.getCount()-1)) {
				if(!isRequestPost){
					postListTask();
				}
			}
		}
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		postListTaskRefresh();
	}


	@Override
	public void onScroll(PLA_AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount, int pagePosition) {
		
	}

	@Override
	public void onScroll(PLA_AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (mScrollTabHolder != null)
			mScrollTabHolder.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, mPosition);
	}

}
