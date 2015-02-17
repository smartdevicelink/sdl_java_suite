package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;

/**
 * Add a SubMenu to the Command Menu
 * <p>
 * A SubMenu can only be added to the Top Level Menu (i.e.a SunMenu cannot be
 * added to a SubMenu), and may only contain commands as children
 * <p>
 * <p>
 * <b>HMILevel needs to be FULL, LIMITED or BACKGROUD</b>
 * </p>
 * 
 * @since SmartDeviceLink 1.0
 * @see DeleteSubMenu
 * @see AddCommand
 * @see DeleteCommand
 */
public class AddSubMenu extends RPCRequest {
	public static final String KEY_POSITION = "position";
	public static final String KEY_MENU_NAME = "menuName";
	public static final String KEY_MENU_ID = "menuID";

	private Integer position, menuId;
	private String menuName;
	
	/**
	 * Constructs a new AddSubMenu object
	 */
	public AddSubMenu() {
        super(FunctionID.ADD_SUB_MENU);
    }
	
	/**
     * Creates an AddSubMenu object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
	 */
	public AddSubMenu(JSONObject jsonObject){
	    super(SdlCommand.ADD_SUBMENU, jsonObject);
        jsonObject = getParameters(jsonObject);
	    switch(sdlVersion){
	    default:
	        this.menuId = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_MENU_ID);
	        this.menuName = JsonUtils.readStringFromJsonObject(jsonObject, KEY_MENU_NAME);
	        this.position = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_POSITION);
	        break;
	    }
	}

	/**
	 * Returns an <i>Integer</i> object representing the Menu ID that identifies
	 * a sub menu
	 * <p>
	 * 
	 * @return Integer -an integer representing the Menu ID that identifies a sub
	 *         menu
	 */
    public Integer getMenuID() {
        return this.menuId;
    }
    
	/**
	 * Sets a Menu ID that identifies a sub menu. This value is used in
	 * {@linkplain AddCommand} to which SubMenu is the parent of the command
	 * being added
	 * <p>
	 * 
	 * @param menuID
	 *            an integer object representing a Menu ID
	 *            <p>
	 *            <b>Notes:</b> Min Value: 0; Max Value: 2000000000
	 */    
    public void setMenuID( Integer menuID ) {
        this.menuId = menuID;
    }
    
	/**
	 * Returns an <i>Integer</i> object representing the position of menu
	 * <p>
	 * 
	 * @return Integer -the value representing the relative position of menus
	 */    
    public Integer getPosition() {
        return this.position;
    }
    
	/**
	 * Sets a position of menu
	 * 
	 * @param position
	 *            An Integer object representing the position within the items
	 *            of the top level Command Menu. 0 will insert at the front, 1
	 *            will insert after the first existing element, etc. Position of
	 *            any submenu will always be located before the return and exit
	 *            options
	 *            <p>
	 *            <b>Notes: </b><br/>
	 *            <ul>
	 *            <li>
	 *            Min Value: 0; Max Value: 1000</li>
	 *            <li>If position is greater or equal than the number of items
	 *            on top level, the sub menu will be appended by the end</li>
	 *            <li>If this parameter is omitted, the entry will be added at
	 *            the end of the list</li>
	 *            </ul>
	 */    
    public void setPosition( Integer position ) {
        this.position = position;
    }
    
	/**
	 * Returns String which is displayed representing this submenu item
	 * 
	 * @return String -a Submenu item's name
	 */
    public String getMenuName() {
        return this.menuName;
    }
    
	/**
	 * Sets a menuName which is displayed representing this submenu item
	 * 
	 * @param menuName
	 *            String which will be displayed representing this submenu item
	 */    
    public void setMenuName( String menuName ) {
        this.menuName = menuName;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_MENU_ID, this.menuId);
            JsonUtils.addToJsonObject(result, KEY_MENU_NAME, this.menuName);
            JsonUtils.addToJsonObject(result, KEY_POSITION, this.position);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((menuId == null) ? 0 : menuId.hashCode());
		result = prime * result + ((menuName == null) ? 0 : menuName.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { 
			return true;
		}
		if (obj == null) { 
			return false;
		}
		if (getClass() != obj.getClass()) { 
			return false;
		}
		AddSubMenu other = (AddSubMenu) obj;
		if (menuId == null) {
			if (other.menuId != null) { 
				return false;
			}
		} 
		else if (!menuId.equals(other.menuId)) { 
			return false;
		}
		if (menuName == null) {
			if (other.menuName != null){ 
				return false;
			}
		} 
		else if (!menuName.equals(other.menuName)) { 
			return false;
		}
		if (position == null) {
			if (other.position != null){ 
				return false;
			}
		} 
		else if (!position.equals(other.position)) { 
			return false;
		}
		return true;
	}
}
