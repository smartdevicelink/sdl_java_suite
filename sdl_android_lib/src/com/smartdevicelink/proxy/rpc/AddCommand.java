package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.util.DebugTool;

/**
* <p>
* This class will add a command to the application's Command Menu
* </p>
* <p>
* <b>Note:</b> A command will be added to the end of the list of elements in
* the Command Menu under the following conditions:
* </p>
* <ul>
* <li>When a Command is added with no MenuParams value provided</li>
* <li>When a MenuParams value is provided with a MenuParam.position value
* greater than or equal to the number of menu items currently defined in the
* menu specified by the MenuParam.parentID value</li>
* </ul>
* <br/>
* <p>
* The set of choices which the application builds using AddCommand can be a
* mixture of:
* </p>
* <ul>
* <li>Choices having only VR synonym definitions, but no MenuParams definitions
* </li>
* <li>Choices having only MenuParams definitions, but no VR synonym definitions
* </li>
* <li>Choices having both MenuParams and VR synonym definitions</li>
* </ul>
* <p>
 * <b>HMILevel needs to be FULL, LIMITED or BACKGROUD</b>
* </p>
* 
* @since SmartDeviceLink 1.0
* @see DeleteCommand
* @see AddSubMenu
* @see DeleteSubMenu
*/

public class AddCommand extends RPCRequest {
	public static final String KEY_CMD_ICON = "cmdIcon";
	public static final String KEY_MENU_PARAMS = "menuParams";
	public static final String KEY_CMD_ID = "cmdID";
	public static final String KEY_VR_COMMANDS = "vrCommands";

	/**
	 * Constructs a new AddCommand object
	 */
	public AddCommand() {
        super(FunctionID.ADD_COMMAND.toString());
    }
	
	/**
	* <p>
	* Constructs a new AddCommand object indicated by the Hashtable
	* parameter
	* </p>
	* 
	* @param hash
	*            The Hashtable to use
	*/
    public AddCommand(Hashtable<String, Object> hash) {
        super(hash);
    }
	/**
	 * <p>
	 * Returns an <i>Integer</i> object representing the Command ID that you want to add
	 * </p>
	 * 
	 * @return Integer -an integer representation a Unique Command ID
	 */
    public Integer getCmdID() {
        return (Integer) parameters.get(KEY_CMD_ID);
    }
	/**
	 * Sets an Unique Command ID that identifies the command. Is returned in an
	 * <i>{@linkplain OnCommand}</i> notification to identify the command
	 * selected by the user
	 * <p>
	 * 
	 * @param cmdID
	 *            an integer object representing a Command ID
	 *            <p>
	 *            <b>Notes:</b> Min Value: 0; Max Value: 2000000000
	 */
    public void setCmdID(Integer cmdID) {
        if (cmdID != null) {
            parameters.put(KEY_CMD_ID, cmdID);
        } else {
            parameters.remove(KEY_CMD_ID);
        }
    }
	/**
	 * <p>
	 * Returns a <I>MenuParams</I> object which will defined the command and how
	 * it is added to the Command Menu
	 * </p>
	 * 
	 * @return MenuParams -a MenuParams object
	 */
    @SuppressWarnings("unchecked")
    public MenuParams getMenuParams() {
        Object obj = parameters.get(KEY_MENU_PARAMS);
        if (obj instanceof MenuParams) {
        	return (MenuParams) obj;
        }
        else if (obj instanceof Hashtable) {
        	try {
        		return new MenuParams((Hashtable<String, Object>) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_MENU_PARAMS, e);
            }
        }
        return null;
    }
	/**
	 * <p>
	 * Sets Menu parameters<br/>
	 * If provided, this will define the command and how it is added to the
	 * Command Menu<br/>
	 * If null, commands will not be accessible through the HMI application menu
	 * </p>
	 * 
	 * @param menuParams
	 *            a menuParams object
	 */    
    public void setMenuParams(MenuParams menuParams) {
        if (menuParams != null) {
            parameters.put(KEY_MENU_PARAMS, menuParams);
        } else {
            parameters.remove(KEY_MENU_PARAMS);
        }
    }
	/**
	 * <p>
	 * Gets Voice Recognition Commands
	 * </p>
	 * 
	 * @return List<String> -(List<String>) indicating one or more VR phrases
	 */    
    @SuppressWarnings("unchecked")
    public List<String> getVrCommands() {
        if (parameters.get(KEY_VR_COMMANDS) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_VR_COMMANDS);
	    	if (list != null && list.size() > 0) {
	    		Object obj = list.get(0);
	    		if (obj instanceof String) {
	    			return (List<String>)list;
	    		}
	    	}
    	}
    	return null;
    }
	/**
	 * <p>
	 * Sets Voice Recognition Commands <br/>
	 * If provided, defines one or more VR phrases the recognition of any of
	 * which triggers the <i>{@linkplain OnCommand}</i> notification with this
	 * cmdID<br/>
	 * If null, commands will not be accessible by voice commands (when the user
	 * hits push-to-talk)
	 * </p>
	 * 
	 * @param vrCommands
	 *            List<String> indicating one or more VR phrases
	 *            <p>
	 *            <b>Notes: </b>Optional only if menuParams is provided. If
	 *            provided, array must contain at least one non-empty (not null,
	 *            not zero-length, not whitespace only) element
	 */
    public void setVrCommands( List<String> vrCommands ) {
        if (vrCommands != null) {
            parameters.put(KEY_VR_COMMANDS, vrCommands );
        } else {
            parameters.remove(KEY_VR_COMMANDS);
        }
    }

	/**
	 * Gets the image to be shown along with a command </p>
	 * 
	 * @return Image -an Image object
	 * @since SmartDeviceLink 2.0
	 */
    @SuppressWarnings("unchecked")
    public Image getCmdIcon() {
        Object obj = parameters.get(KEY_CMD_ICON);
        if (obj instanceof Image) {
            return (Image) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new Image((Hashtable<String, Object>) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_CMD_ICON, e);
            }
        }
        return null;
    }

	/**
	 * Sets the Image<br/>
	 * If provided, defines the image to be be shown along with a  command
	 * @param cmdIcon
	 *            an Image obj representing the Image obj shown along with a
	 *            command
	 *            <p>
	 *            <b>Notes: </b>If omitted on supported displays, no (or the
	 *            default if applicable) icon will be displayed
	 * @since SmartDeviceLink 2.0
	 */
    public void setCmdIcon(Image cmdIcon) {
        if (cmdIcon != null) {
            parameters.put(KEY_CMD_ICON, cmdIcon);
        } else {
            parameters.remove(KEY_CMD_ICON);
        }
    }
}
