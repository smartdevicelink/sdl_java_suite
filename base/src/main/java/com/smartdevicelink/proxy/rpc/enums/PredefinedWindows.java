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
 * The enum PredefinedWindows specifies what windows and IDs are predefined and
 * pre-created on behalf of the app.
 */
public enum PredefinedWindows {
    /**
     * The default window is a main window pre-created on behalf of the app.
     * The default window is always available and represents the app window
     * on the main display. It's an equivalent to today's app window. For
     * backward compatibility, this will ensure the app always has at least
     * the default window on the main display. The app can choose to use this
     * predefined enum element to specifically address app's main window or to
     * duplicate window content. It is not possible to duplicate another
     * window to the default window.
     */
    DEFAULT_WINDOW(0),

    /**
     * The primary widget of the app.
     * The primary widget is a special widget, that can be associated with a
     * service type, which is used by the HMI whenever a single widget needs to
     * represent the whole app. The primary widget should be named as the app
     * and can be pre-created by the HMI.
     */
    PRIMARY_WIDGET(1),
    ;

    private final Integer INTERNAL_NAME;

    private PredefinedLayout(Integer internalName) {
        this.INTERNAL_NAME = internalName;
    }

    /**
     * Returns a PredefinedWindows
     *
     * @param value a String
     * @return PredefinedWindows
     */
    public static PredefinedWindows valueForString(String value) {
        try {
            return valueForInteger(Integer.parseInt(value));
        } catch (NumberFormatException) {
            return null;
        }
    }

    /**
     * Returns a PredefinedWindows
     *
     * @param value an Integer
     * @return PredefinedWindows
     */
    public static PredefinedWindows valueForInteger(Integer value) {
        if (value == null) {
            return null;
        }

        for (PredefinedWindows anEnum : EnumSet.allOf(PredefinedWindows.class)) {
            if (anEnum.INTERNAL_NAME.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}