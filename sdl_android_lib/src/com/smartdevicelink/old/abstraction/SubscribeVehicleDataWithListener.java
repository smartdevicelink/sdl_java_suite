package com.smartdevicelink.old.abstraction;

import com.smartdevicelink.old.abstraction.listeners.VehicleDataListener;
import com.smartdevicelink.proxy.rpc.SubscribeVehicleData;

public class SubscribeVehicleDataWithListener extends SubscribeVehicleData {
	private VehicleDataListener mListener;

	public void setListener(VehicleDataListener listener) {
		mListener = listener;
	}
	
	public VehicleDataListener getListener(){
		return mListener;
	}

}
