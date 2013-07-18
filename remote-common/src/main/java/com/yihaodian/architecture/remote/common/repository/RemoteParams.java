package com.yihaodian.architecture.remote.common.repository;

public class RemoteParams {

	private String[] paramNames;
	private int hashCode = 0;

	public RemoteParams(String[] paramNames)
	{
		this.paramNames=paramNames;
		StringBuffer sb = new StringBuffer();
		for(String paramName:paramNames){
			sb.append(paramName).append("@");
		}
		this.hashCode = sb.toString().hashCode();
	}
	
	public int getLength(){
		return this.paramNames.length;
	}
	
	public String[] getParamNames(){
		return this.paramNames;
	}
	
	public int hashCode(){
		return this.hashCode;
	}
	
	public boolean equals(Object obj){
		return this.hashCode == obj.hashCode();
	}
	
}
