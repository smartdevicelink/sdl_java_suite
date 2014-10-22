/**
 * 
 */
package com.smartdevicelink.proxy;

import java.util.Hashtable;

public class RPCRequest extends RPCMessage {

	public RPCRequest(String functionName) {
		super(functionName, "request");
		messageType = RPCStruct.request;
	}

	public RPCRequest(Hashtable hash) {
		super(hash);
	}

	public Integer getCorrelationID() {
		return (Integer)function.get(RPCStruct.correlationID);
	}
	
	public void setCorrelationID(Integer correlationID) {
		if (correlationID != null) {
            function.put(RPCStruct.correlationID, correlationID );
        } else if (parameters.contains(RPCStruct.correlationID)) {
        	function.remove(RPCStruct.correlationID);
        }
	}
}
