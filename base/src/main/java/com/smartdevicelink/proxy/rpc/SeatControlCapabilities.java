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

public class SeatControlCapabilities extends RPCStruct {
    public static final String KEY_MODULE_NAME = "moduleName";
    public static final String KEY_HEATING_ENABLED_AVAILABLE = "heatingEnabledAvailable";
    public static final String KEY_COOLING_ENABLED_AVAILABLE = "coolingEnabledAvailable";
    public static final String KEY_HEATING_LEVEL_AVAILABLE = "heatingLevelAvailable";
    public static final String KEY_COOLING_LEVEL_AVAILABLE = "coolingLevelAvailable";
    public static final String KEY_HORIZONTAL_POSITION_AVAILABLE = "horizontalPositionAvailable";
    public static final String KEY_VERTICAL_POSITION_AVAILABLE = "verticalPositionAvailable";
    public static final String KEY_FRONT_VERTICAL_POSITION_AVAILABLE = "frontVerticalPositionAvailable";
    public static final String KEY_BACK_VERTICAL_POSITION_AVAILABLE = "backVerticalPositionAvailable";
    public static final String KEY_BACK_TILT_ANGLE_AVAILABLE = "backTiltAngleAvailable";
    public static final String KEY_HEAD_SUPPORT_HORIZONTAL_POSITION_AVAILABLE = "headSupportHorizontalPositionAvailable";
    public static final String KEY_HEAD_SUPPORT_VERTICAL_POSITION_AVAILABLE = "headSupportVerticalPositionAvailable";
    public static final String KEY_MASSAGE_ENABLED_AVAILABLE = "massageEnabledAvailable";
    public static final String KEY_MASSAGE_MODE_AVAILABLE = "massageModeAvailable";
    public static final String KEY_MASSAGE_CUSHION_FIRMNESS_AVAILABLE = "massageCushionFirmnessAvailable";
    public static final String KEY_MEMORY_AVAILABLE = "memoryAvailable";
    public static final String KEY_MODULE_INFO = "moduleInfo";

    /**
     * Constructs a new SeatControlCapabilities object
     */
    public SeatControlCapabilities() {
    }

