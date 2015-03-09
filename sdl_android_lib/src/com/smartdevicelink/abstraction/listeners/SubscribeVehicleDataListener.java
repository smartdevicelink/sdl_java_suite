package com.smartdevicelink.abstraction.listeners;

import java.util.Hashtable;

import com.smartdevicelink.proxy.rpc.SubscribeVehicleData;

public class SubscribeVehicleDataListener extends SubscribeVehicleData {
	
	private VehicleDataListener mListener;
    public SubscribeVehicleDataListener() { }

    public SubscribeVehicleDataListener(Hashtable hash) {
        super(hash);
    }
	public VehicleDataListener getListener() {
		return mListener;
	}

	public void setListener(VehicleDataListener mListener) {
		this.mListener = mListener;
	}
}
