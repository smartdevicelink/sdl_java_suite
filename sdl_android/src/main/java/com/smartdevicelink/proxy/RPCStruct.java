package com.smartdevicelink.proxy;

import java.util.Hashtable;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;

public class RPCStruct {
    public static final String KEY_BULK_DATA = "bulkData";
    public static final String KEY_PROTECTED = "protected";
    
	private byte[] _bulkData = null;
	private Boolean protectedPayload = false;

	protected Hashtable<String, Object> store = null;
	
	public boolean getStoreValue(String key) { // for unit testing
		return store.contains(key);
	}
	
	public Hashtable<String,Object> getStore () { // for unit testing
		return store;
	}
	
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
	
	@SuppressWarnings("unchecked")
    public JSONObject serializeJSON(byte version) throws JSONException {
		if (version > 1) {
			String messageType = getMessageTypeName(store.keySet());
			Hashtable<String, Object> function = (Hashtable<String, Object>) store.get(messageType);
			Hashtable<String, Object> parameters = (Hashtable<String, Object>) function.get(RPCMessage.KEY_PARAMETERS);
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
		}
		else{
		    this._bulkData = null;
		}
	}
	
	public void setPayloadProtected(Boolean bVal) {
		protectedPayload = bVal;
	}
	
	public Boolean isPayloadProtected() {
		return protectedPayload;
	}
	
	protected String getMessageTypeName(Set<String> keys) {
	      for (String key : keys) {
	          if (key == null) {
	              continue;
	          }
	          if (key.equals(RPCMessage.KEY_REQUEST) || key.equals(RPCMessage.KEY_RESPONSE) ||
	                  key.equals(RPCMessage.KEY_NOTIFICATION)) {
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
