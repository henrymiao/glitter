package com.yihaodian.architecture.remote.common;

import com.yihaodian.architecture.remote.common.invoker.ActorClientManager;

public class ClientManagerFactory {
private static ClientManager manager;
	
	public static ClientManager getClientManager() {
		if (manager == null) {
			synchronized (ClientManagerFactory.class) {
				if (manager == null) {
					manager = new ActorClientManager();
				}
			}
		}
		return manager;
	}

	public static void setManager(ClientManager manager) {
		ClientManagerFactory.manager = manager;
	}
}
