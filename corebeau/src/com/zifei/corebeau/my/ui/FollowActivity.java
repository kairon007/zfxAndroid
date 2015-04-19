package com.zifei.corebeau.my.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.zifei.corebeau.R;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.ui.BarActivity;
import com.zifei.corebeau.my.bean.FollowListResponse;
import com.zifei.corebeau.my.task.FollowTask;
import com.zifei.corebeau.my.ui.adapter.FollowAdapter;
import com.zifei.corebeau.utils.Utils;

public class FollowActivity extends BarActivity{
	
	private ListView followList;
	private FollowTask followTask;
	private ProgressBar progressBar;
	private FollowAdapter followAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_follow);
		init();
		getFollowList();
	}
	
	private void init(){
		progressBar = (ProgressBar)findViewById(R.id.pb_follow);
		followList = (ListView) findViewById(R.id.lv_follow);
		followTask = new FollowTask(this);
		followAdapter = new FollowAdapter(this, followList);
		followList.setAdapter(followAdapter);
	}
	
	private void getFollowList(){
		progressBar.setVisibility(View.VISIBLE);
		followTask.getFollowList(new AsyncCallBacks.OneOne<FollowListResponse, String>() {

			@Override
			public void onSuccess(FollowListResponse response) {
				progressBar.setVisibility(View.INVISIBLE);
				followAdapter.addData(response.getFollowUserList(), false);
			}

			@Override
			public void onError(String msg) {
				progressBar.setVisibility(View.INVISIBLE);
				Utils.showToast(FollowActivity.this, msg);
			}
		});
	}
	
}
