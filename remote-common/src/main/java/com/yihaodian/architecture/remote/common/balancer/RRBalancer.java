package com.yihaodian.architecture.remote.common.balancer;

import java.util.Collection;

import com.yihaodian.architecture.remote.common.Constants;

public class RRBalancer extends AbstractBalancer {

	public void updateProfiles(Collection<String> pathSet) {
		// TODO Auto-generated method stub
		lock.lock();
		try
		{
			Circle<Integer, String> circle= new Circle<Integer,String>(); 
			int size = 0;
			for (String path : pathSet) {
				circle.put(size++, path);
			}
			pathCircle = circle;
			
		}finally
		{
			lock.unlock();
		}
	}

	@Override
	protected String doSelect() {
		// TODO Auto-generated method stub
		int key = position.getAndIncrement();
		int totalSize = pathCircle.size();
		int realPos = key % totalSize;
		if (key > Constants.INTEGER_BARRIER) {
			position.set(0);
		}
		return getPathFromCircle(realPos);
	}

}
