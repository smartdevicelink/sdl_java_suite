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

public class AudioControlCapabilities extends RPCStruct {
    public static final String KEY_MODULE_NAME = "moduleName";
    public static final String KEY_SOURCE_AVAILABLE = "sourceAvailable";
    public static final String KEY_KEEP_CONTEXT_AVAILABLE = "keepContextAvailable";
    public static final String KEY_VOLUME_AVAILABLE = "volumeAvailable";
    public static final String KEY_EQUALIZER_AVAILABLE = "equalizerAvailable";
    public static final String KEY_EQUALIZER_MAX_CHANNEL_ID = "equalizerMaxChannelId";
    public static final String KEY_MODULE_INFO = "moduleInfo";

    /**
     * Constructs a newly allocated AudioControlCapabilities object
     */
    public AudioControlCapabilities() {
    }

    /**
     * Constructs a newly allocated AudioControlCapabilities object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public AudioControlCapabilities(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a newly allocated AudioControlCapabilities object
     *
     * @param moduleName short friendly name of the light control module.
     */
    public AudioControlCapabilities(@NonNull String moduleName) {
        this();
        setModuleName(moduleName);
    }

    /**
     * Sets the moduleName portion of the AudioControlCapabilities class
     *
     * @param moduleName The short friendly name of the light control module. It should not be used to identify a module by mobile application.
     */
    public AudioControlCapabilities setModuleName(@NonNull String moduleName) {
        setValue(KEY_MODULE_NAME, moduleName);
        return this;
    }

    /**
     * Gets the moduleName portion of the AudioControlCapabilities class
     *
     * @return String - The short friendly name of the light control module. It should not be used to identify a module by mobile application.
     */
    public String getModuleName() {
        return getString(KEY_MODULE_NAME);
    }

    /**
     * Sets the keepContextAvailable portion of the AudioControlCapabilities class
     *
     * @param keepContextAvailable Availability of the keepContext parameter.
     */
    public AudioControlCapabilities setKeepContextAvailable(Boolean keepContextAvailable) {
        setValue(KEY_KEEP_CONTEXT_AVAILABLE, keepContextAvailable);
        return this;
    }

    /**
     * Gets the keepContextAvailable portion of the AudioControlCapabilities class
     *
     * @return Boolean - Availability of the keepContext parameter.
     */
    public Boolean getKeepContextAvailable() {
        return getBoolean(KEY_KEEP_CONTEXT_AVAILABLE);
    }

    /**
     * Sets the sourceAvailable portion of the AudioControlCapabilities class
     *
     * @param sourceAvailable Availability of the control of audio source.
     */
    public AudioControlCapabilities setSourceAvailable(Boolean sourceAvailable) {
        setValue(KEY_SOURCE_AVAILABLE, sourceAvailable);
        return this;
    }

    /**
     * Gets the sourceAvailable portion of the AudioControlCapabilities class
     *
     * @return Boolean - Availability of the control of audio source.
     */
    public Boolean getSourceAvailable() {
        return getBoolean(KEY_SOURCE_AVAILABLE);
    }

    /**
     * Sets the volumeAvailable portion of the AudioControlCapabilities class
     *
     * @param volumeAvailable Availability of the control of audio volume.
     */
    public AudioControlCapabilities setVolumeAvailable(Boolean volumeAvailable) {
        setValue(KEY_VOLUME_AVAILABLE, volumeAvailable);
        return this;
    }

    /**
     * Gets the volumeAvailable portion of the AudioControlCapabilities class
     *
     * @return Boolean - Availability of the control of audio volume.
     */
    public Boolean getVolumeAvailable() {
        return getBoolean(KEY_VOLUME_AVAILABLE);
    }

    /**
     * Sets the equalizerAvailable portion of the AudioControlCapabilities class
     *
     * @param equalizerAvailable Availability of the control of Equalizer Settings.
     */
    public AudioControlCapabilities setEqualizerAvailable(Boolean equalizerAvailable) {
        setValue(KEY_EQUALIZER_AVAILABLE, equalizerAvailable);
        return this;
    }

    /**
     * Gets the equalizerAvailable portion of the AudioControlCapabilities class
     *
     * @return Boolean - Availability of the control of Equalizer Settings.
     */
    public Boolean getEqualizerAvailable() {
        return getBoolean(KEY_EQUALIZER_AVAILABLE);
    }

    /**
     * Sets the equalizerMaxChannelId portion of the AudioControlCapabilities class
     *
     * @param equalizerMaxChannelId Must be included if equalizerAvailable=true, and assume all IDs starting from 1 to this value are valid.
     */
    public AudioControlCapabilities setEqualizerMaxChannelId(Integer equalizerMaxChannelId) {
        setValue(KEY_EQUALIZER_MAX_CHANNEL_ID, equalizerMaxChannelId);
        return this;
    }

    /**
     * Gets the equalizerMaxChannelId portion of the AudioControlCapabilities class
     *
     * @return Integer - Must be included if equalizerAvailable=true, and assume all IDs starting from 1 to this value are valid.
     */
    public Integer getEqualizerMaxChannelId() {
        return getInteger(KEY_EQUALIZER_MAX_CHANNEL_ID);
    }

    /**
     * Sets ModuleInfo for this capability
     *
     * @param info the ModuleInfo to be set
     */
    public AudioControlCapabilities setModuleInfo(ModuleInfo info) {
        setValue(KEY_MODULE_INFO, info);
        return this;
    }

    /**
     * Gets a ModuleInfo of this capability
     *
     * @return module info of this capability
     */
    public ModuleInfo getModuleInfo() {
        return (ModuleInfo) getObject(ModuleInfo.class, KEY_MODULE_INFO);
    }
}