    /**
     * <p>Constructs a new SeatControlCapabilities object indicated by the Hashtable parameter
     * </p>
     *
     * @param hash The Hashtable to use
     */
    public SeatControlCapabilities(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a newly allocated SeatControlCapabilities object
     *
     * @param moduleName short friendly name of the light control module.
     */
    public SeatControlCapabilities(@NonNull String moduleName) {
        this();
        setModuleName(moduleName);
    }

    /**
     * Get the moduleName portion of the SeatControlCapabilities class
     *
     * @return String
     */
    public String getModuleName() {
        return getString(KEY_MODULE_NAME);
    }

    /**
     * Sets the moduleName portion of the SeatControlCapabilities class
     *
     * @param moduleName -  The short friendly name of the seat control module. It should not be used to identify a module by mobile application.
     */
    public SeatControlCapabilities setModuleName(@NonNull String moduleName) {
        setValue(KEY_MODULE_NAME, moduleName);
        return this;
    }

    /**
     * Sets the heatingEnabledAvailable portion of the SeatControlCapabilities class
     *
     * @param heatingEnabledAvailable
     */
    public SeatControlCapabilities setHeatingEnabledAvailable(Boolean heatingEnabledAvailable) {
        setValue(KEY_HEATING_ENABLED_AVAILABLE, heatingEnabledAvailable);
        return this;
    }

    /**
     * Gets the heatingEnabledAvailable portion of the SeatControlCapabilities class
     *
     * @return Boolean.
     */
    public Boolean getHeatingEnabledAvailable() {
        return getBoolean(KEY_HEATING_ENABLED_AVAILABLE);
    }

    /**
     * Sets the coolingEnabledAvailable portion of the SeatControlCapabilities class
     *
     * @param coolingEnabledAvailable
     */
    public SeatControlCapabilities setCoolingEnabledAvailable(Boolean coolingEnabledAvailable) {
        setValue(KEY_COOLING_ENABLED_AVAILABLE, coolingEnabledAvailable);
        return this;
    }

    /**
     * Gets the coolingEnabledAvailable portion of the SeatControlCapabilities class
     *
     * @return Boolean.
     */
    public Boolean getCoolingEnabledAvailable() {
        return getBoolean(KEY_COOLING_ENABLED_AVAILABLE);
    }

    /**
     * Sets the heatingLevelAvailable portion of the SeatControlCapabilities class
     *
     * @param heatingLevelAvailable
     */
    public SeatControlCapabilities setHeatingLevelAvailable(Boolean heatingLevelAvailable) {
        setValue(KEY_HEATING_LEVEL_AVAILABLE, heatingLevelAvailable);
        return this;
    }

    /**
     * Gets the heatingLevelAvailable portion of the SeatControlCapabilities class
     *
     * @return Boolean.
     */
    public Boolean getHeatingLevelAvailable() {
        return getBoolean(KEY_HEATING_LEVEL_AVAILABLE);
    }

    /**
     * Sets the coolingLevelAvailable portion of the SeatControlCapabilities class
     *
     * @param coolingLevelAvailable
     */
    public SeatControlCapabilities setCoolingLevelAvailable(Boolean coolingLevelAvailable) {
        setValue(KEY_COOLING_LEVEL_AVAILABLE, coolingLevelAvailable);
        return this;
    }

    /**
     * Gets the coolingLevelAvailable portion of the SeatControlCapabilities class
     *
     * @return Boolean.
     */
    public Boolean getCoolingLevelAvailable() {
        return getBoolean(KEY_COOLING_LEVEL_AVAILABLE);
    }

    /**
     * Sets the horizontalPositionAvailable portion of the SeatControlCapabilities class
     *
     * @param horizontalPositionAvailable
     */
    public SeatControlCapabilities setHorizontalPositionAvailable(Boolean horizontalPositionAvailable) {
        setValue(KEY_HORIZONTAL_POSITION_AVAILABLE, horizontalPositionAvailable);
        return this;
    }

    /**
     * Gets the horizontalPositionAvailable portion of the SeatControlCapabilities class
     *
     * @return Boolean.
     */
    public Boolean getHorizontalPositionAvailable() {
        return getBoolean(KEY_HORIZONTAL_POSITION_AVAILABLE);
    }

    /**
     * Sets the verticalPositionAvailable portion of the SeatControlCapabilities class
     *
     * @param verticalPositionAvailable
     */
    public SeatControlCapabilities setVerticalPositionAvailable(Boolean verticalPositionAvailable) {
        setValue(KEY_VERTICAL_POSITION_AVAILABLE, verticalPositionAvailable);
        return this;
    }

    /**
     * Gets the verticalPositionAvailable portion of the SeatControlCapabilities class
     *
     * @return Boolean.
     */
    public Boolean getVerticalPositionAvailable() {
        return getBoolean(KEY_VERTICAL_POSITION_AVAILABLE);
    }

    /**
     * Sets the frontVerticalPositionAvailable portion of the SeatControlCapabilities class
     *
     * @param frontVerticalPositionAvailable
     */
    public SeatControlCapabilities setFrontVerticalPositionAvailable(Boolean frontVerticalPositionAvailable) {
        setValue(KEY_FRONT_VERTICAL_POSITION_AVAILABLE, frontVerticalPositionAvailable);
        return this;
    }

    /**
     * Gets the frontVerticalPositionAvailable portion of the SeatControlCapabilities class
     *
     * @return Boolean.
     */
    public Boolean getFrontVerticalPositionAvailable() {
        return getBoolean(KEY_FRONT_VERTICAL_POSITION_AVAILABLE);
    }

    /**
     * Sets the backVerticalPositionAvailable portion of the SeatControlCapabilities class
     *
     * @param backVerticalPositionAvailable
     */
    public SeatControlCapabilities setBackVerticalPositionAvailable(Boolean backVerticalPositionAvailable) {
        setValue(KEY_BACK_VERTICAL_POSITION_AVAILABLE, backVerticalPositionAvailable);
        return this;
    }

    /**
     * Gets the backVerticalPositionAvailable portion of the SeatControlCapabilities class
     *
     * @return Boolean.
     */
    public Boolean getBackVerticalPositionAvailable() {
        return getBoolean(KEY_BACK_VERTICAL_POSITION_AVAILABLE);
    }

    /**
     * Sets the backTiltAngleAvailable portion of the SeatControlCapabilities class
     *
     * @param backTiltAngleAvailable
     */
    public SeatControlCapabilities setBackTiltAngleAvailable(Boolean backTiltAngleAvailable) {
        setValue(KEY_BACK_TILT_ANGLE_AVAILABLE, backTiltAngleAvailable);
        return this;
    }

    /**
     * Gets the backTiltAngleAvailable portion of the SeatControlCapabilities class
     *
     * @return Boolean.
     */
    public Boolean getBackTiltAngleAvailable() {
        return getBoolean(KEY_BACK_TILT_ANGLE_AVAILABLE);
    }

    /**
     * Sets the headSupportHorizontalPositionAvailable portion of the SeatControlCapabilities class
     *
     * @param headSupportHorizontalPositionAvailable
     */
    public SeatControlCapabilities setHeadSupportHorizontalPositionAvailable(Boolean headSupportHorizontalPositionAvailable) {
        setValue(KEY_HEAD_SUPPORT_HORIZONTAL_POSITION_AVAILABLE, headSupportHorizontalPositionAvailable);
        return this;
    }

    /**
     * Gets the headSupportHorizontalPositionAvailable portion of the SeatControlCapabilities class
     *
     * @return Boolean.
     */
    public Boolean getHeadSupportHorizontalPositionAvailable() {
        return getBoolean(KEY_HEAD_SUPPORT_HORIZONTAL_POSITION_AVAILABLE);
    }

    /**
     * Sets the headSupportVerticalPositionAvailable portion of the SeatControlCapabilities class
     *
     * @param headSupportVerticalPositionAvailable
     */
    public SeatControlCapabilities setHeadSupportVerticalPositionAvailable(Boolean headSupportVerticalPositionAvailable) {
        setValue(KEY_HEAD_SUPPORT_VERTICAL_POSITION_AVAILABLE, headSupportVerticalPositionAvailable);
        return this;
    }

    /**
     * Gets the headSupportVerticalPositionAvailable portion of the SeatControlCapabilities class
     *
     * @return Boolean.
     */
    public Boolean getHeadSupportVerticalPositionAvailable() {
        return getBoolean(KEY_HEAD_SUPPORT_VERTICAL_POSITION_AVAILABLE);
    }

    /**
     * Sets the massageEnabledAvailable portion of the SeatControlCapabilities class
     *
     * @param massageEnabledAvailable
     */
    public SeatControlCapabilities setMassageEnabledAvailable(Boolean massageEnabledAvailable) {
        setValue(KEY_MASSAGE_ENABLED_AVAILABLE, massageEnabledAvailable);
        return this;
    }

    /**
     * Gets the massageEnabledAvailable portion of the SeatControlCapabilities class
     *
     * @return Boolean.
     */
    public Boolean getMassageEnabledAvailable() {
        return getBoolean(KEY_MASSAGE_ENABLED_AVAILABLE);
    }

    /**
     * Sets the massageModeAvailable portion of the SeatControlCapabilities class
     *
     * @param massageModeAvailable
     */
    public SeatControlCapabilities setMassageModeAvailable(Boolean massageModeAvailable) {
        setValue(KEY_MASSAGE_MODE_AVAILABLE, massageModeAvailable);
        return this;
    }

    /**
     * Gets the massageModeAvailable portion of the SeatControlCapabilities class
     *
     * @return Boolean.
     */
    public Boolean getMassageModeAvailable() {
        return getBoolean(KEY_MASSAGE_MODE_AVAILABLE);
    }

    /**
     * Sets the massageCushionFirmnessAvailable portion of the SeatControlCapabilities class
     *
     * @param massageCushionFirmnessAvailable
     */
    public SeatControlCapabilities setMassageCushionFirmnessAvailable(Boolean massageCushionFirmnessAvailable) {
        setValue(KEY_MASSAGE_CUSHION_FIRMNESS_AVAILABLE, massageCushionFirmnessAvailable);
        return this;
    }

    /**
     * Gets the massageCushionFirmnessAvailable portion of the SeatControlCapabilities class
     *
     * @return Boolean.
     */
    public Boolean getMassageCushionFirmnessAvailable() {
        return getBoolean(KEY_MASSAGE_CUSHION_FIRMNESS_AVAILABLE);
    }

    /**
     * Sets the memoryAvailable portion of the SeatControlCapabilities class
     *
     * @param memoryAvailable
     */
    public SeatControlCapabilities setMemoryAvailable(Boolean memoryAvailable) {
        setValue(KEY_MEMORY_AVAILABLE, memoryAvailable);
        return this;
    }

    /**
     * Gets the memoryAvailable portion of the SeatControlCapabilities class
     *
     * @return Boolean.
     */
    public Boolean getMemoryAvailable() {
        return getBoolean(KEY_MEMORY_AVAILABLE);
    }

    /**
     * Sets ModuleInfo for this capability
     *
     * @param info the ModuleInfo to be set
     */
    public SeatControlCapabilities setModuleInfo(ModuleInfo info) {
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
