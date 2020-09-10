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
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.SystemAction;

import java.util.Hashtable;
/**
 * <p> A simulated button or keyboard key that is displayed on a touch screen.</p>
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
 * 			<td>type</td>
 * 			<td>SoftButtonType</td>
 * 			<td>Describes, whether it is text, highlighted text, icon, or dynamic image. </td>
 *                 <td></td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>text</td>
 * 			<td>String</td>
 * 			<td>Optional text to display (if defined as TEXT or BOTH)</td>
 *                 <td>N</td>
 *                 <td>Min: 0; Maxlength: 500</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>image</td>
 * 			<td>Image</td>
 * 			<td>Optional image struct for SoftButton (if defined as IMAGE or BOTH).</td>
 *                 <td></td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>isHighlighted</td>
 * 			<td>Boolean</td>
 * 			<td>True, if highlighted False, if not highlighted</td>
 *                 <td>N</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>softButtonID</td>
 * 			<td>Integer</td>
 * 			<td>Value which is returned via OnButtonPress / OnButtonEvent</td>
 *                 <td></td>
 *                 <td>Min: 0; Max: 65535</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>systemAction</td>
 * 			<td>SystemAction</td>
 * 			<td>Parameter indicating whether selecting a SoftButton shall call a specific system action. This is intended to allow Notifications to bring the callee into full / focus; or in the case of persistent overlays, the overlay can persist when a SoftButton is pressed.</td>
 *                 <td>N</td>
 *                 <td>defvalue: DEFAULT_ACTION</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>			
 * 			
 *  </table>
 *
 */
public class SoftButton extends RPCStruct {

	public static final String KEY_IS_HIGHLIGHTED = "isHighlighted";
	public static final String KEY_SOFT_BUTTON_ID = "softButtonID";
	public static final String KEY_SYSTEM_ACTION = "systemAction";
	public static final String KEY_TEXT = "text";
	public static final String KEY_TYPE = "type";
	public static final String KEY_IMAGE = "image";

	public SoftButton() { }

	/**
	* 
	* <p>Constructs a new SoftButton object indicated by the Hashtable
	* parameter</p>
	*
	* @param hash The Hashtable to use
	*/
	public SoftButton(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new SoftButton object
	 * @param type Describes, whether it is text, highlighted text, icon, or dynamic image.
	 * @param softButtonID Value which is returned via OnButtonPress / OnButtonEvent
	 */
	public SoftButton(@NonNull SoftButtonType type, @NonNull Integer softButtonID){
		this();
		setType(type);
		setSoftButtonID(softButtonID);
	}

    public SoftButton setType(@NonNull SoftButtonType type) {
        setValue(KEY_TYPE, type);
        return this;
    }
    public SoftButtonType getType() {
    	return (SoftButtonType) getObject(SoftButtonType.class, KEY_TYPE);
    }
    public SoftButton setText( String text) {
        setValue(KEY_TEXT, text);
        return this;
    }
    public String getText() {
        return getString(KEY_TEXT);
    }
    public SoftButton setImage( Image image) {
        setValue(KEY_IMAGE, image);
        return this;
    }
    @SuppressWarnings("unchecked")
    public Image getImage() {
    	return (Image) getObject(Image.class, KEY_IMAGE);
    }
    public SoftButton setIsHighlighted( Boolean isHighlighted) {
        setValue(KEY_IS_HIGHLIGHTED, isHighlighted);
        return this;
    }
    public Boolean getIsHighlighted() {
        return getBoolean(KEY_IS_HIGHLIGHTED);
    }
    public SoftButton setSoftButtonID(@NonNull Integer softButtonID) {
        setValue(KEY_SOFT_BUTTON_ID, softButtonID);
        return this;
    }
    public Integer getSoftButtonID() {
        return getInteger(KEY_SOFT_BUTTON_ID);
    }
    public SoftButton setSystemAction( SystemAction systemAction) {
        setValue(KEY_SYSTEM_ACTION, systemAction);
        return this;
    }
    public SystemAction getSystemAction() {
        return (SystemAction) getObject(SystemAction.class, KEY_SYSTEM_ACTION);
    }
}
