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
        setValue(KEY_PRESS_AVAILABLE, pressAvailable);
    }
    
    public Boolean getPressAvailable() {
        return getBoolean(KEY_PRESS_AVAILABLE);
    }
    
    public void setMultiTouchAvailable(Boolean multiTouchAvailable) {
        setValue(KEY_MULTI_TOUCH_AVAILABLE, multiTouchAvailable);
    }
    
    public Boolean getMultiTouchAvailable() {
        return getBoolean(KEY_MULTI_TOUCH_AVAILABLE);
    }
    
    public void setDoublePressAvailable(Boolean doublePressAvailable) {
        setValue(KEY_DOUBLE_PRESS_AVAILABLE, doublePressAvailable);
    }
    
    public Boolean getDoublePressAvailable() {
        return getBoolean(KEY_DOUBLE_PRESS_AVAILABLE);
    }
}
