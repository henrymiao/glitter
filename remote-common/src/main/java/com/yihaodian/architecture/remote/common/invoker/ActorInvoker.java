package com.yihaodian.architecture.remote.common.invoker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.Futures;
import akka.util.Timeout;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.yihaodian.architecture.remote.common.ClientManagerFactory;
import com.yihaodian.architecture.remote.common.Constants;
import com.yihaodian.architecture.remote.common.Invoker;
import com.yihaodian.architecture.remote.common.PackagedMessage;
import com.yihaodian.architecture.remote.common.RemoteRequest;
import com.yihaodian.architecture.remote.common.RemoteResponse;
import com.yihaodian.architecture.remote.common.Util.SystemUtil;

public class ActorInvoker implements Invoker{

	public ActorSystem system ;
	private Map<String,ActorRef> remoteActorMap=new ConcurrentHashMap<String, ActorRef>();
	private static Invoker invoker;
	private ActorInvoker()
	{
		Config cf=ConfigFactory.parseString("akka.remote.netty.tcp.hostname=\""+SystemUtil.getFirstNoLoopbackIP4Address()+"\"").withFallback(ConfigFactory.load().getConfig("client"));
		system = ActorSystem.create(Constants.ClientAkkaSystemName,cf);
	}
	private synchronized static void createInvoker(){
		if(invoker != null){
			return;
		}
		invoker = new ActorInvoker();
	}
	
	public static Invoker getInstance(){
		if(invoker == null){
			createInvoker();
		}
		return invoker;
	}
	public RemoteResponse invokeSync(RemoteRequest request)throws Exception
	{   
		Future<RemoteResponse> future= invokeFuture(request);
		if(future==null)
			return null;
		Timeout timeout = new Timeout(request.getTimeout());
		RemoteResponse response=(RemoteResponse)Await.result(future, timeout.duration());
		return response;
	}
	public Future<RemoteResponse> invokeFuture(RemoteRequest request)throws Exception
	{
		request.setCallType(Constants.CALLTYPE_REPLY);
		request.createMillisTime();
		//remote node
		ActorRef actor=selectActor(ClientManagerFactory.getClientManager().getRemoteHost(request.getServiceName()).getConnect(), request.getServiceName(), request.getMethodName());
		//Timeout timeout = new Timeout(request.getTimeout());
		Promise<RemoteResponse> promise=Futures.promise();
		Future<RemoteResponse> future = promise.future();
		PackagedMessage message=new PackagedMessage(request, promise);
		actor.tell(message,  ActorRef.noSender());
		return future;
	}
	public void invokeOneWay(RemoteRequest request)
	{
		request.setCallType(Constants.CALLTYPE_NOREPLY);
		request.createMillisTime();
		ActorRef actor=selectActor(ClientManagerFactory.getClientManager().getRemoteHost(request.getServiceName()).getConnect(), request.getServiceName(), request.getMethodName());
		actor.tell(request, ActorRef.noSender());
	}
	
	private ActorRef selectActor(String connect,String serviceName,String methodName) 
	{
		String path=formatRemoteAddress(connect, serviceName, methodName);
		ActorRef actor=remoteActorMap.get(path);
		
		if(actor==null)
		{
			synchronized (remoteActorMap){
				if((actor=remoteActorMap.get(path))==null)
				{
					//String[] actName=path.split("\\$");
					actor = system.actorOf(Props.create(ClientActor.class,path),serviceName+Constants.CONNECTOR+methodName);//+actName[1]);
					remoteActorMap.put(path, actor);
				}
				
			}
		}
		return actor;
				
	}
	
	private String formatRemoteAddress(String connect,String serviceName,String methodName)
	{
		//akka.<protocol>://<actorsystemname>@<hostname>:<port>/<actor path>
		String format="akka.tcp://"+Constants.AkkaSystemName+"@"+connect+"/user/"+serviceName+Constants.CONNECTOR+methodName;
	    /*ActorsRouteManager actorsRouteManager=actorsRouteManagerMap.get(format);
	    if(actorsRouteManager==null)
	    {
	    	synchronized(actorsRouteManagerMap)
	    	{
	    		if((actorsRouteManager=actorsRouteManagerMap.get(format))==null)
	    		{
	    			actorsRouteManager=new ActorsRouteManager(format);
	    			actorsRouteManagerMap.put(format, actorsRouteManager);
	    		}
	    	}
	    }
	    
		return actorsRouteManager.route();*/
		return format;
	}
}
