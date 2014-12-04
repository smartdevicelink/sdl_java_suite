package com.smartdevicelink.proxy;

import java.util.Hashtable;

import com.smartdevicelink.proxy.rpc.enums.SdlCommand;

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

    protected String messageType;
    protected SdlCommand commandType;
    protected Integer correlationId;
    
    /**
     * Creates a base RPC message with a message type and a message name.  The name
     * represents the RPC command to be sent.  For example, the message name for an
     * AddCommand message would be "AddCommand".
     * 
     * <b>NOTE:</b> This constructor assumes a correlation ID of -1.  The correlation ID
     * can be updated through the setCorrelationId(int corrId) method.
     * 
     * @param messageType The type of RPC message this object is
     * @param messageName The name of the RPC command this message represents
     */
    public RPCMessage(String messageType, String messageName) {
        this(messageType, messageName, null);
    }
    
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
		function = new Hashtable<String, Object>();
		this.messageType = messageType;
		store.put(messageType, function);
		parameters = new Hashtable<String, Object>();
		function.put(KEY_PARAMETERS, parameters);
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
	}
	
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
