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
import com.smartdevicelink.proxy.rpc.enums.SupportedSeat;

import java.util.Hashtable;
import java.util.List;

/**
 * Seat control data corresponds to "SEAT" ModuleType.
 */
public class SeatControlData extends RPCStruct {
    public static final String KEY_ID = "id";
    public static final String KEY_HEATING_ENABLED = "heatingEnabled";
    public static final String KEY_COOLING_ENABLED = "coolingEnabled";
    public static final String KEY_HEATING_LEVEL = "heatingLevel";
    public static final String KEY_COOLING_LEVEL = "coolingLevel";
    public static final String KEY_HORIZONTAL_POSITION = "horizontalPosition";
    public static final String KEY_VERTICAL_POSITION = "verticalPosition";
    public static final String KEY_FRONT_VERTICAL_POSITION = "frontVerticalPosition";
    public static final String KEY_BACK_VERTICAL_POSITION = "backVerticalPosition";
    public static final String KEY_BACK_TILT_ANGLE = "backTiltAngle";
    public static final String KEY_HEAD_SUPPORT_HORIZONTAL_POSITION = "headSupportHorizontalPosition";
    public static final String KEY_HEAD_SUPPORT_VERTICAL_POSITION = "headSupportVerticalPosition";
    public static final String KEY_MASSAGE_ENABLED = "massageEnabled";
    public static final String KEY_MASSAGE_MODE = "massageMode";
    public static final String KEY_MASSAGE_CUSHION_FIRMNESS = "massageCushionFirmness";
    public static final String KEY_MEMORY = "memory";

    /**
     * Constructs a new SeatControlData object
     */
    public SeatControlData() {
    }

