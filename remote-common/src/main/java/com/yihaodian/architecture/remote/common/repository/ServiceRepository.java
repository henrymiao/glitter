package com.yihaodian.architecture.remote.common.repository;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.yihaodian.architecture.remote.common.Constants;
import com.yihaodian.architecture.remote.common.exception.RemoteException;
import com.yihaodian.architecture.remote.common.invoker.WorkerActor;

public class ServiceRepository {

	private Map<String,ServiceMethods> methods = new ConcurrentHashMap<String,ServiceMethods>();
	private Map<String,ServiceActors> actors=new ConcurrentHashMap<String, ServiceActors>();
	private Set<String> ingoreMethods = new HashSet<String>();
	
	private Map<String,Object> services = new ConcurrentHashMap<String,Object>();
	private final ServiceRepository sr;
	public  ServiceRepository()
	{
		Method[] objectMethodArray = Object.class.getMethods();
		for(Method method : objectMethodArray){
			this.ingoreMethods.add(method.getName());
		}
		
		Method[] classMethodArray = Class.class.getMethods();
		for(Method method : classMethodArray){
			this.ingoreMethods.add(method.getName());
		}
		sr=this;
	}
	public void registerService(String serviceName,Object service,ActorSystem system) throws ClassNotFoundException{
		this.services.put(serviceName, service);
		//注册method level actor
		Method[] methodArray = service.getClass().getMethods();
		ServiceMethods serviceMethods = new ServiceMethods(serviceName,service);
		ServiceActors  serviceActors= new ServiceActors(serviceName);
		
		for(Method method : methodArray){
			if(!this.ingoreMethods.contains(method.getName())){
				method.setAccessible(true);
				serviceMethods.addMethod(method.getName(),new RemoteMethod(service,method));
				if(serviceActors.getActor(""+serviceName+Constants.CONNECTOR+method.getName()) != null)
					continue;
				ActorRef workerActor=system.actorOf(Props.create(WorkerActor.class,sr),""+serviceName+Constants.CONNECTOR+method.getName());//+Constants.CHILD_CONNECTOR+0);
				serviceActors.AddActor( ""+serviceName+Constants.CONNECTOR+method.getName(), workerActor);
				//for(int i=1;i<Constants.DEFAULT_CHILDCOUNT;i++)
				//{
				//	system.actorOf(Props.create(WorkerActor.class,sr),""+serviceName+Constants.CONNECTOR+method.getName()+Constants.CHILD_CONNECTOR+i);
				//}
			}
		}
		this.methods.put(serviceName, serviceMethods);
		this.actors.put(serviceName, serviceActors);
		//zk注册
	}

	public Object getService(String serviceName){
		return this.services.get(serviceName);
	}
	
	public Collection<String> getServiceNames() {
		return this.services.keySet();
	}
	
	public RemoteMethod getMethod(String serviceName,String methodName,String[] paramClassNames) throws RemoteException
	{
		ServiceMethods serviceMethods = this.methods.get(serviceName);
		if(serviceMethods!=null)
		{
			return serviceMethods.getMethod(methodName,new RemoteParams(paramClassNames));
		}
		/*if(serviceMethods == null){
			synchronized(this){
				serviceMethods = this.methods.get(serviceName);
				if(serviceMethods == null){
					Object service = this.services.get(serviceName);
					if(service == null){
						throw new RemoteException("cant not find serivce for serviceName:"+serviceName);
					}
					Method[] methodArray = service.getClass().getMethods();
					serviceMethods = new ServiceMethods(serviceName,service);
					for(Method method : methodArray){
						if(!this.ingoreMethods.contains(method.getName())){
							method.setAccessible(true);
							serviceMethods.addMethod(method.getName(),new RemoteMethod(service,method));
						}
					}
					this.methods.put(serviceName, serviceMethods);
				}
			}
		}*/
		return null;
	}
}
