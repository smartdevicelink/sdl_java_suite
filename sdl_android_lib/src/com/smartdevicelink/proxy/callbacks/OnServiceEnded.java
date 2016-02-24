package com.smartdevicelink.proxy.callbacks;

import com.smartdevicelink.protocol.enums.ServiceType;

public class OnServiceEnded extends InternalProxyMessage {
	private ServiceType serviceType;

	public OnServiceEnded() {
		super(InternalProxyMessage.OnServiceEnded);
	}

	public OnServiceEnded(ServiceType serviceType) {
		super(InternalProxyMessage.OnServiceEnded);
		this.serviceType = serviceType;
	}

	public ServiceType getSessionType() {
		return this.serviceType;
	}

}
