/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;

/**
 * <p>Add a SubMenu to the Command Menu</p>
 * 
 * <p>A SubMenu can only be added to the Top Level Menu (i.e.a SubMenu cannot be
 * added to a SubMenu), and may only contain commands as children</p>
 * 
 * 
 * <p><b>HMILevel needs to be FULL, LIMITED or BACKGROUD</b></p>
 * 
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th> Req.</th>
 * 			<th>Notes</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>menuID</td>
 * 			<td>Integer</td>
 * 			<td>Unique ID that identifies this sub menu. This value is used in AddCommand to which SubMenu is the parent of the command being added.</td>
 *                 <td>Y</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>position</td>
 * 			<td>Integer</td>
 * 			<td>Position within the items of the top level Command Menu. 0 will insert at the front, 1 will insert after the first existing element, etc. Position of any submenu will always be located before the return and exit options.</td>
 *                 <td>N</td>
 * 			<td>Min Value: 0 <p>Max Value: 1000</p> <p>If position is greater or equal than the number of items on top level, the sub menu will be appended by the end.</p><p>If this parameter is omitted, the entry will be added at the end of the list.</P></td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>menuName</td>
 * 			<td>String</td>
 * 			<td>Text which is displayed representing this submenu item</td>
 *                 <td>Y</td>
 * 			<td>maxlength:500</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>menuIcon</td>
 * 			<td>Image</td>
 * 			<td>Image to be be shown along with the submenu item</td>
 *                 <td>N</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 5.0</td>
 * 		</tr>
 *  </table>
 *  <b>Response</b>
 *  <p>Indicates that the corresponding request either failed or succeeded. If the response returns with a SUCCESS result code, this means the SubMenu was added to the Command Menu successfully</p>
 *  
 *  <p><b>Non-default Result Codes:</b></p>
 *  <p>INVALID_ID</p>
 *  <p>DUPLICATE NAME</p>
 * @since SmartDeviceLink 1.0
 * @see DeleteSubMenu
 * @see AddCommand
 * @see DeleteCommand
 */
public class AddSubMenu extends RPCRequest {
	public static final String KEY_POSITION = "position";
	public static final String KEY_MENU_NAME = "menuName";
	public static final String KEY_MENU_ID = "menuID";
	public static final String KEY_MENU_ICON = "menuIcon";

	/**
	 * Constructs a new AddSubMenu object
	 */
	public AddSubMenu() {
        super(FunctionID.ADD_SUB_MENU.toString());
    }
	/**
	 * Constructs a new AddSubMenu object indicated by the Hashtable parameter
	 * 
	 * 
	 * @param hash The Hashtable to use
	 */
    public AddSubMenu(Hashtable<String, Object> hash) {
        super(hash);
    }
	/**
	 * Constructs a new AddSubMenu object
	 * @param menuID an integer object representing a Menu ID
	 * <p><b>Notes:</b> Min Value: 0; Max Value: 2000000000</p>
	 * @param menuName String which will be displayed representing this submenu item
	 */
	public AddSubMenu(@NonNull Integer menuID, @NonNull String menuName) {
		this();
		setMenuID(menuID);
		setMenuName(menuName);
	}
	/**
	 * Returns an <i>Integer</i> object representing the Menu ID that identifies
	 * a sub menu
	 *
	 * @return Integer -an integer representing the Menu ID that identifies a sub
	 *         menu
	 */
    public Integer getMenuID() {
        return getInteger( KEY_MENU_ID );
    }
	/**
	 * <p>Sets a Menu ID that identifies a sub menu.</p><p> This value is used in
	 * {@linkplain AddCommand} to which SubMenu is the parent of the command
	 * being added</p>
	 * 
	 * 
	 * @param menuID
	 *            an integer object representing a Menu ID
	 *            
	 *           <p><b>Notes:</b> Min Value: 0; Max Value: 2000000000</p>
	 */    
    public void setMenuID( @NonNull Integer menuID ) {
		setParameters(KEY_MENU_ID, menuID);
    }
	/**
	 * <p>Returns an <i>Integer</i> object representing the position of menu</p>
	 * 
	 * 
	 * @return Integer -the value representing the relative position of menus
	 */    
    public Integer getPosition() {
        return getInteger( KEY_POSITION );
    }
	/**
	 * Sets a position of menu
	 * 
	 * @param position
	 *            <p>An Integer object representing the position within the items
	 *            of the top level Command Menu. 0 will insert at the front, 1
	 *            will insert after the first existing element, etc. Position of
	 *            any submenu will always be located before the return and exit
	 *            options
	 *            </p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>
	 *            Min Value: 0; Max Value: 1000</li>
	 *            <li>If position is greater than or equal to the number of items
	 *            on top level, the sub menu will be appended by the end</li>
	 *            <li>If this parameter is omitted, the entry will be added at
	 *            the end of the list</li>
	 *            </ul>
	 */    
    public void setPosition( Integer position ) {
		setParameters(KEY_POSITION, position);
    }
	/**
	 * Returns String which is displayed representing this submenu item
	 * 
	 * @return String -a Submenu item's name
	 */
    public String getMenuName() {
        return getString( KEY_MENU_NAME );
    }
	/**
	 * Sets a menuName which is displayed representing this submenu item
	 * 
	 * @param menuName
	 *            String which will be displayed representing this submenu item
	 */    
    public void setMenuName( @NonNull String menuName ) {
		setParameters(KEY_MENU_NAME, menuName);
    }
	/**
	 * Returns Image to be be shown along with the submenu item
	 *
	 * @return Image - the submenu icon
	 */
	public Image getMenuIcon() {
		return (Image) getObject(Image.class, KEY_MENU_ICON);
	}
	/**
	 * Sets image to be be shown along with the submenu item
	 *
	 * @param menuIcon
	 *            Image to be be shown along with the submenu item
	 */
	public void setMenuIcon(Image menuIcon) {
		setParameters(KEY_MENU_ICON, menuIcon);
	}
}
