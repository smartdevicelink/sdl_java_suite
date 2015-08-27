package com.smartdevicelink.old.abstraction.listeners;

import com.smartdevicelink.proxy.rpc.StreamRPCResponse;

public interface StreamRPCResponseListener {
	public void onStreamRPCResponse(StreamRPCResponse response);
}
