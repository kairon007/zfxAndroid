package com.zifei.corebeau.my.task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.net.UrlConstants;
import com.zifei.corebeau.common.task.NetworkExecutor;
import com.zifei.corebeau.my.bean.TokenResponse;
import com.zifei.corebeau.my.qiniu.QiniuTask;
import com.zifei.corebeau.my.qiniu.up.UpParam;
import com.zifei.corebeau.my.qiniu.up.UploadHandler;
import com.zifei.corebeau.my.qiniu.up.rs.UploadResultCallRet;
import com.zifei.corebeau.my.qiniu.up.slice.Block;
import com.zifei.corebeau.utils.Utils;

public class UploadTask {
	
	private OnUploadStatusListener uploadStatusListener;
	private Context context;
	private QiniuTask qiniuTask;
	private List<String>  names = new ArrayList<String>();
	
	public UploadTask(Context context){
		this.context = context;
		qiniuTask = new QiniuTask(context);
	}
	
	public void uploadImageQiniu(){
	}
	
	public void getToken(final ArrayList<String> stringPathList, final AsyncCallBacks.TwoTwo<Integer, String, Integer, String> callback) {
		Map<String, Object> params = new HashMap<String, Object>();

		NetworkExecutor.post(UrlConstants.GET_UPLOAD_TOKEN, params,
				TokenResponse.class,
				new NetworkExecutor.CallBack<TokenResponse>() {
					@Override
					public void onSuccess(TokenResponse response) {
						
						int status = response.getStatusCode();
						String msg = response.getMsg();
						
						if(status == TokenResponse.SUCCESS){
							for (String stringPath : stringPathList) {
								qiniuTask.preUpload(Uri.fromFile(new File(stringPath)), uploadHandler, response.getUploadToken());
							}
							qiniuTask.doUpload();
						}else{
							callback.onError(status, msg);
						}
					}

					@Override
					public void onError(Integer status, String msg) {
						callback.onError(status, msg);
					}

				});
	}
	
	public UploadHandler uploadHandler = new UploadHandler( ) {
		@Override
		protected void onProcess(long contentLength, long currentUploadLength, long lastUploadLength, UpParam p, Object passParam) {
			Log.i("","onProcess - contentLength       : "+contentLength);
			Log.i("","onProcess - currentUploadLength : "+currentUploadLength);
			Log.i("","onProcess - lastUploadLength    : "+lastUploadLength);
			Log.i("","onProcess - =============================================");
		}

		@Override
		protected void onSuccess(UploadResultCallRet ret, UpParam p, Object passParam) {
			ObjectMapper objectMapper = new ObjectMapper();
			Map readValue;
			try {
				readValue = objectMapper.readValue(ret.getResponse(), Map.class);
				names.add((String) readValue.get("key"));
			} catch ( IOException e1) {
				e1.printStackTrace();
			}
			try {
				String sourceId = qiniuTask.generateSourceId(p, passParam);
				qiniuTask.clean(sourceId);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(names.size() == qiniuTask.getLength()){
				uploadStatusListener.uploadFinish(true);
			}
		}

		@Override
		protected void onFailure(UploadResultCallRet ret, UpParam p, Object passParam) {
			Utils.showToast(context, "fail!!!! reupload plz");
			if (ret.getException() != null) {
				ret.getException().printStackTrace();
			}
			uploadStatusListener.uploadFinish(false);
		}
		
        @Override
        protected void onBlockSuccess(List<Block> uploadedBlocks, Block block, UpParam p, Object passParam) {
            Utils.showToast(context, "block success!!");
            try {
                String sourceId = qiniuTask.generateSourceId(p, passParam);
                qiniuTask.addBlock(sourceId, block);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	};
	
	public void upload(String content, final AsyncCallBacks.ZeroOne<String> callback) {
		Map<String, Object> params = Utils.buildMap("title", content ,"picUrls", names);

		NetworkExecutor.post(UrlConstants.UPLOAD, params,
				TokenResponse.class,
				new NetworkExecutor.CallBack<TokenResponse>() {
					@Override
					public void onSuccess(TokenResponse response) {
						callback.onSuccess();
					}

					@Override
					public void onError(Integer status, String msg) {
						callback.onError(msg);
					}

				});
	}
	
	
	public interface OnUploadStatusListener {
		void uploadFinish(boolean status);
	}
	public void setonTouchUpCallBackListener(OnUploadStatusListener uploadStatusListener) {
		this.uploadStatusListener = uploadStatusListener;
	}
	public OnUploadStatusListener getonTouchUpCallBackListener() {
		return uploadStatusListener;
	}
}
