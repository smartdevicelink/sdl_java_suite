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

public enum SdlDisconnectedReason {
    USER_EXIT,
    IGNITION_OFF,
    BLUETOOTH_OFF,
    USB_DISCONNECTED,
    REQUEST_WHILE_IN_NONE_HMI_LEVEL,
    TOO_MANY_REQUESTS,
    DRIVER_DISTRACTION_VIOLATION,
    LANGUAGE_CHANGE,
    MASTER_RESET,
    FACTORY_DEFAULTS,
    TRANSPORT_ERROR,
    RESOURCE_CONSTRAINT,
    APPLICATION_REQUESTED_DISCONNECT,
    DEFAULT,
    TRANSPORT_DISCONNECT,
    HB_TIMEOUT,
    BLUETOOTH_DISABLED,
    BLUETOOTH_ADAPTER_ERROR,
    SDL_REGISTRATION_ERROR,
    APP_INTERFACE_UNREG,
    GENERIC_ERROR,
    /**
     * This only occurs when multiplexing is running and it is found to be on an old gen 1 system.
     */
    LEGACY_BLUETOOTH_MODE_ENABLED,
    RPC_SESSION_ENDED,
    PRIMARY_TRANSPORT_CYCLE_REQUEST,
    MINIMUM_PROTOCOL_VERSION_HIGHER_THAN_SUPPORTED,
    MINIMUM_RPC_VERSION_HIGHER_THAN_SUPPORTED,
    ;


    public static SdlDisconnectedReason valueForString(String value) {
        try {
            return valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }

    public static SdlDisconnectedReason convertAppInterfaceUnregisteredReason(AppInterfaceUnregisteredReason reason) {
        if (reason == null) {
            return null;
        }

        SdlDisconnectedReason returnReason = SdlDisconnectedReason.DEFAULT;

        switch (reason) {
            case USER_EXIT:
                returnReason = SdlDisconnectedReason.USER_EXIT;
                break;
            case IGNITION_OFF:
                returnReason = SdlDisconnectedReason.IGNITION_OFF;
                break;
            case BLUETOOTH_OFF:
                returnReason = SdlDisconnectedReason.BLUETOOTH_OFF;
                break;
            case USB_DISCONNECTED:
                returnReason = SdlDisconnectedReason.USB_DISCONNECTED;
                break;
            case REQUEST_WHILE_IN_NONE_HMI_LEVEL:
                returnReason = SdlDisconnectedReason.REQUEST_WHILE_IN_NONE_HMI_LEVEL;
                break;
            case TOO_MANY_REQUESTS:
                returnReason = SdlDisconnectedReason.TOO_MANY_REQUESTS;
                break;
            case DRIVER_DISTRACTION_VIOLATION:
                returnReason = SdlDisconnectedReason.DRIVER_DISTRACTION_VIOLATION;
                break;
            case LANGUAGE_CHANGE:
                returnReason = SdlDisconnectedReason.LANGUAGE_CHANGE;
                break;
            case MASTER_RESET:
                returnReason = SdlDisconnectedReason.MASTER_RESET;
                break;
            case FACTORY_DEFAULTS:
                returnReason = SdlDisconnectedReason.FACTORY_DEFAULTS;
                break;
            case RESOURCE_CONSTRAINT:
                returnReason = SdlDisconnectedReason.RESOURCE_CONSTRAINT;
                break;
            default:
                break;
        }

        return returnReason;
    }
}
