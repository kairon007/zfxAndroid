package com.zifei.corebeau.account.bean.response;

import com.zifei.corebeau.account.bean.ConfigInfo;
import com.zifei.corebeau.common.net.Response;

public class ConfigResponse extends Response{
	
	private ConfigInfo configInfo;

	public ConfigInfo getConfigInfo() {
		return configInfo;
	}

	public void setConfigInfo(ConfigInfo configInfo) {
		this.configInfo = configInfo;
	}
	
}
