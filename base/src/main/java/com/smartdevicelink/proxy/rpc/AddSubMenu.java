/*
 * Copyright (c) 2017 - 2020, SmartDeviceLink Consortium, Inc.
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
 * Neither the name of the SmartDeviceLink Consortium Inc. nor the names of
 * its contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
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

import androidx.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.MenuLayout;

import java.util.Hashtable;

/**
 * <p>Add a SubMenu to the Command Menu</p>
 *
 * <p>A SubMenu can only be added to the Top Level Menu (i.e.a SubMenu cannot be
 * added to a SubMenu), and may only contain commands as children</p>
 *
 *
 * <p><b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b></p>
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
 * 		<tr>
 * 			<td>menuLayout</td>
 * 			<td>MenuLayout</td>
 * 			<td>Sets the layout of the submenu screen.</td>
 * 			<td></td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 6.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>parentID</td>
 * 			<td>Integer</td>
 * 			<td>unique ID of the sub menu, the command will be added to. If not provided or 0, it will be provided to the top level of the in application menu.</td>
 * 			<td>N</td>
 * 		    <td>Min Value: 0 <p>Max Value: 2000000000</p> </td>
 * 			<td>SmartDeviceLink 7.0.0</td>
 * 		</tr>
 *      <tr>
 *          <td>secondaryText</td>
 *          <td>String</td>
 *          <td>Optional secondary text to display</td>
 *          <td>N</td>
 *          <td>{"string_min_length": 1, "string_max_length": 500}</td>
 *          <td>SmartDeviceLink 7.1.0</td>
 *      </tr>
 *      <tr>
 *          <td>tertiaryText</td>
 *          <td>String</td>
 *          <td>Optional tertiary text to display</td>
 *          <td>N</td>
 *          <td>{"string_min_length": 1, "string_max_length": 500}</td>
 *          <td>SmartDeviceLink 7.1.0</td>
 *      </tr>
 *      <tr>
 *          <td>secondaryImage</td>
 *          <td>Image</td>
 *          <td>Optional secondary image struct for sub-menu cell</td>
 *          <td>N</td>
 *          <td></td>
 *          <td> SmartDeviceLink 7.1.0 </td>
 *      </tr>
 *  </table>
 *  <b>Response</b>
 *  <p>Indicates that the corresponding request either failed or succeeded. If the response returns with a SUCCESS result code, this means the SubMenu was added to the Command Menu successfully</p>
 *
 *  <p><b>Non-default Result Codes:</b></p>
 *  <p>INVALID_ID</p>
 *  <p>DUPLICATE NAME</p>
 *
 * @see DeleteSubMenu
 * @see AddCommand
 * @see DeleteCommand
 * @since SmartDeviceLink 1.0
 */
public class AddSubMenu extends RPCRequest {
    public static final String KEY_POSITION = "position";
    public static final String KEY_MENU_NAME = "menuName";
    public static final String KEY_MENU_ID = "menuID";
    public static final String KEY_MENU_ICON = "menuIcon";
    public static final String KEY_MENU_LAYOUT = "menuLayout";
    public static final String KEY_PARENT_ID = "parentID";
    public static final String KEY_SECONDARY_TEXT = "secondaryText";
    public static final String KEY_TERTIARY_TEXT = "tertiaryText";
    public static final String KEY_SECONDARY_IMAGE = "secondaryImage";


    /**
     * Constructs a new AddSubMenu object
     */
    public AddSubMenu() {
        super(FunctionID.ADD_SUB_MENU.toString());
    }

    /**
     * Constructs a new AddSubMenu object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public AddSubMenu(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new AddSubMenu object
     *
     * @param menuID   an integer object representing a Menu ID
     *                 <p><b>Notes:</b> Min Value: 0; Max Value: 2000000000</p>
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
     * menu
     */
    public Integer getMenuID() {
        return getInteger(KEY_MENU_ID);
    }

    /**
     * <p>Sets a Menu ID that identifies a sub menu.</p><p> This value is used in
     * {@linkplain AddCommand} to which SubMenu is the parent of the command
     * being added</p>
     *
     * @param menuID an integer object representing a Menu ID
     *
     *               <p><b>Notes:</b> Min Value: 0; Max Value: 2000000000</p>
     */
    public AddSubMenu setMenuID(@NonNull Integer menuID) {
        setParameters(KEY_MENU_ID, menuID);
        return this;
    }

    /**
     * <p>Returns an <i>Integer</i> object representing the position of menu</p>
     *
     * @return Integer -the value representing the relative position of menus
     */
    public Integer getPosition() {
        return getInteger(KEY_POSITION);
    }

