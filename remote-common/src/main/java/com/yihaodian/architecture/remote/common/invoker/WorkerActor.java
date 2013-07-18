package com.yihaodian.architecture.remote.common.invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import akka.actor.UntypedActor;

import com.yihaodian.architecture.remote.common.Constants;
import com.yihaodian.architecture.remote.common.RemoteRequest;
import com.yihaodian.architecture.remote.common.RemoteResponse;
import com.yihaodian.architecture.remote.common.exception.RemoteException;
import com.yihaodian.architecture.remote.common.repository.RemoteMethod;
import com.yihaodian.architecture.remote.common.repository.ServiceRepository;

public class WorkerActor  extends UntypedActor{

	private ServiceRepository sr;
	public WorkerActor(ServiceRepository sr)
	{
		this.sr=sr;
	}
	@Override
	public void onReceive(Object message) throws Exception {
		// TODO Auto-generated method stub
		if(message instanceof RemoteRequest)
		{
			RemoteRequest request=(RemoteRequest)message;
			RemoteResponse response =doBusiness(request);
			if(response!=null)
			{
				getSender().tell(response,getSelf());
			}
		}
	}
	
	private RemoteResponse doBusiness(RemoteRequest request)
	{
		RemoteResponse response=null;
		RemoteMethod method=null;
		try {
			method = this.sr.getMethod(request.getServiceName(), 
					request.getMethodName(), request.getParamClassName());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//create failresponse
			if(request.getCallType() == Constants.CALLTYPE_REPLY){
				response = ResponseFactory.createFailResponse(e.getMessage());
			}
		
		}
		if(method != null){
			Method method_ = method.getMethod();
			Object returnObj = null;
			try {
				//timeout control 
				returnObj = method_.invoke(method.getService(), request.getParameters());
			}catch (InvocationTargetException e) {
				
				Throwable e2 = e.getTargetException();
				if(e2 != null){
					//logger.error(e2.getMessage(),e2);
				}
				if(request.getCallType() == Constants.CALLTYPE_REPLY){
					return ResponseFactory.createServiceExceptionResponse(e2);
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				//logger.error(e1.getMessage(),e1);
				if(request.getCallType() == Constants.CALLTYPE_REPLY){
					response = doFailResponse(request,e1);
				}
			}
			if(request.getCallType() == Constants.CALLTYPE_REPLY){
				response = ResponseFactory.createSuccessResponse(returnObj);
			}
		}
		return response;
	}
	private RemoteResponse doFailResponse(RemoteRequest request,Exception e){
		//logger.error(e.getMessage(),e);
		if(request.getCallType() == Constants.CALLTYPE_REPLY){
			return ResponseFactory.createFailResponse(e.getClass().getName()+":::"+e.getMessage());
		}
		return null;
	}
}
