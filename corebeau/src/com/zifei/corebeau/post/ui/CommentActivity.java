package com.zifei.corebeau.post.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.zifei.corebeau.R;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.post.bean.response.CommentListResponse;
import com.zifei.corebeau.post.task.PostTask;
import com.zifei.corebeau.post.ui.adapter.CommentAdapter;
import com.zifei.corebeau.utils.Utils;

/**
 * Created by im14s_000 on 2015/3/28.
 */
public class CommentActivity extends Activity implements View.OnClickListener{

    private PostTask postTask;
    private Integer postId;
    private CommentAdapter commentAdapter;
    private ListView listView;
    private EditText commentEditText;
    private Button commentInsertBtn;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_comment);
        
        
//        Intent intent = getIntent();
//        String stringPostId = intent.getStringExtra("postId");
//        postId = Integer.parseInt(stringPostId);

        init();

    }

    private void init(){
        postTask = new PostTask(this);
        commentAdapter = new CommentAdapter(this, listView);
        listView = (ListView)findViewById(R.id.lv_comment);
        listView.setAdapter(commentAdapter);
        commentEditText = (EditText)findViewById(R.id.et_comment);
        commentInsertBtn = (Button)findViewById(R.id.btn_comment);
        getCommentTask();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_comment:
                checkCommentMsg();
                break;
            default:
                break;
        }
    }

    private void checkCommentMsg() {
        message = commentEditText.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            Utils.showToast(CommentActivity.this, "comment empty");
        } else if (message.length() > 100) {
            Utils.showToast(CommentActivity.this, "comment too long");
        }else{
            insertComment();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getCommentTask();
    }

    // endless listview  20개 부르고 10개 넘어가면 10개 더부르고 이런식이 되어야함
    private void getCommentTask(){
        postTask.getComment(postId, new AsyncCallBacks.OneOne<CommentListResponse, String>() {

            @Override
            public void onSuccess(CommentListResponse result) {
                commentAdapter.addData(result.getCommentList(),false);
            }

            @Override
            public void onError(String msg) {
                Utils.showToast(CommentActivity.this, msg);
//                commentAdapter.addData(TestData.getCommentList(), false);
            }
        });
    }

    private void insertComment() {
        postTask.insertComment(postId, message, new AsyncCallBacks.OneOne<String, String>() {

            @Override
            public void onSuccess(String msg) {
                commentAdapter.notifyDataSetChanged();
                // 재요청하기
                Utils.showToast(CommentActivity.this, msg);
            }

            @Override
            public void onError(String msg) {
                Utils.showToast(CommentActivity.this, msg);
            }
        });
    }


//    private void deletePost(){
//        postTask.insertComment(commentId, new AsyncCallBacks.OneOne<String, String>() {
//
//            @Override
//            public void onSuccess(String msg) {
//                commentAdapter.notifyDataSetChanged();
//                // 리스트뷰 해당 리스트 만 삭제하기...재요청 하지말고
//                Utils.showToast(CommentActivity.this, msg);
//            }
//
//            @Override
//            public void onError(String msg) {
//                Utils.showToast(CommentActivity.this, msg);
//            }
//        });
//    }

}
