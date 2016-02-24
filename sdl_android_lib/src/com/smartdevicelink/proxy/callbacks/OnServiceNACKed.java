package com.smartdevicelink.proxy.callbacks;

import com.smartdevicelink.protocol.enums.ServiceType;

public class OnServiceNACKed extends InternalProxyMessage {
	private ServiceType serviceType;

	public OnServiceNACKed() {
		super(InternalProxyMessage.OnServiceNACKed);
	}

	public OnServiceNACKed(ServiceType serviceType) {
		super(InternalProxyMessage.OnServiceNACKed);
		this.serviceType = serviceType;
	}

	public ServiceType getSessionType() {
		return this.serviceType;
	}

}
