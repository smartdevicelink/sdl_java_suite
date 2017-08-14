package com.smartdevicelink.proxy.interfaces;

public interface OnSystemCapabilityListener {
	void onCapabilityRetrieved(Object capability);
	void onError(String info);
}
