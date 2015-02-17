package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;

/**
 * Deletes a submenu from the Command Menu
 * <p>
 * <b>Notes: </b>When an app deletes a submenu that has child commands, those
 * child commands are also deleted
 * <p>
 * <b>HMILevel needs to be  FULL, LIMITED or BACKGROUND</b>
 * </p>
 * 
 * @since SmartDeviceLink 1.0
 * @see AddCommand
 * @see AddSubMenu
 * @see DeleteCommand
 */
public class DeleteSubMenu extends RPCRequest {
	public static final String KEY_MENU_ID = "menuID";
	
	private Integer menuId;
	
	/**
	* Constructs a new DeleteSubMenu object
	*/
	public DeleteSubMenu() {
        super(FunctionID.DELETE_SUB_MENU);
    }
	
    /**
     * Creates a DeleteSubMenu object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public DeleteSubMenu(JSONObject jsonObject) {
        super(SdlCommand.DELETE_SUB_MENU, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            this.menuId = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_MENU_ID);
            break;
        }
    }
    
    /**
     * Gets the Menu ID that identifies the SubMenu to be delete
     * @return Integer -an Integer value representing menuID that identifies the SubMenu to be delete
     */    
    public Integer getMenuID() {
        return this.menuId;
    }
    
    /**
     * Sets the MenuID that identifies the SubMenu to be delete  
     * @param menuID an Integer value representing menuID that identifies the SubMenu to be delete
     * <p>
     * <b>Notes: </b>Min Value: 0; Max Value: 2000000000
     */    
    public void setMenuID( Integer menuID ) {
        this.menuId = menuID;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_MENU_ID, this.menuId);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((menuId == null) ? 0 : menuId.hashCode());
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
		DeleteSubMenu other = (DeleteSubMenu) obj;
		if (menuId == null) {
			if (other.menuId != null) { 
				return false;
			}
		} 
		else if (!menuId.equals(other.menuId)) { 
			return false;
		}
		return true;
	}
}
