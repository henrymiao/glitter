package com.yihaodian.architecture.remote.common.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.yihaodian.architecture.remote.common.exception.RemoteException;

public class ServiceMethods {
	/**
	 * 根据方法名和参数个数Map方法集合
	 */
	private Map<String,Map<Integer,List<RemoteMethod>>> methods 
		= new ConcurrentHashMap<String,Map<Integer,List<RemoteMethod>>>();
	
	private Map<String,Map<RemoteParams,RemoteMethod>> bestMacthMethod 
		= new ConcurrentHashMap<String,Map<RemoteParams,RemoteMethod>>();
	
	private RemoteMethod currentMethod;
	
	private int methodSize = 0;
	
	private Object service;
	
	private String serviceName;
	
	public ServiceMethods(String serviceName,Object service){
		this.serviceName = serviceName;
		this.service = service;
	}
	
	void addMethod(String methodName,RemoteMethod method){
		if(this.currentMethod == null){
			this.currentMethod = method;
		}
		Map<Integer,List<RemoteMethod>> methodMap = this.methods.get(methodName);
		if(methodMap == null){
			methodMap = new HashMap<Integer,List<RemoteMethod>>();
			this.methods.put(methodName, methodMap);
		}
		List<RemoteMethod> methodList = methodMap.get(method.getParameterSize());
		if(methodList == null){
			methodList = new ArrayList<RemoteMethod>();
			methodMap.put(method.getParameterSize(), methodList);
		}
		methodList.add(method);
		methodSize++;
	}
	public RemoteMethod getMethod(String methodName,RemoteParams paramNames) throws RemoteException{
		if(methodSize == 1){
			return this.currentMethod;
		}else{
			RemoteMethod method = getBestMatchMethodForCache(methodName,paramNames);
			if(method == null){
				synchronized(this){
					method = getBestMatchMethodForCache(methodName,paramNames);
					if(method == null){
						method = getBestMatchMethod(methodName,paramNames);
						this.bestMacthMethod.get(methodName).put(paramNames, method);
					}
				}
			}
			return method;
		}
	}
	
	private RemoteMethod getBestMatchMethodForCache(String methodName,RemoteParams paramNames){
		Map<RemoteParams,RemoteMethod> paramMethodMap = this.bestMacthMethod.get(methodName);
		if(paramMethodMap == null){
			paramMethodMap = new HashMap<RemoteParams,RemoteMethod>();
			this.bestMacthMethod.put(methodName, paramMethodMap);
		}
		return paramMethodMap.get(paramNames);
	}
	
	private RemoteMethod getBestMatchMethod(String methodName,RemoteParams paramNames) throws RemoteException{
		
		Map<Integer,List<RemoteMethod>> methodMap = this.methods.get(methodName);
		if(methodMap == null){
			throw new RemoteException("Service  serviceName:"+this.service+" is not this method for name:"+methodName);
		}
		List<RemoteMethod> methodList = methodMap.get(paramNames.getLength());
		if(methodList == null || methodList.size() == 0){
			throw new RemoteException("Service  serviceName:"+this.service+
					" is not this method:"+methodName+" for "+paramNames.getLength()+" parameters");
		}
		if(paramNames.getLength() == 0){
			return methodList.get(0);
		}
		int matchingValue = -1;
		RemoteMethod bestMethod = null;
		
		for(RemoteMethod rm : methodList){
			int mv = rm.matching(paramNames.getParamNames());
			if(mv > matchingValue){
				matchingValue = mv;
				bestMethod = rm;
			}
		}
		if(matchingValue < 0){
			throw new RemoteException("Service  serviceName:"+this.service
					+" is not this method:"+methodName+" for parameter class types");
		}
		return bestMethod;
	}
}
