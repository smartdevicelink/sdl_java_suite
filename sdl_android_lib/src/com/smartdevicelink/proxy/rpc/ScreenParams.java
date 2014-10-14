package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.TouchEventCapabilities;
import com.smartdevicelink.util.DebugTool;

public class ScreenParams extends RPCStruct {
    public static final String resolution = "resolution";
    public static final String touchEventAvailable = "touchEventAvailable";

	public ScreenParams() { }
  
    public ScreenParams(Hashtable hash) {
        super(hash);
    }
    
    public ImageResolution getImageResolution() {
    	Object obj = store.get(ScreenParams.resolution);
        if (obj instanceof ImageResolution) {
            return (ImageResolution) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new ImageResolution((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + ScreenParams.resolution, e);
            }
        }
        return null;
    } 
    public void setImageResolution( ImageResolution resolution ) {
        if (resolution != null) {
            store.put(ScreenParams.resolution, resolution );
        }
        else {
    		store.remove(ScreenParams.resolution);
    	}
    }
    public TouchEventCapabilities getTouchEventAvailable() {
    	Object obj = store.get(ScreenParams.touchEventAvailable);
        if (obj instanceof TouchEventCapabilities) {
            return (TouchEventCapabilities) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new TouchEventCapabilities((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + ScreenParams.touchEventAvailable, e);
            }
        }
        return null;
    } 
    public void setTouchEventAvailable( TouchEventCapabilities touchEventAvailable ) {
        if (touchEventAvailable != null) {
            store.put(ScreenParams.touchEventAvailable, touchEventAvailable );
        }
        else {
    		store.remove(ScreenParams.touchEventAvailable);
    	}        
    }     
}
