package com.yihaodian.architecture.remote.common;

import scala.concurrent.Promise;

public class PackagedMessage {

	private final RemoteRequest request;
	private final Promise<RemoteResponse> promise;
	
	public PackagedMessage(RemoteRequest request,Promise<RemoteResponse> promise)
	{
		this.request=request;
		this.promise=promise;
	}
	
	public RemoteRequest getRequest() {
		return request;
	}

	public Promise<RemoteResponse> getPromise() {
		return promise;
	}

}
