/*
 * Copyright (c) 2017 - 2020, SmartDeviceLink Consortium, Inc.
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

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

/**
 * Describes the status of a window of a door/liftgate etc.
 *
 * <p><b>Parameter List</b></p>
 *
 * <table border="1" rules="all">
 *  <tr>
 *      <th>Param Name</th>
 *      <th>Type</th>
 *      <th>Description</th>
 *      <th>Required</th>
 *      <th>Version Available</th>
 *  </tr>
 *  <tr>
 *      <td>location</td>
 *      <td>Grid</td>
 *      <td></td>
 *      <td>Y</td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>state</td>
 *      <td>WindowState</td>
 *      <td></td>
 *      <td>Y</td>
 *      <td></td>
 *  </tr>
 * </table>
 *
 * @since SmartDeviceLink 7.0.0
 */
public class WindowStatus extends RPCStruct {
    public static final String KEY_LOCATION = "location";
    public static final String KEY_STATE = "state";

    /**
     * Constructs a new WindowStatus object
     */
    public WindowStatus() { }

    /**
     * Constructs a new WindowStatus object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public WindowStatus(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new WindowStatus object
     *
     * @param location
     * @param state
     */
    public WindowStatus(@NonNull Grid location, @NonNull WindowState state) {
        this();
        setLocation(location);
        setState(state);
    }

    /**
     * Sets the location.
     *
     * @param location
     */
    public void setLocation(@NonNull Grid location) {
        setValue(KEY_LOCATION, location);
    }

    /**
     * Gets the location.
     *
     * @return Grid
     */
    public Grid getLocation() {
        return (Grid) getObject(Grid.class, KEY_LOCATION);
    }

    /**
     * Sets the state.
     *
     * @param state
     */
    public void setState(@NonNull WindowState state) {
        setValue(KEY_STATE, state);
    }

    /**
     * Gets the state.
     *
     * @return WindowState
     */
    public WindowState getState() {
        return (WindowState) getObject(WindowState.class, KEY_STATE);
    }
}
