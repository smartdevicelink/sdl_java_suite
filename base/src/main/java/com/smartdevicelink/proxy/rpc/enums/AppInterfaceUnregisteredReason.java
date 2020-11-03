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
 * Indicates reason why app interface was unregistered. The application is being
 * disconnected by SDL.
 *
 * @since SmartDeviceLink 1.0
 */
public enum AppInterfaceUnregisteredReason {
    USER_EXIT,
    /**
     * Vehicle ignition turned off.
     *
     * @since SmartDeviceLink 1.0
     */
    IGNITION_OFF,
    /**
     * Bluetooth was turned off, causing termination of a necessary Bluetooth
     * connection.
     *
     * @since SmartDeviceLink 1.0
     */
    BLUETOOTH_OFF,
    /**
     * USB was disconnected, causing termination of a necessary iAP connection.
     *
     * @since SmartDeviceLink 1.0
     */
    USB_DISCONNECTED,
    /**
     * Application attempted SmartDeviceLink RPC request while {@linkplain HMILevel}
     * =NONE. App must have HMILevel other than NONE to issue RPC requests or
     * get notifications or RPC responses.
     *
     * @since SmartDeviceLink 1.0
     */
    REQUEST_WHILE_IN_NONE_HMI_LEVEL,
    /**
     * Either too many -- or too many per unit of time -- requests were made by
     * the application.
     *
     * @since SmartDeviceLink 1.0
     */
    TOO_MANY_REQUESTS,
    /**
     * The application has issued requests which cause driver distraction rules
     * to be violated.
     *
     * @since SmartDeviceLink 1.0
     */
    DRIVER_DISTRACTION_VIOLATION,
    /**
     * The user has changed the language in effect on the SDL platform to a
     * language that is incompatible with the language declared by the
     * application in its RegisterAppInterface request.
     *
     * @since SmartDeviceLink 1.0
     */
    LANGUAGE_CHANGE,
    /**
     * The user performed a MASTER RESET on the SDL platform, causing removal
     * of a necessary Bluetooth pairing.
     *
     * @since SmartDeviceLink 1.0
     */
    MASTER_RESET,
    /**
     * The user restored settings to FACTORY DEFAULTS on the SDL platform.
     *
     * @since SmartDeviceLink 1.0
     */
    FACTORY_DEFAULTS,
    /**
     * The app is not being authorized to be connected to SDL.
     *
     * @since SmartDeviceLink 2.0
     */
    APP_UNAUTHORIZED,
    /**
     * The app has committed a protocol violation.
     *
     * @since SmartDeviceLink 4.0
     */
    PROTOCOL_VIOLATION,
    /**
     * The HMI does not support resource.
     *
     * @since SmartDeviceLink 4.1
     */
    UNSUPPORTED_HMI_RESOURCE,


    /**
     * The application is unregistered due to hardware resource constraints.
     * The system will shortly close the application to free up hardware resources
     *
     * @since SmartDeviceLink 7.0
     */
    RESOURCE_CONSTRAINT;

    /**
     * Convert String to AppInterfaceUnregisteredReason
     *
     * @param value String
     * @return AppInterfaceUnregisteredReason
     */
    public static AppInterfaceUnregisteredReason valueForString(String value) {
        try {
            return valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }
}
