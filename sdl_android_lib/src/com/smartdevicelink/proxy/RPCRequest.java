/**
 * 
 */
package com.smartdevicelink.proxy;

import java.util.Hashtable;

public class RPCRequest extends RPCMessage {

	public RPCRequest(String functionName) {
		super(functionName, RPCMessage.KEY_REQUEST);
		messageType = RPCMessage.KEY_REQUEST;
	}

	public RPCRequest(Hashtable<String, Object> hash) {
		super(hash);
	}

	public Integer getCorrelationID() {
		return (Integer)function.get(RPCMessage.KEY_CORRELATION_ID);
	}
	
	public void setCorrelationID(Integer correlationID) {
		if (correlationID != null) {
            function.put(RPCMessage.KEY_CORRELATION_ID, correlationID );
        } else {
        	function.remove(RPCMessage.KEY_CORRELATION_ID);
        }
	}
}
