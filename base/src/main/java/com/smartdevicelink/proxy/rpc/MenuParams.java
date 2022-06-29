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

import androidx.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

/**
 * Used when adding a sub menu to an application menu or existing sub menu.
 * <p><b> Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 		  <th>Req.</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>parentID</td>
 * 			<td>Integer</td>
 * 			<td>The unique ID of an existing submenu to which a command will be added.
 * 					If this element is not provided, the command will be added to the top level of the Command Menu.
 * 					<ul>
 * 					<li>Min: 0</li>
 * 					<li>Max: 2000000000</li>
 * 					</ul>
 * 			</td>
 * 		 <th></th>
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
 * 		  <th></th>
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
 * 		 <th></th>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *      <tr>
 *          <td>secondaryText</td>
 *          <td>String</td>
 *          <td>Optional secondary text to display <br><br> {"string_min_length": 1, "string_max_length": 500}</td>
 *          <td>N</td>
 *          <td>
 *              SmartDeviceLink 7.1.0
 *          </td>
 *      </tr>
 *      <tr>
 *          <td>tertiaryText</td>
 *          <td>String</td>
 *          <td>Optional tertiary text to display <br><br> {"string_min_length": 1, "string_max_length": 500}</td>
 *          <td>N</td>
 *          <td>
 *             SmartDeviceLink 7.1.0
 *          </td>
 *      </tr>
 * </table>
 *
 * @see AddCommand
 * @see AddSubMenu
 * @since SmartDeviceLink 1.0
 */
public class MenuParams extends RPCStruct {
    public static final String KEY_PARENT_ID = "parentID";
    public static final String KEY_POSITION = "position";
    public static final String KEY_MENU_NAME = "menuName";
    /**
     * @since SmartDeviceLink 7.1.0
     */
    public static final String KEY_SECONDARY_TEXT = "secondaryText";
    /**
     * @since SmartDeviceLink 7.1.0
     */
    public static final String KEY_TERTIARY_TEXT = "tertiaryText";

    /**
     * Constructs a newly allocated MenuParams object
     */
    public MenuParams() {
    }

    /**
     * Constructs a newly allocated MenuParams object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public MenuParams(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a newly allocated MenuParams object
     *
     * @param menuName the menu name
     */
    public MenuParams(@NonNull String menuName) {
        this();
        setMenuName(menuName);
    }

    /**
     * Get the unique ID of an existing submenu to which a command will be added.
     * If this element is not provided, the command will be added to the top level of the Command Menu.
     *
     * @return parentID Min: 0 Max: 2000000000
     */
    public Integer getParentID() {
        return getInteger(KEY_PARENT_ID);
    }

    /**
     * Set the unique ID of an existing submenu to which a command will be added.
     * If this element is not provided, the command will be added to the top level of the Command Menu.
     *
     * @param parentID Min: 0; Max: 2000000000
     */
    public MenuParams setParentID(Integer parentID) {
        setValue(KEY_PARENT_ID, parentID);
        return this;
    }

    /**
     * Get the position within the items of the parent Command Menu. 0 will insert at the front, 1 will insert after the first existing element, etc.
     * Position of any submenu will always be located before the return and exit options.
     * <ul>
     * 	<li>Min Value: 0</li>
     * 	<li>Max Value: 1000</li>
     * 	<li>If position is greater or equal than the number of items in the parent Command Menu, the sub menu will be appended to the end of that Command Menu.</li>
     * 	<li>If this element is omitted, the entry will be added at the end of the parent menu.</li>
     * </ul>
     *
     * @return the position within the items of the parent Command Menu
     */
    public Integer getPosition() {
        return getInteger(KEY_POSITION);
    }

    /**
     * Set the position within the items of the parent Command Menu. 0 will insert at the front, 1 will insert after the first existing element, etc.
     * Position of any submenu will always be located before the return and exit options.
     * <ul>
     * 	<li>Min Value: 0</li>
     * 	<li>Max Value: 1000</li>
     * 	<li>If position is greater or equal than the number of items in the parent Command Menu, the sub menu will be appended to the end of that Command Menu.</li>
     * 	<li>If this element is omitted, the entry will be added at the end of the parent menu.</li>
     * </ul>
     *
     * @param position Mix: 0 Max: 1000
     */
    public MenuParams setPosition(Integer position) {
        setValue(KEY_POSITION, position);
        return this;
    }

    /**
     * Get the text which appears in menu, representing this command.
     *  			<ul>
     * 	<li>Min: 1</li>
     * 	<li>Max: 100</li>
     * </ul>
     *
     * @return menuName the menu name
     */

    public String getMenuName() {
        return getString(KEY_MENU_NAME);
    }

    /**
     * Set text which appears in menu, representing this command.
     *  			<ul>
     * 	<li>Min: 1</li>
     * 	<li>Max: 100</li>
     * </ul>
     *
     * @param menuName the menu name
     */

    public MenuParams setMenuName(@NonNull String menuName) {
        setValue(KEY_MENU_NAME, menuName);
        return this;
    }


    /**
     * Sets the secondaryText.
     *
     * @param secondaryText Optional secondary text to display
     * {"string_min_length": 1, "string_max_length": 500}
     * @since SmartDeviceLink 7.1.0
     */
    public MenuParams setSecondaryText(String secondaryText) {
        setValue(KEY_SECONDARY_TEXT, secondaryText);
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
    public MenuParams setTertiaryText(String tertiaryText) {
        setValue(KEY_TERTIARY_TEXT, tertiaryText);
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
}
