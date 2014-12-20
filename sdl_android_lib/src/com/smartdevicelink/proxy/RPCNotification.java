/**
 * 
 */
package com.smartdevicelink.proxy;

import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;

public abstract class RPCNotification extends RPCMessage {

	public RPCNotification(String functionName) {
		super(functionName, KEY_NOTIFICATION);
	}

	public RPCNotification(RPCMessage rpcMsg) {
		super(rpcMsg);
	}
    
    public static JSONObject getParameters(String type, JSONObject fullJson){
        JSONObject function = JsonUtils.readJsonObjectFromJsonObject(fullJson, type);
        if(function == null){
            return fullJson;
        }

        return JsonUtils.readJsonObjectFromJsonObject(function, KEY_PARAMETERS);
    }
    
    public static SdlCommand getCommandType(JSONObject json){
        String name = JsonUtils.readStringFromJsonObject(json, KEY_FUNCTION_NAME);
        return SdlCommand.valueForJsonName(name, sdlVersion);
    }
}