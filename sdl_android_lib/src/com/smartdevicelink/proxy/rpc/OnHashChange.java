package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.constants.Names;

public class OnHashChange extends RPCNotification {

    public OnHashChange() {
        super("OnHashChange");
    }

    public OnHashChange(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    public String getHashID() {
        return (String) parameters.get(Names.hashID);
    }
   
    public void setHashID(String hashID) {
        if (hashID != null) {
            parameters.put(Names.hashID, hashID);
        } else {
        	parameters.remove(Names.hashID);
        }
    }   
    
}
