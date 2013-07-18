package com.yihaodian.architecture.remote.example.service;

public interface ExampleService {
	public ResultObject testService(String name,ParameterVal parameter);
	public void testService();
	public void testOtherService();
	public void testDataService(String name);
}
