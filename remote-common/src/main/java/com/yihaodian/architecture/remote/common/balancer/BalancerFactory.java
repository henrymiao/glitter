/**
 * 
 */
package com.yihaodian.architecture.remote.common.balancer;

import java.util.HashMap;
import java.util.Map;

import com.yihaodian.architecture.remote.common.Constants;
import com.yihaodian.architecture.remote.common.Util.SystemUtil;
import com.yihaodian.architecture.remote.common.exception.InvalidParamException;
import com.yihaodian.architecture.remote.common.exception.InvalidReturnValueException;
import com.yihaodian.architecture.remote.common.exception.RemoteException;


public class BalancerFactory {

	private static BalancerFactory factory = new BalancerFactory();

	private static Map<String, String> balancerContainer;

	private BalancerFactory() {
		super();
		balancerContainer = new HashMap<String, String>();
		balancerContainer.put(Constants.BALANCER_NAME_ROUNDROBIN, RRBalancer.class.getName());
	}

	public static BalancerFactory getInstance() {
		return factory;
	}

	public LoadBalancer<String> getBalancer(String name) throws RemoteException {
		if (SystemUtil.isBlankString(name))
			throw new InvalidParamException("Balancer name must not null");
		String clazzName = balancerContainer.get(name);
		if (clazzName != null) {
			try {
				Class clazz = Class.forName(clazzName);
				return (LoadBalancer<String>) clazz.newInstance();
			} catch (Throwable e) {
				throw new InvalidReturnValueException("Can't find " + clazzName + " balancer");
			}
		} else {
			throw new InvalidReturnValueException("Can't find " + name + " balancer");
		}
	}

}
