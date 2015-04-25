package com.zifei.corebeau.User.ui.fragment;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
public class CommentLayout extends LinearLayout implements View.OnClickListener{

    private Context context;
    private Button commentBtn;
    private EditText commentEditText;
    private ListView listView;
    private PostTask postTask;
    private CommentAdapter commentAdapter;
    private Integer postId;
    private String message;

    public CommentLayout(Context context) {
        super(context);
        init(context);
    }

    public CommentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setData(Integer postId){
        this.postId = postId;
    }

    private void init(Context context){
        LayoutInflater.from(context)
                .inflate(R.layout.layout_post_comment, this);
        this.context = context;
        postTask = new PostTask(context);
        listView = (ListView)findViewById(R.id.lv_post_comment);
        commentEditText = (EditText)findViewById(R.id.et_comment_post);
        commentBtn = (Button) findViewById(R.id.btn_comment_post);

        postTask = new PostTask(context);
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
            Utils.showToast(context, "comment empty");
        } else if (message.length() > 100) {
            Utils.showToast(context, "comment too long");
        }else{
            insertComment();
        }
    }

    private void getPostCommentTask(){
        postTask.getPostComment(postId, new AsyncCallBacks.OneOne<CommentListResponse, String>() {

            @Override
            public void onSuccess(CommentListResponse result) {
                commentAdapter.addData(result.getCommentList(), false);
            }

            @Override
            public void onError(String msg) {
                Utils.showToast(context, msg);
            }
        });
    }

    private void insertComment() {
        postTask.insertComment(postId, message, new AsyncCallBacks.OneOne<String, String>() {

            @Override
            public void onSuccess(String msg) {
                commentAdapter.notifyDataSetChanged();
                // 재요청하기
                Utils.showToast(context, msg);
            }

            @Override
            public void onError(String msg) {
                Utils.showToast(context, msg);
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
