package com.smartdevicelink.old.abstraction.listeners;

import com.smartdevicelink.old.abstraction.listeners.NotificationListener;
import com.smartdevicelink.proxy.rpc.OnVehicleData;

public interface VehicleDataListener extends NotificationListener {

	public void handleVehicleData(OnVehicleData data);
	
}
