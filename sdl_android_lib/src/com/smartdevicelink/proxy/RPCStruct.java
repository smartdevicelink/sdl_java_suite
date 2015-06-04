package com.smartdevicelink.proxy;

import java.util.Hashtable;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshall.JsonRpcMarshaller;

public class RpcStruct {
    public static final String KEY_BULK_DATA = "bulkData";

	private byte[] bulkData = null;

	protected Hashtable<String, Object> store = null;
	
	public RpcStruct() {
		store = new Hashtable<String, Object>();
	}
	
	protected RpcStruct(RpcStruct rpcs) {
		this.store = rpcs.store;
	}
	
	public RpcStruct(Hashtable<String, Object> hashtable) {
		store = hashtable;
		//store = (Hashtable<String, Object>) ObjectCopier.copy(hashtable);
	}
	
	public void deserializeJSON(JSONObject jsonObject) throws JSONException {
		store = JsonRpcMarshaller.deserializeJsonObject(jsonObject);
	}
	
	// deserializeJSONObject method moved to JsonRPCMarshaller for consistency
	// Keep reference here for backwards compatibility
	@Deprecated
	public static Hashtable<String, Object> deserializeJSONObject(JSONObject jsonObject) 
			throws JSONException {
		return JsonRpcMarshaller.deserializeJsonObject(jsonObject);
	}
	
	public JSONObject serializeJson() throws JSONException {
		return JsonRpcMarshaller.serializeHashtable(store);
	}
	
	@SuppressWarnings("unchecked")
    public JSONObject serializeJson(byte version) throws JSONException {
		if (version > 1) {
			String messageType = getMessageTypeName(store.keySet());
			Hashtable<String, Object> function = (Hashtable<String, Object>) store.get(messageType);
			Hashtable<String, Object> parameters = (Hashtable<String, Object>) function.get(RpcMessage.KEY_PARAMETERS);
			return JsonRpcMarshaller.serializeHashtable(parameters);
		} else return JsonRpcMarshaller.serializeHashtable(store);
	}

	public byte[] getBulkData() {
		return this.bulkData;
	}

	public void setBulkData(byte[] bulkData) {
		if (bulkData != null) {
			this.bulkData = new byte[bulkData.length];
			System.arraycopy(bulkData, 0, bulkData, 0, bulkData.length);
		}
		else{
		    this.bulkData = null;
		}
	}
	
	protected String getMessageTypeName(Set<String> keys) {
	      for (String key : keys) {
	          if (key == null) {
	              continue;
	          }
	          if (key.equals(RpcMessage.KEY_REQUEST) || key.equals(RpcMessage.KEY_RESPONSE) ||
	                  key.equals(RpcMessage.KEY_NOTIFICATION)) {
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
