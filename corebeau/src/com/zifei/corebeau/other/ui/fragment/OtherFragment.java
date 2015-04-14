package com.zifei.corebeau.other.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.zifei.corebeau.R;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.ui.MainActivity;
import com.zifei.corebeau.common.ui.OnFragmentInteractionListener;
import com.zifei.corebeau.common.ui.SplashActivity;
import com.zifei.corebeau.other.task.OtherTask;
import com.zifei.corebeau.utils.Utils;

public class OtherFragment extends Fragment implements View.OnClickListener {

    private OtherTask otherTask;
    private RelativeLayout rlSystemSetting, rlAnnounce, rlQna, rlLogout;

    public static OtherFragment newInstance(String param1, String param2) {
        OtherFragment fragment = new OtherFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public OtherFragment() {
        // Required empty public constructor
    }

    private void init(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        otherTask = new OtherTask(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other, container, false);
        
        rlSystemSetting = (RelativeLayout)view.findViewById(R.id.rl_other_system_setting);
        rlAnnounce = (RelativeLayout)view.findViewById(R.id.rl_other_announcement);
        rlQna = (RelativeLayout)view.findViewById(R.id.rl_other_qna);
        rlLogout = (RelativeLayout)view.findViewById(R.id.rl_other_logout);
        
        rlSystemSetting.setOnClickListener(this);
        rlAnnounce.setOnClickListener(this);
        rlQna.setOnClickListener(this);
        rlLogout.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_logout:
//                logoutTask();
//                break;
//            case R.id.btn_qna:
//                break;
//            case R.id.btn_announcement:
//                break;
//            case R.id.btn_system_setting:
//                break;
            default:
                break;
        }
    }

    private void logoutTask(){
        otherTask.logout(new AsyncCallBacks.ZeroOne<String>() {

            @Override
            public void onSuccess() {
                Intent intent = new Intent(getActivity(), SplashActivity.class);

                startActivity(intent);
                getActivity().finish();
            }

            @Override
            public void onError(String errorMsg) {
                Utils.showToast(getActivity(), errorMsg);
            }
        });
    }

}
