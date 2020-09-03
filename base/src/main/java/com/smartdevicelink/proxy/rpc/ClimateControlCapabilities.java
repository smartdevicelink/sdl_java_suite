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
import com.smartdevicelink.proxy.rpc.enums.DefrostZone;
import com.smartdevicelink.proxy.rpc.enums.VentilationMode;

import java.util.Hashtable;
import java.util.List;

/**
 * Contains information about a climate control module's capabilities.
 */

public class ClimateControlCapabilities extends RPCStruct{
    public static final String KEY_MODULE_NAME= "moduleName";
    public static final String KEY_FAN_SPEED_AVAILABLE= "fanSpeedAvailable";
    public static final String KEY_DESIRED_TEMPERATURE_AVAILABLE= "desiredTemperatureAvailable";
    public static final String KEY_AC_ENABLE_AVAILABLE= "acEnableAvailable";
    public static final String KEY_AC_MAX_ENABLE_AVAILABLE= "acMaxEnableAvailable";
    public static final String KEY_CIRCULATE_AIR_ENABLE_AVAILABLE= "circulateAirEnableAvailable";
    public static final String KEY_AUTO_MODE_ENABLE_AVAILABLE= "autoModeEnableAvailable";
    public static final String KEY_DUAL_MODE_ENABLE_AVAILABLE= "dualModeEnableAvailable";
    public static final String KEY_DEFROST_ZONE_AVAILABLE= "defrostZoneAvailable";
    public static final String KEY_DEFROST_ZONE= "defrostZone";
    public static final String KEY_VENTILATION_MODE_AVAILABLE= "ventilationModeAvailable";
    public static final String KEY_VENTILATION_MODE= "ventilationMode";
    public static final String KEY_HEATED_STEERING_WHEEL_AVAILABLE = "heatedSteeringWheelAvailable";
    public static final String KEY_HEATED_WIND_SHIELD_AVAILABLE = "heatedWindshieldAvailable";
    public static final String KEY_HEATED_REAR_WINDOW_AVAILABLE = "heatedRearWindowAvailable";
    public static final String KEY_HEATED_MIRRORS_AVAILABLE = "heatedMirrorsAvailable";
    public static final String KEY_MODULE_INFO = "moduleInfo";
    public static final String KEY_CLIMATE_ENABLE_AVAILABLE = "climateEnableAvailable";

    public ClimateControlCapabilities() {
    }

