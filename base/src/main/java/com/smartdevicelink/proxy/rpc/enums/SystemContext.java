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

import java.util.EnumSet;

/**
 * Indicates whether or not a user-initiated interaction is in progress, and if
 * so, in what mode (i.e. MENU or VR).
 *
 * @since SmartDeviceLink 1.0
 */
public enum SystemContext {
    /**
     * No user interaction (user-initiated or app-initiated) is in progress.
     *
     * @since SmartDeviceLink 1.0
     */
    SYSCTXT_MAIN("MAIN"),
    /**
     * VR-oriented, user-initiated or app-initiated interaction is in-progress.
     *
     * @since SmartDeviceLink 1.0
     */
    SYSCTXT_VRSESSION("VRSESSION"),
    /**
     * Menu-oriented, user-initiated or app-initiated interaction is
     * in-progress.
     *
     * @since SmartDeviceLink 1.0
     */
    SYSCTXT_MENU("MENU"),
    /**
     * The app's display HMI is currently being obscured by either a system or
     * other app's overlay.
     *
     * @since SmartDeviceLink 2.0
     */
    SYSCTXT_HMI_OBSCURED("HMI_OBSCURED"),
    /**
     * Broadcast only to whichever app has an alert currently being displayed.
     *
     * @since SmartDeviceLink 2.0
     */
    SYSCTXT_ALERT("ALERT");

    private final String VALUE;

    private SystemContext(String value) {
        this.VALUE = value;
    }

    public String toString() {
        return this.VALUE;
    }

    public static SystemContext valueForString(String value) {
        if (value == null) {
            return null;
        }

        for (SystemContext anEnum : EnumSet.allOf(SystemContext.class)) {
            if (anEnum.toString().equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
