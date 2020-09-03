/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.proxy;

import com.smartdevicelink.protocol.enums.FunctionID;

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
		this(cloneStore(rpcm));
	}
	
	protected RPCMessage(RPCStruct rpcs) {
		this("", "");
		this.parameters = cloneStore(rpcs);
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
        if (function != null) {
        	parameters = (Hashtable<String, Object>) function.get(KEY_PARAMETERS);
		}
        if (hasKey(hash.keySet(), RPCStruct.KEY_BULK_DATA)) {
            setBulkData((byte[]) hash.get(RPCStruct.KEY_BULK_DATA));
        }
        if (hasKey(hash.keySet(), RPCStruct.KEY_PROTECTED)) {
        	setPayloadProtected((Boolean) hash.get(RPCStruct.KEY_PROTECTED));
        }
	}

	public FunctionID getFunctionID(){
		if(function.containsKey(KEY_FUNCTION_NAME)){
			return FunctionID.getEnumForString((String)function.get(KEY_FUNCTION_NAME));
		}
		return null;
	}

	protected String messageType;
	protected Hashtable<String, Object> parameters;
	protected Hashtable<String, Object> function;

	public String getFunctionName() {
		return (String)function.get(KEY_FUNCTION_NAME);
	}
	
	protected RPCMessage setFunctionName(String functionName) {
		function.put(KEY_FUNCTION_NAME, functionName);
		return this;
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
	
	public RPCMessage setParameters(String key, Object value) {
		if (value != null) {
			parameters.put(key, value);
		} else {
			parameters.remove(key);
		}
		return this;
	}

	public Object getParameters(String key) {
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
	public Float getFloat(String key) {
		return (Float) parameters.get(key);
	}

	@Override
	public Double getDouble(String key) {
		return (Double) parameters.get(key);
	}

	@Override
	public Boolean getBoolean(String key) { return (Boolean) parameters.get(key); }

	@Override
	public Long getLong(String key){
		Object result = parameters.get(key);
		if (result instanceof Integer) {
			return ((Integer) result).longValue();
		}else if(result instanceof Long){
			return (Long) result;
		}
		return null;
	}
}
