package com.zifei.corebeau.extra.qiniu.up.simple;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.AbstractContentBody;
import org.apache.http.entity.mime.content.StringBody;

import com.zifei.corebeau.extra.qiniu.config.Config;
import com.zifei.corebeau.extra.qiniu.up.UpParam;
import com.zifei.corebeau.extra.qiniu.up.Upload;
import com.zifei.corebeau.extra.qiniu.up.UploadHandler;
import com.zifei.corebeau.extra.qiniu.up.auth.Authorizer;
import com.zifei.corebeau.extra.qiniu.up.rs.PutExtra;
import com.zifei.corebeau.extra.qiniu.up.rs.UploadResultCallRet;
import com.zifei.corebeau.extra.qiniu.util.Util;

public abstract class SimpleUpload extends Upload {
	private HttpPost post;

	SimpleUpload(Authorizer authorizer, String key, UpParam upParam, PutExtra extra, Object passParam, UploadHandler handler) {
		super(authorizer, key, upParam, extra, passParam, handler);
	}

	public UploadResultCallRet doExecute() throws ClientProtocolException, IOException {
		MultipartEntity requestEntity = new MultipartEntity();
		requestEntity.addPart("token", new StringBody(authorizer.getUploadToken()));
		requestEntity.addPart("file", buildFileBody());
		buildKeyPart(requestEntity);
		buildExtraParam(requestEntity);

		post = Util.newPost(Config.UP_HOST);
		post.setEntity(requestEntity);
		HttpResponse response = getHttpClient().execute(post);
		UploadResultCallRet ret = new UploadResultCallRet(Util.handleResult(response));
		return ret;
	}

	private void buildExtraParam(MultipartEntity requestEntity) throws UnsupportedEncodingException {
		if (extra == null || extra.params == null) {
			return;
		}
		for (Map.Entry<String, String> xvar : extra.params.entrySet()) {
			// 必须以 x: 开头
			if (xvar.getKey().startsWith("x:")) {
				requestEntity.addPart(xvar.getKey(), new StringBody(xvar.getValue(), Charset.forName(Config.CHARSET)));
			}
		}
	}

	protected abstract AbstractContentBody buildFileBody();

	protected void buildKeyPart(MultipartEntity requestEntity) throws UnsupportedEncodingException {
		if (key != null) {
			requestEntity.addPart("key", new StringBody(key, Charset.forName(Config.CHARSET)));
		}
	}

	protected void clean() throws Exception {
		super.clean();
		post = null;
	}

	public synchronized void cancel() {
		super.cancel();
		if (post != null) {
			try {
				post.abort();
			} catch (Exception e) {
			}
		}
	}

}
