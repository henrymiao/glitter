/**
 * 
 */
package com.yihaodian.architecture.remote.common.exception;

public class InvalidParamException extends RemoteException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8242693325670325410L;

	public InvalidParamException() {
		super();
	}

	public InvalidParamException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidParamException(String message) {
		super(message);
	}

	public InvalidParamException(Throwable cause) {
		super(cause);
	}

}