    /**
     * <p>Constructs a new SeatControlData object indicated by the Hashtable parameter
     * </p>
     *
     * @param hash The Hashtable to use
     */
    public SeatControlData(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a newly allocated SeatControlData object
     *
     * @param id type of SupportedSeat.
     */
    public SeatControlData(@NonNull SupportedSeat id) {
        this();
        setId(id);
    }

    /**
     * Sets the id portion of the SeatControlData class
     *
     * @param id
     */
    public SeatControlData setId(@NonNull SupportedSeat id) {
        setValue(KEY_ID, id);
        return this;
    }

    /**
     * Gets the id portion of the SeatControlData class
     *
     * @return SupportedSeat.
     */
    public SupportedSeat getId() {
        return (SupportedSeat) getObject(SupportedSeat.class, KEY_ID);
    }

    /**
     * Sets the heatingEnabled portion of the SeatControlData class
     *
     * @param heatingEnabled
     */
    public SeatControlData setHeatingEnabled(Boolean heatingEnabled) {
        setValue(KEY_HEATING_ENABLED, heatingEnabled);
        return this;
    }

    /**
     * Gets the heatingEnabled portion of the SeatControlData class
     *
     * @return Boolean.
     */
    public Boolean getHeatingEnabled() {
        return getBoolean(KEY_HEATING_ENABLED);
    }

    /**
     * Sets the coolingEnabled portion of the SeatControlData class
     *
     * @param coolingEnabled
     */
    public SeatControlData setCoolingEnabled(Boolean coolingEnabled) {
        setValue(KEY_COOLING_ENABLED, coolingEnabled);
        return this;
    }

    /**
     * Gets the coolingEnabled portion of the SeatControlData class
     *
     * @return Boolean.
     */
    public Boolean getCoolingEnabled() {
        return getBoolean(KEY_COOLING_ENABLED);
    }

    /**
     * Sets the heatingLevel portion of the SeatControlData class
     *
     * @param heatingLevel
     */
    public SeatControlData setHeatingLevel(Integer heatingLevel) {
        setValue(KEY_HEATING_LEVEL, heatingLevel);
        return this;
    }

    /**
     * Gets the heatingLevel portion of the SeatControlData class
     *
     * @return Integer
     */
    public Integer getHeatingLevel() {
        return getInteger(KEY_HEATING_LEVEL);
    }

    /**
     * Sets the coolingLevel portion of the SeatControlData class
     *
     * @param coolingLevel
     */
    public SeatControlData setCoolingLevel(Integer coolingLevel) {
        setValue(KEY_COOLING_LEVEL, coolingLevel);
        return this;
    }

    /**
     * Gets the coolingLevel portion of the SeatControlData class
     *
     * @return Integer
     */
    public Integer getCoolingLevel() {
        return getInteger(KEY_COOLING_LEVEL);
    }

    /**
     * Sets the horizontalPosition portion of the SeatControlData class
     *
     * @param horizontalPosition
     */
    public SeatControlData setHorizontalPosition(Integer horizontalPosition) {
        setValue(KEY_HORIZONTAL_POSITION, horizontalPosition);
        return this;
    }

    /**
     * Gets the horizontalPosition portion of the SeatControlData class
     *
     * @return Integer
     */
    public Integer getHorizontalPosition() {
        return getInteger(KEY_HORIZONTAL_POSITION);
    }

    /**
     * Sets the verticalPosition portion of the SeatControlData class
     *
     * @param verticalPosition
     */
    public SeatControlData setVerticalPosition(Integer verticalPosition) {
        setValue(KEY_VERTICAL_POSITION, verticalPosition);
        return this;
    }

    /**
     * Gets the verticalPosition portion of the SeatControlData class
     *
     * @return Integer
     */
    public Integer getVerticalPosition() {
        return getInteger(KEY_VERTICAL_POSITION);
    }

    /**
     * Sets the frontVerticalPosition portion of the SeatControlData class
     *
     * @param frontVerticalPosition
     */
    public SeatControlData setFrontVerticalPosition(Integer frontVerticalPosition) {
        setValue(KEY_FRONT_VERTICAL_POSITION, frontVerticalPosition);
        return this;
    }

    /**
     * Gets the frontVerticalPosition portion of the SeatControlData class
     *
     * @return Integer
     */
    public Integer getFrontVerticalPosition() {
        return getInteger(KEY_FRONT_VERTICAL_POSITION);
    }

    /**
     * Sets the backVerticalPosition portion of the SeatControlData class
     *
     * @param backVerticalPosition
     */
    public SeatControlData setBackVerticalPosition(Integer backVerticalPosition) {
        setValue(KEY_BACK_VERTICAL_POSITION, backVerticalPosition);
        return this;
    }

    /**
     * Gets the backVerticalPosition portion of the SeatControlData class
     *
     * @return Integer
     */
    public Integer getBackVerticalPosition() {
        return getInteger(KEY_BACK_VERTICAL_POSITION);
    }

    /**
     * Sets the backTiltAngle portion of the SeatControlData class
     *
     * @param backTiltAngle
     */
    public SeatControlData setBackTiltAngle(Integer backTiltAngle) {
        setValue(KEY_BACK_TILT_ANGLE, backTiltAngle);
        return this;
    }

    /**
     * Gets the backTiltAngle portion of the SeatControlData class
     *
     * @return Integer
     */
    public Integer getBackTiltAngle() {
        return getInteger(KEY_BACK_TILT_ANGLE);
    }

    /**
     * Sets the headSupportHorizontalPosition portion of the SeatControlData class
     *
     * @param headSupportHorizontalPosition
     */
    public SeatControlData setHeadSupportHorizontalPosition(Integer headSupportHorizontalPosition) {
        setValue(KEY_HEAD_SUPPORT_HORIZONTAL_POSITION, headSupportHorizontalPosition);
        return this;
    }

    /**
     * Gets the headSupportHorizontalPosition portion of the SeatControlData class
     *
     * @return Integer
     */
    public Integer getHeadSupportHorizontalPosition() {
        return getInteger(KEY_HEAD_SUPPORT_HORIZONTAL_POSITION);
    }

    /**
     * Sets the headSupportVerticalPosition portion of the SeatControlData class
     *
     * @param headSupportVerticalPosition
     */
    public SeatControlData setHeadSupportVerticalPosition(Integer headSupportVerticalPosition) {
        setValue(KEY_HEAD_SUPPORT_VERTICAL_POSITION, headSupportVerticalPosition);
        return this;
    }

    /**
     * Gets the headSupportVerticalPosition portion of the SeatControlData class
     *
     * @return Integer
     */
    public Integer getHeadSupportVerticalPosition() {
        return getInteger(KEY_HEAD_SUPPORT_VERTICAL_POSITION);
    }

    /**
     * Sets the massageEnabled portion of the SeatControlData class
     *
     * @param massageEnabled
     */
    public SeatControlData setMassageEnabled(Boolean massageEnabled) {
        setValue(KEY_MASSAGE_ENABLED, massageEnabled);
        return this;
    }

    /**
     * Gets the massageEnabled portion of the SeatControlData class
     *
     * @return Boolean.
     */
    public Boolean getMassageEnabled() {
        return getBoolean(KEY_MASSAGE_ENABLED);
    }

    /**
     * Gets the List<MassageModeData> portion of the SeatControlData class
     *
     * @return List<MassageModeData>.
     */
    @SuppressWarnings("unchecked")
    public List<MassageModeData> getMassageMode() {
        return (List<MassageModeData>) getObject(MassageModeData.class, KEY_MASSAGE_MODE);
    }

    /**
     * Sets the massageMode portion of the SeatControlData class
     *
     * @param massageMode
     */
    public SeatControlData setMassageMode(List<MassageModeData> massageMode) {
        setValue(KEY_MASSAGE_MODE, massageMode);
        return this;
    }

    /**
     * Gets the List<MassageCushionFirmness> portion of the SeatControlData class
     *
     * @return List<MassageCushionFirmness>.
     */
    @SuppressWarnings("unchecked")
    public List<MassageCushionFirmness> getMassageCushionFirmness() {
        return (List<MassageCushionFirmness>) getObject(MassageCushionFirmness.class, KEY_MASSAGE_CUSHION_FIRMNESS);
    }

    /**
     * Sets the massageCushionFirmness portion of the SeatControlData class
     *
     * @param massageCushionFirmness
     */
    public SeatControlData setMassageCushionFirmness(List<MassageCushionFirmness> massageCushionFirmness) {
        setValue(KEY_MASSAGE_CUSHION_FIRMNESS, massageCushionFirmness);
        return this;
    }

    /**
     * Sets the memory portion of the SeatControlData class
     *
     * @param memory
     */
    public SeatControlData setMemory(SeatMemoryAction memory) {
        setValue(KEY_MEMORY, memory);
        return this;
    }

    /**
     * Gets the memory portion of the SeatControlData class
     *
     * @return SeatMemoryAction.
     */
    public SeatMemoryAction getMemory() {
        return (SeatMemoryAction) getObject(SeatMemoryAction.class, KEY_MEMORY);
    }
}
