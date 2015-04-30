package com.zifei.corebeau.my.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zifei.corebeau.R;

public class MyItemFragment extends Fragment{
	
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
		View view = inflater.inflate(R.layout.fragment_my, container, false);
		
		return view;
	}

}
