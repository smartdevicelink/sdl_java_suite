package com.smartdevicelink.abstraction.listeners;

import com.smartdevicelink.proxy.RPCResponse;

public interface RPCListener {

	public void handleResponse(RPCResponse response);
	
}
