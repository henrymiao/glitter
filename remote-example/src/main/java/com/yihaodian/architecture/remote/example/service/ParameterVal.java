package com.yihaodian.architecture.remote.example.service;

import java.io.Serializable;

public class ParameterVal implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 578994066507927425L;
	private String value;
	public ParameterVal(){}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	public ParameterVal(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}

}