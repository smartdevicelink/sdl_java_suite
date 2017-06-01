package com.smartdevicelink.proxy;

import com.smartdevicelink.marshal.JsonRPCMarshaller;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

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

	// Generalized Getters and Setters

	public void setValue(String key, Object value){
		if (value != null) {
			store.put(key, value);
		} else {
			store.remove(key);
		}
	}

	public Object getValue(String key) {
		return store.get(key);
	}

	public Object getObject(Class tClass, String key) {
		Object obj = store.get(key);
		return formatObject(tClass, obj);
	}

	// Helper methods

	protected Object formatObject(Class tClass, Object obj){
		if(obj == null){
			return null;
		} else if (tClass.isInstance(obj)) {
			return obj;
		} else if (obj instanceof String) {
			return getValueForString(tClass, (String) obj);
		} else if (obj instanceof Hashtable) {
			try {
				Constructor constructor = tClass.getConstructor(Hashtable.class);
				return constructor.newInstance((Hashtable<String, Object>) obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (obj instanceof List<?>) {
			List<?> list = (List<?>) obj;
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
				} else if (item instanceof String){
					List<Object> newList = new ArrayList<Object>();
					for (Object hashObj : list) {
						Object toAdd = getValueForString(tClass, (String) hashObj);
						if (toAdd != null) {
							newList.add(toAdd);
						}
					}
					return newList;
				}
			}
		}
		return null;
	}

	protected Object getValueForString(Class tClass, String s){
		Method valueForString = null;
		try {
			valueForString = tClass.getDeclaredMethod("valueForString", String.class);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		if(valueForString != null){
			try {
				Object value = valueForString.invoke(null, (String) s);
				return value;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	// Common Object Getters

	public String getString(String key) {
		return (String) store.get(key);
	}

	public Integer getInteger(String key) {
		return (Integer) store.get(key);
	}

	public Double getDouble(String key) {
		return (Double) store.get(key);
	}

	public Boolean getBoolean(String key) { return (Boolean) store.get(key); }

	public Long getLong(String key){ return (Long) store.get(key); }
}
