package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;

public class HMICapabilities extends RPCStruct{
    public static final String KEY_NAVIGATION = "navigation";
    public static final String KEY_PHONE_CALL = "phoneCall";
	
	 public HMICapabilities() { }
	  
	 public HMICapabilities(Hashtable<String, Object> hash) {
		 super(hash);
	 }
	 
	 public boolean isNavigationAvailable(){
		 Object available = store.get(KEY_NAVIGATION);
		 if(available == null){
			 return false;
		 }
		 return (Boolean)available;
	 }
	 
	 public void setNavigationAvilable(Boolean available){
		 if (available) {
	            store.put(KEY_NAVIGATION, available);
	        } else {
	        	store.remove(KEY_NAVIGATION);
	        }
	 }
	 
	 public boolean isPhoneCallAvailable(){
		 Object available = store.get(KEY_PHONE_CALL);
		 if(available == null){
			 return false;
		 }
		 return (Boolean)available;
	 }
	 
	 public void setPhoneCallAvilable(Boolean available){
		 if (available) {
	            store.put(KEY_PHONE_CALL, available);
	        } else {
	        	store.remove(KEY_PHONE_CALL);
	        }
	 }

}
