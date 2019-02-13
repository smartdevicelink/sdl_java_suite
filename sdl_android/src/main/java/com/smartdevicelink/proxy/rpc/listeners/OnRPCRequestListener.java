package com.smartdevicelink.proxy.rpc.listeners;

import com.smartdevicelink.proxy.RPCRequest;

public abstract class OnRPCRequestListener {

	public abstract void onRequestReceived(RPCRequest request);

}
