package com.smartdevicelink.proxy;

import java.util.Hashtable;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;

public class RPCStruct {
	public static final String request = "request";
	public static final String response = "response";
	public static final String notification = "notification";
	public static final String function_name = "name";
	public static final String parameters = "parameters";
	public static final String bulkData = "bulkData";
	public static final String correlationID = "correlationID";

	private byte[] _bulkData = null;

	protected Hashtable<String, Object> store = null;
	
	public RPCStruct() {
		store = new Hashtable<String, Object>();
	}
	
	protected RPCStruct(RPCStruct rpcs) {
		this.store = rpcs.store;
	}
	
	public RPCStruct(Hashtable<String, Object> hashtable) {
		store = hashtable;
		//store = (Hashtable<String, Object>) ObjectCopier.copy(hashtable);
	}
	
	public void deserializeJSON(JSONObject jsonObject) throws JSONException {
		store = JsonRPCMarshaller.deserializeJSONObject(jsonObject);
	}
	
	// deserializeJSONObject method moved to JsonRPCMarshaller for consistency
	// Keep reference here for backwards compatibility
	@Deprecated
	public static Hashtable<String, Object> deserializeJSONObject(JSONObject jsonObject) 
			throws JSONException {
		return JsonRPCMarshaller.deserializeJSONObject(jsonObject);
	}
	
	public JSONObject serializeJSON() throws JSONException {
		return JsonRPCMarshaller.serializeHashtable(store);
	}
	
	public JSONObject serializeJSON(byte version) throws JSONException {
		if (version > 1) {
			String messageType = getMessageTypeName(store.keySet());
			Hashtable function = (Hashtable) store.get(messageType);
			Hashtable parameters = (Hashtable) function.get(RPCStruct.parameters);
			return JsonRPCMarshaller.serializeHashtable(parameters);
		} else return JsonRPCMarshaller.serializeHashtable(store);
	}

	public byte[] getBulkData() {
		return this._bulkData;
	}

	public void setBulkData(byte[] bulkData) {
		if (bulkData != null) {
			this._bulkData = new byte[bulkData.length];
			System.arraycopy(bulkData, 0, _bulkData, 0, bulkData.length);
			//this._bulkData = bulkData;
		}
	}
	
	protected String getMessageTypeName(Set<String> keys) {
	      for (String key : keys) {
	          if (key == null) {
	              continue;
	          }
	          if (key.equals(RPCStruct.request) || key.equals(RPCStruct.response) ||
	                  key.equals(RPCStruct.notification)) {
	              return key;
	          }
	      }
	      return null;
	}
	  
	protected boolean hasKey(Set<String> keys, String keyName) {
	      for (String key : keys) {
	    	  if (key == null) {
	    		  continue;
	          }
	    	  if (key.equals(keyName)) {
	    		  return true;
	    	  }
	      }
	      return false;
	}
}
