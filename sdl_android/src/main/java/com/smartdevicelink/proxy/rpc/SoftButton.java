package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.SystemAction;
import com.smartdevicelink.util.DebugTool;
/**
 * <p> A simulated button or keyboard key that is displayed on a touch screen.</p>
 *
 * <p><b>Parameter List</b></p>
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
 * 			<td>type</td>
 * 			<td>SoftButtonType</td>
 * 			<td>Describes, whether it is text, highlighted text, icon, or dynamic image. </td>
 *                 <td></td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>text</td>
 * 			<td>String</td>
 * 			<td>Optional text to display (if defined as TEXT or BOTH)</td>
 *                 <td>N</td>
 *                 <td>Min: 0; Maxlength: 500</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>image</td>
 * 			<td>Image</td>
 * 			<td>Optional image struct for SoftButton (if defined as IMAGE or BOTH).</td>
 *                 <td></td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>isHighlighted</td>
 * 			<td>Boolean</td>
 * 			<td>True, if highlighted False, if not highlighted</td>
 *                 <td>N</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>softButtonID</td>
 * 			<td>Integer</td>
 * 			<td>Value which is returned via OnButtonPress / OnButtonEvent</td>
 *                 <td></td>
 *                 <td>Min: 0; Max: 65535</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>systemAction</td>
 * 			<td>SystemAction</td>
 * 			<td>Parameter indicating whether selecting a SoftButton shall call a specific system action. This is intended to allow Notifications to bring the callee into full / focus; or in the case of persistent overlays, the overlay can persist when a SoftButton is pressed.</td>
 *                 <td>N</td>
 *                 <td>defvalue: DEFAULT_ACTION</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>			
 * 			
 *  </table>
 *
 */
public class SoftButton extends RPCStruct {

	public static final String KEY_IS_HIGHLIGHTED = "isHighlighted";
	public static final String KEY_SOFT_BUTTON_ID = "softButtonID";
	public static final String KEY_SYSTEM_ACTION = "systemAction";
	public static final String KEY_TEXT = "text";
	public static final String KEY_TYPE = "type";
	public static final String KEY_IMAGE = "image";
	/**
	* 
	* <p>Constructs a new SoftButton object indicated by the Hashtable
	* parameter</p>
	* 
	* 
	* @param hash
	* 
	*            The Hashtable to use
	*/

    public SoftButton() { }
    public SoftButton(Hashtable<String, Object> hash) {
        super(hash);
    }
    public void setType(SoftButtonType type) {
        if (type != null) {
            store.put(KEY_TYPE, type);
        } else {
        	store.remove(KEY_TYPE);
        }
    }
    public SoftButtonType getType() {
    	Object obj = store.get(KEY_TYPE);
        if (obj instanceof SoftButtonType) {
            return (SoftButtonType) obj;
        } else if (obj instanceof String) {
        	return SoftButtonType.valueForString((String) obj);
        }
        return null;
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
    public void setIsHighlighted(Boolean isHighlighted) {
        if (isHighlighted != null) {
            store.put(KEY_IS_HIGHLIGHTED, isHighlighted);
        } else {
        	store.remove(KEY_IS_HIGHLIGHTED);
        }
    }
    public Boolean getIsHighlighted() {
        return (Boolean) store.get(KEY_IS_HIGHLIGHTED);
    }
    public void setSoftButtonID(Integer softButtonID) {
        if (softButtonID != null) {
            store.put(KEY_SOFT_BUTTON_ID, softButtonID);
        } else {
        	store.remove(KEY_SOFT_BUTTON_ID);
        }
    }
    public Integer getSoftButtonID() {
        return (Integer) store.get(KEY_SOFT_BUTTON_ID);
    }
    public void setSystemAction(SystemAction systemAction) {
        if (systemAction != null) {
            store.put(KEY_SYSTEM_ACTION, systemAction);
        } else {
        	store.remove(KEY_SYSTEM_ACTION);
        }
    }
    public SystemAction getSystemAction() {
    	Object obj = store.get(KEY_SYSTEM_ACTION);
        if (obj instanceof SystemAction) {
            return (SystemAction) obj;
        } else if (obj instanceof String) {
        	return SystemAction.valueForString((String) obj);
        }
        return null;
    }
}
