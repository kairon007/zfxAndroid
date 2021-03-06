package com.zifei.corebeau.ui.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.bean.PageBean;
import com.zifei.corebeau.bean.response.MyPostListResponse;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.extra.parallaxheader.ScrollTabHolder;
import com.zifei.corebeau.task.MyTask;
import com.zifei.corebeau.task.UserInfoService;
import com.zifei.corebeau.ui.activity.MyItemDetailActivity;
import com.zifei.corebeau.ui.adapter.MyItemListAdapter;
import com.zifei.corebeau.ui.adapter.MyItemListAdapter.OnMyDetailStartClickListener;
import com.zifei.corebeau.utils.Utils;

public class MyItemFragment extends ScrollTabHolderFragment implements OnMyDetailStartClickListener,OnScrollListener {
	
	private static final String ARG_POSITION = "position";
	private ListView postList;
	private int mPosition;
	private MyTask myTask;
	private MyItemListAdapter myPostAdapter;
	private final int REQ_CODE_MY_DETAIL = 111;
	private int currentPage;
	private ImageView errorImg;
	
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
		mPosition = 0;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my_post, container, false);
		
		myTask = new MyTask(getActivity());
		postList = (ListView) view.findViewById(R.id.lv_my_post);
		postList.setOverScrollMode(View.OVER_SCROLL_NEVER);
		errorImg = (ImageView)view.findViewById(R.id.network_error_my_page);

		View placeHolderView = inflater.inflate(R.layout.view_header_placeholder, postList, false);
		postList.addHeaderView(placeHolderView);
		
		myPostAdapter = new MyItemListAdapter(getActivity(), postList);
		postList.setAdapter(myPostAdapter);
		postListTask();

		myPostAdapter.setOnMyDetailStartClickListener(this);
		postList.setOnScrollListener(this);
		return view;
	}
	
	private void postListTask() {
		myTask.getMyItemList(currentPage, new AsyncCallBacks.OneOne<MyPostListResponse, String>() {

			@Override
			public void onSuccess(MyPostListResponse response) {
				
				PageBean<ItemInfo> pageBean = (PageBean<ItemInfo>) response
						.getPageBean();
				List<ItemInfo> list = pageBean.getList();
				errorImg.setVisibility(View.GONE);
				if (response.getPageBean().getList().size() <= 0) {

				} else {
					myPostAdapter.addData(list, false);
				}
			}

			@Override
			public void onError(String msg) {
				errorImg.setVisibility(View.VISIBLE);
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
			myPostAdapter.addData(list, false);
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
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (mScrollTabHolder != null)
			mScrollTabHolder.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, mPosition);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// nothing
	}

}
