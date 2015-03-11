package com.smartdevicelink.streaming;

import com.smartdevicelink.protocol.ProtocolMessage;

public interface IStreamListener {
	void sendStreamPacket(ProtocolMessage pm);
}