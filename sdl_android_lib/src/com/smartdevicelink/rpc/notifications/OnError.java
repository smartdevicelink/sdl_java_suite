package com.smartdevicelink.rpc.notifications;

import com.smartdevicelink.proxy.callbacks.InternalProxyMessage;

public class OnError extends InternalProxyMessage {

	private String info;
	private Exception e;
	
	public OnError() {
		super(InternalProxyMessage.ON_PROXY_ERROR);
	}

	public OnError(String info, Exception e) {
		super(InternalProxyMessage.ON_PROXY_ERROR);
		this.info = info;
		this.e = e;
	}
	
	public String getInfo() {
		return info;
	}
	
	public Exception getException() {
		return e;
	}
}