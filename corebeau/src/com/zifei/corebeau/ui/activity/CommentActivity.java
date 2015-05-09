package com.zifei.corebeau.ui.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.zifei.corebeau.R;
import com.zifei.corebeau.bean.ItemComment;
import com.zifei.corebeau.bean.response.CommentListResponse;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.task.PostTask;
import com.zifei.corebeau.ui.adapter.CommentAdapter;
import com.zifei.corebeau.utils.StringUtil;
import com.zifei.corebeau.utils.Utils;

/**
 * Created by im14s_000 on 2015/3/28.
 */
public class CommentActivity extends Activity implements View.OnClickListener, OnItemClickListener{

    private PostTask postTask;
    private String itemId;
    private CommentAdapter commentAdapter;
    private ListView listView;
    private EditText commentEditText;
    private Button commentInsertBtn;
    private String replyUserId;
    private String replyUserNickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_comment);
        
        Intent intent = getIntent();
        itemId = intent.getStringExtra("itemId");
        init();

    }

    private void init(){
        postTask = new PostTask(this);
        commentAdapter = new CommentAdapter(this, listView);
        listView = (ListView)findViewById(R.id.lv_comment);
        listView.setAdapter(commentAdapter);
        commentEditText = (EditText)findViewById(R.id.et_comment);
        commentInsertBtn = (Button)findViewById(R.id.btn_comment);
        getComment();
        
        commentInsertBtn.setOnClickListener(this);
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
        String message = commentEditText.getText().toString();
        if (TextUtils.isEmpty(message)) {
            Utils.showToast(CommentActivity.this, "comment empty");
        } else if (message.length() > 100) {
            Utils.showToast(CommentActivity.this, "comment too long");
        }else{
        	
//        	if(!StringUtil.isEmail(replyUserId)){
        		insertComment(message,replyUserId,replyUserNickName);
//        	}
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // endless listview  20개 부르고 10개 넘어가면 10개 더부르고 이런식이 되어야함
    private void getComment(){
        postTask.getComment(itemId, new AsyncCallBacks.OneOne<CommentListResponse, String>() {

            @Override
            public void onSuccess(CommentListResponse result) {
                commentAdapter.addData(result.getPageBean().getList(),false);
            }

            @Override
            public void onError(String msg) {
                Utils.showToast(CommentActivity.this, msg);
            }
        });
    }

    private void insertComment(String message, String replyUserId, String replyUserNickName) {
        postTask.insertComment(itemId, message, replyUserId, replyUserNickName , new AsyncCallBacks.OneOne<CommentListResponse, String>() {

            @Override
            public void onSuccess(CommentListResponse response) {
            	List<ItemComment> list = response.getPageBean().getList();
            	commentAdapter.clearAdapter();
            	commentAdapter.addData(list, false);
            	commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String msg) {
                Utils.showToast(CommentActivity.this, msg);
            }
        });
    }


    private void deleteComment(String commentId){
        postTask.deleteComment(commentId, new AsyncCallBacks.OneOne<String, String>() {

            @Override
            public void onSuccess(String msg) {
                commentAdapter.notifyDataSetChanged();
                // 리스트뷰 해당 리스트 만 삭제하기...재요청 하지말고
                Utils.showToast(CommentActivity.this, msg);
            }

            @Override
            public void onError(String msg) {
                Utils.showToast(CommentActivity.this, msg);
            }
        });
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ItemComment itemComment = commentAdapter.getData().get(position);
		replyUserId = itemComment.getUserId();
		replyUserNickName = itemComment.getUserNickName();
		commentEditText.requestFocus();
		commentEditText.setHint(replyUserNickName +" : ");
	}

}
