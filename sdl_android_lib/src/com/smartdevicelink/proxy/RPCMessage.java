package com.smartdevicelink.proxy;

import java.util.Hashtable;


public class RPCMessage extends RPCStruct  {

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
		function = new Hashtable();
		this.messageType = messageType;
		store.put(messageType, function);
		parameters = new Hashtable();
		function.put(RPCStruct.parameters, parameters);
		function.put(RPCStruct.function_name, functionName);
	}

	public RPCMessage(Hashtable hash) {
        store = hash;
        messageType = getMessageTypeName(hash.keySet());
        function = (Hashtable) hash.get(messageType);
        parameters = (Hashtable) function.get(RPCStruct.parameters);
        if (hasKey(hash.keySet(), RPCStruct.bulkData)) {
            setBulkData((byte[]) hash.get(RPCStruct.bulkData));
        }
	}

	protected String messageType;
	protected Hashtable parameters;
	protected Hashtable function;
	
	public String getFunctionName() {
		return (String)function.get(RPCStruct.function_name);
	}
	
	protected void setFunctionName(String functionName) {
		function.put(RPCStruct.function_name, functionName);
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
