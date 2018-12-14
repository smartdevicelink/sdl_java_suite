package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;
/** The image resolution of this field.
 * 
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
 * 		<tr>
 * 			<td>resolutionWidth</td>
 * 			<td>Integer</td>
 * 			<td>The image resolution width.</td>
 *                 <td></td>
 *                 <td>minvalue:1; maxvalue: 10000</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>resolutionHeight</td>
 * 			<td>Integer</td>
 * 			<td>The image resolution height.</td>
 *                 <td></td>
 *                 <td>minvalue:1;  maxvalue: 10000</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		
 *  </table>
 * @since SmartDeviceLink 3.0
 * @see DisplayType
 * @see MediaClockFormat
 * @see TextFieldName
 * @see ImageType 
 *  
 */

public class ImageResolution extends RPCStruct {
	public static final String KEY_RESOLUTION_WIDTH = "resolutionWidth";
	public static final String KEY_RESOLUTION_HEIGHT = "resolutionHeight";
	
    public ImageResolution() {}
    /**
	* <p>
	* Constructs a new ImageResolution object indicated by the Hashtable
	* parameter
	* </p>
	* 
	* @param hash
	*            The Hashtable to use
	*/    

    public ImageResolution(Hashtable<String, Object> hash) {
        super(hash);
    }

    public ImageResolution(@NonNull Integer resolutionWidth, @NonNull Integer resolutionHeight) {
        this();
        setResolutionWidth(resolutionWidth);
        setResolutionHeight(resolutionHeight);
    }
    
    public void setResolutionWidth(@NonNull Integer resolutionWidth) {
        setValue(KEY_RESOLUTION_WIDTH, resolutionWidth);
    }
    
    public Integer getResolutionWidth() {
        return getInteger(KEY_RESOLUTION_WIDTH);
    }
    
    public void setResolutionHeight(@NonNull Integer resolutionHeight) {
        setValue(KEY_RESOLUTION_HEIGHT, resolutionHeight);
    }
    
    public Integer getResolutionHeight() {
        return getInteger(KEY_RESOLUTION_HEIGHT);
    }

    @Override
    public String toString() {
        return "width=" + String.valueOf(getResolutionWidth()) +
               ", height=" + String.valueOf(getResolutionHeight());
    }
}
