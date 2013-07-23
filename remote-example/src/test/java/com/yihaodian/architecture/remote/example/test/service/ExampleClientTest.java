package com.yihaodian.architecture.remote.example.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yihaodian.architecture.remote.example.service.DataCreator;
import com.yihaodian.architecture.remote.example.service.ExampleService;
import com.yihaodian.architecture.remote.example.service.ParameterVal;

public class ExampleClientTest extends AbstractServiceTest {

	@Autowired
	public ExampleService exampleClient;

	@Test
	public void TestExampleService() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			exampleClient.testService();
		}

		Thread.sleep(100000);
	}

	@Test
	public void TestExampleOtherService() throws InterruptedException {
		exampleClient.testOtherService();
		Thread.sleep(1000);
	}

	@Test
	public void TestDataSize() throws Exception {
		// AtomicInteger count=new AtomicInteger(0);
		for (int i = 0; i < 50000; i++) {
			exampleClient.testDataService(DataCreator.createObject(2));
			System.out.println(i);
		}
		Long curr = System.currentTimeMillis();
		for (int i = 0; i < 50000; i++) {
			String a = exampleClient.testDataService(DataCreator.createObject(2));
			System.out.println(i);
		}
		System.out.println(System.currentTimeMillis() - curr);
		Thread.sleep(1000000);
	}

	@Test
	public void TestExampleService3() {
		for (int i = 0; i < 100; i++) {
			String name = "helloworld";
			ParameterVal parameter = new ParameterVal("start");
			exampleClient.testService(name, parameter);
			// System.out.println(exampleClient.testService(name,
			// parameter).getValue());
		}
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}