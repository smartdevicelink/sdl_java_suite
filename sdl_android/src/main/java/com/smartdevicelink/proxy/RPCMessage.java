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

	// Generalized Getters and Setters
	
	public void setParameter(String key, Object value) {
		if (value != null) {
			parameters.put(key, value);
		} else {
			parameters.remove(key);
		}
	}

	public Object getParameter(String key) {
		return parameters.get(key);
	}

	@Override
	public Object getObject(Class tClass, String key) {
		Object obj = parameters.get(key);
		return formatObject(tClass, obj);
	}

	// Common Object Getters

	@Override
	public String getString(String key) {
		return (String) parameters.get(key);
	}

	@Override
	public Integer getInteger(String key) {
		return (Integer) parameters.get(key);
	}

	@Override
	public Double getDouble(String key) {
		return (Double) parameters.get(key);
	}

	@Override
	public Boolean getBoolean(String key) { return (Boolean) parameters.get(key); }

	@Override
	public Long getLong(String key){ return (Long) parameters.get(key); }
}
