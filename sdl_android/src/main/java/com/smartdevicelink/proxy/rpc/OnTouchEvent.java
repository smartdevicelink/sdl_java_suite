package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.TouchType;

import java.util.Hashtable;
import java.util.List;

/**
 * 
 * Notifies about touch events on the screen's prescribed area.
 * <p><b>Parameter List</b></p>
 * 
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th>Reg.</th>
 *               <th>Notes</th>
 * 			<th>Version</th>
 * 		</tr>
 *            <tr>
 * 			<td>type</td>
 * 			<td>TouchType</td>
 * 			<td>The type of touch event.</td>
 *                 <td>Y</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>event</td>
 * 			<td>TouchEvent</td>
 * 			<td>List of all individual touches involved in this event.</td>
 *                 <td>Y</td>
 *                 <td>minsize:1; maxsize:10</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 *  </table> 
 *  
 * <p><b>Note:</b></p>
 * <p>SDL needs to be informed about every User`s touching the touch screen.</p>
 * 
 */
public class OnTouchEvent extends RPCNotification {
	public static final String KEY_EVENT = "event";
	public static final String KEY_TYPE = "type";
	/**
	 * Constructs a new OnTouchEvent object
	 */  

    public OnTouchEvent() {
        super(FunctionID.ON_TOUCH_EVENT.toString());
    }
	/**
	* <p>
	* Constructs a new OnTouchEvent object indicated by the Hashtable
	* parameter
	* </p>
	* 
	* @param hash
	*            The Hashtable to use
	*/  

    public OnTouchEvent(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    public void setType(TouchType type) {
		setParameter(KEY_TYPE, type);
    }
    
    public TouchType getType() {
		return (TouchType) getObject(TouchType.class, KEY_TYPE);
    }
    
    public void setEvent(List<TouchEvent> event) {
		setParameter(KEY_EVENT, event);
    }
    
    @SuppressWarnings("unchecked")
    public List<TouchEvent> getEvent() {
		return (List<TouchEvent>) getObject(TouchEvent.class, KEY_EVENT);
    }
}
