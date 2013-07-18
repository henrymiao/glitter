package com.yihaodian.architecture.remote.common;

import com.yihaodian.architecture.remote.common.exception.RemoteException;

public interface RemoteRequest extends ExtensionSerializable {

	public void setCallType(int callType);
	
	public int getCallType();
	
	public int getTimeout();
	
	public long getCreateMillisTime();
	
	public void createMillisTime();
	
	public String getServiceName();
	
	public String getMethodName();
	
	public String[] getParamClassName() throws RemoteException ;
	
	public Object[] getParameters()throws RemoteException;
	
	public int getMessageType();
}
