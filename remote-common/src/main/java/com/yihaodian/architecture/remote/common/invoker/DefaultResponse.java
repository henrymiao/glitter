package com.yihaodian.architecture.remote.common.invoker;

import com.yihaodian.architecture.remote.common.RemoteRequest;
import com.yihaodian.architecture.remote.common.RemoteResponse;
import com.yihaodian.architecture.remote.common.exception.RemoteException;

public class DefaultResponse implements RemoteResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4843775573625990759L;
	private int messageType;
	private String cause;
	private Object returnVal;
	public DefaultResponse(int messageType)
	{
		this.messageType=messageType;
	}
		
	public DefaultResponse(int messageType,Object returnVal)
	{
		this.messageType=messageType;
		this.returnVal=returnVal;
	}
	public DefaultResponse(int messageType,String cause){
		this.messageType=messageType;
		this.cause=cause;
	}
	public Object getObject() {
		// TODO Auto-generated method stub
		return this;
	}

	public void setMessageType(int messageType) {
		// TODO Auto-generated method stub
		this.messageType=messageType;
	}

	public int getMessageType() throws RemoteException {
		// TODO Auto-generated method stub
		return this.messageType;
	}

	public String getCause() {
		// TODO Auto-generated method stub
		return this.cause;
	}

	public Object getReturn() {
		// TODO Auto-generated method stub
		return this.returnVal;
	}

	public void setReturn(Object returnVal) {
		// TODO Auto-generated method stub
		this.returnVal=returnVal;
	}

}
