package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

/**
 * Deletes a submenu from the Command Menu.
 * 
 * <p><b>Notes: </b>When an app deletes a submenu that has child commands, those
 * child commands are also deleted</p>
 * 
 * <p><b>HMILevel needs to be  FULL, LIMITED or BACKGROUND</b></p>
 * 
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
 * 			<td>menuID</td>
 * 			<td>Integer</td>
 * 			<td>Unique ID that identifies the SubMenu to be delete</td>
 *                 <td>Y</td>
 *                 <td>Min Value: 0; Max Value: 2000000000</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *  </table>
 *
 *<p> <b>Response </b></p>
 * 
 * <p><b>Non-default Result Codes:</b></p>
 *<p>SUCCESS</p>
 *<p> 	INVALID_DATA</p>
 * <p>	OUT_OF_MEMORY</p>
 * <p>	TOO_MANY_PENDING_REQUESTS</p>
 * <p>	APPLICATION_NOT_REGISTERED</p>
 * <p>	GENERIC_ERROR</p>
 * <p>	REJECTED</p>
 * <p> INVALID_ID</p>
 * <p> IN_USE   </p>   
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
     * Constructs a new DeleteSubMenu object indicated by the Hashtable parameter    
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
     * 
     * <p><b>Notes: </b>Min Value: 0; Max Value: 2000000000</p>
     */    
    public void setMenuID( Integer menuID ) {
        if (menuID != null) {
            parameters.put(KEY_MENU_ID, menuID );
        } else {
            parameters.remove(KEY_MENU_ID);
        }
    }
}
