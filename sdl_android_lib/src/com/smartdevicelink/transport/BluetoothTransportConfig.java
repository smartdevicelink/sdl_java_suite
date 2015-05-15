package com.smartdevicelink.transport;

import android.content.Context;

/**
 * This is just a simple extension on the parent class to support developers
 * who already have the TransportConfig inplace
 * @author Joey Grover
 *
 */
public final class BluetoothTransportConfig extends MultiplexTransportConfig{

	public BluetoothTransportConfig(Context context, String appId) {
		super(context, appId);
	}

}
