package com.smartdevicelink.abstraction.listeners;

import com.smartdevicelink.proxy.rpc.enums.DriverDistractionState;


public interface DriverDistractionListener extends NotificationListener {
	
	public void onDriverDistraction(DriverDistractionState driverDistraction);

}