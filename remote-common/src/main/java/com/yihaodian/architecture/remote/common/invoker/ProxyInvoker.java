package com.yihaodian.architecture.remote.common.invoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import com.yihaodian.architecture.remote.common.Constants;
import com.yihaodian.architecture.remote.common.RemoteMetaData;
import com.yihaodian.architecture.remote.common.RemoteRequest;
import com.yihaodian.architecture.remote.common.RemoteResponse;
import com.yihaodian.architecture.remote.common.exception.ClientException;

class ProxyInvoker implements InvocationHandler {

	private RemoteMetaData metaData;
	private Set<String> ingoreMethods = new HashSet<String>();
	public ProxyInvoker(RemoteMetaData metaData)
	{
		this.metaData=metaData;
		Method[] objectMethodArray = Object.class.getMethods();
		for(Method method : objectMethodArray){
			this.ingoreMethods.add(method.getName());
		}
	}
	public Object invoke(Object proxy, Method method, Object[] args)throws Throwable {
		// TODO Auto-generated method stub
		if(method.getName().equals("toString")){
			return proxy.getClass().getName();
		}else if(method.getName().equals("equals")){
			if(args == null || args.length != 1 || args[0].getClass() != proxy.getClass()){
				return false;
			}
			return method.equals(args[0].getClass().getDeclaredMethod("equals", new Class[]{Object.class}));
		}else if(method.getName().equals("hashCode")){
			return method.hashCode();
		}
		RemoteRequest request=new DefaultRequest(this.metaData.getServiceName(), method.getName(), args, Constants.MESSAGE_TYPE_SERVICE,this.metaData.getTimeout(), method.getParameterTypes());
		
		if(Constants.CALL_SYNC.equalsIgnoreCase(this.metaData.getCallMode())){
			//同步返回
			RemoteResponse response=null;
			response=ActorInvoker.getInstance().invokeSync(request);
			if(response==null)
				return null;
			if(response.getMessageType() == Constants.MESSAGE_TYPE_SERVICE){
				return response.getReturn();
			}else if(response.getMessageType() == Constants.MESSAGE_TYPE_EXCEPTION){
				//log.error(response.getCause());
				throw new ClientException(response.getCause());
			}else if(response.getMessageType() == Constants.MESSAGE_TYPE_SERVICE_EXCEPTION){
				throw (Throwable)response.getReturn();
			}
			throw new ClientException("no result to call");
		}else if(Constants.CALL_FUTURE.equals(this.metaData.getCallMode())){
			//
		}else if(Constants.CALL_ONEWAY.equals(this.metaData.getCallMode())){
			//执行不返回
			ActorInvoker.getInstance().invokeOneWay(request);
			return getReturn(method.getReturnType());
		}
		throw new ClientException("callmethod configure is error:"+this.metaData.getCallMode());
	}
	private Object getReturn(Class returnType){
		if(returnType == byte.class){
			return (byte)0;
		}else if(returnType == short.class){
			return (short)0;
		}else if(returnType == int.class){
			return 0;
		}else if(returnType == boolean.class){
			return false;
		}else if(returnType == long.class){
			return 0l;
		}else if(returnType == float.class){
			return 0.0f;
		}else if(returnType == double.class){
			return 0.0d;
		}else{
			return null;
		}
	}

}
