package com.smartdevicelink.rpc.notifications;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcNotification;

public class OnHashChange extends RpcNotification {
	public static final String KEY_HASH_ID = "hashID";

    public OnHashChange() {
        super(FunctionId.ON_HASH_CHANGE.toString());
    }

    public OnHashChange(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    public String getHashID() {
        return (String) parameters.get(KEY_HASH_ID);
    }
   
    public void setHashID(String hashID) {
        if (hashID != null) {
            parameters.put(KEY_HASH_ID, hashID);
        } else {
        	parameters.remove(KEY_HASH_ID);
        }
    }   
    
}
