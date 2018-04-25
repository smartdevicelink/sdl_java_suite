package com.smartdevicelink.marshal;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.trace.*;
import com.smartdevicelink.trace.enums.InterfaceActivityDirection;
import com.smartdevicelink.util.DebugTool;

/*
 * Responsible for marshalling and unmarshing between RPC Objects and byte streams that are sent
 * over transmission
 */

public class JsonRPCMarshaller {
	
	private static final String SDL_LIB_PRIVATE_KEY = "42baba60-eb57-11df-98cf-0800200c9a66";
	
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

    @SuppressWarnings("unchecked")
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
