package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCNotification;

public class OnHashChange extends RPCNotification {
	public static final String hashID = "hashID";

    public OnHashChange() {
        super("OnHashChange");
    }

    public OnHashChange(Hashtable hash) {
        super(hash);
    }
    
    public String getHashID() {
        return (String) parameters.get(OnHashChange.hashID);
    }
   
    public void setHashID(String hashID) {
        if (hashID != null) {
            parameters.put(OnHashChange.hashID, hashID);
        } else {
        	parameters.remove(OnHashChange.hashID);
        }
    }   
    
}
