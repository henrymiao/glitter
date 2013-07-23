package com.yihaodian.architecture.remote.common.invoker;
import com.yihaodian.architecture.remote.common.Constants;
import com.yihaodian.architecture.remote.common.PackagedMessage;
import com.yihaodian.architecture.remote.common.RemoteRequest;
import akka.actor.ActorRef;
import akka.actor.ActorIdentity;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.ReceiveTimeout;
import akka.event.Logging;
import akka.event.LoggingAdapter;
public class ClientActor  extends UntypedActor{

	private final String path;
	private ActorRef remoteActor = null;
	private final LoggingAdapter log=Logging.getLogger(getContext().system(), this);
	public ClientActor(String path)
	{
		this.path=path;
		sendIdentifyRequest();
	}
	private void sendIdentifyRequest() 
	{
		//getContext().actorSelection(path).tell(new Identify(path), getSelf());
		remoteActor=getContext().actorFor(path);
	}

	@Override
	public void onReceive(Object message) throws Exception {
		// TODO Auto-generated method stub
		if (message instanceof ActorIdentity) {
		      remoteActor = ((ActorIdentity) message).getRef();
		}else if (message.equals(ReceiveTimeout.getInstance())) {
		      sendIdentifyRequest();
	    } else if (remoteActor == null) {
	    	log.warning("remoteactor not ready yet");
	    }else if(message instanceof RemoteRequest){
	    	RemoteRequest request=(RemoteRequest)message;
	    	if(request.getCallType()==Constants.CALLTYPE_NOREPLY)
	    	{
	    		remoteActor.tell(request,ActorRef.noSender());
	    	}
	    }else if(message instanceof PackagedMessage){
	    	PackagedMessage msg=(PackagedMessage)message;
	    	ActorRef promiseActor= getContext().actorOf(Props.create(PromiseActor.class, msg.getPromise()));
	    	remoteActor.tell(msg.getRequest(), promiseActor);
	    }else
	    {
	    	unhandled(message);
	    }
	}
	

}
