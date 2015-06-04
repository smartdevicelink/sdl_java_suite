/**
 * 
 */
package com.smartdevicelink.proxy;

import java.util.Hashtable;

public class RpcRequest extends RpcMessage {

	public RpcRequest(String functionName) {
		super(functionName, RpcMessage.KEY_REQUEST);
		messageType = RpcMessage.KEY_REQUEST;
	}

	public RpcRequest(Hashtable<String, Object> hash) {
		super(hash);
	}

	public Integer getCorrelationId() {
		return (Integer)function.get(RpcMessage.KEY_CORRELATION_ID);
	}
	
	public void setCorrelationId(Integer correlationId) {
		if (correlationId != null) {
            function.put(RpcMessage.KEY_CORRELATION_ID, correlationId );
        } else {
        	function.remove(RpcMessage.KEY_CORRELATION_ID);
        }
	}
}
