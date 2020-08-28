/*
 * Copyright (c) 2020 Livio, Inc.
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
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
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
 * Shows an alert which typically consists of text-to-speech message and text on the display.
 * At least either alertText1, alertText2 or ttsChunks need to be provided.
 *
 * <p><b>Parameter List</b></p>
 *
 * <table border="1" rules="all">
 *  <tr>
 *      <th>Param Name</th>
 *      <th>Type</th>
 *      <th>Description</th>
 *      <th>Required</th>
 *      <th>Notes</th>
 *      <th>Version Available</th>
 *  </tr>
 *  <tr>
 *      <td>alertText1</td>
 *      <td>String</td>
 *      <td>The first line of the alert text field</td>
 *      <td>N</td>
 *      <td>Max Value: 500</td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>alertText2</td>
 *      <td>String</td>
 *      <td>The second line of the alert text field</td>
 *      <td>N</td>
 *      <td>Max Value: 500</td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>alertIcon</td>
 *      <td>Image</td>
 *      <td>Image to be displayed for the corresponding alert. See Image. If omitted on supported displays, no (or the default if applicable) icon should be displayed.</td>
 *      <td>N</td>
 *      <td></td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>ttsChunks</td>
 *      <td>List<TTSChunk></td>
 *      <td>An array of text chunks of type TTSChunk. See TTSChunk. The array must have at least oneitem.</td>
 *      <td>N</td>
 *      <td>Min Value: 1; Max Value: 100</td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>duration</td>
 *      <td>Integer</td>
 *      <td>Timeout in milliseconds. Typical timeouts are 3-5 seconds. If omitted, timeout is set to5s.</td>
 *      <td>N</td>
 *      <td>Min Value: 3000; Max Value: 10000; Default Value: 5000</td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>softButtons</td>
 *      <td>List<SoftButton></td>
 *      <td>App defined SoftButtons. If omitted on supported displays, the displayed alert shall nothave any SoftButtons.</td>
 *      <td>N</td>
 *      <td>Min Value: 0; Max Value: 2</td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>cancelID</td>
 *      <td>Integer</td>
 *      <td>An ID for this specific alert to allow cancellation through the `CancelInteraction` RPC.</td>
 *      <td>N</td>
 *      <td></td>
 *      <td></td>
 *  </tr>
 * </table>
 *
 * @since SmartDeviceLink 7.0.0
 */
public class SubtleAlert extends RPCRequest {
    public static final String KEY_ALERT_TEXT_1 = "alertText1";
    public static final String KEY_ALERT_TEXT_2 = "alertText2";
    public static final String KEY_ALERT_ICON = "alertIcon";
    public static final String KEY_TTS_CHUNKS = "ttsChunks";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_SOFT_BUTTONS = "softButtons";
    public static final String KEY_CANCEL_ID = "cancelID";

    /**
     * Constructs a new SubtleAlert object
     */
    public SubtleAlert() {
        super(FunctionID.SUBTLE_ALERT.toString());
    }

    /**
     * Constructs a new SubtleAlert object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public SubtleAlert(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Sets the alertText1.
     *
     * @param alertText1 The first line of the alert text field
     */
    public void setAlertText1(String alertText1) {
        setParameters(KEY_ALERT_TEXT_1, alertText1);
    }

    /**
     * Gets the alertText1.
     *
     * @return String The first line of the alert text field
     */
    public String getAlertText1() {
        return getString(KEY_ALERT_TEXT_1);
    }

    /**
     * Sets the alertText2.
     *
     * @param alertText2 The second line of the alert text field
     */
    public void setAlertText2(String alertText2) {
        setParameters(KEY_ALERT_TEXT_2, alertText2);
    }

    /**
     * Gets the alertText2.
     *
     * @return String The second line of the alert text field
     */
    public String getAlertText2() {
        return getString(KEY_ALERT_TEXT_2);
    }

    /**
     * Sets the alertIcon.
     *
     * @param alertIcon Image to be displayed for the corresponding alert. See Image. If omitted on supported
     * displays, no (or the default if applicable) icon should be displayed.
     */
    public void setAlertIcon(Image alertIcon) {
        setParameters(KEY_ALERT_ICON, alertIcon);
    }

    /**
     * Gets the alertIcon.
     *
     * @return Image Image to be displayed for the corresponding alert. See Image. If omitted on supported
     * displays, no (or the default if applicable) icon should be displayed.
     */
    public Image getAlertIcon() {
        return (Image) getObject(Image.class, KEY_ALERT_ICON);
    }

    /**
     * Sets the ttsChunks.
     *
     * @param ttsChunks An array of text chunks of type TTSChunk. See TTSChunk. The array must have at least one
     * item.
     */
    public void setTtsChunks(List<TTSChunk> ttsChunks) {
        setParameters(KEY_TTS_CHUNKS, ttsChunks);
    }

    /**
     * Gets the ttsChunks.
     *
     * @return List<TTSChunk> An array of text chunks of type TTSChunk. See TTSChunk. The array must have at least one
     * item.
     */
    @SuppressWarnings("unchecked")
    public List<TTSChunk> getTtsChunks() {
        return (List<TTSChunk>) getObject(TTSChunk.class, KEY_TTS_CHUNKS);
    }

    /**
     * Sets the duration.
     *
     * @param duration Timeout in milliseconds. Typical timeouts are 3-5 seconds. If omitted, timeout is set to
     * 5s.
     */
    public void setDuration(Integer duration) {
        setParameters(KEY_DURATION, duration);
    }

    /**
     * Gets the duration.
     *
     * @return Integer Timeout in milliseconds. Typical timeouts are 3-5 seconds. If omitted, timeout is set to
     * 5s.
     */
    public Integer getDuration() {
        return getInteger(KEY_DURATION);
    }

    /**
     * Sets the softButtons.
     *
     * @param softButtons App defined SoftButtons. If omitted on supported displays, the displayed alert shall not
     * have any SoftButtons.
     */
    public void setSoftButtons(List<SoftButton> softButtons) {
        setParameters(KEY_SOFT_BUTTONS, softButtons);
    }

    /**
     * Gets the softButtons.
     *
     * @return List<SoftButton> App defined SoftButtons. If omitted on supported displays, the displayed alert shall not
     * have any SoftButtons.
     */
    @SuppressWarnings("unchecked")
    public List<SoftButton> getSoftButtons() {
        return (List<SoftButton>) getObject(SoftButton.class, KEY_SOFT_BUTTONS);
    }

    /**
     * Sets the cancelID.
     *
     * @param cancelID An ID for this specific alert to allow cancellation through the `CancelInteraction` RPC.
     */
    public void setCancelID(Integer cancelID) {
        setParameters(KEY_CANCEL_ID, cancelID);
    }

    /**
     * Gets the cancelID.
     *
     * @return Integer An ID for this specific alert to allow cancellation through the `CancelInteraction` RPC.
     */
    public Integer getCancelID() {
        return getInteger(KEY_CANCEL_ID);
    }
}
