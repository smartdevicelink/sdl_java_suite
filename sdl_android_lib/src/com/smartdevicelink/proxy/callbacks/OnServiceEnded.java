package com.smartdevicelink.proxy.callbacks;

import com.smartdevicelink.protocol.enums.SessionType;

public class OnServiceEnded extends InternalProxyMessage {
	private SessionType sessionType;

	public OnServiceEnded() {
		super(InternalProxyMessage.OnServiceEnded);
	}

	public OnServiceEnded(SessionType sessionType) {
		super(InternalProxyMessage.OnServiceEnded);
		this.sessionType = sessionType;
	}

	public SessionType getSessionType() {
		return this.sessionType;
	}

}
