package com.smartdevicelink.transport;

import com.smartdevicelink.protocol.SdlPacket;

public interface ITransportListener {
	// Called to indicate and deliver a packet received from transport
	void onTransportPacketReceived(SdlPacket packet);

	// Called to indicate that transport connection was established
	void onTransportConnected();

	// Called to indicate that transport was disconnected (by either side)
	void onTransportDisconnected(String info);

	// Called to indicate that some error occurred on the transport
	void onTransportError(String info, Exception e);
} // end-interface