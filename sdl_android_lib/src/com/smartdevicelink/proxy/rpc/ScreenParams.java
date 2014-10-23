package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.TouchEventCapabilities;
import com.smartdevicelink.util.DebugTool;

public class ScreenParams extends RPCStruct {
    public static final String KEY_RESOLUTION = "resolution";
    public static final String KEY_TOUCH_EVENT_AVAILABLE = "touchEventAvailable";

	public ScreenParams() { }
  
    public ScreenParams(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    @SuppressWarnings("unchecked")
    public ImageResolution getImageResolution() {
    	Object obj = store.get(KEY_RESOLUTION);
        if (obj instanceof ImageResolution) {
            return (ImageResolution) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new ImageResolution((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_RESOLUTION, e);
            }
        }
        return null;
    } 
    public void setImageResolution( ImageResolution resolution ) {
        if (resolution != null) {
            store.put(KEY_RESOLUTION, resolution );
        }
        else {
    		store.remove(KEY_RESOLUTION);
    	}
    }
    @SuppressWarnings("unchecked")
    public TouchEventCapabilities getTouchEventAvailable() {
    	Object obj = store.get(KEY_TOUCH_EVENT_AVAILABLE);
        if (obj instanceof TouchEventCapabilities) {
            return (TouchEventCapabilities) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new TouchEventCapabilities((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_TOUCH_EVENT_AVAILABLE, e);
            }
        }
        return null;
    } 
    public void setTouchEventAvailable( TouchEventCapabilities touchEventAvailable ) {
        if (touchEventAvailable != null) {
            store.put(KEY_TOUCH_EVENT_AVAILABLE, touchEventAvailable );
        }
        else {
    		store.remove(KEY_TOUCH_EVENT_AVAILABLE);
    	}        
    }     
}
