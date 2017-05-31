package com.smartdevicelink.proxy;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

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
	
	public void setParameters(String functionName, Object value) {
		if (value != null) {
			parameters.put(functionName, value);
		} else {
			parameters.remove(functionName);
		}
	}

	public Object getParameters(String key) {
		return parameters.get(key);
	}

	public Object getParameterArray(Class tClass, String key){
		Object obj = parameters.get(key);
		if (obj instanceof Hashtable) {
			try {
				Constructor constructor = tClass.getConstructor(Hashtable.class);
				return constructor.newInstance((Hashtable<String, Object>) obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (obj instanceof List<?>) {
			List<?> list = (List<?>)parameters.get(key);
			if (list != null && list.size() > 0) {
				Object item = list.get(0);
				if (tClass.isInstance(item)) {
					return list;
				} else if (item instanceof Hashtable) {
					List<Object> newList = new ArrayList<Object>();
					for (Object hashObj : list) {
						try {
							Constructor constructor = tClass.getConstructor(Hashtable.class);
							newList.add(constructor.newInstance((Hashtable<String, Object>)hashObj));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					return newList;
				}
			}
		}
		return null;
	}

	// Common Object Getters

	public String getString(String key) {
		return (String) getParameters(key);
	}

	public Integer getInteger(String key) {
		return (Integer) getParameters(key);
	}

	public Double getDouble(String key) {
		return (Double) getParameters(key);
	}

	public Boolean getBoolean(String key) { return (Boolean) getParameters(key); }
}
