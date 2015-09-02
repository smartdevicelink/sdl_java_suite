package com.smartdevicelink.abstraction.listener;

import com.smartdevicelink.proxy.rpc.OnHMIStatus;

public interface HMINotificationListener {

	public void onHMIStatus(OnHMIStatus status);
	
}
