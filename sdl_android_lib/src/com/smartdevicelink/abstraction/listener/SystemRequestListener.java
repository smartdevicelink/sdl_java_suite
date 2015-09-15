package com.smartdevicelink.abstraction.listener;

import com.smartdevicelink.proxy.rpc.OnSystemRequest;

public interface SystemRequestListener extends NotificationListener, RPCListener{
	
	public void handleSystemRequest(OnSystemRequest onSystemRequest);
	
}
