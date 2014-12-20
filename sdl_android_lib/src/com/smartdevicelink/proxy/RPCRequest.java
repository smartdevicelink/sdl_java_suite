/**
 * 
 */
package com.smartdevicelink.proxy;

import org.json.JSONObject;

/**
 * 
 *
 * @author Mike Burke
 *
 */
public abstract class RPCRequest extends RPCMessage {

	public RPCRequest(String functionName) {
		super(functionName, RPCMessage.KEY_REQUEST);
		messageType = RPCMessage.KEY_REQUEST;
	}
    
    public RPCRequest(JSONObject json){
        super(KEY_REQUEST, json);
    }
    
    public static JSONObject getParameters(JSONObject json){
        return RPCMessage.getParameters(KEY_REQUEST, json);
    }
}
