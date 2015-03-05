package com.smartdevicelink.abstraction;

import com.smartdevicelink.abstraction.listeners.VehicleDataListener;
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
