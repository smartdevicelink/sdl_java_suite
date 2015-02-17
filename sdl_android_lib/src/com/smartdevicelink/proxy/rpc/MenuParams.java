package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.util.JsonUtils;
/**
 * Used when adding a sub menu to an application menu or existing sub menu.
 * <p><b> Parameter List
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>parentID</td>
 * 			<td>Int32</td>
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
 * 			<td>Int16</td>
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
 */
public class MenuParams extends RPCObject {
	public static final String KEY_PARENT_ID = "parentID";
	public static final String KEY_POSITION = "position";
	public static final String KEY_MENU_NAME = "menuName";
	
	private Integer parentId, position;
	private String menuName;
	
	/**
	 * Constructs a newly allocated MenuParams object
	 */
    public MenuParams() { }
    
    /**
     * Creates a MenuParams object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */    
    public MenuParams(JSONObject jsonObject) {
        switch(sdlVersion){
        default:
            this.parentId = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_PARENT_ID);
            this.position = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_POSITION);
            this.menuName = JsonUtils.readStringFromJsonObject(jsonObject, KEY_MENU_NAME);
            break;
        }
    }
    
    /**
     * Get the unique ID of an existing submenu to which a command will be added.
     *	If this element is not provided, the command will be added to the top level of the Command Menu.
     * @return parentID Min: 0 Max: 2000000000
     */    
    public Integer getParentID() {
        return this.parentId;
    }
    
    /**
     * Set the unique ID of an existing submenu to which a command will be added.
     *	If this element is not provided, the command will be added to the top level of the Command Menu.
     * @param parentID Min: 0; Max: 2000000000
     */    
    public void setParentID( Integer parentID ) {
        this.parentId = parentID;
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
        return this.position;
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
        this.position = position;
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
        return this.menuName;
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
        this.menuName = menuName;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_MENU_NAME, this.menuName);
            JsonUtils.addToJsonObject(result, KEY_PARENT_ID, this.parentId);
            JsonUtils.addToJsonObject(result, KEY_POSITION, this.position);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((menuName == null) ? 0 : menuName.hashCode());
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
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
		MenuParams other = (MenuParams) obj;
		if (menuName == null) {
			if (other.menuName != null) { 
				return false;
			}
		} 
		else if (!menuName.equals(other.menuName)) { 
			return false;
		}
		if (parentId == null) {
			if (other.parentId != null) { 
				return false;
			}
		} 
		else if (!parentId.equals(other.parentId)) { 
			return false;
		}
		if (position == null) {
			if (other.position != null) { 
				return false;
			}
		} 
		else if (!position.equals(other.position)) { 
			return false;
		}
		return true;
	}
}
