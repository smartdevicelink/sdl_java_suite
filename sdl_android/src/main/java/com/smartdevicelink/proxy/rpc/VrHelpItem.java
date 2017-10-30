package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.util.DebugTool;
/**
 * 
 * VR help items  i.e. the text strings to be displayed, and when pronounced by the user the recognition of any of which must trigger the corresponding VR command.
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
 * 			<td>text</td>
 * 			<td>String</td>
 * 			<td>Text to display for VR Help item</td>
 *                 <td>Y</td>
 * 			<td>maxlength: 500</td>
 * 			<td>SmartDeviceLink 2.3.2</td>
 * 		</tr>
 * 		<tr>
 * 			<td>image</td>
 * 			<td>Image</td>
 * 			<td>Image struct for VR Help item</td>
 *                 <td>N</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.3.2</td>
 * 		</tr>
 * 		<tr>
 * 			<td>position</td>
 * 			<td>Integer</td>
 * 			<td>Position to display item in VR Help list</td>
 *                 <td>N</td>
 * 			<td> minvalue=1; maxvalue=100</td>
 * 			<td>SmartDeviceLink 2.3.2</td>
 * 		</tr>
 *  </table>
 *
 */
public class VrHelpItem extends RPCStruct {
	public static final String KEY_POSITION = "position";
	public static final String KEY_TEXT = "text";
	public static final String KEY_IMAGE = "image";
	  /**
		* <p>
		* Constructs a new VrHelpItem object indicated by the Hashtable
		* parameter
		* </p>
		* 
		* @param hash
		* <p>
		*            The Hashtable to use
		*/

    public VrHelpItem() { }
    public VrHelpItem(Hashtable<String, Object> hash) {
        super(hash);
    }
    public void setText(String text) {
        setValue(KEY_TEXT, text);
    }
    public String getText() {
        return getString(KEY_TEXT);
    }
    public void setImage(Image image) {
        setValue(KEY_IMAGE, image);
    }
    @SuppressWarnings("unchecked")
    public Image getImage() {
        return (Image) getObject(Image.class, KEY_IMAGE);
    }
    public void setPosition(Integer position) {
        setValue(KEY_POSITION, position);
    }
    public Integer getPosition() {
        return getInteger(KEY_POSITION);
    }
}
