package com.smartdevicelink.transport;

import android.content.Context;

public class MultiplexTransportConfig extends BaseTransportConfig{

	Context context;
	String appId;
	

	
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
	
	

}
