package com.smartdevicelink.transport;

import com.smartdevicelink.transport.enums.TransportType;

import android.content.ComponentName;
import android.content.Context;

public class MultiplexTransportConfig extends BaseTransportConfig{

	Context context;
	String appId;
	ComponentName service;

	

	
	public MultiplexTransportConfig(Context context, String appId) {
		this.context = context;
		this.appId = appId;
	}


	/**
	 * Overridden abstract method which returns specific type of this transport configuration.
	 * 
	 * @return Constant value TransportType.MULTIPLEX. 
	 * 
	 * @see TransportType
	 */
	public TransportType getTransportType() {
		return TransportType.MULTIPLEX;
	}
	
	public Context getContext(){
		return this.context;
	}


	public ComponentName getService() {
		return service;
	}


	public void setService(ComponentName service) {
		this.service = service;
	}
	
	
	

}
