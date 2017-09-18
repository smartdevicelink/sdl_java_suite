package com.smartdevicelink.proxy.rpc;

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
    
    public void setResolutionWidth(Integer resolutionWidth) {
        setValue(KEY_RESOLUTION_WIDTH, resolutionWidth);
    }
    
    public Integer getResolutionWidth() {
        return getInteger(KEY_RESOLUTION_WIDTH);
    }
    
    public void setResolutionHeight(Integer resolutionHeight) {
        setValue(KEY_RESOLUTION_HEIGHT, resolutionHeight);
    }
    
    public Integer getResolutionHeight() {
        return getInteger(KEY_RESOLUTION_HEIGHT);
    }
}
