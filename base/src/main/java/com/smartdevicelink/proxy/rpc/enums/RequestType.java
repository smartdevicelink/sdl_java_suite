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
package com.smartdevicelink.proxy.rpc.enums;
/**
 * Enumeration listing possible asynchronous requests.
 * 
 *
 */
public enum RequestType {

	HTTP,
	FILE_RESUME,
	AUTH_REQUEST,
	AUTH_CHALLENGE,
	AUTH_ACK,
	PROPRIETARY,
	/** 
     * @since SmartDeviceLink 4.0
     */
	QUERY_APPS,
	/** 
     * @since SmartDeviceLink 4.0
     */
    LAUNCH_APP,
    /** 
     * @since SmartDeviceLink 4.0
     */
    LOCK_SCREEN_ICON_URL,
    /** 
     * @since SmartDeviceLink 4.0
     */
    TRAFFIC_MESSAGE_CHANNEL,
    /** 
     * @since SmartDeviceLink 4.0
     */
    DRIVER_PROFILE,
    /** 
     * @since SmartDeviceLink 4.0
     */
    VOICE_SEARCH, 
    /** 
     * @since SmartDeviceLink 4.0
     */
    NAVIGATION,
    /** 
     * @since SmartDeviceLink 4.0
     */
    PHONE,
    /** 
     * @since SmartDeviceLink 4.0
     */
    CLIMATE,
    /** 
     * @since SmartDeviceLink 4.0
     */
    SETTINGS,
    /** 
     * @since SmartDeviceLink 4.0
     */
    VEHICLE_DIAGNOSTICS,
    /** 
     * @since SmartDeviceLink 4.0
     */
    EMERGENCY,
    /** 
     * @since SmartDeviceLink 4.0
     */
    MEDIA,
    /** 
     * @since SmartDeviceLink 4.0
     */
    FOTA,
    /**
     * @since SmartDeviceLink 5.0
     */
    OEM_SPECIFIC,
    /**
     * @since SmartDeviceLink 5.1
     */
    ICON_URL,
	;
	/**
     * Convert String to RequestType
     * @param value String
     * @return RequestType
     */  
    public static RequestType valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
