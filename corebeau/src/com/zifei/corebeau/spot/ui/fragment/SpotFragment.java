package com.zifei.corebeau.spot.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.zifei.corebeau.R;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.ui.OnFragmentInteractionListener;
import com.zifei.corebeau.spot.bean.SpotList;
import com.zifei.corebeau.spot.bean.response.SpotListResponse;
import com.zifei.corebeau.spot.task.SpotTask;
import com.zifei.corebeau.spot.ui.adapter.SpotAdapter;
import com.zifei.corebeau.test.TestData;

import java.util.List;

public class SpotFragment extends Fragment implements AbsListView.OnItemClickListener {

    private SpotAdapter spotAdapter;
    private ListView listview;
    private SpotTask spotTask;
    private ProgressBar progressBar;
    private OnFragmentInteractionListener mListener;
    public SpotFragment() {

    }

    public static SpotFragment newInstance(String param1, String param2) {
        SpotFragment fragment = new SpotFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spotTask = new SpotTask(getActivity());

        spotAdapter = new SpotAdapter(getActivity(), listview);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spot, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.pb_spot);
        listview = (ListView) view.findViewById(R.id.lv_spot);
        listview.setEmptyView(view.findViewById(android.R.id.empty));
        listview.setAdapter(spotAdapter);
        getSpotTask();
        return view;
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    private void getSpotTask() {
        progressBar.setVisibility(View.VISIBLE);
        spotTask.getSpotList(new AsyncCallBacks.OneOne<SpotListResponse, String>() {
            @Override
            public void onSuccess(SpotListResponse response) {
                progressBar.setVisibility(View.GONE);
                List<SpotList> spotList = response.getSpotPostList();
                if (spotList.size() <= 0) {
//                    showNotice(getString(R.string.common_notice_recommend));

                } else {
//                    spotAdapter.refresh(spotList);
                }
            }

            @Override
            public void onError(String msg) {
                progressBar.setVisibility(View.GONE);
                spotAdapter.addData(TestData.getSpotList(), false);
                spotAdapter.notifyDataSetChanged();
//                Utils.showToast(getActivity(), msg);
            }
        });
    }

}
