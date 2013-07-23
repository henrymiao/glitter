package com.yihaodian.architecture.remote.example.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BootStrap {
	
	private BootStrap()
	{
		
	}
	public static void main(String[] args) {
			new ClassPathXmlApplicationContext(
	            new String[] { "applicationContext.xml"});
	}		
}
