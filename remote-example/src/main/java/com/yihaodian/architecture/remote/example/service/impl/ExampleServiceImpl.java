package com.yihaodian.architecture.remote.example.service.impl;

import java.util.concurrent.atomic.AtomicInteger;

import com.yihaodian.architecture.remote.example.service.DataCreator;
import com.yihaodian.architecture.remote.example.service.ExampleService;
import com.yihaodian.architecture.remote.example.service.ParameterVal;
import com.yihaodian.architecture.remote.example.service.ResultObject;

public class ExampleServiceImpl implements ExampleService {

	private AtomicInteger count=new AtomicInteger(0);
	
	public ResultObject testService(String name, ParameterVal parameter) {
		// TODO Auto-generated method stub
		System.out.println(name+"*******");
		return new ResultObject("return");
	}

	public void testService() {
		// TODO Auto-generated method stub
		System.out.println("no return result");
	}

	public void testOtherService() {
		// TODO Auto-generated method stub
		System.out.println("no return other result");
	}

	public String testDataService(String name) {
		// TODO Auto-generated method stub
		System.out.println("recevied Data"+count.incrementAndGet());
		return DataCreator.createObject(1);
	}

}
