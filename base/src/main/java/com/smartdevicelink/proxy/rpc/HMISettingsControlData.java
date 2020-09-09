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
import com.smartdevicelink.proxy.rpc.enums.DisplayMode;
import com.smartdevicelink.proxy.rpc.enums.DistanceUnit;
import com.smartdevicelink.proxy.rpc.enums.TemperatureUnit;

import java.util.Hashtable;

/**
 * Corresponds to "HMI_SETTINGS" ModuleType
 */

public class HMISettingsControlData extends RPCStruct {
	public static final String KEY_DISPLAY_MODE = "displayMode";
	public static final String KEY_TEMPERATURE_UNIT = "temperatureUnit";
	public static final String KEY_DISTANCE_UNIT = "distanceUnit";

	public HMISettingsControlData() {
	}

	public HMISettingsControlData(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Sets the displayMode portion of the HMISettingsControlData class
	 *
	 * @param displayMode the display mode (DAY, NIGHT, AUTO)  of screen on the respective module
	 *
	 * @see DisplayMode
	 */
	public HMISettingsControlData setDisplayMode( DisplayMode displayMode) {
        setValue(KEY_DISPLAY_MODE, displayMode);
        return this;
    }

	/**
	 * Gets the displayMode portion of the HMISettingsControlData class
	 *
	 * @return DisplayMode the display mode (DAY, NIGHT, AUTO)  of screen on the respective module
	 *
	 * @see com.smartdevicelink.proxy.rpc.enums.DisplayMode
	 */
	public DisplayMode getDisplayMode() {
		return (DisplayMode) getObject(DisplayMode.class, KEY_DISPLAY_MODE);
	}

	/**
	 * Sets the temperatureUnit portion of the HMISettingsControlData class
	 *
	 * @param temperatureUnit enum value of temperature unit associated with the display of the current module
	 *
	 * @see TemperatureUnit
	 */
	public HMISettingsControlData setTemperatureUnit( TemperatureUnit temperatureUnit) {
        setValue(KEY_TEMPERATURE_UNIT, temperatureUnit);
        return this;
    }

	/**
	 * Gets the temperatureUnit portion of the HMISettingsControlData class
	 *
	 * @return TemperatureUnit enum value of temperature unit associated with the display of the current module
	 *
	 * @see com.smartdevicelink.proxy.rpc.enums.TemperatureUnit
	 */
	public TemperatureUnit getTemperatureUnit() {
		return (TemperatureUnit) getObject(TemperatureUnit.class, KEY_TEMPERATURE_UNIT);
	}

	/**
	 * Sets the distanceUnit portion of the HMISettingsControlData class
	 *
	 * @param distanceUnit enum value of distance unit associated with the display of the current module
	 *
	 * @see DistanceUnit
	 */
	public HMISettingsControlData setDistanceUnit( DistanceUnit distanceUnit) {
        setValue(KEY_DISTANCE_UNIT, distanceUnit);
        return this;
    }

	/**
	 * Gets the distanceUnit portion of the HMISettingsControlData class
	 *
	 * @return DistanceUnit enum value of distance unit associated with the display of the current module
	 *
	 * @see com.smartdevicelink.proxy.rpc.enums.DistanceUnit
	 */
	public DistanceUnit getDistanceUnit() {
		return (DistanceUnit) getObject(DistanceUnit.class, KEY_DISTANCE_UNIT);
	}
}
