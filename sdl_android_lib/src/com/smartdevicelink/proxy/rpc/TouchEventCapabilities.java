package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

public class TouchEventCapabilities extends RPCStruct {
    public static final String pressAvailable = "pressAvailable";
    public static final String multiTouchAvailable = "multiTouchAvailable";
    public static final String doublePressAvailable = "doublePressAvailable";
    public TouchEventCapabilities() {}
    
    public TouchEventCapabilities(Hashtable hash) {
        super(hash);
    }
    
    public void setPressAvailable(Boolean pressAvailable) {
        if (pressAvailable != null) {
            store.put(TouchEventCapabilities.pressAvailable, pressAvailable);
        } else {
        	store.remove(TouchEventCapabilities.pressAvailable);
        }
    }
    
    public Boolean getPressAvailable() {
        return (Boolean) store.get(TouchEventCapabilities.pressAvailable);
    }
    
    public void setMultiTouchAvailable(Boolean multiTouchAvailable) {
        if (multiTouchAvailable != null) {
            store.put(TouchEventCapabilities.multiTouchAvailable, multiTouchAvailable);
        } else {
        	store.remove(TouchEventCapabilities.multiTouchAvailable);
        }
    }
    
    public Boolean getMultiTouchAvailable() {
        return (Boolean) store.get(TouchEventCapabilities.multiTouchAvailable);
    }
    
    public void setDoublePressAvailable(Boolean doublePressAvailable) {
        if (doublePressAvailable != null) {
            store.put(TouchEventCapabilities.doublePressAvailable, doublePressAvailable);
        } else {
        	store.remove(TouchEventCapabilities.doublePressAvailable);
        }
    }
    
    public Boolean getDoublePressAvailable() {
        return (Boolean) store.get(TouchEventCapabilities.doublePressAvailable);
    }
}
