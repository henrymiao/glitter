package com.yihaodian.architecture.remote.common;

public class RemoteHost {
	
	private String connect;
	private String host;
	private int port;
	private int weight;
	
	public RemoteHost(String host, int port, int weight)
	{
		this.host = host;
		this.port = port;
		this.connect = host + ":" + port;
		this.weight = weight;
	}
	public RemoteHost(String connect, int weight)
	{
		int colonIdx = connect.indexOf(":");
		this.connect = connect;
		this.host = connect.substring(0, colonIdx);
		this.port = Integer.parseInt(connect.substring(colonIdx + 1));
		this.weight = weight;
	}
	public String getConnect() {
		return connect;
	}
	public String getHost() {
		return host;
	}
	public int getPort() {
		return port;
	}
	public int getWeight() {
		return weight;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + port;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RemoteHost other = (RemoteHost) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (port != other.port)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "RemoteHost [host=" + host + ", port=" + port + ", weight="
				+ weight + "]";
	}
}
