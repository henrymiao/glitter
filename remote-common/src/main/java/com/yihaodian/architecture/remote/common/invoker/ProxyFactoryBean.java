package com.yihaodian.architecture.remote.common.invoker;

import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;

import com.yihaodian.architecture.remote.common.ClientManagerFactory;
import com.yihaodian.architecture.remote.common.Constants;
import com.yihaodian.architecture.remote.common.RemoteMetaData;

public class ProxyFactoryBean implements FactoryBean{

	private Object obj;
	private Class objType;
	private String serviceName;
	private String interfaceClass;
	private String callMode=Constants.CALL_SYNC;
	private String hosts;
	private int timeout=2000;//2s
	@SuppressWarnings("unused")
	private void init()throws Exception{
		
		ClientManagerFactory.getClientManager().registeClient(serviceName, hosts);
		this.objType = Class.forName(this.interfaceClass);
		this.obj = Proxy.newProxyInstance(ProxyFactoryBean.class.getClassLoader(), 
				new Class[]{this.objType}, new ProxyInvoker(new RemoteMetaData(this.serviceName,this.callMode,this.timeout)));
	}
	public boolean isSingleton() {
		return true;
	}
	public Object getObject() throws Exception {
		// TODO Auto-generated method stub
		return this.obj;
	}
	public Class getObjectType() {
		// TODO Auto-generated method stub
		return this.objType;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public void setInterfaceClass(String interfaceClass) {
		this.interfaceClass = interfaceClass;
	}
	public void setCallMode(String callMode) {
		this.callMode = callMode;
	}
	public void setHosts(String hosts) {
		this.hosts = hosts;
	}


}
