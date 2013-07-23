package com.yihaodian.architecture.remote.common.balancer;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.yihaodian.architecture.remote.common.Constants;


public abstract class AbstractBalancer implements LoadBalancer<String> {
	protected volatile Circle<Integer, String> pathCircle = new Circle<Integer, String>();
	protected Lock lock = new ReentrantLock();
	protected Random random = new Random();
	protected AtomicInteger position = new AtomicInteger(random.nextInt(Constants.INTEGER_BARRIER));
	
	public String select() {
		if (pathCircle == null || pathCircle.size() == 0) {
			return null;
		} else if (pathCircle.size() == 1) {
			String path = pathCircle.firstVlue();
			return path;
		} else {
			return doSelect();
		}
	}
	protected abstract String doSelect();
	
	protected String getPathFromCircle(int code) {
		int size = pathCircle.size();
		String path = null;
		if (size > 0) {
			int tmp = code;
			while (size > 0) {
				tmp = pathCircle.lowerKey(tmp);
				path = pathCircle.get(tmp);
				if (path != null ) {
					break;
				} else {
					path = null;
				}
				size--;
			}
		}
		return path;
	}
}
