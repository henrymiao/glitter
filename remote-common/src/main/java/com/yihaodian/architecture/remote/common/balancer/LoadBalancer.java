package com.yihaodian.architecture.remote.common.balancer;

import java.util.Collection;

public interface LoadBalancer<P> {

	public P select();

	public void updateProfiles(Collection<P> pathSet);
	
}
