package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.TouchEventCapabilities;
import com.smartdevicelink.util.DebugTool;

public class ScreenParams extends RPCStruct {

	public ScreenParams() { }
  
    public ScreenParams(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    @SuppressWarnings("unchecked")
    public ImageResolution getImageResolution() {
    	Object obj = store.get(Names.resolution);
        if (obj instanceof ImageResolution) {
            return (ImageResolution) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new ImageResolution((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.resolution, e);
            }
        }
        return null;
    } 
    public void setImageResolution( ImageResolution resolution ) {
        if (resolution != null) {
            store.put(Names.resolution, resolution );
        }
        else {
    		store.remove(Names.resolution);
    	}
    }
    @SuppressWarnings("unchecked")
    public TouchEventCapabilities getTouchEventAvailable() {
    	Object obj = store.get(Names.touchEventAvailable);
        if (obj instanceof TouchEventCapabilities) {
            return (TouchEventCapabilities) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new TouchEventCapabilities((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.touchEventAvailable, e);
            }
        }
        return null;
    } 
    public void setTouchEventAvailable( TouchEventCapabilities touchEventAvailable ) {
        if (touchEventAvailable != null) {
            store.put(Names.touchEventAvailable, touchEventAvailable );
        }
        else {
    		store.remove(Names.touchEventAvailable);
    	}        
    }     
}
