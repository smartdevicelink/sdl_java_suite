package com.smartdevicelink.proxy.callbacks;

import com.smartdevicelink.protocol.enums.SessionType;

public class OnServiceNACKed extends InternalProxyMessage {
	private SessionType sessionType;

	public OnServiceNACKed() {
		super(InternalProxyMessage.OnServiceNACKed);
	}

	public OnServiceNACKed(SessionType sessionType) {
		super(InternalProxyMessage.OnServiceNACKed);
		this.sessionType = sessionType;
	}

	public SessionType getSessionType() {
		return this.sessionType;
	}

}
