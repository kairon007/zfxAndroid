package com.zifei.corebeau.my.ui.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.ItemInfo;
import com.zifei.corebeau.bean.PageBean;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.ui.view.CircularImageView;
import com.zifei.corebeau.my.bean.response.MyPostListResponse;
import com.zifei.corebeau.my.task.MyTask;
import com.zifei.corebeau.my.ui.MyItemDetailActivity;
import com.zifei.corebeau.my.ui.MyItemListActivity;
import com.zifei.corebeau.my.ui.adapter.MyItemListAdapter;
import com.zifei.corebeau.my.ui.adapter.MyItemListAdapter.OnMyDetailStartClickListener;
import com.zifei.corebeau.utils.Utils;

public class MyItemFragment extends Fragment implements OnMyDetailStartClickListener{
	
	private ListView postList;
	private MyTask myTask;
	private ProgressBar progressBar;
	private MyItemListAdapter myPostAdapter;
	private final int REQ_CODE_MY_DETAIL = 900002;
	
	public static MyItemFragment newInstance(String param1, String param2) {
		MyItemFragment fragment = new MyItemFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public MyItemFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my_post, container, false);
		
		myTask = new MyTask(getActivity());
		postList = (ListView) view.findViewById(R.id.lv_my_post);
		progressBar = (ProgressBar) view.findViewById(R.id.pb_my_post);
		myPostAdapter = new MyItemListAdapter(getActivity(), postList);
		postList.setAdapter(myPostAdapter);
		postListTask();

		myPostAdapter.setOnMyDetailStartClickListener(this);
		
		return view;
	}
	
	private void postListTask() {
		progressBar.setVisibility(View.VISIBLE);
		myTask.getMyItemList(new AsyncCallBacks.OneOne<MyPostListResponse, String>() {

			@Override
			public void onSuccess(MyPostListResponse response) {
				progressBar.setVisibility(View.GONE);

				PageBean<ItemInfo> pageBean = (PageBean<ItemInfo>) response
						.getPageBean();
				List<ItemInfo> list = pageBean.getList();
				if (response.getPageBean().getList().size() <= 0) {

				} else {
					myPostAdapter.addData(list, false);
				}
			}

			@Override
			public void onError(String msg) {
				progressBar.setVisibility(View.GONE);
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

}
