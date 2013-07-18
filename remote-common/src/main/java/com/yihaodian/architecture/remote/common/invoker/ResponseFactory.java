package com.yihaodian.architecture.remote.common.invoker;

import com.yihaodian.architecture.remote.common.Constants;
import com.yihaodian.architecture.remote.common.RemoteResponse;

public class ResponseFactory {

	public static RemoteResponse createFailResponse(String message)
	{
		RemoteResponse response = null;
		if(message == null){
			message = "Service has Exception";
		}
		response = new DefaultResponse(Constants.MESSAGE_TYPE_EXCEPTION, message);
		return response;
	}
	public static RemoteResponse createServiceExceptionResponse(Throwable e){
		RemoteResponse response = null;
		if(e == null){
			return createFailResponse(null);
		}
		response = new DefaultResponse(Constants.MESSAGE_TYPE_SERVICE_EXCEPTION, e.getCause());
		return response;
	}
	public static RemoteResponse createSuccessResponse(Object returnObj){
		RemoteResponse response = null;
		response = new DefaultResponse(Constants.MESSAGE_TYPE_SERVICE,returnObj);
		return response;
	}
}