    /**
     * Sets a position of menu
     *
     * @param position <p>An Integer object representing the position within the items
     *                 of the top level Command Menu. 0 will insert at the front, 1
     *                 will insert after the first existing element, etc. Position of
     *                 any submenu will always be located before the return and exit
     *                 options
     *                 </p>
     *                 <b>Notes: </b>
     *                 <ul>
     *                 <li>
     *                 Min Value: 0; Max Value: 1000</li>
     *                 <li>If position is greater than or equal to the number of items
     *                 on top level, the sub menu will be appended by the end</li>
     *                 <li>If this parameter is omitted, the entry will be added at
     *                 the end of the list</li>
     *                 </ul>
     */
    public AddSubMenu setPosition(Integer position) {
        setParameters(KEY_POSITION, position);
        return this;
    }

    /**
     * Returns String which is displayed representing this submenu item
     *
     * @return String -a Submenu item's name
     */
    public String getMenuName() {
        return getString(KEY_MENU_NAME);
    }

    /**
     * Sets a menuName which is displayed representing this submenu item
     *
     * @param menuName String which will be displayed representing this submenu item
     */
    public AddSubMenu setMenuName(@NonNull String menuName) {
        setParameters(KEY_MENU_NAME, menuName);
        return this;
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
     * @param menuIcon Image to be be shown along with the submenu item
     */
    public AddSubMenu setMenuIcon(Image menuIcon) {
        setParameters(KEY_MENU_ICON, menuIcon);
        return this;
    }

    /**
     * Sets the layout of the submenu screen.
     *
     * @param menuLayout - the menuLayout
     */
    public AddSubMenu setMenuLayout(MenuLayout menuLayout) {
        setParameters(KEY_MENU_LAYOUT, menuLayout);
        return this;
    }

    /**
     * Gets the layout of the submenu screen.
     *
     * @return the MenuLayout
     */
    public MenuLayout getMenuLayout() {
        return (MenuLayout) getObject(MenuLayout.class, KEY_MENU_LAYOUT);
    }

    /**
     * Sets the parentID.
     *
     * @param parentID unique ID of the sub menu, the command will be added to. If not provided or 0, it will be
     *                 provided to the top level of the in application menu.
     * @since SmartDeviceLink 7.0.0
     */
    public AddSubMenu setParentID(Integer parentID) {
        setParameters(KEY_PARENT_ID, parentID);
        return this;
    }

    /**
     * Gets the parentID.
     *
     * @return Integer unique ID of the sub menu, the command will be added to. If not provided or 0, it will be
     * provided to the top level of the in application menu.
     * @since SmartDeviceLink 7.0.0
     */
    public Integer getParentID() {
        return getInteger(KEY_PARENT_ID);
    }

    /**
     * Sets the secondaryText.
     *
     * @param secondaryText Optional secondary text to display
     * {"string_min_length": 1, "string_max_length": 500}
     * @since SmartDeviceLink 7.1.0
     */
    public AddSubMenu setSecondaryText(String secondaryText) {
        setParameters(KEY_SECONDARY_TEXT, secondaryText);
        return this;
    }

    /**
     * Gets the secondaryText.
     *
     * @return String Optional secondary text to display
     * {"string_min_length": 1, "string_max_length": 500}
     * @since SmartDeviceLink 7.1.0
     */
    public String getSecondaryText() {
        return getString(KEY_SECONDARY_TEXT);
    }

    /**
     * Sets the tertiaryText.
     *
     * @param tertiaryText Optional tertiary text to display
     * {"string_min_length": 1, "string_max_length": 500}
     * @since SmartDeviceLink 7.1.0
     */
    public AddSubMenu setTertiaryText(String tertiaryText) {
        setParameters(KEY_TERTIARY_TEXT, tertiaryText);
        return this;
    }

    /**
     * Gets the tertiaryText.
     *
     * @return String Optional tertiary text to display
     * {"string_min_length": 1, "string_max_length": 500}
     * @since SmartDeviceLink 7.1.0
     */
    public String getTertiaryText() {
        return getString(KEY_TERTIARY_TEXT);
    }

    /**
     * Sets the secondaryImage.
     *
     * @param secondaryImage Optional secondary image struct for sub-menu cell
     * @since SmartDeviceLink 7.1.0
     */
    public AddSubMenu setSecondaryImage(Image secondaryImage) {
        setParameters(KEY_SECONDARY_IMAGE, secondaryImage);
        return this;
    }

    /**
     * Gets the secondaryImage.
     *
     * @return Image Optional secondary image struct for sub-menu cell
     * @since SmartDeviceLink 7.1.0
     */
    public Image getSecondaryImage() {
        return (Image) getObject(Image.class, KEY_SECONDARY_IMAGE);
    }
}
