package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;
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
        return (ImageResolution) getObject(ImageResolution.class, KEY_RESOLUTION);
    } 
    public void setImageResolution( ImageResolution resolution ) {
        setValue(KEY_RESOLUTION, resolution);
    }
    @SuppressWarnings("unchecked")
    public TouchEventCapabilities getTouchEventAvailable() {
    	return (TouchEventCapabilities) getObject(TouchEventCapabilities.class, KEY_TOUCH_EVENT_AVAILABLE);
    } 
    public void setTouchEventAvailable( TouchEventCapabilities touchEventAvailable ) {
    	setValue(KEY_TOUCH_EVENT_AVAILABLE, touchEventAvailable);
    }     
}
