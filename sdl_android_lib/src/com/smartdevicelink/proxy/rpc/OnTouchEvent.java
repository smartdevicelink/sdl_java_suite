package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.TouchType;
import com.smartdevicelink.util.DebugTool;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
/**
 * 
 * Notifies about touch events on the screen's prescribed area.
 * <p><b>Parameter List</b>
 * <p>
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
 *                 <td>minsize:1<br> maxsize:10</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 *  </table> 
 *  <p>
  * <b>Note:</b>
 * SDL needs to be informed about every User`s touching the touch screen.
 * <p>
 */
public class OnTouchEvent extends RPCNotification {
	public static final String KEY_EVENT = "event";
	public static final String KEY_TYPE = "type";
	
    public OnTouchEvent() {
        super(FunctionID.ON_TOUCH_EVENT);
    }
    public OnTouchEvent(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    public void setType(TouchType type) {
    	if (type != null) {
    		parameters.put(KEY_TYPE, type);
    	} else {
    		parameters.remove(KEY_TYPE);
    	}
    }
    
    public TouchType getType() {
        Object obj = parameters.get(KEY_TYPE);
        if (obj instanceof TouchType) {
            return (TouchType) obj;
        } else if (obj instanceof String) {
        	TouchType theCode = null;
            try {
                theCode = TouchType.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_TYPE, e);
            }
            return theCode;
        }
        return null;
    }
    
    public void setEvent(List<TouchEvent> event) {
        if (event != null) {
            parameters.put(KEY_EVENT, event);
        } else {
        	parameters.remove(KEY_EVENT);
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<TouchEvent> getEvent() {
        if (parameters.get(KEY_EVENT) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_EVENT);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof TouchEvent) {
	                return (List<TouchEvent>) list;
	            } else if (obj instanceof Hashtable) {
	            	List<TouchEvent> newList = new ArrayList<TouchEvent>();
	                for (Object hashObj : list) {
	                    newList.add(new TouchEvent((Hashtable<String, Object>) hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
    }
}
