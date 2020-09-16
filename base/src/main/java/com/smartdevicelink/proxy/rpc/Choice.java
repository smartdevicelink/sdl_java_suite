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
import com.smartdevicelink.util.Version;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * A choice is an option which a user can select either via the menu or via voice recognition (VR) during an application initiated interaction.
 *  For example, the application may request for the user`s choice among several suggested ones: Yes, No, Skip.
 * <p><b> Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>choiceID</td>
 * 			<td>Integer</td>
 * 			<td>Application-scoped identifier that uniquely identifies this choice.
 *             Min: 0;
 *				Max: 65535
 *			</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>menuName</td>
 * 			<td>String</td>
 * 			<td>Text which appears in menu, representing this choice.
 *				Min: 1;
 *				Max: 100
 * 			</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *     <tr>
 * 			<td>vrCommands</td>
 * 			<td>String[]</td>
 * 			<td>An array of strings to be used as VR synonyms for this choice. If this array is provided, it must have at least one non-empty element</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *     <tr>
 * 			<td>image</td>
 * 			<td>Image</td>
 * 			<td>Either a static hex icon value or a binary image file  name identifier (sent by PutFile).</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * </table>
 * 
  * @since SmartDeviceLink 1.0
  * 
  * @see AddCommand
  * @see PerformInteraction
  * @see Image
 */
public class Choice extends RPCStruct {
	public static final String KEY_SECONDARY_TEXT = "secondaryText";
	public static final String KEY_TERTIARY_TEXT = "tertiaryText";
	public static final String KEY_SECONDARY_IMAGE = "secondaryImage";
	public static final String KEY_MENU_NAME = "menuName";
	public static final String KEY_VR_COMMANDS = "vrCommands";
	public static final String KEY_CHOICE_ID = "choiceID";
	public static final String KEY_IMAGE = "image";

    /**
     * Used to bypass the format() method that adds VR items based on RPC version. This is used by the
     * choiceSetManager, which has a more in-depth approach as to whether or not it should add VR items
     */
	private boolean ignoreAddingVRItems;

	/**
	 * Constructs a newly allocated Choice object
	 */
    public Choice() { }

    /**
     * Constructs a newly allocated Choice object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */    
    public Choice(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a newly allocated Choice object
     * @param choiceID Min: 0  Max: 65535
     * @param menuName the menu name
     */
    public Choice(@NonNull Integer choiceID, @NonNull String menuName) {
        this();
        setChoiceID(choiceID);
        setMenuName(menuName);
    }

    /**
     * VrCommands became optional as of RPC Spec 5.0. On legacy systems, we must still set VrCommands, as
     * they are expected, even though the developer may not specify them. <br>
     *
     * Additionally, VrCommands must be unique, therefore we will use the string value of the command's ID
     *
     * @param rpcVersion the rpc spec version that has been negotiated. If value is null the
     *                   the max value of RPC spec version this library supports should be used.
     * @param formatParams if true, the format method will be called on subsequent params
     */
    @Override
    public void format(Version rpcVersion, boolean formatParams){

        if (rpcVersion == null || rpcVersion.getMajor() < 5){

            // this is added to allow the choice set manager to disable this functionality
            if (!ignoreAddingVRItems) {
                // make sure there is at least one vr param
                List<String> existingVrCommands = getVrCommands();

                if (existingVrCommands == null || existingVrCommands.size() == 0) {
                    // if no commands set, set one due to a legacy head unit requirement
                    Integer choiceID = getChoiceID();
                    List<String> vrCommands = new ArrayList<>();
                    vrCommands.add(String.valueOf(choiceID));
                    setVrCommands(vrCommands);
                }
            }
        }

        super.format(rpcVersion, formatParams);
    }

    /**
     * Get the application-scoped identifier that uniquely identifies this choice.
     * @return choiceID Min: 0;  Max: 65535
     */
    public Integer getChoiceID() {
        return getInteger(KEY_CHOICE_ID);
    }
    /**
     * Set the application-scoped identifier that uniquely identifies this choice.
     * @param choiceID Min: 0  Max: 65535
     */
    public Choice setChoiceID(@NonNull Integer choiceID) {
        setValue(KEY_CHOICE_ID, choiceID);
        return this;
    }
    /**
     * Text which appears in menu, representing this choice.
     *				Min: 1;
     *				Max: 100
     * @return menuName the menu name
     */    
    public String getMenuName() {
        return getString(KEY_MENU_NAME);
    }
    /**
     * Text which appears in menu, representing this choice.
     *				Min: 1;
     *				Max: 100
     * @param menuName the menu name
     */
    public Choice setMenuName(@NonNull String menuName) {
        setValue(KEY_MENU_NAME, menuName);
        return this;
    }
    /**
     * Get an array of strings to be used as VR synonyms for this choice. If this array is provided, it must have at least one non-empty element
     * @return vrCommands List
     * @since SmartDeviceLink 2.0
     */    
    @SuppressWarnings("unchecked")
    public List<String> getVrCommands() {
        return (List<String>) getObject(String.class, KEY_VR_COMMANDS);
    }
    /**
     * Set an array of strings to be used as VR synonyms for this choice. If this array is provided, it must have at least one non-empty element
     * @param vrCommands the List of  vrCommands
     * @since SmartDeviceLink 2.0
     */
    public Choice setVrCommands( List<String> vrCommands) {
        setValue(KEY_VR_COMMANDS, vrCommands);
        return this;
    }
    /**
     * Set the image
     * @param image the image of the choice
     */
    public Choice setImage( Image image) {
        setValue(KEY_IMAGE, image);
        return this;
    }
    /**
     * Get the image
     * @return the image of the choice
     */    
    public Image getImage() {
        return (Image) getObject(Image.class, KEY_IMAGE);
    }
    
    public String getSecondaryText() {
        return getString(KEY_SECONDARY_TEXT);
    }

    public Choice setSecondaryText( String secondaryText) {
        setValue(KEY_SECONDARY_TEXT, secondaryText);
        return this;
    }

    public String getTertiaryText() {
        return getString(KEY_TERTIARY_TEXT);
    }

    public Choice setTertiaryText( String tertiaryText) {
        setValue(KEY_TERTIARY_TEXT, tertiaryText);
        return this;
    }

    public Choice setSecondaryImage( Image image) {
        setValue(KEY_SECONDARY_IMAGE, image);
        return this;
    }

    public Image getSecondaryImage() {
        return (Image) getObject(Image.class, KEY_SECONDARY_IMAGE);
    }

    /**
     * This prevents the @{link Choice#format} method from adding VR commands if set to true
     * @param ignoreAddingVRItems - whether or not to let the format method add vr commands
     */
    public Choice setIgnoreAddingVRItems( boolean ignoreAddingVRItems) {
        this.ignoreAddingVRItems = ignoreAddingVRItems;
        return this;
    }
}
