package com.yihaodian.architecture.remote.common;

public interface ClientManager {
	public void registeClient(String serviceName,String connect);
	RemoteHost getRemoteHost(String serviceName);
}
