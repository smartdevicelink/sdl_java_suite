package com.smartdevicelink.abstraction.listeners;

import com.smartdevicelink.proxy.rpc.OnVehicleData;

public interface VehicleDataListener extends NotificationListener {

	public void handleVehicleData(OnVehicleData data);
	
}
