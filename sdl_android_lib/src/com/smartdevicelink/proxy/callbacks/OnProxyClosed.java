package com.smartdevicelink.proxy.callbacks;

import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;

public class OnProxyClosed extends InternalProxyMessage {
	
	private String _info;
	private Exception _e;
	private SdlDisconnectedReason _reason;
	
	public OnProxyClosed() {
		super(InternalProxyMessage.OnProxyClosed);
	}
	
	public OnProxyClosed(String info, Exception e, SdlDisconnectedReason reason) {
		super(InternalProxyMessage.OnProxyClosed);
		this._info = info;
		this._e = e;
		this._reason = reason;
	}

	public String getInfo() {
		return _info;
	}

	public SdlDisconnectedReason getReason() {
		return _reason;
	}	
	
	public Exception getException() {
		return _e;
	}
}