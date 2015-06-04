package com.smartdevicelink.proxy;

import java.util.Hashtable;


public class RpcMessage extends RpcStruct  {
    public static final String KEY_REQUEST = "request";
    public static final String KEY_RESPONSE = "response";
    public static final String KEY_NOTIFICATION = "notification";
    public static final String KEY_FUNCTION_NAME = "name";
    public static final String KEY_PARAMETERS = "parameters";
    public static final String KEY_CORRELATION_ID = "correlationID";

	public RpcMessage(String functionName) {
		this(functionName, "request");
	}
	
	protected RpcMessage(RpcMessage rpcm) {
		this(rpcm.store);
	}
	
	protected RpcMessage(RpcStruct rpcs) {
		this("", "");
		this.parameters = rpcs.store;
	}
	
	public RpcMessage(String functionName, String messageType) {
		function = new Hashtable<String, Object>();
		this.messageType = messageType;
		store.put(messageType, function);
		parameters = new Hashtable<String, Object>();
		function.put(KEY_PARAMETERS, parameters);
		function.put(KEY_FUNCTION_NAME, functionName);
	}

	@SuppressWarnings("unchecked")
    public RpcMessage(Hashtable<String, Object> hash) {
        store = hash;
        messageType = getMessageTypeName(hash.keySet());
        function = (Hashtable<String, Object>) hash.get(messageType);
        parameters = (Hashtable<String, Object>) function.get(KEY_PARAMETERS);
        if (hasKey(hash.keySet(), RpcStruct.KEY_BULK_DATA)) {
            setBulkData((byte[]) hash.get(RpcStruct.KEY_BULK_DATA));
        }
	}

	protected String messageType;
	protected Hashtable<String, Object> parameters;
	protected Hashtable<String, Object> function;
	
	public String getFunctionName() {
		return (String)function.get(KEY_FUNCTION_NAME);
	}
	
	protected void setFunctionName(String functionName) {
		function.put(KEY_FUNCTION_NAME, functionName);
	}

	public String getMessageType() {
		return messageType;
	}
	
	public void setParameters(String functionName, Object value) {
		if (value != null) {
			parameters.put(functionName, value);
		} else {
			parameters.remove(functionName);
		}
	}
	
	public Object getParameters(String functionName) {
		return parameters.get(functionName);
	}
}
