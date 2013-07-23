package com.yihaodian.architecture.remote.common;

import scala.concurrent.Future;

public interface Invoker {
	public RemoteResponse invokeSync(RemoteRequest request)throws Exception;
	public void invokeOneWay(RemoteRequest request);
	public Future<RemoteResponse> invokeFuture(RemoteRequest request)throws Exception;
}
