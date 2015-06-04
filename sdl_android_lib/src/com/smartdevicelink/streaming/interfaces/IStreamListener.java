package com.smartdevicelink.streaming.interfaces;

import com.smartdevicelink.protocol.ProtocolMessage;

public interface IStreamListener {
	void sendStreamPacket(ProtocolMessage pm);
}