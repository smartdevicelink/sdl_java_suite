package com.smartdevicelink.test;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCStruct;


public final class JsonUtils {
	
	private JsonUtils(){}
	
	public static JSONObject createJsonObject(byte[] data){
		JSONObject result = null;
		try {
			String jsonStr = new String(data);
			result = new JSONObject(jsonStr);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static JSONArray createJsonArrayOfJsonObjects(List<? extends RPCStruct> parameterList, int sdlVersion) throws JSONException{
		JSONArray result = new JSONArray();
		
		for(RPCStruct parameter : parameterList){
			//result.put(parameter.getJsonParameters(sdlVersion));
			result.put(parameter.serializeJSON());
		}
		
		return result;
	}
	
	public static JSONArray createJsonArrayOfJsonNames(List<? extends Enum<?>> parameterList, int sdlVersion) throws JSONException{
		JSONArray result = new JSONArray();
		
		for(Enum<?> name : parameterList){
			result.put(name);
		}
		
		return result;
	}
	
	public static <T> JSONArray createJsonArray(List<T> list) throws JSONException{
		JSONArray result = new JSONArray();
		
		for(T str : list){
			result.put(str);
		}
		
		return result;
	}
	
	// this method is basically to get around the annoying JSONException that is thrown when a key doesn't exist
	// in the JSON object.  this method returns null instead of throwing an exception.
	public static Object readObjectFromJsonObject(JSONObject json, String key){
		try {
			return json.get(key);
		} catch (JSONException e) {
			return null;
		}
	}
	
	public static Boolean readBooleanFromJsonObject(JSONObject json, String key){
		try {
			return json.getBoolean(key);
		} catch (JSONException e) {
			return null;
		}
	}
	
	public static Double readDoubleFromJsonObject(JSONObject json, String key){
		try {
			return json.getDouble(key);
		} catch (JSONException e) {
			return null;
		}
	}
	
	public static Integer readIntegerFromJsonObject(JSONObject json, String key){
		try {
			return json.getInt(key);
		} catch (JSONException e) {
			return null;
		}
	}

	public static JSONArray readJsonArrayFromJsonObject(JSONObject json, String key){
		try {
			return json.getJSONArray(key);
		} catch (JSONException e) {
			return null;
		}
	}
	
	public static List<String> readStringListFromJsonObject(JSONObject json, String key){
		JSONArray jsonArray = readJsonArrayFromJsonObject(json, key);
		
		if(jsonArray != null){
			int len = jsonArray.length();
			List<String> result = new ArrayList<String>(len);
			for(int i=0; i<len; i++){
				try {
					String str = jsonArray.getString(i);
					result.add(str);
				} catch (JSONException e) {}
			}
			return result;
		}
		
		return null;
	}
	
	public static List<Integer> readIntegerListFromJsonObject(JSONObject json, String key){
		JSONArray jsonArray = readJsonArrayFromJsonObject(json, key);
		
		if(jsonArray != null){
			int len = jsonArray.length();
			List<Integer> result = new ArrayList<Integer>(len);
			for(int i=0; i<len; i++){
				try {
					Integer str = jsonArray.getInt(i);
					result.add(str);
				} catch (JSONException e) {}
			}
			return result;
		}
		
		return null;
	}
	
	public static List<Double> readDoubleListFromJsonObject(JSONObject json, String key){
		JSONArray jsonArray = readJsonArrayFromJsonObject(json, key);
		
		if(jsonArray != null){
			int len = jsonArray.length();
			List<Double> result = new ArrayList<Double>(len);
			for(int i=0; i<len; i++){
				try {
					Double str = jsonArray.getDouble(i);
					result.add(str);
				} catch (JSONException e) {}
			}
			return result;
		}
		
		return null;
	}
	
	public static List<Long> readLongListFromJsonObject(JSONObject json, String key){
		JSONArray jsonArray = readJsonArrayFromJsonObject(json, key);
		
		if(jsonArray != null){
			int len = jsonArray.length();
			List<Long> result = new ArrayList<Long>(len);
			for(int i=0; i<len; i++){
				try {
					Long str = jsonArray.getLong(i);
					result.add(str);
				} catch (JSONException e) {}
			}
			return result;
		}
		
		return null;
	}

	public static List<JSONObject> readJsonObjectListFromJsonObject(JSONObject json, String key){
		JSONArray jsonArray = readJsonArrayFromJsonObject(json, key);
		
		if(jsonArray != null){
			int len = jsonArray.length();
			List<JSONObject> result = new ArrayList<JSONObject>(len);
			for(int i=0; i<len; i++){
				try{
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					result.add(jsonObject);
				} catch(JSONException e) {}
			}
			return result;
		}
		
		return null;
	}
	
	public static JSONObject readJsonObjectFromJsonObject(JSONObject json, String key){
		try {
			return json.getJSONObject(key);
		} catch (JSONException e) {
			return null;
		}
	}
	
	public static Long readLongFromJsonObject(JSONObject json, String key){
		try {
			return json.getLong(key);
		} catch (JSONException e) {
			return null;
		}
	}
	
	public static String readStringFromJsonObject(JSONObject json, String key){
		try {
			return json.getString(key);
		} catch (JSONException e) {
			return null;
		}
	}
}