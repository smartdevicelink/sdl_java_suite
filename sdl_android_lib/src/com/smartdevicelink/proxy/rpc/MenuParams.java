package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
/**
 * Used when adding a sub menu to an application menu or existing sub menu.
 * <p><b> Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>parentID</td>
 * 			<td>Integer</td>
 * 			<td>The unique ID of an existing submenu to which a command will be added.
 *					If this element is not provided, the command will be added to the top level of the Command Menu.
 *					<ul>
 *					<li>Min: 0</li>
 *					<li>Max: 2000000000</li>
 *					</ul>
 *			</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>position</td>
 * 			<td>Integer</td>
 * 			<td>Position within the items of the parent Command Menu. 0 will insert at the front, 1 will insert after the first existing element, etc. 
 * 					Position of any submenu will always be located before the return and exit options.
 * 					<ul>
 * 						<li>Min Value: 0</li>
 * 						<li>Max Value: 1000</li>
 * 						<li>If position is greater or equal than the number of items in the parent Command Menu, the sub menu will be appended to the end of that Command Menu.</li>
 * 						<li>If this element is omitted, the entry will be added at the end of the parent menu.</li>
 * 					</ul>
 * 			</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *     <tr>
 * 			<td>menuName</td>
 * 			<td>String</td>
 * 			<td>Text which appears in menu, representing this command.
 *       			<ul>
 * 						<li>Min: 1</li>
 * 						<li>Max: 100</li>
 * 					</ul>
 * 			</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * </table>
 * @since SmartDeviceLink 1.0
 * @see AddCommand 
 * @see AddSubMenu
 */
public class MenuParams extends RPCStruct {
	public static final String KEY_PARENT_ID = "parentID";
	public static final String KEY_POSITION = "position";
	public static final String KEY_MENU_NAME = "menuName";
	/**
	 * Constructs a newly allocated MenuParams object
	 */
    public MenuParams() { }
    /**
     * Constructs a newly allocated MenuParams object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */    
    public MenuParams(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * Get the unique ID of an existing submenu to which a command will be added.
     *	If this element is not provided, the command will be added to the top level of the Command Menu.
     * @return parentID Min: 0 Max: 2000000000
     */    
    public Integer getParentID() {
        return (Integer) store.get( KEY_PARENT_ID );
    }
    /**
     * Set the unique ID of an existing submenu to which a command will be added.
     *	If this element is not provided, the command will be added to the top level of the Command Menu.
     * @param parentID Min: 0; Max: 2000000000
     */    
    public void setParentID( Integer parentID ) {
        if (parentID != null) {
            store.put(KEY_PARENT_ID, parentID );
        } else {
        	store.remove(KEY_PARENT_ID);
        }
    }
    /**
     * Get the position within the items of the parent Command Menu. 0 will insert at the front, 1 will insert after the first existing element, etc. 
     * 	Position of any submenu will always be located before the return and exit options.
     * 					<ul>
     * 						<li>Min Value: 0</li>
     * 						<li>Max Value: 1000</li>
     * 						<li>If position is greater or equal than the number of items in the parent Command Menu, the sub menu will be appended to the end of that Command Menu.</li>
     * 						<li>If this element is omitted, the entry will be added at the end of the parent menu.</li>
     * 					</ul>
     * @return  the position within the items of the parent Command Menu
     */    
    public Integer getPosition() {
        return (Integer) store.get( KEY_POSITION );
    }
    /**
     * Set the position within the items of the parent Command Menu. 0 will insert at the front, 1 will insert after the first existing element, etc. 
     * 	Position of any submenu will always be located before the return and exit options.
     * 					<ul>
     * 						<li>Min Value: 0</li>
     * 						<li>Max Value: 1000</li>
     * 						<li>If position is greater or equal than the number of items in the parent Command Menu, the sub menu will be appended to the end of that Command Menu.</li>
     * 						<li>If this element is omitted, the entry will be added at the end of the parent menu.</li>
     * 					</ul>
     * @param position Mix: 0 Max: 1000
     */    
    public void setPosition( Integer position ) {
        if (position != null) {
            store.put(KEY_POSITION, position );
        } else {
        	store.remove(KEY_POSITION);
        }
    }
    /**
     * Get the text which appears in menu, representing this command.
     *       			<ul>
     * 						<li>Min: 1</li>
     * 						<li>Max: 100</li>
     * 					</ul>
     * @return menuName the menu name
     */
    
    public String getMenuName() {
        return (String) store.get( KEY_MENU_NAME );
    }
    /**
     * Set text which appears in menu, representing this command.
     *       			<ul>
     * 						<li>Min: 1</li>
     * 						<li>Max: 100</li>
     * 					</ul>
     * @param menuName the menu name
     */
    
    public void setMenuName( String menuName ) {
        if (menuName != null) {
            store.put(KEY_MENU_NAME, menuName );
        } else {
        	store.remove(KEY_MENU_NAME);
        }
    }
}
