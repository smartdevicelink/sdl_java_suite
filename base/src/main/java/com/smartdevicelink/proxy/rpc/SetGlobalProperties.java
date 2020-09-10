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

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.MenuLayout;

import java.util.Hashtable;
import java.util.List;

/**
 * Sets value(s) for the specified global property(ies)
 * 
 * <p>Function Group: Base </p>
 * <p><b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b></p>
 * 
 * <p><b>AudioStreamingState:</b></p>
 * Any
 * 
 * <p><b>SystemContext:</b></p>
 * Any
 * 
 * 
 * <p><b>Parameter List</b></p>
 * 
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
 * 			<td>helpPrompt</td>
 * 			<td>TTSChunk</td>
 * 			<td>The help prompt. An array of text chunks of type TTSChunk. See {@linkplain TTSChunk}.The array must have at least one item.</td>
 *                 <td>N</td>
 * 			<td>Array must have at least one element.<p>Only optional it timeoutPrompt has been specified.</p>minsize:1; maxsize: 100</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>timeoutPrompt</td>
 * 			<td>TTSChunk</td>
 * 			<td>Array of one or more TTSChunk elements specifying the help prompt used in an interaction started by PTT.</td>
 *                 <td>N</td>
 * 			<td>Array must have at least one element. Only optional it helpPrompt has been specified <p>minsize: 1; maxsize: 100</p></td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>vrHelpTitle</td>
 * 			<td>string</td>
 * 			<td>Text, which is shown as title of the VR help screen used in an interaction started by PTT.</td>
 *                 <td>N</td>
 * 			<td>If omitted on supported displays, the default SDL help title will be used. <p>If omitted and one or more vrHelp items are provided, the request will be rejected.</p>maxlength: 500</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>vrHelp</td>
 * 			<td>VrHelp</td>
 * 			<td>Items listed in the VR help screen used in an interaction started by PTT.</td>
 *                 <td>N</td>
 * 			<td>If omitted on supported displays, the default SDL VR help / What Can I Say? screen will be used<p>If the list of VR Help Items contains nonsequential positions (e.g. [1,2,4]), the RPC will be rejected.</p><p>If omitted and a vrHelpTitle is provided, the request will be rejected.</p>minsize:1; maxsize: 100 </td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>menuTitle</td>
 * 			<td></td>
 * 			<td>Optional text to label an app menu button (for certain touchscreen platforms).</td>
 *                 <td>N</td>
 * 			<td>maxlength: 500</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>menuIcon</td>
 * 			<td> Image</td>
 * 			<td>Optional icon to draw on an app menu button (for certain touchscreen platforms).</td>
 *                 <td>N</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>keyboardProperties</td>
 * 			<td>KeyboardProperties</td>
 * 			<td>On-screen keyboard configuration (if available).</td>
 *                 <td>N</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>menuLayout</td>
 * 			<td>MenuLayout</td>
 * 			<td>Sets the layout of the main menu screen. If this is sent while a menu is already on-screen, the head unit will change the display to the new layout type.</td>
 * 			<td></td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 6.0</td>
 * 		</tr>
 *
 *  </table>
 *  
 * <p><b>Note: </b>Your application shall send a SetGlobalProperties to establish an advanced help prompt before sending any voice commands.</p>
 * 
 *  <p><b>Response</b></p>
 *  Indicates whether the requested Global Properties were successfully set. 
 *  <p><b>Non-default Result Codes:</b></p>
 *  <p>SUCCESS</p>
 *  <p>INVALID_DATA</p>
 *  </p>OUT_OF_MEMORY</p>
 *  <p>TOO_MANY_PENDING_REQUESTS</p>
 *  <p>APPLICATION_NOT_REGISTERED</p>
 *  <p>GENERIC_ERROR</p>
 *  <p>REJECTED</p>
 *  <p>DISALLOWED</p>
 * @since SmartDeviceLink 1.0
 * @see ResetGlobalProperties
 */
