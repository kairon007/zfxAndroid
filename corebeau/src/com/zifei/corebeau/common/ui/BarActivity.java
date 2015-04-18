package com.zifei.corebeau.common.ui;

import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;

import com.zifei.corebeau.R;
import com.zifei.corebeau.common.CorebeauApp;
import com.zifei.corebeau.common.ui.widget.NavigationBar;

public abstract class BarActivity extends Activity {
	
	public NavigationBar navi;

	private OnBaseActivityCallbackListener callback;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((CorebeauApp) getApplication()).add(this);
		if (hasNavigationBar()) {
			navi = new NavigationBar(this);
			String title;
			try {
				title = getPackageManager().getActivityInfo(this.getComponentName(), 0).loadLabel(getPackageManager())
						.toString();
				navi.setTitle(title);
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
		}
		

		if (callback != null) {
			callback.onCreate(savedInstanceState);
		}
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		if (hasNavigationBar()) {
			((FrameLayout) findViewById(android.R.id.content)).addView(navi, new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
			navi.setLeftButton(R.drawable.btn_back, new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
	}

	public void setNavTitle(String title) {
		if (hasNavigationBar()) {
			navi.setTitle(title);
		}
	}
	
	public void setNavRightText(String txt) {
		if (hasNavigationBar()) {
			navi.setRightText(txt);
		}
	}
	
	public static interface OnBaseActivityCallbackListener {
		public void onCreate(Bundle savedInstanceState);

		public void onDestroy();
	}
	
	public boolean hasToolBar() {
		return false;
	}

	public boolean hasNavigationBar() {
		return true;
	}
	
}
