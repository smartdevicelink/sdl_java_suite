package com.smartdevicelink.proxy.rpc;

import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.util.JsonUtils;

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

	private List<String> vrCommands;
	private MenuParams menuParams;
	private Image image;
	private Integer commandId;
	
	/**
	 * Constructs a new AddCommand object
	 */
	public AddCommand() {
        super(FunctionID.ADD_COMMAND);
    }
	
	public AddCommand(JSONObject jsonObject, int sdlVersion){
	    super(jsonObject);
	    switch(sdlVersion){
	    default:
	        this.commandId = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_CMD_ID);
	        this.vrCommands = JsonUtils.readStringListFromJsonObject(jsonObject, KEY_VR_COMMANDS);
	        JSONObject jsonMenuParams = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_MENU_PARAMS);
	        if(jsonMenuParams != null){
	            this.menuParams = new MenuParams(jsonMenuParams);
	        }
	        JSONObject jsonImage = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_CMD_ICON);
	        if(jsonImage != null){
	            this.image = new Image(jsonImage);
	        }
	        break;
	    }
	}
	
	/**
	 * <p>
	 * Returns an <i>Integer</i> object representing the Command ID that you want to add
	 * </p>
	 * 
	 * @return Integer -an integer representation a Unique Command ID
	 */
    public Integer getCmdID() {
        return this.commandId;
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
        this.commandId = cmdID;
    }
    
	/**
	 * <p>
	 * Returns a <I>MenuParams</I> object which will defined the command and how
	 * it is added to the Command Menu
	 * </p>
	 * 
	 * @return MenuParams -a MenuParams object
	 */
    public MenuParams getMenuParams() {
        return this.menuParams;
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
        this.menuParams = menuParams;
    }
    
	/**
	 * <p>
	 * Gets Voice Recognition Commands
	 * </p>
	 * 
	 * @return List<String> -(List<String>) indicating one or more VR phrases
	 */
    public List<String> getVrCommands() {
        return this.vrCommands;
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
        this.vrCommands = vrCommands;
    }

	/**
	 * Gets the image to be shown along with a command </p>
	 * 
	 * @return Image -an Image object
	 * @since SmartDeviceLink 2.0
	 */
    public Image getCmdIcon() {
        return this.image;
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
        this.image = cmdIcon;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = new JSONObject();
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_CMD_ID, this.commandId);
            JsonUtils.addToJsonObject(result, KEY_CMD_ICON, (this.image == null) ? null : 
                image.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_MENU_PARAMS, (this.menuParams == null) ? null : 
                menuParams.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_VR_COMMANDS, (this.vrCommands == null) ? null : 
                JsonUtils.createJsonArray(this.vrCommands));
            break;
        }
        
        return result;
    }
}
