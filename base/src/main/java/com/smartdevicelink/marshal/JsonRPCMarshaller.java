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
package com.smartdevicelink.marshal;

import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.trace.SdlTrace;
import com.smartdevicelink.trace.enums.InterfaceActivityDirection;
import com.smartdevicelink.util.DebugTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/*
 * Responsible for marshalling and unmarshing between RPC Objects and byte streams that are sent
 * over transmission
 */

public class JsonRPCMarshaller {
	
	private static final String SDL_LIB_PRIVATE_KEY = "42baba60-eb57-11df-98cf-0800200c9a66";

	/**
	 * @param msg RPC message to be marshaled
	 * @param version protocol version
	 * @return byte array of the marshalled message
	 */
	public static byte[] marshall(RPCMessage msg, byte version) {
		byte[] jsonBytes = null;
		try {
			JSONObject jsonObject = msg.serializeJSON(version);
			jsonBytes = jsonObject.toString().getBytes();
			
			SdlTrace.logMarshallingEvent(InterfaceActivityDirection.Transmit, jsonBytes, SDL_LIB_PRIVATE_KEY);
		} catch (JSONException e) {
			DebugTool.logError("Failed to encode messages to JSON.", e);
		}
		return jsonBytes;
	}
	
	public static Hashtable<String, Object> unmarshall(byte[] message) {
		SdlTrace.logMarshallingEvent(InterfaceActivityDirection.Receive, message, SDL_LIB_PRIVATE_KEY);
		Hashtable<String, Object> ret = null;
		try {
			String jsonString = new String(message);
			JSONObject jsonObject = new JSONObject(jsonString);
			ret = deserializeJSONObject(jsonObject);
		} catch (JSONException e) {
			DebugTool.logError("Failed to parse JSON", e);
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
    public static Hashtable<String, Object> deserializeJSONObject(JSONObject jsonObject) 
			throws JSONException {
		Hashtable<String, Object> ret = new Hashtable<String, Object>();
		Iterator<String> it = jsonObject.keys();
		String key = null;
		while (it.hasNext()) {
			key = it.next();
			Object value = jsonObject.get(key);
			if (value instanceof JSONObject) {
				ret.put(key, deserializeJSONObject((JSONObject)value));
			} else if (value instanceof JSONArray) {
				JSONArray arrayValue = (JSONArray) value;
				List<Object> putList = new ArrayList<Object>(arrayValue.length());
				for (int i = 0; i < arrayValue.length(); i++) {
					Object anObject = arrayValue.get(i); 
					if (anObject instanceof JSONObject) {
						Hashtable<String, Object> deserializedObject = deserializeJSONObject((JSONObject)anObject);
						putList.add(deserializedObject);
					} else {
						putList.add(anObject);
					}
				}
				ret.put(key, putList);
			} else {
				ret.put(key, value);
			}
		}
		return ret;
	}

	@SuppressWarnings("unchecked" )
	private static JSONArray serializeList(List<?> list) throws JSONException{
		JSONArray toPut = new JSONArray();
		Iterator<Object> valueIterator = (Iterator<Object>) list.iterator();
		while(valueIterator.hasNext()){
			Object anObject = valueIterator.next();
			if (anObject instanceof RPCStruct) {
				RPCStruct toSerialize = (RPCStruct) anObject;
				toPut.put(toSerialize.serializeJSON());
			} else if(anObject instanceof Hashtable){
				Hashtable<String, Object> toSerialize = (Hashtable<String, Object>)anObject;
				toPut.put(serializeHashtable(toSerialize));
			} else {
				toPut.put(anObject);
			}
		}
		return toPut;
	}

	@SuppressWarnings({"unchecked" })
    public static JSONObject serializeHashtable(Hashtable<String, Object> hash) throws JSONException{
		JSONObject obj = new JSONObject();
		Iterator<String> hashKeyIterator = hash.keySet().iterator();
		while (hashKeyIterator.hasNext()){
			String key = (String) hashKeyIterator.next();
			Object value = hash.get(key);
			if (value instanceof RPCStruct) {
				obj.put(key, ((RPCStruct) value).serializeJSON());
			} else if (value instanceof List<?>) {
				obj.put(key, serializeList((List<?>) value));
			} else if (value instanceof Hashtable) {
				obj.put(key, serializeHashtable((Hashtable<String, Object>)value));
			} else {
				obj.put(key, value);
			}
		}
		return obj;
	}
}
