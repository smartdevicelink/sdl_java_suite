package com.smartdevicelink.proxy;

import com.smartdevicelink.streaming.StreamRPCPacketizer;

public class RPCStreamController {
	private StreamRPCPacketizer rpcPacketizer;
	private Integer iCorrelationID;

	public RPCStreamController(StreamRPCPacketizer rpcPacketizer, Integer iCorrelationID)
	{
		this.rpcPacketizer = rpcPacketizer;
		this.iCorrelationID = iCorrelationID;
	}	
	
	public Integer getCorrelationID()
	{
		return iCorrelationID;
	}
	
	public void pause()
	{
		rpcPacketizer.pause();
	}
	public void resume()
	{
		rpcPacketizer.resume();
	}
	public void stop()
	{
		rpcPacketizer.onPutFileStreamError(null, "Stop Putfile Stream Requested");
	}	
}
