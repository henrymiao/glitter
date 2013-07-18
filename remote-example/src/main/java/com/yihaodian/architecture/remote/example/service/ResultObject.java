package com.yihaodian.architecture.remote.example.service;

import java.io.Serializable;

public class ResultObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4939830223027450136L;
	private String value;
	
	public ResultObject()
	{}
	public ResultObject(String value)
	{
		this.value=value;
	}
	public String getValue(){
		return this.value;
	}
}
