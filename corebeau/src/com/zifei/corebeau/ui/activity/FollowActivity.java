package com.zifei.corebeau.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.response.FollowListResponse;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.task.FollowTask;
import com.zifei.corebeau.ui.adapter.FollowAdapter;
import com.zifei.corebeau.utils.Utils;

public class FollowActivity extends SherlockActivity{
	
	private ListView followList;
	private FollowTask followTask;
	private ProgressBar progressBar;
	private FollowAdapter followAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_follow);
		initActionBar();
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
	
	private void initActionBar() {
		getSupportActionBar().setTitle(" follow");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().equals(" follow")) {
			finish();
		}
		return true;
	}

	
	private void getFollowList(){
		progressBar.setVisibility(View.VISIBLE);
		followTask.getFollowList(new AsyncCallBacks.OneOne<FollowListResponse, String>() {

			@Override
			public void onSuccess(FollowListResponse response) {
				progressBar.setVisibility(View.INVISIBLE);
				followAdapter.addData(response.getPageBean().getList(), false);
			}

			@Override
			public void onError(String msg) {
				progressBar.setVisibility(View.INVISIBLE);
				Utils.showToast(FollowActivity.this, msg);
			}
		});
	}
	
}
