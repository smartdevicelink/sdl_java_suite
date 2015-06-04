package com.smartdevicelink.proxy.callbacks;

public class InternalProxyMessage {
	private String functionName;
	public static final String ON_PROXY_ERROR = "OnProxyError";
	public static final String ON_PROXY_OPENED = "OnProxyOpened";
	public static final String ON_PROXY_CLOSED = "OnProxyClosed";
	
	public InternalProxyMessage(String functionName) {
		//this(functionName, null, null);
		this.functionName = functionName;
	}
	
	public String getFunctionName() {
		return functionName;
	}
}