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

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;

import java.util.Hashtable;

/**
 * Struct that indicates the a SystemCapabilityType and houses different structs to describe particular capabilities
 */

public class SystemCapability extends RPCStruct {
    public static final String KEY_SYSTEM_CAPABILITY_TYPE = "systemCapabilityType";
    public static final String KEY_NAVIGATION_CAPABILITY = "navigationCapability";
    public static final String KEY_PHONE_CAPABILITY = "phoneCapability";
    public static final String KEY_VIDEO_STREAMING_CAPABILITY = "videoStreamingCapability";
    public static final String KEY_REMOTE_CONTROL_CAPABILITY = "remoteControlCapability";
    public static final String KEY_APP_SERVICES_CAPABILITIES = "appServicesCapabilities";
    public static final String KEY_SEAT_LOCATION_CAPABILITY = "seatLocationCapability";
    public static final String KEY_DISPLAY_CAPABILITIES = "displayCapabilities";
    public SystemCapability(){}

    public SystemCapability(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Create a systemCapability object
     * @param systemCapabilityType The type
     */
    public SystemCapability(@NonNull SystemCapabilityType systemCapabilityType){
        this();
        setSystemCapabilityType(systemCapabilityType);
    }

    /**
     *
     * @return The SystemCapabilityType that indicates which type of data should be changed and identifies which data object exists in this struct. For example, if the SystemCapability Type is NAVIGATION then a "navigationCapability" should exist
     */
    public SystemCapabilityType getSystemCapabilityType(){
        return (SystemCapabilityType) getObject(SystemCapabilityType.class, KEY_SYSTEM_CAPABILITY_TYPE);
    }

    /**
     * @param value Set the SystemCapabilityType that indicates which type of data should be changed and identifies which data object exists in this struct.
     */
    public void setSystemCapabilityType(@NonNull SystemCapabilityType value){
        setValue(KEY_SYSTEM_CAPABILITY_TYPE, value);
    }

    public Object getCapabilityForType(SystemCapabilityType type) {
        if (type == null) {
            return null;
        } else if (type.equals(SystemCapabilityType.NAVIGATION)) {
            return getObject(NavigationCapability.class, KEY_NAVIGATION_CAPABILITY);
        } else if (type.equals(SystemCapabilityType.PHONE_CALL)) {
            return getObject(PhoneCapability.class, KEY_PHONE_CAPABILITY);
        } else if (type.equals(SystemCapabilityType.VIDEO_STREAMING)) {
            return getObject(VideoStreamingCapability.class, KEY_VIDEO_STREAMING_CAPABILITY);
        } else if (type.equals(SystemCapabilityType.REMOTE_CONTROL)) {
            return getObject(RemoteControlCapabilities.class, KEY_REMOTE_CONTROL_CAPABILITY);
        } else if (type.equals(SystemCapabilityType.APP_SERVICES)) {
            return getObject(AppServicesCapabilities.class, KEY_APP_SERVICES_CAPABILITIES);
        } else if (type.equals(SystemCapabilityType.SEAT_LOCATION)) {
            return getObject(SeatLocationCapability.class, KEY_SEAT_LOCATION_CAPABILITY);
        } else if (type.equals(SystemCapabilityType.DISPLAYS)) {
            return getObject(DisplayCapability.class, KEY_DISPLAY_CAPABILITIES);
        } else {
            return null;
        }
    }

    public void setCapabilityForType(SystemCapabilityType type, Object capability) {
        if (type == null) {
            return;
        } else if (type.equals(SystemCapabilityType.NAVIGATION)) {
            setValue(KEY_NAVIGATION_CAPABILITY, capability);
        } else if (type.equals(SystemCapabilityType.PHONE_CALL)) {
            setValue(KEY_PHONE_CAPABILITY, capability);
        } else if (type.equals(SystemCapabilityType.VIDEO_STREAMING)) {
            setValue(KEY_VIDEO_STREAMING_CAPABILITY, capability);
        } else if (type.equals(SystemCapabilityType.REMOTE_CONTROL)) {
            setValue(KEY_REMOTE_CONTROL_CAPABILITY, capability);
        } else if (type.equals(SystemCapabilityType.APP_SERVICES)) {
            setValue(KEY_APP_SERVICES_CAPABILITIES, capability);
        } else if (type.equals(SystemCapabilityType.SEAT_LOCATION)) {
            setValue(KEY_SEAT_LOCATION_CAPABILITY, capability);
        } else if (type.equals(SystemCapabilityType.DISPLAYS)) {
            setValue(KEY_DISPLAY_CAPABILITIES, capability);
        } else {
            return;
        }
    }

}
