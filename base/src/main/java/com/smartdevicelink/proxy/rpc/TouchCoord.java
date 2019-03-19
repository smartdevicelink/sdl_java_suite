package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;
/**
 * <p>The x or y coordinate of the touch.</p>
 * 
 * 
 * 
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th>Reg.</th>
 *               <th>Notes</th>
 * 			<th> Version</th>
 * 		</tr>
 * 		<tr>
 * 			<td>x</td>
 * 			<td>Integer</td>
 * 			<td>The x coordinate of the touch.</td>
 *                 <td>Y</td>
 *                 <td>minvalue = 0; maxvalue = 10000</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>y</td>
 * 			<td>Integer</td>
 * 			<td>The y coordinate of the touch.</td>
 *                 <td>Y</td>
 *                 <td>minvalue = 0; maxvalue = 10000</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 *  </table>
 *@see SoftButtonCapabilities
 *@see ButtonCapabilities
 *@see OnButtonPress
 *
 *@since SmartDeviceLink 3.0
 */

public class TouchCoord extends RPCStruct {
    public static final String KEY_X = "x";
    public static final String KEY_Y = "y";

    public TouchCoord() {}

	 /**
		 * <p>Constructs a new TouchCoord object indicated by the Hashtable parameter</p>
		 *
		 * @param hash The Hashtable to use
		 */
    public TouchCoord(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new TouchCoord object
	 * @param x The x coordinate of the touch.
	 * @param y The y coordinate of the touch.
	 */
	public TouchCoord(@NonNull Integer x, @NonNull Integer y){
		this();
		setX(x);
		setY(y);
	}
    
    public void setX(@NonNull Integer x) {
        setValue(KEY_X, x);
    }
    
    public Integer getX() {
        return getInteger(KEY_X);
    }
    
    public void setY(@NonNull Integer y) {
        setValue(KEY_Y, y);
    }
    
    public Integer getY() {
        return getInteger(KEY_Y);
    }
    
}