public class SetGlobalProperties extends RPCRequest {
	public static final String KEY_VR_HELP_TITLE = "vrHelpTitle";
	public static final String KEY_MENU_TITLE = "menuTitle";
	public static final String KEY_MENU_ICON = "menuIcon";
	public static final String KEY_KEYBOARD_PROPERTIES = "keyboardProperties";
	public static final String KEY_HELP_PROMPT = "helpPrompt";
	public static final String KEY_TIMEOUT_PROMPT = "timeoutPrompt";
	public static final String KEY_VR_HELP = "vrHelp";
	public static final String KEY_USER_LOCATION = "userLocation";
	public static final String KEY_MENU_LAYOUT = "menuLayout";
	/**
	 * Constructs a new SetGlobalProperties object
	 */
    public SetGlobalProperties() {
        super(FunctionID.SET_GLOBAL_PROPERTIES.toString());
    }
	/**
	 * Constructs a new SetGlobalProperties object indicated by the Hashtable
	 * parameter
	 * <p></p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */    
    public SetGlobalProperties(Hashtable<String, Object> hash) {
        super(hash);
    }
	/**
	 * Gets a List<TTSChunk> for Help Prompt representing Array of one or more
	 * TTSChunk elements specifying the help prompt used in an interaction
	 * started by PTT
	 * 
	 * @return List<TTSChunk> -an Array of one or more TTSChunk elements
	 *         specifying the help prompt used in an interaction started by PTT
	 */    
    @SuppressWarnings("unchecked")
    public List<TTSChunk> getHelpPrompt() {
		return (List<TTSChunk>) getObject(TTSChunk.class, KEY_HELP_PROMPT);
    }
	/**
	 * Sets a List<TTSChunk> for Help Prompt that Array of one or more
	 * TTSChunk elements specifying the help prompt used in an interaction
	 * started by PTT
	 *
	 * @param helpPrompt
	 *            a List<TTSChunk> of one or more TTSChunk elements
	 *            <p></p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>Array must have at least one element</li>
	 *            <li>Only optional it timeoutPrompt has been specified</li>
	 *            </ul>
	 */
    public SetGlobalProperties setHelpPrompt( List<TTSChunk> helpPrompt) {
        setParameters(KEY_HELP_PROMPT, helpPrompt);
        return this;
    }
	/**
	 * Gets a List<TTSChunk> for Timeout Prompt representing Array of one or
	 * more TTSChunk elements specifying the help prompt used in an interaction
	 * started by PTT
	 * 
	 * @return List<TTSChunk> -an Array of one or more TTSChunk elements
	 *         specifying the help prompt used in an interaction started by PTT
	 */    
    @SuppressWarnings("unchecked")
    public List<TTSChunk> getTimeoutPrompt() {
		return (List<TTSChunk>) getObject(TTSChunk.class, KEY_TIMEOUT_PROMPT);
    }
	/**
	 * Sets a List<TTSChunk> for Timeout Prompt representing Array of one or
	 * more TTSChunk elements specifying the help prompt used in an interaction
	 * started by PTT
	 *
	 */
    public SetGlobalProperties setTimeoutPrompt( List<TTSChunk> timeoutPrompt) {
        setParameters(KEY_TIMEOUT_PROMPT, timeoutPrompt);
        return this;
    }

	/**
	 * Gets a voice recognition Help Title
	 * 
	 * @return String - a String value representing the text, which is shown as
	 *         title of the VR help screen used in an interaction started by PTT
	 * @since SmartDeviceLink 2.0
	 */
    public String getVrHelpTitle() {
        return getString(KEY_VR_HELP_TITLE);
    }

