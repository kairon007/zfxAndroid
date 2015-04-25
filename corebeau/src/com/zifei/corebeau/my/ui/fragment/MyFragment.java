package com.zifei.corebeau.my.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zifei.corebeau.R;
import com.zifei.corebeau.my.ui.FollowActivity;
import com.zifei.corebeau.my.ui.MyInfoActivity;
import com.zifei.corebeau.my.ui.MyPostActivity;
import com.zifei.corebeau.my.ui.ScrapPostActivity;
import com.zifei.corebeau.post.ui.CommentActivity;

public class MyFragment extends Fragment implements View.OnClickListener {

	private TextView ibMyPost, ibMyScrap, ibMyInfo, ibMyFollow;

	public static MyFragment newInstance(String param1, String param2) {
		MyFragment fragment = new MyFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public MyFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my, container, false);
		ibMyPost = (TextView) view.findViewById(R.id.ib_my_post);
		ibMyScrap = (TextView) view.findViewById(R.id.ib_my_scrap);
		ibMyFollow = (TextView) view.findViewById(R.id.ib_my_follow);
		ibMyInfo = (TextView) view.findViewById(R.id.ib_my_info);

		ibMyPost.setOnClickListener(this);
		ibMyScrap.setOnClickListener(this);
		ibMyFollow.setOnClickListener(this);
		ibMyInfo.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_my_post:
			Intent intent1 = new Intent(getActivity(), MyPostActivity.class);
			startActivity(intent1);
			break;
		case R.id.ib_my_scrap:
			Intent intent2 = new Intent(getActivity(), ScrapPostActivity.class);
			startActivity(intent2);
			break;
		case R.id.ib_my_follow:
			Intent intent4 = new Intent(getActivity(), FollowActivity.class);
			startActivity(intent4);
			break;
		case R.id.ib_my_info:
			Intent intent5 = new Intent(getActivity(), MyInfoActivity.class);
			startActivity(intent5);
			break;
		default:
			break;
		}
	}

}
