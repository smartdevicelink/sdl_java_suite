package com.smartdevicelink.old.abstraction.listeners;

import com.smartdevicelink.proxy.rpc.OnHMIStatus;

public interface HMINotificationListener {

	public void onHMIStatus(OnHMIStatus status);
	
}
