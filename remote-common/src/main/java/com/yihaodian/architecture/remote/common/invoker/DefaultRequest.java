package com.yihaodian.architecture.remote.common.invoker;

import com.yihaodian.architecture.remote.common.Constants;
import com.yihaodian.architecture.remote.common.RemoteRequest;
import com.yihaodian.architecture.remote.common.exception.RemoteException;

public class DefaultRequest implements RemoteRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6888116289896199664L;
	private int callType;
	private int timeout;
	private transient long createMillisTime;
	private String serviceName;
	private String methodName;
	private Object[] parameters;
	private int messageType;
	private transient Class<?>[] parameterClasses;
	
	public DefaultRequest(String serviceName,String methodName,
			Object[] parameters,int messageType,int timeout,Class<?>[] parameterClasses){
		this.serviceName = serviceName;
		this.methodName = methodName;
		this.parameters = parameters;
		this.messageType = messageType;
		this.timeout = timeout;
		this.parameterClasses = parameterClasses;
	}
	
	public Object getObject() {
		return this;
	}

	public void setCallType(int callType) {
		// TODO Auto-generated method stub
		this.callType = callType;
	}

	public int getCallType() {
		// TODO Auto-generated method stub
		return this.callType;
	}

	public int getTimeout() {
		// TODO Auto-generated method stub
		return this.timeout;
	}

	public long getCreateMillisTime() {
		// TODO Auto-generated method stub
		return this.createMillisTime;
	}

	public void createMillisTime() {
		// TODO Auto-generated method stub
		this.createMillisTime = System.currentTimeMillis();
	}

	public String getServiceName() {
		// TODO Auto-generated method stub
		return this.serviceName;
	}

	public String getMethodName() {
		// TODO Auto-generated method stub
		return this.methodName;
	}

	public String[] getParamClassName() throws RemoteException {
		// TODO Auto-generated method stub
		if(this.parameters == null){
			return new String[0];
		}
		String[] paramClassNames = new String[this.parameters.length];
		
		int k = 0;
		for(Object parameter : this.parameters){
			if(parameter == null){
				paramClassNames[k] = Constants.VALUE_NULL;
			}else{
				paramClassNames[k] = this.parameters[k].getClass().getName();
			}
			k++;
		}
		return paramClassNames;
	}

	public Object[] getParameters() throws RemoteException {
		// TODO Auto-generated method stub
		return this.parameters;
	}

	public int getMessageType() {
		// TODO Auto-generated method stub
		return this.messageType;
	}
	
	public Class<?>[] getParameterClasses() {
		return parameterClasses;
	}

	public void setParameterClasses(Class<?>[] parameterClasses) {
		this.parameterClasses = parameterClasses;
	}


}
