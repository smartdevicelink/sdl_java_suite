package com.smartdevicelink.proxy;

import com.smartdevicelink.streaming.StreamRpcPacketizer;

public class RpcStreamController {
	private StreamRpcPacketizer rpcPacketizer;
	private Integer iCorrelationId;

	public RpcStreamController(StreamRpcPacketizer rpcPacketizer, Integer iCorrelationId)
	{
		this.rpcPacketizer = rpcPacketizer;
		this.iCorrelationId = iCorrelationId;
	}	
	
	public Integer getCorrelationId()
	{
		return iCorrelationId;
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
