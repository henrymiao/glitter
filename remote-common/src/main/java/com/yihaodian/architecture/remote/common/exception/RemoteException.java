package com.yihaodian.architecture.remote.common.exception;

public class RemoteException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5646871193769845652L;

	public RemoteException(){
		super();
	}
	
	public RemoteException(String msg){
		super(msg);
	}
	
	public RemoteException(String msg,Throwable cause){
		super(msg,cause);
	}
	public RemoteException(Throwable cause) {
		super(cause.getMessage(), cause);
	}

}