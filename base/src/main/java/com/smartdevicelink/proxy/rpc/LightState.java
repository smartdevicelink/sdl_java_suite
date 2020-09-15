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
import com.smartdevicelink.proxy.rpc.enums.LightStatus;
import com.smartdevicelink.util.SdlDataTypeConverter;

import java.util.Hashtable;

public class LightState extends RPCStruct {
	public static final String KEY_ID = "id";
	public static final String KEY_STATUS = "status";
	public static final String KEY_DENSITY = "density";
	public static final String KEY_COLOR = "color";

	/**
	 * Constructs a new LightState object
	 */
	public LightState() {
	}

	/**
	 * <p>Constructs a new LightState object indicated by the Hashtable parameter
	 * </p>
	 *
	 * @param hash The Hashtable to use
	 */
	public LightState(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Constructs a newly allocated LightState object
	 *
	 * @param id     The name of a light or a group of lights.
	 * @param status Status of Light.
	 */
	public LightState(@NonNull LightName id, @NonNull LightStatus status) {
		this();
		setId(id);
		setStatus(status);
	}

	/**
	 * Sets the id portion of the LightState class
	 *
	 * @param id The name of a light or a group of lights.
	 */
	public LightState setId(@NonNull LightName id) {
        setValue(KEY_ID, id);
        return this;
    }

	/**
	 * Gets the id portion of the LightState class
	 *
	 * @return LightName  - The name of a light or a group of lights.
	 */
	public LightName getId() {
		return (LightName) getObject(LightName.class, KEY_ID);
	}

	/**
	 * Sets the status portion of the LightState class
	 *
	 * @param status enum value of type LightStatus that describes the specific lights state
	 *
	 * @see LightStatus
	 */
	public LightState setStatus(@NonNull LightStatus status) {
        setValue(KEY_STATUS, status);
        return this;
    }

	/**
	 * Gets the status portion of the LightState class
	 *
	 * @return LightStatus enum value that describes the specific lights state
	 *
	 *  @see com.smartdevicelink.proxy.rpc.enums.LightStatus
	 */
	public LightStatus getStatus() {
		return (LightStatus) getObject(LightStatus.class, KEY_STATUS);
	}

	/**
	 * Gets the density portion of the LightState class
	 *
	 * @return a float representation of the density of the specific light state
	 */
	public Float getDensity() {
		Object value = getValue(KEY_DENSITY);
		return SdlDataTypeConverter.objectToFloat(value);
	}

	/**
	 * Sets the density portion of the LightState class
	 *
	 * @param density a float representation of the density of the specific light state
	 */
	public LightState setDensity( Float density) {
        setValue(KEY_DENSITY, density);
        return this;
    }

	/**
	 * Gets the color portion of the LightState class
	 *
	 * @return an RGBColor representation of the color of this specific light state
	 *
	 * @see com.smartdevicelink.proxy.rpc.RGBColor
	 */
	public RGBColor getColor() {
		return (RGBColor) getObject(RGBColor.class, KEY_COLOR);
	}

	/**
	 * Sets the color portion of the LightState class
	 *
	 * @param color an RGBColor representation of the color of this specific light state
	 *
	 * @see RGBColor
	 */
	public LightState setColor( RGBColor color) {
        setValue(KEY_COLOR, color);
        return this;
    }
}
