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
        if (text != null) {
            store.put(KEY_TEXT, text);
        } else {
        	store.remove(KEY_TEXT);
        }
    }
    public String getText() {
        return (String) store.get(KEY_TEXT);
    }
    public void setImage(Image image) {
        if (image != null) {
            store.put(KEY_IMAGE, image);
        } else {
        	store.remove(KEY_IMAGE);
        }
    }
    @SuppressWarnings("unchecked")
    public Image getImage() {
    	Object obj = store.get(KEY_IMAGE);
        if (obj instanceof Image) {
            return (Image) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new Image((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_IMAGE, e);
            }
        }
        return null;
    }
    public void setPosition(Integer position) {
        if (position != null) {
            store.put(KEY_POSITION, position);
        } else {
        	store.remove(KEY_POSITION);
        }
    }
    public Integer getPosition() {
        return (Integer) store.get(KEY_POSITION);
    }
}
