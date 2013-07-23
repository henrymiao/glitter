package com.yihaodian.architecture.remote.common.test;

import org.junit.Test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class CreateActorPay {
	public ActorSystem system =ActorSystem.create();
	@Test
	public void testCreate()
	{
		Long current=System.nanoTime();
		int count=1000000;
		System.out.println("start");
		for(int i=0;i<count;i++)
		{
			ActorRef actor= system.actorOf(Props.create(TestActor.class),"service"+i);
			system.stop(actor);
		}
		System.out.println((System.nanoTime()-current)/count);
	}
}
