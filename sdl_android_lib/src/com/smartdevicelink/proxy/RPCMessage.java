package com.smartdevicelink.proxy;

import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;
import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonParameters;

/**
 * The BaseRpcMessage class represents all common variables and methods that will be shared across
 * all RPC messages in the system.
 *
 * @author Mike Burke
 *
 */
public abstract class RPCMessage implements JsonParameters {
    public static final String KEY_REQUEST = "request";
    public static final String KEY_RESPONSE = "response";
    public static final String KEY_NOTIFICATION = "notification";
    public static final String KEY_FUNCTION_NAME = "name";
    public static final String KEY_PARAMETERS = "parameters";
    public static final String KEY_CORRELATION_ID = "correlationID";

    protected static int sdlVersion = 0; // TODO: appropriate default for sdlVersion code?
    
    protected String messageType;
    protected SdlCommand commandType;
    protected Integer correlationId;
    
    public RPCMessage(String functionName) {
        this(functionName, KEY_REQUEST);
    }
    
    public RPCMessage(String functionName, String messageType) {
        this.messageType = messageType;
        this.commandType = SdlCommand.valueForJsonName(functionName, sdlVersion);
    }
    
    public RPCMessage(String type, JSONObject parameters){
        this.messageType = type;
        JSONObject typeJson = JsonUtils.readJsonObjectFromJsonObject(parameters, type);
        if(typeJson != null){
            this.correlationId = JsonUtils.readIntegerFromJsonObject(parameters, KEY_CORRELATION_ID);
            String msgType = JsonUtils.readStringFromJsonObject(parameters, KEY_FUNCTION_NAME);
            this.commandType = SdlCommand.valueForJsonName(msgType, sdlVersion);
        }
    }
	
	protected RPCMessage(RPCMessage rpcm) {
		this.messageType = rpcm.messageType;
		this.commandType = rpcm.commandType;
		this.correlationId = rpcm.correlationId;
	}
	
	public String getFunctionName() {
		return this.commandType.getJsonName(sdlVersion);
	}
	
	protected void setFunctionName(String functionName) {
		this.commandType = SdlCommand.valueForJsonName(functionName, sdlVersion);
	}

	public String getMessageType() {
		return messageType;
	}
	
	public Integer getCorrelationID(){
	    return this.correlationId;
	}
	
	public void setCorrelationID(Integer corrId){
	    this.correlationId = corrId;
	}
	
	public JSONObject toJson(int sdlVersion){
	    JSONObject store = new JSONObject();
	    
	    JSONObject function = new JSONObject();
	    
	    switch(sdlVersion){
	    default:
	        JsonUtils.addToJsonObject(store, this.messageType, function);
	        JsonUtils.addToJsonObject(function, KEY_CORRELATION_ID, this.correlationId);
	        JsonUtils.addToJsonObject(function, KEY_FUNCTION_NAME, this.commandType.getJsonName(sdlVersion));
	        JsonUtils.addToJsonObject(function, KEY_PARAMETERS, getJsonParameters(sdlVersion));
	        break;
	    }
        
        return store;
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
	
	public static void setSdlVersion(int inSdlVersion){
	    sdlVersion = inSdlVersion;
	}
	
}
