package com.yihaodian.architecture.remote.common;

public class RemoteMetaData {

	private String serviceName;
	private String callMode;
	private int timeout;
	public RemoteMetaData(String serviceName,String callMode,int timeout)
	{
		this.serviceName=serviceName;
		this.callMode=callMode;
		this.timeout=timeout;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getCallMode() {
		return callMode;
	}
	public void setCallMode(String callMode) {
		this.callMode = callMode;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	
}
