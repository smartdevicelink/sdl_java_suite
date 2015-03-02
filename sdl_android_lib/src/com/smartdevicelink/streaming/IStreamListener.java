package com.smartdevicelink.streaming;

import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.interfaces.IPutFileResponseListener;
import com.smartdevicelink.proxy.rpc.OnStreamRPC;
import com.smartdevicelink.proxy.rpc.StreamRPCResponse;

public interface IStreamListener {
	void sendStreamPacket(ProtocolMessage pm);
}