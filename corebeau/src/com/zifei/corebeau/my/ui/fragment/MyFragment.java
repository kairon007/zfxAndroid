package com.zifei.corebeau.my.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zifei.corebeau.R;
import com.zifei.corebeau.common.ui.OnFragmentInteractionListener;
import com.zifei.corebeau.my.ui.MyPostActivity;
import com.zifei.corebeau.post.ui.CommentActivity;
import com.zifei.corebeau.post.ui.PostActivity;

public class MyFragment extends Fragment implements View.OnClickListener {

	private OnFragmentInteractionListener mListener;
	private TextView ibMyPost, ibMyLike, ibMyMail, ibMyLogout, ibMyFollow;

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
		ibMyLike = (TextView) view.findViewById(R.id.ib_my_like);
		ibMyMail = (TextView) view.findViewById(R.id.ib_my_mail);
		ibMyFollow = (TextView) view.findViewById(R.id.ib_my_follow);
		ibMyLogout = (TextView) view.findViewById(R.id.ib_my_logout);

		ibMyPost.setOnClickListener(this);
		ibMyLike.setOnClickListener(this);
		ibMyMail.setOnClickListener(this);
		ibMyFollow.setOnClickListener(this);
		ibMyLogout.setOnClickListener(this);
		return view;
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_my_post:
			Intent intent1 = new Intent(getActivity(), MyPostActivity.class);
			startActivity(intent1);
			break;
		case R.id.ib_my_like:
			Intent intent2 = new Intent(getActivity(), CommentActivity.class);
			startActivity(intent2);
			break;
		case R.id.ib_my_mail:
			Intent intent3 = new Intent(getActivity(), CommentActivity.class);
			startActivity(intent3);
			break;
		case R.id.ib_my_follow:
			Intent intent4 = new Intent(getActivity(), CommentActivity.class);
			startActivity(intent4);
			break;
			
		default:
			break;
		}
	}

}
