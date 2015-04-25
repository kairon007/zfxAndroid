package com.zifei.corebeau.account.bean.response;

import java.util.Map;

import com.zifei.corebeau.common.net.Response;

public class ConfigResponse extends Response{
	
	private Map<String ,String> confMap;

	public Map<String, String> getConfMap() {
		return confMap;
	}

	public void setConfMap(Map<String, String> confMap) {
		this.confMap = confMap;
	}
	
	public static final int UPDATE = 200;
	
	public static final int NOT_UPDATE = -50;
	
}
