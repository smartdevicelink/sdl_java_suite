package com.smartdevicelink.proxy.rpc.listeners;

import com.smartdevicelink.proxy.RPCNotification;

public abstract class OnRPCNotificationListener {
	
	public abstract void onNotified(RPCNotification notification);
}
