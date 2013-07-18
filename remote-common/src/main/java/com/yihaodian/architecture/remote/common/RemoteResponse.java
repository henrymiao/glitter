package com.yihaodian.architecture.remote.common;

import com.yihaodian.architecture.remote.common.exception.RemoteException;

public interface RemoteResponse extends ExtensionSerializable {

	public void setMessageType(int messageType);
	
	public int getMessageType() throws RemoteException;
	
	public String getCause();
	
	public Object getReturn();

	public void setReturn(Object obj);
}
