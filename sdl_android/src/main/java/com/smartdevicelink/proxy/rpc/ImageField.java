package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.AmbientLightStatus;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.util.DebugTool;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static com.smartdevicelink.proxy.rpc.HeadLampStatus.KEY_AMBIENT_LIGHT_SENSOR_STATUS;

/** <p>The name that identifies the field.For example AppIcon,SoftButton, LocationImage, etc.</p>
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
 * 			<td>name</td>
 * 			<td>ImageFieldName</td>
 * 			<td>The name that identifies the field.{@linkplain  ImageFieldName}</td>
 *                 <td></td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>imageTypeSupported</td>
 * 			<td>FileType</td>
 * 			<td>The image types that are supported in this field. {@linkplain FileType}</td>
 *                 <td></td>
 *                 <td>maxlength: 100</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>imageResolution</td>
 * 			<td>ImageResolution</td>
 * 			<td>The image resolution of this field.</td>
 *                 <td>Y</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 3.0
 * @see DisplayType 
 * @see MediaClockFormat
 * @see TextFieldName
 * @see ImageType
 * 
 *
 * 
 *
 */

public class ImageField extends RPCStruct {
    public static final String KEY_IMAGE_TYPE_SUPPORTED = "imageTypeSupported";
    public static final String KEY_IMAGE_RESOLUTION = "imageResolution";
    public static final String KEY_NAME = "name";
    
    
    public ImageField() { }
   
    public ImageField(Hashtable<String, Object> hash) {
        super(hash);
    }
    public ImageFieldName getName() {
        return (ImageFieldName) getObject(ImageFieldName.class, KEY_NAME);
    } 
    public void setName( ImageFieldName name ) {
        setValue(KEY_NAME, name);
    } 
    @SuppressWarnings("unchecked")
	public List<FileType> getImageTypeSupported() {
        return (List<FileType>) getObject(FileType.class, KEY_IMAGE_TYPE_SUPPORTED);
    }
    public void setImageTypeSupported( List<FileType> imageTypeSupported ) {
        setValue(KEY_IMAGE_TYPE_SUPPORTED, imageTypeSupported);
    }
    @SuppressWarnings("unchecked")
    public ImageResolution getImageResolution() {
        return (ImageResolution) getObject(ImageResolution.class, KEY_IMAGE_RESOLUTION);
    } 
    public void setImageResolution( ImageResolution imageResolution ) {
        setValue(KEY_IMAGE_RESOLUTION, imageResolution);
    }      
}
