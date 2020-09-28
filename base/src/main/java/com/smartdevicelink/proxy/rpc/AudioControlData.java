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

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.PrimaryAudioSource;

import java.util.Hashtable;
import java.util.List;

public class AudioControlData extends RPCStruct {
    public static final String KEY_SOURCE = "source";
    public static final String KEY_KEEP_CONTEXT = "keepContext";
    public static final String KEY_VOLUME = "volume";
    public static final String KEY_EQUALIZER_SETTINGS = "equalizerSettings";

    /**
     * Constructs a newly allocated AudioControlData object
     */
    public AudioControlData() {
    }

    /**
     * Constructs a newly allocated AudioControlData object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public AudioControlData(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Sets the source portion of the AudioControlData class
     *
     * @param source In a getter response or a notification, it is the current primary audio source of the system.
     *               In a setter request, it is the target audio source that the system shall switch to.
     *               If the value is MOBILE_APP, the system shall switch to the mobile media app that issues the setter RPC.
     */
    public AudioControlData setSource(PrimaryAudioSource source) {
        setValue(KEY_SOURCE, source);
        return this;
    }

    /**
     * Gets the source portion of the AudioControlData class
     *
     * @return PrimaryAudioSource - In a getter response or a notification, it is the current primary audio source of the system.
     * In a setter request, it is the target audio source that the system shall switch to.
     * If the value is MOBILE_APP, the system shall switch to the mobile media app that issues the setter RPC.
     */
    public PrimaryAudioSource getSource() {
        return (PrimaryAudioSource) getObject(PrimaryAudioSource.class, KEY_SOURCE);
    }

    /**
     * Sets the keepContext portion of the AudioControlData class
     *
     * @param keepContext This parameter shall not be present in any getter responses or notifications.
     *                    This parameter is optional in a setter request. The default value is false if it is not included.
     *                    If it is false, the system not only changes the audio source but also brings the default application or
     *                    system UI associated with the audio source to foreground.
     *                    If it is true, the system only changes the audio source, but keeps the current application in foreground.
     */
    public AudioControlData setKeepContext(Boolean keepContext) {
        setValue(KEY_KEEP_CONTEXT, keepContext);
        return this;
    }

    /**
     * Gets the keepContext portion of the AudioControlData class
     *
     * @return Boolean - This parameter shall not be present in any getter responses or notifications.
     * This parameter is optional in a setter request. The default value is false if it is not included.
     * If it is false, the system not only changes the audio source but also brings the default application or
     * system UI associated with the audio source to foreground.
     * If it is true, the system only changes the audio source, but keeps the current application in foreground.
     */
    public Boolean getKeepContext() {
        return getBoolean(KEY_KEEP_CONTEXT);
    }

    /**
     * Sets the volume portion of the AudioControlData class
     *
     * @param volume Reflects the volume of audio, from 0%-100%.
     */
    public AudioControlData setVolume(Integer volume) {
        setValue(KEY_VOLUME, volume);
        return this;
    }

    /**
     * Gets the volume portion of the AudioControlData class
     *
     * @return Integer - Reflects the volume of audio, from 0%-100%.
     */
    public Integer getVolume() {
        return getInteger(KEY_VOLUME);
    }

    /**
     * Gets the equalizerSettings portion of the AudioControlData class
     *
     * @return List<EqualizerSettings> - Defines the list of supported channels (band) and their current/desired settings on HMI.
     */
    @SuppressWarnings("unchecked")
    public List<EqualizerSettings> getEqualizerSettings() {
        return (List<EqualizerSettings>) getObject(EqualizerSettings.class, KEY_EQUALIZER_SETTINGS);
    }

    /**
     * Sets the equalizerSettings portion of the AudioControlData class
     *
     * @param equalizerSettings Defines the list of supported channels (band) and their current/desired settings on HMI.
     */
    public AudioControlData setEqualizerSettings(List<EqualizerSettings> equalizerSettings) {
        setValue(KEY_EQUALIZER_SETTINGS, equalizerSettings);
        return this;
    }

}
