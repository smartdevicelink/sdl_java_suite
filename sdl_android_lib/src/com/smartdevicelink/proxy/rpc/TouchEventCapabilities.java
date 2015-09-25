package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;
/**
 * Types of screen touch events available in screen area.
 * 
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th>Reg.</th>
 *               <th>Notes</th>
 * 			<th>SmartDeviceLink Version</th>
 * 		</tr>
 * 		<tr>
 * 			<td>pressAvailable</td>
 * 			<td>Boolean</td>
 * 			<td></td>
 *                 <td>Y</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 *            <tr>
 * 			<td>multiTouchAvailable</td>
 * 			<td>Boolean</td>
 * 			<td></td>
 *                 <td>Y</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>doublePressAvailable</td>
 * 			<td>Boolean</td>
 * 			<td></td>
 *                 <td>Y</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 *  </table>
 *
 */
public class TouchEventCapabilities extends RPCStruct {
    public static final String KEY_PRESS_AVAILABLE = "pressAvailable";
    public static final String KEY_MULTI_TOUCH_AVAILABLE = "multiTouchAvailable";
    public static final String KEY_DOUBLE_PRESS_AVAILABLE = "doublePressAvailable";
    public TouchEventCapabilities() {}
    
    public TouchEventCapabilities(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    public void setPressAvailable(Boolean pressAvailable) {
        if (pressAvailable != null) {
            store.put(KEY_PRESS_AVAILABLE, pressAvailable);
        } else {
        	store.remove(KEY_PRESS_AVAILABLE);
        }
    }
    
    public Boolean getPressAvailable() {
        return (Boolean) store.get(KEY_PRESS_AVAILABLE);
    }
    
    public void setMultiTouchAvailable(Boolean multiTouchAvailable) {
        if (multiTouchAvailable != null) {
            store.put(KEY_MULTI_TOUCH_AVAILABLE, multiTouchAvailable);
        } else {
        	store.remove(KEY_MULTI_TOUCH_AVAILABLE);
        }
    }
    
    public Boolean getMultiTouchAvailable() {
        return (Boolean) store.get(KEY_MULTI_TOUCH_AVAILABLE);
    }
    
    public void setDoublePressAvailable(Boolean doublePressAvailable) {
        if (doublePressAvailable != null) {
            store.put(KEY_DOUBLE_PRESS_AVAILABLE, doublePressAvailable);
        } else {
        	store.remove(KEY_DOUBLE_PRESS_AVAILABLE);
        }
    }
    
    public Boolean getDoublePressAvailable() {
        return (Boolean) store.get(KEY_DOUBLE_PRESS_AVAILABLE);
    }
}
