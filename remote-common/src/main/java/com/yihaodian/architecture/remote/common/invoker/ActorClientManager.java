package com.yihaodian.architecture.remote.common.invoker;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.yihaodian.architecture.remote.common.ClientManager;
import com.yihaodian.architecture.remote.common.RemoteHost;

public class ActorClientManager implements ClientManager {

	private Map<String, Set<RemoteHost>> serviceNameToRemoteHosts = new ConcurrentHashMap<String, Set<RemoteHost>>();
	
	public ActorClientManager()
	{
		
	}
	public synchronized void registeClient(String serviceName, String connect) {
		// TODO Auto-generated method stub
		//zk注册code
		
		int weight=1;
		Set<RemoteHost> remoteHostSet = serviceNameToRemoteHosts.get(serviceName);
		RemoteHost hi = new RemoteHost(connect, weight);
		if(remoteHostSet == null) {
			remoteHostSet = new HashSet<RemoteHost>();
			remoteHostSet.add(hi);
			serviceNameToRemoteHosts.put(serviceName, remoteHostSet);
		} else {
			remoteHostSet.add(hi);
		}
	}
	public RemoteHost getRemoteHost(String serviceName)
	{
		Iterator<RemoteHost> it= serviceNameToRemoteHosts.get(serviceName).iterator();
		while(it.hasNext())
		{
			return it.next();
		}
		return null;
				
	}

}
