package com.yihaodian.architecture.remote.common;

public interface Constants {
	public static final String VALUE_NULL = "NULL";
	
	public final static int MESSAGE_TYPE_SERVICE = 1;
	public final static int MESSAGE_TYPE_EXCEPTION = 2;
	public final static int MESSAGE_TYPE_SERVICE_EXCEPTION = 3;
	public final static int MESSAGE_TYPE_TIMEOUT = 4;
	
	public final static int CALLTYPE_REPLY = 1;
	public final static int CALLTYPE_NOREPLY = 2;
	
	public static final String CALL_SYNC = "sync";
	public static final String CALL_ONEWAY = "oneway";
	public static final String CALL_FUTURE = "future";
	
	public static final String CONNECTOR="-";
	public static final String CHILD_CONNECTOR="$";
	public static final String AkkaSystemName="YHDServiceSystem";
	public static final String ClientAkkaSystemName="YHDClientSystem";
	
	public static final int INTEGER_BARRIER = Integer.MAX_VALUE / 2;
	
	public static final String BALANCER_NAME_ROUNDROBIN = "RoundRobin";
	
	public static final int DEFAULT_CHILDCOUNT=10000;
}
