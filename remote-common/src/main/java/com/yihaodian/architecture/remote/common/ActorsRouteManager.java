package com.yihaodian.architecture.remote.common;

import java.util.ArrayList;
import java.util.List;

import com.yihaodian.architecture.remote.common.balancer.BalancerFactory;
import com.yihaodian.architecture.remote.common.balancer.LoadBalancer;
import com.yihaodian.architecture.remote.common.exception.RemoteException;

public class ActorsRouteManager {

	private LoadBalancer<String> balancer;
	private boolean initialized = false;
	public ActorsRouteManager(String parentPath)
	{
		try {
			balancer = BalancerFactory.getInstance().getBalancer(Constants.BALANCER_NAME_ROUNDROBIN);
			List<String> childPaths=new ArrayList<String>();
			for(int i=0;i<Constants.DEFAULT_CHILDCOUNT;i++)
			{
				childPaths.add(parentPath+Constants.CHILD_CONNECTOR+i);
			}
			balancer.updateProfiles(childPaths);
			initialized=true;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			
		}
		
	}
	public String route() {
		String path = null;
		while (!initialized) {
			Thread.yield();
		}
		path = balancer.select();
		return path;
	}
}
