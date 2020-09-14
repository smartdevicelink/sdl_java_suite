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

import java.util.Hashtable;
import java.util.List;

/**
 * Provides information to the user using either TTS, the Display or both and
 * can include a system-generated alert tone.
 *
 * If connecting to SDL Core v.6.0+, the alert can be canceled programmatically using the `cancelID`. Canceling will not dismiss the alert's speech - only the modal view will be dismissed. On older versions of SDL Core, the alert will persist until the user has interacted with the alert or the specified timeout has elapsed.
 * 
 * <ul>
 * <li>The displayed portion of the Alert, if any, will persist until the
 * specified timeout has elapsed, or the Alert is preempted</li>
 * <li>An Alert will preempt (abort) any SmartDeviceLink Operation that is in-progress,
 * except an already-in-progress Alert</li>
 * <li>An Alert cannot be preempted by any SmartDeviceLink Operation</li>
 * <li>An Alert can be preempted by a user action (button push)</li>
 * <li>An Alert will fail if it is issued while another Alert is in progress</li>
 * <li>Although each Alert parameter is optional, in fact each Alert request
 * must supply at least one of the following parameters:
 * <ul>
 * <li>alertText1</li>
 * <li>alertText2</li>
 * <li>alertText3</li>
 * <li>ttsChunks</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * <p><b>HMILevel needs to be FULL or LIMITED.</b></p>
 * <b>If the app has been granted function group Notification the HMILevel can
 * also be BACKGROUND</b>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *          <th>Req.</th>
 * 			<th>Notes</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>alertText1</td>
 * 			<td>String</td>
 * 			<td>Text to be displayed in the first field of the display during the Alert. </td>
 *          <td>N</td>
 * 			<td>Length is limited to what is indicated in RegisterAppInterface response.  If omitted, top display line will be cleared. Text is always centered</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>alertText2</td>
 * 			<td>String</td>
 * 			<td>Text to be displayed in the second field of the display during the Alert. </td>
 *          <td>N</td>
 * 			<td>Only permitted if HMI supports a second display line.	Length is limited to what is indicated in RegisterAppInterface response. 	If omitted, second display line will be cleared.  </td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>alertText3</td>
 * 			<td>String</td>
 * 			<td>Text to be displayed in the third field of the display during the Alert.</td>
 *          <td>N</td>
 * 			<td>Array must have a least one element. </td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>ttsChunks</td>
 * 			<td>TTSChunk[]</td>
 * 			<td>Array of type TTSChunk which, taken together, specify what is to be spoken to the user.</td>
 *          <td>N</td>
 * 			<td>Array must have a least one element. </td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>duration</td>
 * 			<td>Integer</td>
 * 			<td>The duration of the displayed portion of the alert, in milliseconds. After this amount of time has passed, the display fields alertText1 and alertText2 will revert to what was displayed in those fields before the alert began.</td>
 *          <td>N</td>
 * 			<td>Min Value: 3000 Max Value: 10000. If omitted, the default is 5000 milliseconds</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>playTone</td>
 * 			<td>Boolean</td>
 * 			<td>Specifies whether the alert tone should be played before the TTS (if any) is spoken.</td>
 *          <td>N</td>
 * 			<td>If omitted, default is true.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>softButtons</td>
 * 			<td>SoftButton[]</td>
 * 			<td>Specifies the soft buttons, the apps wants to use in this alert.</td>
 *          <td>N</td>
 * 			<td>If omitted on supported displays, the alert will not have any SoftButton.ArrayMin: 0; ArrayMax: 4</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>progressIndicator</td>
 * 			<td>Boolean</td>
 * 			<td>If supported on the given platform, the alert GUI will include some sort of animation indicating that loading of a feature is progressing.  e.g. a spinning wheel or hourglass, etc.</td>
 *         	<td>N</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>cancelID</td>
 * 			<td>Integer</td>
 * 			<td>An ID for this specific alert to allow cancellation through the `CancelInteraction` RPC.</td>
 *          <td>N</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 6.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>alertIcon</td>
 * 			<td>Image</td>
 * 			<td>Image struct determining whether the icon is static or dynamic. If omitted on supported displays, no (or the default if applicable) icon should be displayed.</td>
 *          <td>N</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 6.0.0</td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 1.0
 * 
 *  
 * @see GetVehicleData
 * @see OnVehicleData 
 * @see Show
 * @see Speak
 */
public class Alert extends RPCRequest {
	public static final String KEY_PLAY_TONE = "playTone";
	public static final String KEY_DURATION = "duration";
	public static final String KEY_ALERT_TEXT_1 = "alertText1";
	public static final String KEY_ALERT_TEXT_2 = "alertText2";
	public static final String KEY_ALERT_TEXT_3 = "alertText3";
    public static final String KEY_PROGRESS_INDICATOR = "progressIndicator";
	public static final String KEY_TTS_CHUNKS = "ttsChunks";
	public static final String KEY_SOFT_BUTTONS = "softButtons";
	public static final String KEY_CANCEL_ID = "cancelID";
	public static final String KEY_ALERT_ICON = "alertIcon";

