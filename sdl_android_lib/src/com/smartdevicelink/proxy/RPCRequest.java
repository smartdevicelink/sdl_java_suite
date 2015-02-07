/**
 * 
 */
package com.smartdevicelink.proxy;

import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.enums.SdlCommand;

/**
 * An RPC request is a type of message that, when sent has an expectation of
 * receiving a response.
 *
 * @author Mike Burke
 *
 */
public abstract class RPCRequest extends RPCMessage {

	public RPCRequest(String functionName) {
		super(functionName, RPCMessage.KEY_REQUEST);
		messageType = RPCMessage.KEY_REQUEST;
	}
    
    public RPCRequest(SdlCommand commandType, JSONObject json){
        super(KEY_REQUEST, commandType, json);
    }
    
    public static JSONObject getParameters(JSONObject json){
        return RPCMessage.getParameters(KEY_REQUEST, json);
    }
}
