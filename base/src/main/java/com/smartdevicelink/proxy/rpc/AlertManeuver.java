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
 * <p>This will bring up an alert with information related to the next navigation maneuver including potential voice
 * navigation instructions. Shown information will be taken from the ShowConstantTBT function
 * </p>
 * <p>Function Group: Navigation</p>
 *
 * <p><b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b></p>
 *
 * @see ShowConstantTbt
 * @since SmartDeviceLink 2.0
 */
public class AlertManeuver extends RPCRequest {

    public static final String KEY_TTS_CHUNKS = "ttsChunks";
    public static final String KEY_SOFT_BUTTONS = "softButtons";

    /**
     * Constructs a new AlertManeuver object
     */
    public AlertManeuver() {
        super(FunctionID.ALERT_MANEUVER.toString());
    }

    /**
     * <p>Constructs a new AlertManeuver object indicated by the Hashtable parameter</p>
     *
     * @param hash The Hashtable to use
     */
    public AlertManeuver(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Gets the SoftButton List object
     *
     * @return List<SoftButton> -a List<SoftButton> representing the List object
     * @since SmartDeviceLink 2.0
     */
    @SuppressWarnings("unchecked")
    public List<SoftButton> getSoftButtons() {
        return (List<SoftButton>) getObject(SoftButton.class, KEY_SOFT_BUTTONS);
    }

    /**
     * Sets the SoftButtons
     *
     * @param softButtons a List<SoftButton> value
     *                    <p>
     *                    <b>Notes: </b></p>
     *                    <ul>
     *                    <li>If omitted on supported displays, the alert will not have any SoftButton</li>
     *                    <li>ArrayMin: 0</li>
     *                    <li>ArrayMax: 4</li>
     *                    </ul>
     * @since SmartDeviceLink 2.0
     */

    public AlertManeuver setSoftButtons(List<SoftButton> softButtons) {
        setParameters(KEY_SOFT_BUTTONS, softButtons);
        return this;
    }

    /**
     * Gets TTSChunk[], the Array of type TTSChunk which, taken together, specify what is to be spoken to the user
     *
     * @return List -a List<TTSChunk> value specify what is to be spoken to the user
     */
    @SuppressWarnings("unchecked")
    public List<TTSChunk> getTtsChunks() {
        return (List<TTSChunk>) getObject(TTSChunk.class, KEY_TTS_CHUNKS);
    }

    /**
     * Sets array of type TTSChunk which, taken together, specify what is to be spoken to the user
     *
     * @param ttsChunks <p>
     *                  <b>Notes: </b></p>Array must have a least one element
     */
    public AlertManeuver setTtsChunks(List<TTSChunk> ttsChunks) {
        setParameters(KEY_TTS_CHUNKS, ttsChunks);
        return this;
    }

}
