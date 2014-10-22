package com.smartdevicelink.proxy.callbacks;

public class OnError extends InternalProxyMessage {

	private String _info;
	private Exception _e;
	
	public OnError() {
		super(InternalProxyMessage.OnProxyError);
	}

	public OnError(String info, Exception e) {
		super(InternalProxyMessage.OnProxyError);
		this._info = info;
		this._e = e;
	}
	
	public String getInfo() {
		return _info;
	}
	
	public Exception getException() {
		return _e;
	}
}