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

public class HMISettingsControlCapabilities extends RPCStruct {
    public static final String KEY_MODULE_NAME = "moduleName";
    public static final String KEY_DISTANCE_UNIT_AVAILABLE = "distanceUnitAvailable";
    public static final String KEY_TEMPERATURE_UNIT_AVAILABLE = "temperatureUnitAvailable";
    public static final String KEY_DISPLAY_MODE_UNIT_AVAILABLE = "displayModeUnitAvailable";
    public static final String KEY_MODULE_INFO = "moduleInfo";

    /**
     * Constructs a new HMISettingsControlCapabilities object
     */
    public HMISettingsControlCapabilities() {
    }

    /**
     * <p>Constructs a new HMISettingsControlCapabilities object indicated by the Hashtable parameter
     * </p>
     *
     * @param hash The Hashtable to use
     */
    public HMISettingsControlCapabilities(Hashtable<String, Object> hash) {
        super(hash);
    }

    public HMISettingsControlCapabilities(@NonNull String moduleName) {
        this();
        setModuleName(moduleName);
    }

    /**
     * Sets the moduleName portion of the HMISettingsControlCapabilities class
     *
     * @param moduleName The short friendly name of the hmi setting module. It should not be used to identify a module by mobile application.
     */
    public HMISettingsControlCapabilities setModuleName(@NonNull String moduleName) {
        setValue(KEY_MODULE_NAME, moduleName);
        return this;
    }

    /**
     * Gets the moduleName portion of the HMISettingsControlCapabilities class
     *
     * @return String - The short friendly name of the hmi setting module. It should not be used to identify a module by mobile application.
     */
    public String getModuleName() {
        return getString(KEY_MODULE_NAME);
    }

    /**
     * Sets the distanceUnitAvailable portion of the HMISettingsControlCapabilities class
     *
     * @param distanceUnitAvailable Availability of the control of distance unit.
     */
    public HMISettingsControlCapabilities setDistanceUnitAvailable(Boolean distanceUnitAvailable) {
        setValue(KEY_DISTANCE_UNIT_AVAILABLE, distanceUnitAvailable);
        return this;
    }

    /**
     * Gets the distanceUnitAvailable portion of the HMISettingsControlCapabilities class
     *
     * @return Boolean - Availability of the control of distance unit.
     */
    public Boolean getDistanceUnitAvailable() {
        return getBoolean(KEY_DISTANCE_UNIT_AVAILABLE);
    }

    /**
     * Sets the temperatureUnitAvailable portion of the HMISettingsControlCapabilities class
     *
     * @param temperatureUnitAvailable Availability of the control of temperature unit.
     */
    public HMISettingsControlCapabilities setTemperatureUnitAvailable(Boolean temperatureUnitAvailable) {
        setValue(KEY_TEMPERATURE_UNIT_AVAILABLE, temperatureUnitAvailable);
        return this;
    }

    /**
     * Gets the temperatureUnitAvailable portion of the HMISettingsControlCapabilities class
     *
     * @return Boolean - Availability of the control of temperature unit.
     */
    public Boolean getTemperatureUnitAvailable() {
        return getBoolean(KEY_TEMPERATURE_UNIT_AVAILABLE);
    }

    /**
     * Sets the displayModeUnitAvailable portion of the HMISettingsControlCapabilities class
     *
     * @param displayModeUnitAvailable Availability of the control of HMI display mode.
     */
    public HMISettingsControlCapabilities setDisplayModeUnitAvailable(Boolean displayModeUnitAvailable) {
        setValue(KEY_DISPLAY_MODE_UNIT_AVAILABLE, displayModeUnitAvailable);
        return this;
    }

    /**
     * Gets the displayModeUnitAvailable portion of the HMISettingsControlCapabilities class
     *
     * @return Boolean - Availability of the control of HMI display mode.
     */
    public Boolean getDisplayModeUnitAvailable() {
        return getBoolean(KEY_DISPLAY_MODE_UNIT_AVAILABLE);
    }

    /**
     * Sets ModuleInfo for this capability
     *
     * @param info the ModuleInfo to be set
     */
    public HMISettingsControlCapabilities setModuleInfo(ModuleInfo info) {
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
