package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

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
	/**
	* Constructs a new DeleteSubMenu object
	*/
	public DeleteSubMenu() {
        super(FunctionID.DELETE_SUB_MENU.toString());
    }
    /**
     * Constructs a new DeleteSubMenu object indicated by the Hashtable parameter<p>    
     * @param hash The Hashtable to use
     */
    public DeleteSubMenu(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * Gets the Menu ID that identifies the SubMenu to be delete
     * @return Integer -an Integer value representing menuID that identifies the SubMenu to be delete
     */    
    public Integer getMenuID() {
        return (Integer) parameters.get( KEY_MENU_ID );
    }
    /**
     * Sets the MenuID that identifies the SubMenu to be delete  
     * @param menuID an Integer value representing menuID that identifies the SubMenu to be delete
     * <p>
     * <b>Notes: </b>Min Value: 0; Max Value: 2000000000
     */    
    public void setMenuID( Integer menuID ) {
        if (menuID != null) {
            parameters.put(KEY_MENU_ID, menuID );
        } else {
            parameters.remove(KEY_MENU_ID);
        }
    }
}