	/**
	 * Constructs a new Alert object
	 */    
	public Alert() {
        super(FunctionID.ALERT.toString());
    }
	/**
	 * <p>Constructs a new Alert object indicated by the Hashtable parameter</p>
	 * 
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */	
    public Alert(Hashtable<String, Object> hash) {
        super(hash);
    }
	/**
	 * Gets the text which is displayed in the first field of the display during
	 * the Alert
	 * 
	 * @return String - a String value representing the text which is displayed
	 *         in the first field during the Alert
	 */    
    public String getAlertText1() {
        return getString(KEY_ALERT_TEXT_1);
    }
	/**
	 * Sets the String to be displayed in the first field of the display during
	 * the Alert
	 *
	 * @param alertText1
	 *            String Value
	 *
	 *            <p><b>Notes: </b></p>
	 *            <ul>
	 *            <li>Length is limited to what is indicated in <i>
	 *            {@linkplain RegisterAppInterface}</i> response</li>
	 *            <li>If omitted, top display line will be cleared</li>
	 *            <li>Text is always centered</li>
	 *            </ul>
	 */
    public Alert setAlertText1( String alertText1) {
        setParameters(KEY_ALERT_TEXT_1, alertText1);
        return this;
    }
	/**
	 * Gets the text which is displayed in the second field of the display
	 * during the Alert
	 * 
	 * @return String -a String value representing the text which is displayed
	 *         in the second field during the Alert
	 */    
    public String getAlertText2() {
		return getString(KEY_ALERT_TEXT_2);
    }
	/**
	 * Sets the String to be displayed in the second field of the display during
	 * the Alert
	 *
	 * @param alertText2
	 *            String Value
	 *
	 *            <p><b>Notes: </b></p>
	 *            <ul>
	 *            <li>Only permitted if HMI supports a second display line</li>
	 *            <li>Length is limited to what is indicated in <i>
	 *            {@linkplain RegisterAppInterface}</i> response</li>
	 *            <li>If omitted, second display line will be cleared</li>
	 *            <li>Text is always centered</li>
	 *            </ul>
	 */
    public Alert setAlertText2( String alertText2) {
        setParameters(KEY_ALERT_TEXT_2, alertText2);
        return this;
    }

	/**
	 * Gets the text which is displayed in the third field of the display during
	 * the Alert
	 * 
	 * @return String -a String value representing the text which is displayed
	 *         in the third field during the Alert
	 * 
	 * @since SmartDeviceLink 2.0
	 */
    public String getAlertText3() {
		return getString(KEY_ALERT_TEXT_3);
    }

	/**
	 * Sets the String to be displayed in the third field of the display during
	 * the Alert
	 *
	 * @param alertText3
	 *            String Value
	 *
	 *           <p> <b>Notes: </b></p>
	 *            <ul>
	 *            <li>Only permitted if HMI supports a third display line</li>
	 *            <li>Length is limited to what is indicated in <i>
	 *            {@linkplain RegisterAppInterface}</i> response</li>
	 *            <li>If omitted, third display line will be cleared</li>
	 *            <li>Text is always centered</li>
	 *            </ul>
	 *
	 * @since SmartDeviceLink 2.0
	 */
    public Alert setAlertText3( String alertText3) {
        setParameters(KEY_ALERT_TEXT_3, alertText3);
        return this;
    }
	/**
	 * Gets TTSChunk[], the Array of type TTSChunk which, taken together,
	 * specify what is to be spoken to the user
	 * 
	 * @return List -a List<TTSChunk> value specify what is to be spoken to
	 *         the user
	 */    
    @SuppressWarnings("unchecked")
    public List<TTSChunk> getTtsChunks() {
		return (List<TTSChunk>) getObject(TTSChunk.class, KEY_TTS_CHUNKS);
    }
	/**
	 * Sets array of type TTSChunk which, taken together, specify what is to be
	 * spoken to the user
	 *
	 * @param ttsChunks
	 *
	 *           <p> <b>Notes: </b>Array must have a least one element</p>
	 */
    public Alert setTtsChunks( List<TTSChunk> ttsChunks) {
        setParameters(KEY_TTS_CHUNKS, ttsChunks);
        return this;
    }
	/**
	 * Gets the duration of the displayed portion of the alert, in milliseconds
	 * 
	 * @return Integer -an Integer value representing the duration of the
	 *         displayed portion of the alert, in milliseconds
	 */    
    public Integer getDuration() {
		return getInteger(KEY_DURATION);
    }
	/**
	 * <p>Sets the duration of the displayed portion of the alert, in milliseconds.
	 * After this amount of time has passed, the display fields alertText1 and
	 * alertText2 will revert to what was displayed in those fields before the
	 * alert began</p>
	 *
	 *
	 * @param duration
	 *            the Integer values representing the duration time, in
	 *            milliseconds
	 *            <p>
	 *            <b>Notes: </b></p>
	 *            <ul>
	 *            <li>Min Value: 3000; Max Value: 10000</li>
	 *            <li>If omitted, the default is 5000 milliseconds</li>
	 *            </ul>
	 */
    public Alert setDuration( Integer duration) {
        setParameters(KEY_DURATION, duration);
        return this;
    }
	/**
	 * Gets a Boolean value representing the alert tone
	 * 
	 * @return Boolean -If TRUE, means an alert tone will be played before the
	 *         TTS (if any) is spoken
	 */    
    public Boolean getPlayTone() {
		return getBoolean(KEY_PLAY_TONE);
    }
	/**
	 * Sets whether the alert tone should be played before the TTS (if any) is
	 * spoken
	 *
	 * @param playTone
	 *            a Boolean value which specifies whether the alert tone should
	 *            be played before the TTS (if any) is spoken
	 *
	 *           <p> <b>Notes: </b>If omitted, default is true</p>
	 */
    public Alert setPlayTone( Boolean playTone) {
        setParameters(KEY_PLAY_TONE, playTone);
        return this;
    }

