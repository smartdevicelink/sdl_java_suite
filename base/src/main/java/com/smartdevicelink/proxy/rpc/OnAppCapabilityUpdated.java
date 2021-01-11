/*
 * Copyright (c) 2017 - 2021, SmartDeviceLink Consortium, Inc.
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
 * Neither the name of the SmartDeviceLink Consortium Inc. nor the names of
 * its contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
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

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;

import java.util.Hashtable;

/**
 * A notification to inform SDL Core that a specific app capability has changed.
 *
 * <p><b>Parameter List</b></p>
 *
 * <table border="1" rules="all">
 *  <tr>
 *      <th>Param Name</th>
 *      <th>Type</th>
 *      <th>Description</th>
 *      <th>Required</th>
 *      <th>Notes</th>
 *      <th>Version Available</th>
 *  </tr>
 *  <tr>
 *      <td>appCapability</td>
 *      <td>AppCapability</td>
 *      <td>The app capability that has been updated</td>
 *      <td>Y</td>
 *      <td></td>
 *      <td></td>
 *  </tr>
 * </table>
 *
 * @since SmartDeviceLink 7.1.0
 */
public class OnAppCapabilityUpdated extends RPCNotification {
    public static final String KEY_APP_CAPABILITY = "appCapability";

    /**
     * Constructs a new OnAppCapabilityUpdated object
     */
    public OnAppCapabilityUpdated() {
        super(FunctionID.ON_APP_CAPABILITY_UPDATED.toString());
    }

    /**
     * Constructs a new OnAppCapabilityUpdated object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public OnAppCapabilityUpdated(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new OnAppCapabilityUpdated object
     *
     * @param appCapability The app capability that has been updated
     */
    public OnAppCapabilityUpdated(@NonNull AppCapability appCapability) {
        this();
        setAppCapability(appCapability);
    }

    /**
     * Sets the appCapability.
     *
     * @param appCapability The app capability that has been updated
     */
    public OnAppCapabilityUpdated setAppCapability(@NonNull AppCapability appCapability) {
        setParameters(KEY_APP_CAPABILITY, appCapability);
        return this;
    }

    /**
     * Gets the appCapability.
     *
     * @return AppCapability The app capability that has been updated
     */
    public AppCapability getAppCapability() {
        return (AppCapability) getObject(AppCapability.class, KEY_APP_CAPABILITY);
    }
}
