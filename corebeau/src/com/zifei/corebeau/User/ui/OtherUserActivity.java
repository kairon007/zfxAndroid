package com.zifei.corebeau.User.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.zifei.corebeau.R;
import com.zifei.corebeau.User.bean.response.PostResponse;
import com.zifei.corebeau.User.ui.adapter.OtherUserPostAdapter;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.post.task.PostTask;
import com.zifei.corebeau.utils.Utils;

/**
 * Created by im14s_000 on 2015/3/28.
 */
public class OtherUserActivity extends ActionBarActivity {

    private ListView imageListView;
    private PostTask postTask;
    private OtherUserPostAdapter imageAdapter;
    private Integer postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_user);
//        init();
    }

}
