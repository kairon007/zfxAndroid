package com.zifei.corebeau.common.net;

import com.zifei.corebeau.common.task.NetworkConstants;
import com.zifei.corebeau.utils.AESTools;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;


public class HttpNetworkClient {

	public static CustomHttpResponse post(String url, Map<String, Object> paramData) {

		CustomHttpResponse customHttpResponse = new CustomHttpResponse();

		// 生成参数
		ObjectMapper mapper = new ObjectMapper();
		String encriptData = null;
		try {
			//encriptData = AESTools.encode(mapper.writeValueAsString(paramData));
			encriptData = URLEncoder.encode(mapper.writeValueAsString(paramData), "UTF-8");
		} catch (Exception e) {
			customHttpResponse.setStatusCode(NetworkConstants.PARAM_ENCRPT_EXCEPTION);
		}

		DefaultHttpClient client = null;
		try {
			// Get HTTP Client， 可以优化到另外一个方法中，或者使用HttpURLConnection
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			HttpProtocolParams.setUseExpectContinue(params, true);
//			HttpProtocolParams.setUserAgent(params, AppEnvironment.getuserAgent());
			ConnManagerParams.setTimeout(params, 1000);
			HttpConnectionParams.setConnectionTimeout(params, 15000);
			HttpConnectionParams.setSoTimeout(params, 20000);
			HttpRequestRetryHandler handler = new HttpRequestRetryHandler() {
				@Override
				public boolean retryRequest(IOException arg0, int arg1, HttpContext arg2) {
					return false;
				}
			};
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			ClientConnectionManager conMgr = new SingleClientConnManager(params, schReg);
			client = new DefaultHttpClient(conMgr, params);
			client.setHttpRequestRetryHandler(handler);

			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("param", encriptData));
			HttpEntity formEntity = new UrlEncodedFormEntity(postParameters, "UTF-8");
			HttpPost request = new HttpPost(url);
			request.setEntity(formEntity);

			// 执行请求
			HttpResponse responseData = client.execute(request);
			StatusLine statusLine = responseData.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				customHttpResponse.setStatusCode(NetworkConstants.OK);
				HttpEntity entity = responseData.getEntity();
				if (entity != null) {
//					if(EntityUtils.toString(entity)  empty)// exception 처리하기
					customHttpResponse.setData(EntityUtils.toString(entity));
				}
			} else if (statusLine.getStatusCode() == HttpStatus.SC_REQUEST_TIMEOUT){
				customHttpResponse.setStatusCode(NetworkConstants.NETWORK_TIMEOUT_EXCEPTION);
			} else {
				customHttpResponse.setStatusCode(NetworkConstants.NETWORK_EXCEPTION);
			}
		} catch (IOException e) {
			if (e instanceof ConnectTimeoutException || e instanceof SocketTimeoutException) {
				customHttpResponse.setStatusCode(NetworkConstants.NETWORK_TIMEOUT_EXCEPTION);
			} else {
				customHttpResponse.setStatusCode(NetworkConstants.NETWORK_EXCEPTION);
			}
		} finally {
			try {
				client.getConnectionManager().shutdown();
			} catch (Exception e) {
			}
		}
		return customHttpResponse;
	}
}
