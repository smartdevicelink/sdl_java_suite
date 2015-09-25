package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.TouchEventCapabilities;
import com.smartdevicelink.util.DebugTool;
/** The resolution of the prescribed screen area.
 * 
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th> Req.</th>
 * 			<th>Notes</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>resolution</td>
 * 			<td>ImageResolution</td>
 * 			<td>The resolution of the prescribed screen area.</td>
 *                 <td>Y</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.3.2</td>
 * 		</tr>
 * 		<tr>
 * 			<td>touchEventAvailable</td>
 * 			<td>TouchEventCapabilities</td>
 * 			<td>Types of screen touch events available in screen area.</td>
 *                 <td>N</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.3.2</td>
 * 		</tr>
 *
 *  </table>
 * @since SmartDeviceLink 2.3.2
 *
 */

public class ScreenParams extends RPCStruct {
    public static final String KEY_RESOLUTION = "resolution";
    public static final String KEY_TOUCH_EVENT_AVAILABLE = "touchEventAvailable";
	/**
	 * Constructs a new ScreenParams object
	 */  

	public ScreenParams() { }
	/**
	* <p>
	* Constructs a new ScreenParamst object indicated by the Hashtable
	* parameter
	* </p>
	* 
	* @param hash
	*            The Hashtable to use
	*/  

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
