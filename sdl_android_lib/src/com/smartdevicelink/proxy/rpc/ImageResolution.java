package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

public class ImageResolution extends RPCStruct {
	public static final String resolutionWidth = "resolutionWidth";
	public static final String resolutionHeight = "resolutionHeight";
    public ImageResolution() {}
    
    public ImageResolution(Hashtable hash) {
        super(hash);
    }
    
    public void setResolutionWidth(Integer resolutionWidth) {
        if (resolutionWidth != null) {
            store.put(ImageResolution.resolutionWidth, resolutionWidth);
        } else {
        	store.remove(ImageResolution.resolutionWidth);
        }
    }
    
    public Integer getResolutionWidth() {
        return (Integer) store.get(ImageResolution.resolutionWidth);
    }
    
    public void setResolutionHeight(Integer resolutionHeight) {
        if (resolutionHeight != null) {
            store.put(ImageResolution.resolutionHeight, resolutionHeight);
        } else {
        	store.remove(ImageResolution.resolutionHeight);
        }
    }
    
    public Integer getResolutionHeight() {
        return (Integer) store.get(ImageResolution.resolutionHeight);
    }
}
