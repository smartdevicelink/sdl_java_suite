package com.smartdevicelink.proxy;

import java.util.Hashtable;

public class RPCMessage extends RPCStruct  {
    public static final String KEY_REQUEST = "request";
    public static final String KEY_RESPONSE = "response";
    public static final String KEY_NOTIFICATION = "notification";
    public static final String KEY_FUNCTION_NAME = "name";
    public static final String KEY_PARAMETERS = "parameters";
    public static final String KEY_CORRELATION_ID = "correlationID";

	public RPCMessage(String functionName) {
		this(functionName, "request");
	}
	
	protected RPCMessage(RPCMessage rpcm) {
		this(rpcm.store);
	}
	
	protected RPCMessage(RPCStruct rpcs) {
		this("", "");
		this.parameters = rpcs.store;
	}
	
	public RPCMessage(String functionName, String messageType) {
		function   = new Hashtable<String, Object>();
		parameters = new Hashtable<String, Object>();
		
		this.messageType = messageType;
		function.put(KEY_PARAMETERS, parameters);
		
		if (messageType != null)
			store.put(messageType, function);
		if (functionName != null)
			function.put(KEY_FUNCTION_NAME, functionName);
	}

	@SuppressWarnings("unchecked")
    public RPCMessage(Hashtable<String, Object> hash) {
        store = hash;
        messageType = getMessageTypeName(hash.keySet());
        function = (Hashtable<String, Object>) hash.get(messageType);
        parameters = (Hashtable<String, Object>) function.get(KEY_PARAMETERS);
        if (hasKey(hash.keySet(), RPCStruct.KEY_BULK_DATA)) {
            setBulkData((byte[]) hash.get(RPCStruct.KEY_BULK_DATA));
        }
        if (hasKey(hash.keySet(), RPCStruct.KEY_PROTECTED)) {
        	setPayloadProtected((Boolean) hash.get(RPCStruct.KEY_PROTECTED));
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
		if (messageType.equals(KEY_REQUEST) || 
			messageType.equals(KEY_RESPONSE) ||
            messageType.equals(KEY_NOTIFICATION)) {
			return messageType;
		}
		return null;
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
