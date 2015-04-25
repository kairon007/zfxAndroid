package com.zifei.corebeau.search.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.SearchView;

import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.bean.PageBean;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.ui.view.HorizontalListView;
import com.zifei.corebeau.common.ui.widget.progress.CircularProgressBar;
import com.zifei.corebeau.common.ui.widget.progress.CircularProgressDrawable;
import com.zifei.corebeau.common.ui.widget.staggered.StaggeredGridView;
import com.zifei.corebeau.search.bean.Response.RecommendPostResponse;
import com.zifei.corebeau.search.bean.Response.RecommendUserResponse;
import com.zifei.corebeau.search.task.SearchTask;
import com.zifei.corebeau.search.ui.adapter.RecommedUserAdapter;
import com.zifei.corebeau.search.ui.adapter.SampleAdapter;
import com.zifei.corebeau.utils.Utils;

public class SearchFragment extends Fragment implements
		AbsListView.OnScrollListener, AbsListView.OnItemClickListener,
		AdapterView.OnItemLongClickListener {

	private SearchView searchView;
	private HorizontalListView horizontalListView;
	private StaggeredGridView staggeredGridView;
	private RecommedUserAdapter recommedUserAdapter;
	private SearchTask searchTask;
	private CircularProgressBar progressBar;
	private SampleAdapter mAdapter;
	private boolean mHasRequestedMore;
	private static final String TAG = "StaggeredGridActivity";
	private ArrayList<ItemInfo> mData;
	public static final String SAVED_DATA_KEY = "SAVED_DATA";
	private static final int FETCH_DATA_TASK_DURATION = 2000;

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
		progressBar = (CircularProgressBar) view.findViewById(R.id.pb_search);
		staggeredGridView = (StaggeredGridView) view
				.findViewById(R.id.sgv_search);
		staggeredGridView.setOnItemClickListener(this);
		
		LayoutInflater layoutInflater = getActivity().getLayoutInflater();
		View header = layoutInflater.inflate(R.layout.layout_search_header,
				null);
		horizontalListView = (HorizontalListView) header
				.findViewById(R.id.hlv_search);
		staggeredGridView.addHeaderView(header);
		staggeredGridView.setEmptyView(view.findViewById(android.R.id.empty));
		staggeredGridView.setOnItemClickListener(this);
		staggeredGridView.setOnScrollListener(this);
		staggeredGridView.setOnItemLongClickListener(this);
		recommedUserAdapter = new RecommedUserAdapter(getActivity(),
				horizontalListView);
		horizontalListView.setAdapter(recommedUserAdapter);

		getRecommendPostList();
		return view;
	}

	private void fillAdapter() {
		for (ItemInfo data : mData) {
			mAdapter.add(data);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		((CircularProgressDrawable) progressBar
				.getIndeterminateDrawable()).progressiveStop();
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// RecommendPostList mData =
		// (RecommendPostList)parent.getAdapter().getItem(position);
		// if(mData != null){
		// Intent intent = new Intent(getActivity(), PostActivity.class);
		// intent.putExtra("postId", mData.getPostId());
		// getActivity().startActivity(intent);
		// }
	}

	@Override
	public void onScroll(final AbsListView view, final int firstVisibleItem,
			final int visibleItemCount, final int totalItemCount) {
		Log.d(TAG, "onScroll firstVisibleItem:" + firstVisibleItem
				+ " visibleItemCount:" + visibleItemCount + " totalItemCount:"
				+ totalItemCount);
		// our handling
		if (!mHasRequestedMore) {
			int lastInScreen = firstVisibleItem + visibleItemCount;
			if (lastInScreen >= totalItemCount) {
				Log.d(TAG, "onScroll lastInScreen - so load more");
				mHasRequestedMore = true;
				onLoadMoreItems();
			}
		}
	}

	private void onLoadMoreItems() {
		// final ArrayList<RecommendPostList> sampleData =
		// SampleData.generateSampleData();
		// for (RecommendPostList data : sampleData) {
		// mAdapter.add(data);
		// }
		// // stash all the data in our backing store
		// mData.addAll(sampleData);
		// // notify the adapter that we can update now
		// mAdapter.notifyDataSetChanged();
		// mHasRequestedMore = false;
	}

	private ArrayList<ItemInfo> generateData() {
		return mData;
	}

	private void getRecommendUserList() {
		// progressBar.setVisibility(View.VISIBLE);
		((CircularProgressDrawable) progressBar.getIndeterminateDrawable())
				.start();
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
		((CircularProgressDrawable) progressBar.getIndeterminateDrawable())
				.start();
		searchTask
				.getRecommendPostList(new AsyncCallBacks.OneOne<RecommendPostResponse, String>() {
					@Override
					public void onSuccess(RecommendPostResponse response) {
						((CircularProgressDrawable) progressBar
								.getIndeterminateDrawable()).progressiveStop();
						PageBean<ItemInfo> pageBean = (PageBean<ItemInfo>) response
								.getPageBean();
						List<ItemInfo> list = pageBean.getList();
						if (response.getPageBean().getList().size() <= 0) {

						} else {
							mAdapter = new SampleAdapter(getActivity(),
									android.R.layout.simple_list_item_1, list);
							mAdapter.setColumnWidth(staggeredGridView.getColumnWidth());
							staggeredGridView.setAdapter(mAdapter);

						}
					}

					@Override
					public void onError(String msg) {
						((CircularProgressDrawable) progressBar
								.getIndeterminateDrawable()).progressiveStop();

						Utils.showToast(getActivity(), msg);
					}
				});
	}

	// private void fetchData() {
	// new AsyncTask<Void, Void, Void>() {
	// @Override
	// protected Void doInBackground(Void... params) {
	// SystemClock.sleep(FETCH_DATA_TASK_DURATION);
	// return null;
	// }
	//
	// @Override
	// protected void onPostExecute(Void aVoid) {
	// fillAdapter();
	// }
	// }.execute();
	// }

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		return true;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

}
