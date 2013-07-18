package com.yihaodian.architecture.remote.common.exception;

public class ClientException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2893054432645076340L;
	public ClientException(){
		super();
	}
	
	public ClientException(String msg){
		super(msg);
	}
	
	public ClientException(String msg,Throwable cause){
		super(msg,cause);
	}
}
