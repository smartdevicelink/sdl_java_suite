/**
 * 
 */
package com.smartdevicelink.proxy;

import java.util.Hashtable;

public class RPCNotification extends RPCMessage {

	public RPCNotification(String functionName) {
		super(functionName, RPCMessage.KEY_NOTIFICATION);
	}

	public RPCNotification(Hashtable<String, Object> hash) {
		super(hash);
	}

	public RPCNotification(RPCMessage rpcMsg) {
		super(preprocessMsg(rpcMsg));
	}
	
	static RPCMessage preprocessMsg (RPCMessage rpcMsg) {
		if (rpcMsg.getMessageType() != RPCMessage.KEY_NOTIFICATION) {
			rpcMsg.messageType = RPCMessage.KEY_NOTIFICATION;
		}
		
		return rpcMsg;
	}
}