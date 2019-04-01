package com.smartdevicelink.proxy.rpc.listeners;

import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCRequest;

public abstract class OnRPCRequestListener extends OnRPCListener {

	public final void onReceived(final RPCMessage message){
		if (message instanceof RPCRequest){
			onRequest((RPCRequest)message);
		}
	}

	/**
	 * @param request - The incoming RPC Request
	 */
	public abstract void onRequest(final RPCRequest request);

}