	/**
	 * Gets the SoftButton List object
	 * 
	 * @return List<SoftButton> -a List<SoftButton> representing the List
	 *         object
	 * @since SmartDeviceLink 2.0
	 */
    @SuppressWarnings("unchecked")
    public List<SoftButton> getSoftButtons() {
		return (List<SoftButton>) getObject(SoftButton.class, KEY_SOFT_BUTTONS);
    }

	/**
	 * Sets the SoftButtons
	 *
	 * @param softButtons
	 *            a List<SoftButton> value
	 *            <p>
	 *            <b>Notes: </b></p>
	 *            <ul>
	 *            <li>If omitted on supported displays, the alert will not have
	 *            any SoftButton</li>
	 *            <li>ArrayMin: 0</li>
	 *            <li>ArrayMax: 4</li>
	 *            </ul>
	 * @since SmartDeviceLink 2.0
	 */
    public Alert setSoftButtons( List<SoftButton> softButtons) {
        setParameters(KEY_SOFT_BUTTONS, softButtons);
        return this;
    }

	/**
	 * Gets a Boolean value representing the progress indicator
	 *
	 * @return Boolean - If TRUE, the alert GUI will include some sort of animation indicating that loading of a feature is progressing. e.g. a spinning wheel or hourglass, etc.
	 *
	 * @since SmartDeviceLink 3.0
	 */
	public Boolean getProgressIndicator() {
		return getBoolean(KEY_PROGRESS_INDICATOR);
    }

	/**
	 * Sets whether the progress indicator should be shown
	 *
	 * @param progressIndicator A Boolean value which specifies whether the alert GUI will include some sort of animation indicating that loading of a feature is progressing. e.g. a spinning wheel or hourglass, etc.
	 *
	 * @since SmartDeviceLink 3.0
	 */
	public Alert setProgressIndicator( Boolean progressIndicator) {
        setParameters(KEY_PROGRESS_INDICATOR, progressIndicator);
        return this;
    }

	/**
	 * Gets an Integer value representing the cancel ID
	 *
	 * @return Integer - An Integer value representing the ID for this specific alert to allow cancellation through the `CancelInteraction` RPC.
	 *
	 * @since SmartDeviceLink 6.0
	 */
    public Integer getCancelID() {
		return getInteger(KEY_CANCEL_ID);
	}

	/**
	 * Sets the cancel ID
	 *
	 * @param cancelID An Integer ID for this specific alert to allow cancellation through the `CancelInteraction` RPC.
	 *
	 * @since SmartDeviceLink 6.0
	 */
	public Alert setCancelID( Integer cancelID) {
        setParameters(KEY_CANCEL_ID, cancelID);
        return this;
    }
	
	/**
	 * <p>Sets the Image
	 * If provided, defines the image to be shown along with the alert</p>
	 * @param alertIcon
	 *            <p>an Image object representing the Image shown along with the alert</p>
	 *            <p>
	 *            <b>Notes: </b>If omitted on supported displays, no (or the
	 *            default if applicable) icon will be displayed</p>
	 */
	public Alert setAlertIcon( Image alertIcon) {
        setParameters(KEY_ALERT_ICON, alertIcon);
        return this;
    }

	/**
	 * <p>Gets the image to be shown along with the alert </p>
	 * @return Image -an Image object
	 */
	public Image getAlertIcon() {
		return (Image) getObject(Image.class, KEY_ALERT_ICON);
	}
}
