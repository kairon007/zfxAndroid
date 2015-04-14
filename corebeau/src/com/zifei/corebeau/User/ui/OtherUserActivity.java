package com.zifei.corebeau.User.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.meetme.android.horizontallistview.HorizontalListView;
import com.zifei.corebeau.R;
import com.zifei.corebeau.User.bean.response.PostResponse;
import com.zifei.corebeau.User.task.PostTask;
import com.zifei.corebeau.User.ui.adapter.OtherUserPostAdapter;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.utils.Utils;

/**
 * Created by im14s_000 on 2015/3/28.
 */
public class OtherUserActivity extends Activity {

    private ListView imageListView;
    //    private ListView commentListView;
    private HorizontalListView subListView;
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
        postTask.getPost(postId, new AsyncCallBacks.OneOne<PostResponse, String>() {
            @Override
            public void onSuccess(PostResponse result) {

//                imageAdapter = new ImageAdapter(OtherUserActivity.this,imageListView);

            }

            @Override
            public void onError(String result) {
            }
        });
    }


    // 해놓고 백그라운드에서 돌린다
    private void insertLikeTask() {
        postTask.insertLike(postId, new AsyncCallBacks.OneOne<Response, String>() {

            @Override
            public void onSuccess(Response response) {

//                Utils.showToast(PostActivity.this, msg);
            }

            @Override
            public void onError(String msg) {
                Utils.showToast(OtherUserActivity.this, msg);
            }
        });
    }

    private void deleteLikeTask() {
        postTask.deleteLike(postId, new AsyncCallBacks.OneOne<Response, String>() {

            @Override
            public void onSuccess(Response response) {

//                Utils.showToast(PostActivity.this, msg);
            }

            @Override
            public void onError(String msg) {
                Utils.showToast(OtherUserActivity.this, msg);
            }
        });
    }
}
