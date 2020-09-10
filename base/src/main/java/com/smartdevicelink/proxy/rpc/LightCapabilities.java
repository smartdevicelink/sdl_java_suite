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
import com.smartdevicelink.proxy.rpc.enums.LightName;

import java.util.Hashtable;

public class LightCapabilities extends RPCStruct {

	public static final String KEY_NAME = "name";
	public static final String KEY_DENSITY_AVAILABLE = "densityAvailable";
	public static final String KEY_RGB_COLOR_SPACE_AVAILABLE = "rgbColorSpaceAvailable";
	public static final String KEY_STATUS_AVAILABLE = "statusAvailable";

	/**
	 * Constructs a newly allocated LightCapabilities object
	 */
	public LightCapabilities() {
	}

	/**
	 * Constructs a newly allocated LightCapabilities object indicated by the Hashtable parameter
	 *
	 * @param hash The Hashtable to use
	 */
	public LightCapabilities(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Constructs a newly allocated LightCapabilities object
	 *
	 * @param name name of Light
	 */
	public LightCapabilities(@NonNull LightName name) {
		this();
		setName(name);
	}

	/**
	 * Sets the name portion of the LightCapabilities class
	 *
	 * @param name enum value of type LightName that describes the specific light
	 *
	 * @see LightName
	 */
	public LightCapabilities setName(@NonNull LightName name) {
        setValue(KEY_NAME, name);
        return this;
    }

	/**
	 * Gets the name portion of the LightCapabilities class
	 *
	 * @return LightName enum value that describes the specific light
	 *
	 *  @see com.smartdevicelink.proxy.rpc.enums.LightName
	 */
	public LightName getName() {
		return (LightName) getObject(LightName.class, KEY_NAME);
	}

	/**
	 * Sets the densityAvailable portion of the LightCapabilities class
	 *
	 * @param densityAvailable Indicates if the light's density can be set remotely (similar to a dimmer).
	 */
	public LightCapabilities setDensityAvailable( Boolean densityAvailable) {
        setValue(KEY_DENSITY_AVAILABLE, densityAvailable);
        return this;
    }

	/**
	 * Gets the densityAvailable portion of the LightCapabilities class
	 *
	 * @return Boolean - Indicates if the light's density can be set remotely (similar to a dimmer).
	 */
	public Boolean getDensityAvailable() {
		return getBoolean(KEY_DENSITY_AVAILABLE);
	}

	/**
	 * Sets the RGBColorSpaceAvailable portion of the LightCapabilities class
	 *
	 * @param RGBColorSpaceAvailable Indicates if the light's color can be set remotely by using the RGB color space.
	 */
	public LightCapabilities setRGBColorSpaceAvailable( Boolean RGBColorSpaceAvailable) {
        setValue(KEY_RGB_COLOR_SPACE_AVAILABLE, RGBColorSpaceAvailable);
        return this;
    }

	/**
	 * Gets the RGBColorSpaceAvailable portion of the LightCapabilities class
	 *
	 * @return Boolean - Indicates if the light's color can be set remotely by using the RGB color space.
	 */
	public Boolean getRGBColorSpaceAvailable() {
		return getBoolean(KEY_RGB_COLOR_SPACE_AVAILABLE);
	}

	/**
	 * Sets the statusAvailable portion of the LightCapabilities class
	 *
	 * @param statusAvailable Indicates if the status (ON/OFF) can be set remotely. App shall not use read-only values (RAMP_UP/RAMP_DOWN/UNKNOWN/INVALID) in a setInteriorVehicleData request.
	 */
	public LightCapabilities setStatusAvailable( Boolean statusAvailable) {
        setValue(KEY_STATUS_AVAILABLE, statusAvailable);
        return this;
    }

	/**
	 * Gets the statusAvailable portion of the LightCapabilities class
	 *
	 * @return Boolean - Indicates if the status (ON/OFF) can be set remotely. App shall not use read-only values (RAMP_UP/RAMP_DOWN/UNKNOWN/INVALID) in a setInteriorVehicleData request.
	 */
	public Boolean getStatusAvailable() {
		return getBoolean(KEY_STATUS_AVAILABLE);
	}
}
