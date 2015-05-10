package com.zifei.corebeau.common.task;

import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import android.os.AsyncTask;

import com.zifei.corebeau.common.AsyncCallBacks;
import com.zifei.corebeau.common.CorebeauApp;
import com.zifei.corebeau.common.net.CustomHttpResponse;
import com.zifei.corebeau.common.net.HttpNetworkClient;
import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.task.UserInfoService;
import com.zifei.corebeau.utils.StringUtil;

public class NetworkExecutor {

	public static <T> NetworkAsync<T> post(String url, Map<String, Object> paramData, Class<T> resultType,
			CallBack<T> callback) {
		paramData = setUserIdLoginId(paramData);
		NetworkAsync<T> task = new NetworkAsync<T>(url, paramData, resultType, callback);
		task.execute();
		return task;
	}

	public static <T> void syncPost(String url, Map<String, Object> paramData, Class<T> resultType, CallBack<T> callback) {
		paramData = setUserIdLoginId(paramData);
		CustomHttpResponse result = HttpNetworkClient.post(url, paramData);
		processResult(result, callback, resultType);
	}

	private static Map<String, Object> setUserIdLoginId(Map<String, Object> paramData) {
		String userId = UserInfoService.getUserId();
		String loginId = UserInfoService.getLoginId();
		
		if (!StringUtil.isEmpty(loginId)) {
		
			paramData.put("loginId", loginId);
			paramData.put("userId", userId);
		}
		return paramData;
	}

	public static class NetworkAsync<T> extends AsyncTask<Void, Integer, CustomHttpResponse> {

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
			processResult(result, callback, resultType);
		}
	}

	private static <T> void processResult(CustomHttpResponse result, CallBack<T> callback, Class<T> resultType) {
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
					callback.onError(response.getStatusCode(), response.getMsg());
				} else {
					callback.onError(NetworkConstants.NETWORK_EXCEPTION, "未知错误，请稍后再试");
				}
			} else {
				switch (statusCode) {
				case NetworkConstants.NETWORK_TIMEOUT_EXCEPTION: {
					callback.onError(NetworkConstants.NETWORK_TIMEOUT_EXCEPTION, "网络请求超时，请检查网络后再试");
					break;
				}
				case NetworkConstants.PARAM_ENCRPT_EXCEPTION: {
					callback.onError(NetworkConstants.PARAM_ENCRPT_EXCEPTION, "参数加密错误，请稍后再试");
					break;
				}
				case NetworkConstants.NETWORK_EXCEPTION: {
					callback.onError(NetworkConstants.NETWORK_EXCEPTION, "网络异常，请检查网络后再试");
					break;
				}
				default:
					callback.onError(NetworkConstants.NETWORK_EXCEPTION, "未知错误，请稍后再试");
					break;
				}
			}
		}
	}

	public static class CallBack<T> extends AsyncCallBacks.OneTwo<T, Integer, String> {
	}
}