package com.yihaodian.architecture.remote.common.invoker;

import com.yihaodian.architecture.remote.common.RemoteResponse;

import scala.concurrent.Promise;
import akka.actor.UntypedActor;

public class PromiseActor extends UntypedActor{

	private Promise<RemoteResponse> promise;
	public PromiseActor(Promise<RemoteResponse> promise)
	{
		this.promise=promise;
	}
	@Override
	public void onReceive(Object message) throws Exception {
		// TODO Auto-generated method stub
		if(message instanceof RemoteResponse)
		{
			RemoteResponse response=(RemoteResponse)message;
			this.promise.success(response);
			getContext().stop(getSelf());
		}
	}

}
