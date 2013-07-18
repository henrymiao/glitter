package com.yihaodian.architecture.remote.common.invoker;

import scala.concurrent.Future;
import akka.actor.ActorRef;
import akka.actor.TypedActor.Receiver;
import com.yihaodian.architecture.remote.common.Invoker;
import com.yihaodian.architecture.remote.common.RemoteRequest;
import com.yihaodian.architecture.remote.common.RemoteResponse;

public class ClientTypedActor implements Invoker,Receiver{

	private final String path;
	private ActorRef remoteActor = null;
	
	public ClientTypedActor(String path)
	{
		this.path=path;
		
	}
	
	public void onReceive(Object arg0, ActorRef arg1) {
		// TODO Auto-generated method stub
		
	}

	public RemoteResponse invokeSync(RemoteRequest request) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void invokeOneWay(RemoteRequest request) {
		// TODO Auto-generated method stub
		
	}

	public Future<Object> invokeFuture(RemoteRequest request) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void invokeResponse(RemoteResponse response) {
		// TODO Auto-generated method stub
		
	}

}
