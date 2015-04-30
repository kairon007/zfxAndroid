package com.zifei.corebeau.my.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zifei.corebeau.R;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.my.bean.response.ScrapItemListResponse;
import com.zifei.corebeau.my.task.ScrapTask;
import com.zifei.corebeau.my.ui.adapter.ScrapPostAdapter;
import com.zifei.corebeau.utils.Utils;

public class ScrapPostFragment extends Fragment{
	
	private ScrapPostAdapter scrapPostAdapter;
	private ScrapTask scrapTask;
	private ListView listView;
	
	public static ScrapPostFragment newInstance(String param1, String param2) {
		ScrapPostFragment fragment = new ScrapPostFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public ScrapPostFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_scrap, container, false);
		
		scrapTask = new ScrapTask(getActivity());
		listView = (ListView) view.findViewById(R.id.lv_scrap);
		scrapPostAdapter = new ScrapPostAdapter(getActivity(), listView);
		listView.setAdapter(scrapPostAdapter);
		
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
}
