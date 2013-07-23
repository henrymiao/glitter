package com.yihaodian.architecture.remote.common.invoker;

import java.util.HashMap;
import java.util.Map;

import akka.actor.ActorSystem;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.yihaodian.architecture.remote.common.Constants;
import com.yihaodian.architecture.remote.common.Util.SystemUtil;
import com.yihaodian.architecture.remote.common.exception.RemoteException;
import com.yihaodian.architecture.remote.common.repository.ServiceRepository;

public class RemoteRegistry{

	private Map<String,Object> services;
	private Integer port=null;
	private String hostName=null;
	public  static boolean isInit = false;
	public final String AkkaSystemName=Constants.AkkaSystemName;
	public ActorSystem system ;
	private ServiceRepository sr;
	public RemoteRegistry(){
		
	}
	public void init() throws Exception{
		isInit=true;
		if(this.services != null)
		{
			this.sr = new ServiceRepository();
			Config config=null;
			Map<String,String> parseMap=new HashMap<String, String>();
			if(hostName==null)
			{
				hostName=SystemUtil.getFirstNoLoopbackIP4Address();
			}
			parseMap.put("akka.remote.netty.tcp.hostname", hostName);
			if(port!=null)
			{
				parseMap.put("akka.remote.netty.tcp.port", port.toString());
			}
			config=ConfigFactory.parseMap(parseMap).withFallback(ConfigFactory.load().getConfig("server"));
			//System.out.println(config.getString("akka.remote.netty.tcp.port"));
			system = ActorSystem.create(AkkaSystemName,config);
			for(String serviceName : this.services.keySet())
			{
				this.sr.registerService(serviceName, this.services.get(serviceName),system);
			}
			//服务注册到zk上
		}
	}
	public void register(String serviceName,Object service) throws RemoteException
	{
		if(this.services == null){
			this.services = new HashMap<String,Object>();
		}
		if(this.services.containsKey(serviceName)){
			throw new RemoteException("service:"+serviceName+" has been existent");
		}
		this.services.put(serviceName, service);
		
	}
	public void setServices(Map<String, Object> services) {
		this.services = services;
	}
	
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
}
