package com.zifei.corebeau.common.task;

import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import android.os.AsyncTask;

import com.zifei.corebeau.account.task.UserInfoService;
import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.CorebeauApp;
import com.zifei.corebeau.common.net.CustomHttpResponse;
import com.zifei.corebeau.common.net.HttpNetworkClient;
import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.utils.StringUtil;


public class NetworkExecutor {

	private static UserInfoService userInfoService = new UserInfoService(CorebeauApp.app);

	public static <T> NetworkAsync<T> post(String url, Map<String, Object> paramData, Class<T> resultType,
			CallBack<T> callback) {
		paramData = setUserIdLoginId(paramData);
		NetworkAsync<T> task = new NetworkAsync<T>(url, paramData, resultType, callback);
		task.execute();
		return task;
	}
	
	private static Map<String, Object> setUserIdLoginId(Map<String, Object> paramData) {
		paramData.put("loginId", userInfoService.getLoginId());
		paramData.put("userId", userInfoService.getUserId());
		return paramData;
	}
	
	private static class NetworkAsync<T> extends AsyncTask<Void, Integer, CustomHttpResponse> implements CancelListener{

		private String url;

		private CallBack<T> callback;

		private Class<T> resultType;

		private Map<String, Object> paramData;

		public NetworkAsync(String url, Map<String, Object> paramData, Class<T> resultType, CallBack<T> callback) {
			this.url = url;
			this.callback = callback;
			this.resultType = resultType;
			this.paramData = paramData;
		}

		@Override
		protected CustomHttpResponse doInBackground(Void... params) {
			return HttpNetworkClient.post(url, paramData);
		}

		@Override
		protected void onPostExecute(CustomHttpResponse result) {
			super.onPostExecute(result);
			if (result != null) {
				int statusCode = result.getStatusCode();
				if (statusCode == NetworkConstants.OK) {
					ObjectMapper objectMapper = new ObjectMapper();
					T data = null;
					try {
						if (!StringUtil.isEmpty(result.getData())) {
							data = objectMapper.readValue(result.getData(), resultType);
						}
					} catch (Exception e) {
						callback.onError(NetworkConstants.RESPONSE_DECODE_EXCEPTION, "网络返回数据解析异常，请稍后再试");
						return;
					}
					if (data != null) {
						Response response = (Response) data;
						if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
							callback.onSuccess(data);
							return;
						}
						switch (response.getStatusCode()) {
						case Response.DUPLICATE_REQUEST: {
							callback.onError(Response.DUPLICATE_REQUEST, "请求太频繁，请稍后再试");
							return;
						}
						case Response.NOT_LOGIN: {
							callback.onError(Response.NOT_LOGIN, "您还没有登录，请登录");
							return;
						}
						case Response.PARAM_INVALID: {
							callback.onError(Response.PARAM_INVALID, "请求参数无效，请稍后再试");
							return;
						}
						case Response.PARAM_PARSE_ERROR: {
							callback.onError(Response.PARAM_PARSE_ERROR, "参数解析出错，请稍后再试");
							return;
						}
						default:
							callback.onError(response.getStatusCode(), response.getMsg());
							return;
						}
					} else {
						callback.onError(NetworkConstants.NETWORK_EXCEPTION, "未知错误，请稍后再试");
					}
				} else {
					switch (statusCode) {
					case NetworkConstants.NETWORK_TIMEOUT_EXCEPTION: {
						callback.onError(NetworkConstants.NETWORK_TIMEOUT_EXCEPTION, "网络请求超时，请检查网络后再试");
						return;
					}
					case NetworkConstants.PARAM_ENCRPT_EXCEPTION: {
						callback.onError(NetworkConstants.PARAM_ENCRPT_EXCEPTION, "参数加密错误，请稍后再试");
						return;
					}
					case NetworkConstants.NETWORK_EXCEPTION: {
						callback.onError(NetworkConstants.NETWORK_EXCEPTION, "网络异常，请检查网络后再试");
						return;
					}
					default:
						callback.onError(NetworkConstants.NETWORK_EXCEPTION, "未知错误，请稍后再试");
						return;
					}
				}
			}
		}

		@Override
		public void cancel() {
			cancel(true);
		}
	}

	public static class CallBack<T> extends AsyncCallBacks.OneTwo<T, Integer, String> {
	}

}