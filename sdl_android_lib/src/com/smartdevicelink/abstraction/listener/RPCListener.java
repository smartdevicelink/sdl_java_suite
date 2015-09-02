package com.smartdevicelink.abstraction.listener;

import com.smartdevicelink.proxy.RPCResponse;

public interface RPCListener {

	public void handleResponse(RPCResponse response);
	
}
