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

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;
import java.util.List;

/**
 * <p>This class will add a command to the application's Command Menu</p>
 *
 *
 * <p><b>Note:</b> A command will be added to the end of the list of elements in
 * the Command Menu under the following conditions:</p>
 *
 * <ul>
 * <li>When a Command is added with no MenuParams value provided</li>
 * <li>When a MenuParams value is provided with a MenuParam.position value
 * greater than or equal to the number of menu items currently defined in the
 * menu specified by the MenuParam.parentID value</li>
 * </ul>
 *
 *
 * <p>The set of choices which the application builds using AddCommand can be a
 * mixture of:</p>
 *
 * <ul>
 * <li>Choices having only VR synonym definitions, but no MenuParams definitions
 * </li>
 * <li>Choices having only MenuParams definitions, but no VR synonym definitions
 * </li>
 * <li>Choices having both MenuParams and VR synonym definitions</li>
 * </ul>
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
 * 			<td>cmdID</td>
 * 			<td> Integer</td>
 * 			<td>unique ID of the command to add</td>
 *                 <td>Y</td>
 * 			<td> minvalue:0; maxvalue:2000000000</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		<tr>
 * 			<td>menuParams</td>
 * 			<td>ButtonName</td>
 * 			<td>Name of the button to unsubscribe.</td>
 *                 <td>Y</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>vrCommands</td>
 * 			<td>String</td>
 * 			<td><p>An array of strings to be used as VR synonyms for this command.</p>    	<p>If this array is provided, it may not be empty.</p></td>
 *                 <td>N</td>
 * 			<td>minsize:1; maxsize:100</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		<tr>
 * 			<td>cmdIcon</td>
 * 			<td>Image</td>
 * 			<td><p>Image struct determining whether static or dynamic icon.</p><p>If omitted on supported displays, no (or the default if applicable) icon shall be displayed.</p></td>
 *                 <td>N</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *  </table>
 * <p> <b>Response</b></p><p>Indicates that the corresponding request has failed or succeeded, if the response returns with a SUCCESS result code, this means a command was added to the Command Menu successfully.</p>
 *
 *  <p><b>Non-default Result Codes:</b></p> <p>INVALID_ID</p> <p>DUPLICATE_NAME</p>
 *
 * @see DeleteCommand
 * @see AddSubMenu
 * @see DeleteSubMenu
 * @since SmartDeviceLink 1.0
 */

public class AddCommand extends RPCRequest {
    public static final String KEY_CMD_ICON = "cmdIcon";
    public static final String KEY_MENU_PARAMS = "menuParams";
    public static final String KEY_CMD_ID = "cmdID";
    public static final String KEY_VR_COMMANDS = "vrCommands";

    /**
     * Constructs a new AddCommand object
     */
    public AddCommand() {
        super(FunctionID.ADD_COMMAND.toString());
    }

    /**
     * <p>
     * Constructs a new AddCommand object indicated by the Hashtable
     * parameter
     * </p>
     *
     * @param hash The Hashtable to use
     */
    public AddCommand(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new AddCommand object
     *
     * @param cmdID an integer object representing a Command ID <p><b>Notes:</b> Min Value: 0; Max Value: 2000000000</p>
     */
    public AddCommand(@NonNull Integer cmdID) {
        this();
        setCmdID(cmdID);
    }

    /**
     * <p>
     * Returns an <i>Integer</i> object representing the Command ID that you want to add
     * </p>
     *
     * @return Integer -an integer representation a Unique Command ID
     */
    public Integer getCmdID() {
        return getInteger(KEY_CMD_ID);
    }

    /**
     * <p>Sets an Unique Command ID that identifies the command. Is returned in an
     * <i>{@linkplain OnCommand}</i> notification to identify the command
     * selected by the user</p>
     *
     * @param cmdID an integer object representing a Command ID
     *              <p>
     *              <b>Notes:</b> Min Value: 0; Max Value: 2000000000</p>
     */
    public AddCommand setCmdID(@NonNull Integer cmdID) {
        setParameters(KEY_CMD_ID, cmdID);
        return this;
    }

    /**
     * <p>
     * Returns a <I>MenuParams</I> object which will defined the command and how
     * it is added to the Command Menu
     * </p>
     *
     * @return MenuParams -a MenuParams object
     */
    public MenuParams getMenuParams() {
        return (MenuParams) getObject(MenuParams.class, KEY_MENU_PARAMS);
    }

    /**
     * <p>
     * Sets Menu parameters</p>
     * If provided, this will define the command and how it is added to the
     * Command Menu<p>
     * If null, commands will not be accessible through the HMI application menu
     * </p>
     *
     * @param menuParams a menuParams object
     */
    public AddCommand setMenuParams(MenuParams menuParams) {
        setParameters(KEY_MENU_PARAMS, menuParams);
        return this;
    }

    /**
     * <p>
     * Gets Voice Recognition Commands
     * </p>
     *
     * @return List<String> -(List<String>) indicating one or more VR phrases
     */
    @SuppressWarnings("unchecked")
    public List<String> getVrCommands() {
        return (List<String>) getObject(String.class, KEY_VR_COMMANDS);
    }

    /**
     * <p>
     * Sets Voice Recognition Commands </p>
     * <p>If provided, defines one or more VR phrases the recognition of any of
     * which triggers the <i>{@linkplain OnCommand}</i> notification with this
     * cmdID</p>
     * <p>If null, commands will not be accessible by voice commands (when the user
     * hits push-to-talk)
     * </p>
     *
     * @param vrCommands List<String> indicating one or more VR phrases
     *                   <p>
     *                   <b>Notes: </b>Optional only if menuParams is provided. If
     *                   provided, array must contain at least one non-empty (not null,
     *                   not zero-length, not whitespace only) element</p>
     */
    public AddCommand setVrCommands(List<String> vrCommands) {
        setParameters(KEY_VR_COMMANDS, vrCommands);
        return this;
    }

    /**
     * <p>Gets the image to be shown along with a command </p>
     *
     * @return Image -an Image object
     * @since SmartDeviceLink 2.0
     */
    public Image getCmdIcon() {
        return (Image) getObject(Image.class, KEY_CMD_ICON);
    }

    /**
     * <p>Sets the Image
     * If provided, defines the image to be be shown along with a  command</p>
     *
     * @param cmdIcon <p>an Image obj representing the Image obj shown along with a
     *                command</p>
     *                <p>
     *                <b>Notes: </b>If omitted on supported displays, no (or the
     *                default if applicable) icon will be displayed</p>
     * @since SmartDeviceLink 2.0
     */
    public AddCommand setCmdIcon(Image cmdIcon) {
        setParameters(KEY_CMD_ICON, cmdIcon);
        return this;
    }
}