	/**
	 * Sets a voice recognition Help Title
	 *
	 * @param vrHelpTitle
	 *            a String value representing a voice recognition Help Title
	 *            <p></p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>If omitted on supported displays, the default SDL help
	 *            title will be used</li>
	 *            <li>If omitted and one or more vrHelp items are provided, the
	 *            request will be rejected.</li>
	 *            <li>String Maxlength = 500</li>
	 *            </ul>
	 * @since SmartDeviceLink 2.0
	 */
    public SetGlobalProperties setVrHelpTitle( String vrHelpTitle) {
        setParameters(KEY_VR_HELP_TITLE, vrHelpTitle);
        return this;
    }

	/**
	 * Gets items listed in the VR help screen used in an interaction started by
	 * PTT
	 * 
	 * @return List<VrHelpItem> - a List value representing items listed in
	 *         the VR help screen used in an interaction started by PTT
	 * @since SmartDeviceLink 2.0
	 */
    @SuppressWarnings("unchecked")
    public List<VrHelpItem> getVrHelp() {
		return (List<VrHelpItem>) getObject(VrHelpItem.class, KEY_VR_HELP);
    }

	/**
	 * Sets the items listed in the VR help screen used in an interaction
	 * started by PTT
	 *
	 * @param vrHelp
	 *            a List value representing items listed in the VR help screen
	 *            used in an interaction started by PTT
	 *            <p></p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>If omitted on supported displays, the default SmartDeviceLink VR
	 *            help / What Can I Say? screen will be used</li>
	 *            <li>If the list of VR Help Items contains nonsequential
	 *            positions (e.g. [1,2,4]), the RPC will be rejected</li>
	 *            <li>If omitted and a vrHelpTitle is provided, the request
	 *            will be rejected</li>
	 *            <li>Array Minsize: = 1</li>
	 *            <li>Array Maxsize = 100</li>
	 *            </ul>
	 * @since SmartDeviceLink 2.0
	 */
    public SetGlobalProperties setVrHelp( List<VrHelpItem> vrHelp) {
        setParameters(KEY_VR_HELP, vrHelp);
        return this;
    }
    
    public String getMenuTitle() {
        return getString(KEY_MENU_TITLE);
    }

    public SetGlobalProperties setMenuTitle( String menuTitle) {
        setParameters(KEY_MENU_TITLE, menuTitle);
        return this;
    }

    public SetGlobalProperties setMenuIcon( Image menuIcon) {
        setParameters(KEY_MENU_ICON, menuIcon);
        return this;
    }

    public Image getMenuIcon() {
		return (Image) getObject(Image.class, KEY_MENU_ICON);
    }
    
    public SetGlobalProperties setKeyboardProperties( KeyboardProperties keyboardProperties) {
        setParameters(KEY_KEYBOARD_PROPERTIES, keyboardProperties);
        return this;
    }

	/**
	 * Sets the user seat location
	 * @param location the location to be set
	 */
	public SetGlobalProperties setUserLocation( SeatLocation location) {
        setParameters(KEY_USER_LOCATION, location);
        return this;
    }

	/**
	 * Gets the user seat location
	 * @return the user seat location
	 */
	public SeatLocation getUserLocation() {
    	return (SeatLocation) getObject(SeatLocation.class, KEY_USER_LOCATION);
	}

    public KeyboardProperties getKeyboardProperties() {
		return (KeyboardProperties) getObject(KeyboardProperties.class, KEY_KEYBOARD_PROPERTIES);
    }

	/**
	 * Sets the layout of the main menu screen. If this is sent while a menu is already on-screen,
	 * the head unit will change the display to the new layout type.
	 * @param menuLayout - the menuLayout
	 */
	public SetGlobalProperties setMenuLayout( MenuLayout menuLayout) {
        setParameters(KEY_MENU_LAYOUT, menuLayout);
        return this;
    }

	/**
	 * Sets the layout of the main menu screen. If this is sent while a menu is already on-screen,
	 * the head unit will change the display to the new layout type.
	 * @return the MenuLayout
	 */
	public MenuLayout getMenuLayout() {
		return (MenuLayout) getObject(MenuLayout.class, KEY_MENU_LAYOUT);
	}
    
}