    public ClimateControlCapabilities(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a newly allocated ClimateControlCapabilities
	 *
     * @param moduleName The short friendly name of the climate control module. It should not be used to identify a module by mobile application.
     */
    public ClimateControlCapabilities(@NonNull String moduleName) {
        this();
        setModuleName(moduleName);
    }

    /**
     * Sets the moduleName portion of the ClimateControlCapabilities class
     *
     * @param moduleName The short friendly name of the climate control module. It should not be used to identify a module by mobile application.
     */
    public ClimateControlCapabilities setModuleName(@NonNull String moduleName) {
        setValue(KEY_MODULE_NAME, moduleName);
        return this;
    }

    /**
     * Gets the moduleName portion of the ClimateControlCapabilities class
     *
     * @return String - Short friendly name of the climate control module.
     */
    public String getModuleName() {
        return getString(KEY_MODULE_NAME);
    }

    /**
     * Sets the fanSpeedAvailable portion of the ClimateControlCapabilities class
     *
     * @param fanSpeedAvailable
     * Availability of the control of fan speed.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public ClimateControlCapabilities setFanSpeedAvailable( Boolean fanSpeedAvailable) {
        setValue(KEY_FAN_SPEED_AVAILABLE, fanSpeedAvailable);
        return this;
    }

    /**
     * Gets the fanSpeedAvailable portion of the ClimateControlCapabilities class
     *
     * @return Boolean - Availability of the control of fan speed.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getFanSpeedAvailable() {
        return getBoolean(KEY_FAN_SPEED_AVAILABLE);
    }

    /**
     * Sets the desiredTemperatureAvailable portion of the ClimateControlCapabilities class
     *
     * @param desiredTemperatureAvailable
     * Availability of the control of desired temperature.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public ClimateControlCapabilities setDesiredTemperatureAvailable( Boolean desiredTemperatureAvailable) {
        setValue(KEY_DESIRED_TEMPERATURE_AVAILABLE, desiredTemperatureAvailable);
        return this;
    }

    /**
     * Gets the desiredTemperatureAvailable portion of the ClimateControlCapabilities class
     *
     * @return Boolean - Availability of the control of desired temperature.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getDesiredTemperatureAvailable() {
        return getBoolean(KEY_DESIRED_TEMPERATURE_AVAILABLE);
    }

    /**
     * Sets the acEnableAvailable portion of the ClimateControlCapabilities class
     *
     * @param acEnableAvailable
     * Availability of the control of turn on/off AC.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public ClimateControlCapabilities setAcEnableAvailable( Boolean acEnableAvailable) {
        setValue(KEY_AC_ENABLE_AVAILABLE, acEnableAvailable);
        return this;
    }

    /**
     * Gets the acEnableAvailable portion of the ClimateControlCapabilities class
     *
     * @return Boolean - Availability of the control of turn on/off AC.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getAcEnableAvailable() {
        return getBoolean(KEY_AC_ENABLE_AVAILABLE);
    }

    /**
     * Sets the acMaxEnableAvailable portion of the ClimateControlCapabilities class
     *
     * @param acMaxEnableAvailable
     * Availability of the control of enable/disable air conditioning is ON on the maximum level.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public ClimateControlCapabilities setAcMaxEnableAvailable( Boolean acMaxEnableAvailable) {
        setValue(KEY_AC_MAX_ENABLE_AVAILABLE, acMaxEnableAvailable);
        return this;
    }

    /**
     * Gets the acMaxEnableAvailable portion of the ClimateControlCapabilities class
     *
     * @return Boolean - Availability of the control of enable/disable air conditioning is ON on the maximum level.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getAcMaxEnableAvailable() {
        return getBoolean(KEY_AC_MAX_ENABLE_AVAILABLE);
    }

    /**
     * Sets the circulateAirEnableAvailable portion of the ClimateControlCapabilities class
     *
     * @param circulateAirEnableAvailable
     * Availability of the control of enable/disable circulate Air mode.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public ClimateControlCapabilities setCirculateAirEnableAvailable( Boolean circulateAirEnableAvailable) {
        setValue(KEY_CIRCULATE_AIR_ENABLE_AVAILABLE, circulateAirEnableAvailable);
        return this;
    }

    /**
     * Gets the circulateAirEnableAvailable portion of the ClimateControlCapabilities class
     *
     * @return Boolean - Availability of the control of enable/disable circulate Air mode.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getCirculateAirEnableAvailable() {
        return getBoolean(KEY_CIRCULATE_AIR_ENABLE_AVAILABLE);
    }

    /**
     * Sets the autoModeEnableAvailable portion of the ClimateControlCapabilities class
     *
     * @param autoModeEnableAvailable
     * Availability of the control of enable/disable auto mode.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public ClimateControlCapabilities setAutoModeEnableAvailable( Boolean autoModeEnableAvailable) {
        setValue(KEY_AUTO_MODE_ENABLE_AVAILABLE, autoModeEnableAvailable);
        return this;
    }

    /**
     * Gets the autoModeEnableAvailable portion of the ClimateControlCapabilities class
     *
     * @return Boolean - Availability of the control of enable/disable auto mode.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getAutoModeEnableAvailable() {
        return getBoolean(KEY_AUTO_MODE_ENABLE_AVAILABLE);
    }

    /**
     * Sets the dualModeEnableAvailable portion of the ClimateControlCapabilities class
     *
     * @param dualModeEnableAvailable
     * Availability of the control of enable/disable dual mode.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public ClimateControlCapabilities setDualModeEnableAvailable( Boolean dualModeEnableAvailable) {
        setValue(KEY_DUAL_MODE_ENABLE_AVAILABLE, dualModeEnableAvailable);
        return this;
    }

    /**
     * Gets the dualModeEnableAvailable portion of the ClimateControlCapabilities class
     *
     * @return Boolean - Availability of the control of enable/disable dual mode.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getDualModeEnableAvailable() {
        return getBoolean(KEY_DUAL_MODE_ENABLE_AVAILABLE);
    }

    /**
     * Sets the defrostZoneAvailable portion of the ClimateControlCapabilities class
     *
     * @param defrostZoneAvailable
     * Availability of the control of defrost zones.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public ClimateControlCapabilities setDefrostZoneAvailable( Boolean defrostZoneAvailable) {
        setValue(KEY_DEFROST_ZONE_AVAILABLE, defrostZoneAvailable);
        return this;
    }

    /**
     * Gets the defrostZoneAvailable portion of the ClimateControlCapabilities class
     *
     * @return Boolean - Availability of the control of defrost zones.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getDefrostZoneAvailable() {
        return getBoolean(KEY_DEFROST_ZONE_AVAILABLE);
    }

    /**
     * Gets the List<DefrostZone> portion of the ClimateControlCapabilities class
     *
     * @return List<DefrostZone> -  A set of all defrost zones that are controllable.
     */
    public List<DefrostZone> getDefrostZone() {
        return (List<DefrostZone>) getObject(DefrostZone.class, KEY_DEFROST_ZONE);
    }

    /**
     * Sets the defrostZone portion of the ClimateControlCapabilities class
     *
     * @param defrostZone
     * A set of all defrost zones that are controllable.
     */
    public ClimateControlCapabilities setDefrostZone( List<DefrostZone> defrostZone) {
        setValue(KEY_DEFROST_ZONE, defrostZone);
        return this;
    }

    /**
     * Sets the ventilationModeAvailable portion of the ClimateControlCapabilities class
     *
     * @param ventilationModeAvailable
     * Availability of the control of air ventilation mode.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public ClimateControlCapabilities setVentilationModeAvailable( Boolean ventilationModeAvailable) {
        setValue(KEY_VENTILATION_MODE_AVAILABLE, ventilationModeAvailable);
        return this;
    }

    /**
     * Gets the ventilationModeAvailable portion of the ClimateControlCapabilities class
     *
     * @return Boolean - Availability of the control of air ventilation mode.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getVentilationModeAvailable() {
        return getBoolean(KEY_VENTILATION_MODE_AVAILABLE);
    }

    /**
     * Gets the List<VentilationMode> portion of the ClimateControlCapabilities class
     *
     * @return List<VentilationMode> -  A set of all ventilation modes that are controllable.
     */
    public List<VentilationMode> getVentilationMode() {
        return (List<VentilationMode>) getObject(VentilationMode.class, KEY_VENTILATION_MODE);
    }

    /**
     * Sets the ventilationMode portion of the ClimateControlCapabilities class
     *
     * @param ventilationMode
     * A set of all ventilation modes that are controllable.
     */
    public ClimateControlCapabilities setVentilationMode( List<VentilationMode> ventilationMode) {
        setValue(KEY_VENTILATION_MODE, ventilationMode);
        return this;
    }

    /**
     * Sets the heatedSteeringWheelAvailable portion of the ClimateControlCapabilities class
     *
     * @param heatedSteeringWheelAvailable Availability of the control (enable/disable) of heated Steering Wheel.
     *                                     True: Available, False: Not Available, Not present: Not Available.
     */
    public ClimateControlCapabilities setHeatedSteeringWheelAvailable( Boolean heatedSteeringWheelAvailable) {
        setValue(KEY_HEATED_STEERING_WHEEL_AVAILABLE, heatedSteeringWheelAvailable);
        return this;
    }

    /**
     * Gets the heatedSteeringWheelAvailable portion of the ClimateControlCapabilities class
     *
     * @return Boolean - Availability of the control (enable/disable) of heated Steering Wheel.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getHeatedSteeringWheelAvailable() {
        return getBoolean(KEY_HEATED_STEERING_WHEEL_AVAILABLE);
    }

    /**
     * Sets the heatedWindshieldAvailable portion of the ClimateControlCapabilities class
     *
     * @param heatedWindshieldAvailable Availability of the control (enable/disable) of heated Windshield.
     *                                  True: Available, False: Not Available, Not present: Not Available.
     */
    public ClimateControlCapabilities setHeatedWindshieldAvailable( Boolean heatedWindshieldAvailable) {
        setValue(KEY_HEATED_WIND_SHIELD_AVAILABLE, heatedWindshieldAvailable);
        return this;
    }

    /**
     * Gets the heatedWindshieldAvailable portion of the ClimateControlCapabilities class
     *
     * @return Boolean - Availability of the control (enable/disable) of heated Windshield.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getHeatedWindshieldAvailable() {
        return getBoolean(KEY_HEATED_WIND_SHIELD_AVAILABLE);
    }

    /**
     * Sets the heatedRearWindowAvailable portion of the ClimateControlCapabilities class
     *
     * @param heatedRearWindowAvailable Availability of the control (enable/disable) of heated Rear Window.
     *                                  True: Available, False: Not Available, Not present: Not Available.
     */
    public ClimateControlCapabilities setHeatedRearWindowAvailable( Boolean heatedRearWindowAvailable) {
        setValue(KEY_HEATED_REAR_WINDOW_AVAILABLE, heatedRearWindowAvailable);
        return this;
    }

    /**
     * Gets the heatedRearWindowAvailable portion of the ClimateControlCapabilities class
     *
     * @return Boolean - Availability of the control (enable/disable) of heated Rear Window.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getHeatedRearWindowAvailable() {
        return getBoolean(KEY_HEATED_REAR_WINDOW_AVAILABLE);
    }

    /**
     * Sets the heatedMirrorsAvailable portion of the ClimateControlCapabilities class
     *
     * @param heatedMirrorsAvailable Availability of the control (enable/disable) of heated Mirrors.
     *                               True: Available, False: Not Available, Not present: Not Available.
     */
    public ClimateControlCapabilities setHeatedMirrorsAvailable( Boolean heatedMirrorsAvailable) {
        setValue(KEY_HEATED_MIRRORS_AVAILABLE, heatedMirrorsAvailable);
        return this;
    }

    /**
     * Gets the heatedMirrorsAvailable portion of the ClimateControlCapabilities class
     *
     * @return Boolean - Availability of the control (enable/disable) of heated Mirrors.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getHeatedMirrorsAvailable() {
	    return getBoolean(KEY_HEATED_MIRRORS_AVAILABLE);
    }

    /**
     * Sets ModuleInfo for this capability
     * @param info the ModuleInfo to be set
     */
    public ClimateControlCapabilities setModuleInfo( ModuleInfo info) {
        setValue(KEY_MODULE_INFO, info);
        return this;
    }

    /**
     * Gets a ModuleInfo of this capability
     * @return module info of this capability
     */
    public ModuleInfo getModuleInfo() {
        return (ModuleInfo) getObject(ModuleInfo.class, KEY_MODULE_INFO);
    }
    /**
     * Sets the climateEnableAvailable portion of the ClimateControlCapabilities class
     *
     * @param climateEnableAvailable Availability of the control of enable/disable climate control.
     *                               True: Available, False: Not Available, Not present: Not Available.
     */
    public ClimateControlCapabilities setClimateEnableAvailable( Boolean climateEnableAvailable) {
        setValue(KEY_CLIMATE_ENABLE_AVAILABLE, climateEnableAvailable);
        return this;
    }

    /**
     * Gets the climateEnableAvailable portion of the ClimateControlCapabilities class
     *
     * @return Boolean - Availability of the control of enable/disable climate control.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getClimateEnableAvailable() {
        return getBoolean(KEY_CLIMATE_ENABLE_AVAILABLE);
    }
}
