package com.smartdevicelink.proxy.callbacks;

import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;

public class OnProxyClosed extends InternalProxyMessage {
	
	private String info;
	private Exception e;
	private SdlDisconnectedReason reason;
	
	public OnProxyClosed() {
		super(InternalProxyMessage.ON_PROXY_CLOSED);
	}
	
	public OnProxyClosed(String info, Exception e, SdlDisconnectedReason reason) {
		super(InternalProxyMessage.ON_PROXY_CLOSED);
		this.info = info;
		this.e = e;
		this.reason = reason;
	}

	public String getInfo() {
		return info;
	}

	public SdlDisconnectedReason getReason() {
		return reason;
	}	
	
	public Exception getException() {
		return e;
	}
}