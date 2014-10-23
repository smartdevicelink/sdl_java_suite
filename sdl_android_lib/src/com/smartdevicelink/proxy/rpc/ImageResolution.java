package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

public class ImageResolution extends RPCStruct {
	public static final String KEY_RESOLUTION_WIDTH = "resolutionWidth";
	public static final String KEY_RESOLUTION_HEIGHT = "resolutionHeight";
	
    public ImageResolution() {}
    
    public ImageResolution(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    public void setResolutionWidth(Integer resolutionWidth) {
        if (resolutionWidth != null) {
            store.put(KEY_RESOLUTION_WIDTH, resolutionWidth);
        } else {
        	store.remove(KEY_RESOLUTION_WIDTH);
        }
    }
    
    public Integer getResolutionWidth() {
        return (Integer) store.get(KEY_RESOLUTION_WIDTH);
    }
    
    public void setResolutionHeight(Integer resolutionHeight) {
        if (resolutionHeight != null) {
            store.put(KEY_RESOLUTION_HEIGHT, resolutionHeight);
        } else {
        	store.remove(KEY_RESOLUTION_HEIGHT);
        }
    }
    
    public Integer getResolutionHeight() {
        return (Integer) store.get(KEY_RESOLUTION_HEIGHT);
    }
}
