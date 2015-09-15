package com.smartdevicelink.abstraction.listener;

import com.smartdevicelink.proxy.rpc.OnStreamRPC;


public interface PutFileStreamListener extends RPCListener, NotificationListener{
	
	public void handleOnStreamRPC(OnStreamRPC notification);
	
}
