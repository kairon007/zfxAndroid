package com.zifei.corebeau.my.ui;

import android.os.Bundle;
import android.widget.ListView;

import com.zifei.corebeau.R;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.ui.BarActivity;
import com.zifei.corebeau.my.bean.response.ScrapItemListResponse;
import com.zifei.corebeau.my.task.ScrapTask;
import com.zifei.corebeau.my.ui.adapter.ScrapPostAdapter;
import com.zifei.corebeau.utils.Utils;

public class ScrapPostActivity extends BarActivity {

	private ListView listView;
	private ScrapPostAdapter scrapPostAdapter;
	private ScrapTask scrapTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_like_post);
		init();
		getScrapList();
	}

	private void init() {
		setTitle("scrap");
		scrapTask = new ScrapTask(this);
		listView = (ListView) findViewById(R.id.lv_like_post);
		scrapPostAdapter = new ScrapPostAdapter(this, listView);
		listView.setAdapter(scrapPostAdapter);
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
						Utils.showToast(ScrapPostActivity.this, msg);
					}
				});
	}

}
