package com.smartdevicelink.protocol;

import com.smartdevicelink.transport.utl.TransportRecord;

public interface ISecondaryTransportListener {
	void onConnectionSuccess(TransportRecord transportRecord);
	void onConnectionFailure();
}
