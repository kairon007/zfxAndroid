package com.zifei.corebeau.User.ui;

import android.app.Activity;
import android.os.Bundle;
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
public class OtherUserActivity extends Activity {

    private ListView imageListView;
    //    private ListView commentListView;
    private PostTask postTask;
    private OtherUserPostAdapter imageAdapter;
    private Integer postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        init();
    }

    private void init() {
//        imageListView = (ListView) findViewById(R.id.lv_post_image);
//        subListView = (HorizontalListView) findViewById(R.id.hlv_post_image_sub);
//        commentListView = (ListView)findViewById(R.id.lv_post_comment);
        postTask = new PostTask(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getPostTask() {
    }


    // 해놓고 백그라운드에서 돌린다
    private void insertLikeTask() {
    }

    private void deleteLikeTask() {
    }
}
