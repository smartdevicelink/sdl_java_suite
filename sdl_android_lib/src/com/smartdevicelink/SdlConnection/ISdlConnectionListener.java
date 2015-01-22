package com.smartdevicelink.SdlConnection;

import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.interfaces.IPutFileResponseListener;
import com.smartdevicelink.proxy.rpc.OnStreamRPC;
import com.smartdevicelink.proxy.rpc.StreamRPCResponse;

public interface ISdlConnectionListener {
	public void onTransportDisconnected(String info);
	
	public void onTransportError(String info, Exception e);
	
	public void onProtocolMessageReceived(ProtocolMessage msg);
	
	public void onProtocolSessionNACKed(SessionType sessionType,
			byte sessionID, byte version, String correlationID);	
	
	public void onProtocolSessionStarted(SessionType sessionType,
			byte sessionID, byte version, String correlationID);
	
	public void onProtocolSessionEnded(SessionType sessionType,
			byte sessionID, String correlationID);
	
	public void onProtocolError(String info, Exception e);
	
	public void onHeartbeatTimedOut(byte sessionID);
	
	public void onOnStreamRPC(IPutFileResponseListener putFileListener, OnStreamRPC rpcNote);
	
	public void onStreamRPCResponse(IPutFileResponseListener putFileListener, StreamRPCResponse result);	
}
