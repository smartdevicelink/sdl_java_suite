package com.smartdevicelink.old.abstraction.listeners;

import com.smartdevicelink.old.abstraction.listeners.NotificationListener;
import com.smartdevicelink.proxy.rpc.OnDriverDistraction;
import com.smartdevicelink.proxy.rpc.enums.DriverDistractionState;


public interface DriverDistractionListener extends NotificationListener {
	
	public void onDriverDistraction(OnDriverDistraction driverDistraction);

}