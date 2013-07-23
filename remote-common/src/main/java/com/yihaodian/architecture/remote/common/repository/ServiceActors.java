package com.yihaodian.architecture.remote.common.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import akka.actor.ActorRef ;
public class ServiceActors {

	private String serviceName;
	private Map<String,ActorRef> actors =new ConcurrentHashMap<String, ActorRef>();
	public ServiceActors(String serviceName){
		this.serviceName = serviceName;
	}
	void AddActor(String methodName,ActorRef actor)
	{
		actors.put(methodName, actor);
	}
	public ActorRef getActor(String methodName)
	{
		return actors.get(methodName);
	}
	
}
