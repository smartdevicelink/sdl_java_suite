/**
 * 
 */
package com.smartdevicelink.proxy;

import java.util.Hashtable;

public class RpcNotification extends RpcMessage {

	public RpcNotification(String functionName) {
		super(functionName, "notification");
	}

	public RpcNotification(Hashtable<String, Object> hash) {
		super(hash);
	}

	public RpcNotification(RpcMessage rpcMsg) {
		super(rpcMsg);
	}
} // end-class