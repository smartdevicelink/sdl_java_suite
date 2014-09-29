package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.constants.Names;

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
	/**
	* Constructs a new DeleteSubMenu object
	*/
	public DeleteSubMenu() {
        super("DeleteSubMenu");
    }
    /**
     * Constructs a new DeleteSubMenu object indicated by the Hashtable parameter<p>    
     * @param hash The Hashtable to use
     */
    public DeleteSubMenu(Hashtable hash) {
        super(hash);
    }
    /**
     * Gets the Menu ID that identifies the SubMenu to be delete
     * @return Integer -an Integer value representing menuID that identifies the SubMenu to be delete
     */    
    public Integer getMenuID() {
        return (Integer) parameters.get( Names.menuID );
    }
    /**
     * Sets the MenuID that identifies the SubMenu to be delete  
     * @param menuID an Integer value representing menuID that identifies the SubMenu to be delete
     * <p>
     * <b>Notes: </b>Min Value: 0; Max Value: 2000000000
     */    
    public void setMenuID( Integer menuID ) {
        if (menuID != null) {
            parameters.put(Names.menuID, menuID );
        }
    }
}