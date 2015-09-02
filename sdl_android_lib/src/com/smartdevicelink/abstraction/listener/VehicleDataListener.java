package com.smartdevicelink.abstraction.listener;

import com.smartdevicelink.proxy.rpc.OnVehicleData;

public interface VehicleDataListener extends NotificationListener {

	public void handleVehicleData(OnVehicleData data);
	
}
