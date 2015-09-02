package com.smartdevicelink.abstraction.rpc;

import com.smartdevicelink.abstraction.listener.VehicleDataListener;
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
