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
 * Enumeration listing possible app hmi types.
 *
 * @since SmartDeviceLink 2.0
 */
public enum AppHMIType {
    /**
     * The App will have default rights.
     */
    DEFAULT,
    /**
     * Communication type of App
     */
    COMMUNICATION,
    /**
     * App dealing with Media
     */
    MEDIA,
    /**
     * Messaging App
     */
    MESSAGING,
    /**
     * Navigation App
     */
    NAVIGATION,
    /**
     * Information App
     */
    INFORMATION,
    /**
     * App dealing with social media
     */
    SOCIAL,
    BACKGROUND_PROCESS,
    /**
     * App only for Testing purposes
     */
    TESTING,
    /**
     * Custom App Interfaces
     */
    PROJECTION,
    /**
     * System App
     */
    SYSTEM,
    /**
     * Remote Control
     */
    REMOTE_CONTROL,
    /**
     * Web View
     *
     * @since SmartDeviceLink 7.0.0
     */
    WEB_VIEW;

    /**
     * Convert String to AppHMIType
     *
     * @param value String
     * @return AppHMIType
     */
    public static AppHMIType valueForString(String value) {
        try {
            return valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }
}
