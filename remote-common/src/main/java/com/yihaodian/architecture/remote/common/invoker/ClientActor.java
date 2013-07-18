package com.yihaodian.architecture.remote.common.invoker;
import com.yihaodian.architecture.remote.common.Constants;
import com.yihaodian.architecture.remote.common.RemoteRequest;
import com.yihaodian.architecture.remote.common.RemoteResponse;

import akka.actor.ActorRef;
import akka.actor.ActorIdentity;
import akka.actor.Identify;
import akka.actor.UntypedActor;
import akka.actor.ReceiveTimeout;
public class ClientActor  extends UntypedActor{

	private final String path;
	private ActorRef remoteActor = null;

	public ClientActor(String path)
	{
		this.path=path;
		sendIdentifyRequest();
	}
	private void sendIdentifyRequest() 
	{
		getContext().actorSelection(path).tell(new Identify(path), getSelf());
		//remoteActor=getContext().actorFor(path);
	}

	@Override
	public void onReceive(Object message) throws Exception {
		// TODO Auto-generated method stub
		if (message instanceof ActorIdentity) {
		      remoteActor = ((ActorIdentity) message).getRef();
		}else if (message.equals(ReceiveTimeout.getInstance())) {
		      sendIdentifyRequest();
	    } else if (remoteActor == null) {
	      
	    }else if(message instanceof RemoteRequest){
	    	RemoteRequest request=(RemoteRequest)message;
	    	if(request.getCallType()==Constants.CALLTYPE_NOREPLY)
	    	{
	    		remoteActor.tell(request,ActorRef.noSender());
	    	}else if(request.getCallType()==Constants.CALLTYPE_REPLY)
	    	{
	    		remoteActor.tell(request, getSelf());
	    	}
	    }else if(message instanceof RemoteResponse){
	    	RemoteResponse result=(RemoteResponse)message;
	    	doResponse(result);
	    }else
	    {
	    	unhandled(message);
	    }
	}
	private void doResponse(RemoteResponse result)
	{
		System.out.println(result.getReturn());
	}

}
